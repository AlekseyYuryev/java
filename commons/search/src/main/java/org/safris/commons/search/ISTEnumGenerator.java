/* Copyright (c) 2014 Seva Safris
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.safris.commons.search;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.safris.commons.cli.Options;
import org.safris.commons.io.Files;
import org.safris.commons.lang.Resources;

public class ISTEnumGenerator {
  private static class Word {
    public final String name;
    public final int[][] tree;

    public Word(final String name) {
      this.name = name;
      this.tree = new int[name.length() + 1][];
    }

    @Override
    public String toString() {
      return name;
    }
  }

  public static void main(final String[] args) throws Exception {
    main(Options.parse(Resources.getResource("cli.xml").getURL(), ISTEnumGenerator.class, args));
  }

  public static void main(final Options options) throws Exception {
    final String className = options.getOption("className");
    final File outDir = new File(options.getOption("dir"));
    final File outFile = new File(outDir, className.replace('.', '/') + ".java");

    final String inheritsFrom = options.getOption("inheritsFrom");

    final File tokensFile = new File(options.getOption("file"));
    ISTEnumGenerator.generate(className, inheritsFrom, outFile, tokensFile);
  }

  public static void generate(final String className, final String inheritsFrom, final File outFile, final File tokensFile) throws IOException {
    final File parentFile = outFile.getParentFile();
    if (!parentFile.exists())
      if (!parentFile.mkdirs())
        throw new Error("Unable to create output path: " + parentFile.getAbsolutePath());

    String in = new String(Files.getBytes(tokensFile));
    in = in.replaceAll("([ \t\n\r\f]){2,}", " ");
    final String[] tokens = in.split("[ \t\n\r\f]");
    ISTEnumGenerator.generate(className, inheritsFrom, outFile, tokens);
  }

  public static void generate(final String className, final String inheritsFrom, final File outFile, final String[] tokens) throws IOException {
    final ISTEnumGenerator generator = new ISTEnumGenerator(className, inheritsFrom, tokens);
    generator.print(outFile);
  }

  private final String pkg;
  private final String enumName;
  private final String inheritsFrom;
  private final Word[] words;

  private ISTEnumGenerator(final String className, final String inheritsFrom, final String[] tokens) {
    final int lastDot = className.lastIndexOf('.');
    this.pkg = className.substring(0, lastDot);
    this.enumName = className.substring(lastDot + 1);
    this.inheritsFrom = inheritsFrom;
    this.words = new Word[tokens.length];
    Arrays.sort(tokens);
    for (int i = 0; i < tokens.length; i++)
      words[i] = new Word(tokens[i]);

    root = new int[tokens.length];
    for (int i = 0; i < root.length; i++)
      root[i] = i;

    init(root, 0);
  }

  protected final int[] root;

  protected void init(final int[] keywords, final int depth) {
    traverse(keywords, depth);
    for (final int keyword : keywords) {
      final int[][] tree = words[keyword].tree;
      if (tree[depth] != null)
        init(tree[depth], depth + 1);
    }
  }

  private void traverse(final int[] keywords, final int depth) {
    if (keywords.length <= 1)
      return;

    int l = 0;
    while (l < keywords.length) {
      final String name = words[keywords[l]].name;
      final int[] indices = recurse(keywords, l, depth < name.length() ? name.charAt(depth) : '\0', depth, 0);
      if (indices == null)
        break;

      for (final int index : indices)
        words[index].tree[depth] = indices;

      l += indices.length;
    }
  }

  private int[] recurse(final int[] keywords, final int index, final char ch, final int depth, final int size) {
    final String name = words[keywords[index]].name;
    if (name.length() <= depth || ch != name.charAt(depth))
      return 0 < size ? new int[size] : null;

    final int[] array = index + 1 < keywords.length ? recurse(keywords, index + 1, ch, depth, size + 1) : new int[size + 1];
    array[size] = keywords[index];
    return array;
  }

  public void print(final File file) throws IOException {
    String outer = "";
    for (final Word word : words) {
      String x = "";
      for (int i = 0; i < word.tree.length; i++) {
        String y = "";
        if (word.tree[i] != null)
          for (int j = 0; j < word.tree[i].length; j++)
            y += ", " + word.tree[i][j];

        if (y.length() >= 2)
          x += ", {" + y.substring(2) + "}";
      }

      outer += ",\n  " + word.toString().toUpperCase() + "(\"" + word.name + "\", new int[][] {" + x.substring(2) + "})";
    }

    String code = "package " + pkg + ";\n\npublic enum " + enumName;
    code += inheritsFrom != null ? " implements " + inheritsFrom + " {\n" : " {\n";
    code += outer.substring(2) + ";\n\n";
    code += "  private static final int[] root = new int[] {";
    String root = "";
    for (int i = 0; i < words.length; i++)
      root += ", " + i;

    code += root.substring(2) + "};\n";
    code += "  public final " + String.class.getName() + " token;\n";
    code += "  protected final int[][] tree;\n\n";
    code += "  " + enumName + "(final " + String.class.getName() + " token, final int[][] tree) {\n";
    code += "    this.token = token;\n";
    code += "    this.tree = tree;\n";
    code += "  }\n\n";

    code += "  public static " + enumName + " findNext(final " + enumName + " word, int position, final char ch) {\n";
    code += "    if (position == 0) {\n";
    code += "      final int found = " + ISTEnumUtil.class.getName() + ".binarySearch(" + enumName + ".values(), " + enumName + ".root, ch, position);\n";
    code += "      return found < 0 ? null : " + enumName + ".values()[found];\n";
    code += "    }\n\n";
    code += "    if (position <= word.tree.length) {\n";
    code += "      final int[] tree = word.tree[position - 1];\n";
    code += "      final int found = " + ISTEnumUtil.class.getName() + ".binarySearch(" + enumName + ".values(), tree, ch, position);\n";
    code += "      return found < 0 ? null : " + enumName + ".values()[tree[found]];\n";
    code += "    }\n\n";
    code += "    return word.token.length() <= position || word.token.charAt(position) != ch ? null : word;\n";
    code += "  }\n\n";

    code += "  public " + String.class.getName() + " toString() {\n";
    code += "    return token;\n";
    code += "  }\n";
    code += "}";

    final FileOutputStream out = new FileOutputStream(file);
    out.write(code.getBytes());
    out.close();
  }
}