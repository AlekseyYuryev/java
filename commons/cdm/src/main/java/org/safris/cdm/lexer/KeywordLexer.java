package org.safris.cdm.lexer;

public class KeywordLexer {
  public static enum Keyword {
    ABSTRACT, ASSERT, BOOLEAN, BREAK, BYTE, CASE, CATCH, CHAR, CLASS, CONST, CONTINUE, DEFAULT, DO, DOUBLE, ELSE, ENUM, EXTENDS, FALSE, FINAL, FINALLY, FLOAT, FOR, GOTO, IF, IMPLEMENTS, IMPORT, INSTANCEOF, INT, INTERFACE, LONG, NATIVE, NEW, NULL, PACKAGE, PRIVATE, PROTECTED, PUBLIC, RETURN, SHORT, STATIC, STRICTFP, SUPER, SWITCH, SYNCHRONIZED, THIS, THROW, THROWS, TRANSIENT, TRUE, TRY, VOID, VOLATILE, WHILE;

    public final String lcname;
    public final int[][] children = new int[name().length() + 1][];

    Keyword() {
      this.lcname = name().toLowerCase();
    }
    
    public String toString() {
      return lcname;
    }
  }
  
  protected static final Keyword[] KEYWORDS = Keyword.values();
  private static final int[] INDICES = new int[KEYWORDS.length];
  
  static {
    for (int i = 0; i < INDICES.length; i++)
      INDICES[i] = i;
  }

  public static void main(final String[] args) {
    int[] keywords = INDICES;
    final int count = dig(keywords, 0);
    keywords = shrink(keywords, count);
    final String out = Util.print(keywords);
    final String ex = "abstract\n bstract\n ssert\n  stract\nboolean\n oolean\n reak\n yte\n  olean\ncase\n ase\n atch\n har\n lass\n onst\n ontinue\n  se\n  tch\n   e\ndefault\n efault\n o\n ouble\n  fault\nelse\n lse\n num\n xtends\n  se\nfalse\n alse\n inal\n inally\n loat\n or\n  lse\ngoto\n oto\nif\n f\n mplements\n mport\n nstanceof\n nt\n nterface\n  \nlong\n ong\nnative\n ative\n ew\n ull\n  tive\npackage\n ackage\n rivate\n rotected\n ublic\n  ckage\nreturn\n eturn\nshort\n hort\n tatic\n trictfp\n uper\n witch\n ynchronized\n  ort\nthis\n his\n hrow\n hrows\n ransient\n rue\n ry\n  is\n  row\n  rows\n   s\nvoid\n oid\n olatile\n  id\n  latile\n   d\nwhile\n hile";
    if (!ex.equals(out))
      throw new Error("\n" + ex + "\n\nNOT EQUAL TO\n\n" + out);

    System.out.println(out);
    System.out.println("\nTESTING......");
    test(keywords, Keyword.THROWS.lcname);
    for (final Keyword expected : KEYWORDS) {
      Keyword result = test(keywords, expected.lcname);
      System.out.println(result == null ? "[FAIL] " + expected + " != null" : result == expected ? ("[OK]" + expected) : ("[FAIL] " + expected + " != " + result));
    }
  }

  private static int[] shrink(final int[] keywords, final int size) {
    final int[] min = new int[size];
    char last = '\u0000';
    int index = 0;
    for (final int keyword : keywords) {
      final char ch = KEYWORDS[keyword].name().charAt(0);
      if (last == ch)
        continue;

      last = ch;
      min[index++] = keyword;
    }

    return min;
  }

  private static int dig(final int[] keywords, final int depth) {
    final int count = traverse(keywords, depth);
    for (final int keyword : keywords) {
      final int[][] children = KEYWORDS[keyword].children;
      if (children[depth] != null)
        dig(children[depth], depth + 1);
    }
    
    return count;
  }

  private static int traverse(final int[] keywords, final int depth) {
    if (keywords.length <= 1)
      return 0;

    int count = 0;
    int d = 0;
    while (d < keywords.length) {
      final String name = KEYWORDS[keywords[d]].lcname;
      final int[] words = rec(keywords, d, depth < name.length() ? name.charAt(depth) : ' ', depth, 0);
      if (words == null)
        break;
      
      for (final int word : words)
        KEYWORDS[word].children[depth] = words;
      
      d += words.length;
      count++;
    }
    
    return count;
  }
  
  private static int[] rec(final int[] keywords, final int index, final char ch, final int depth, final int size) {
    final String name = KEYWORDS[keywords[index]].lcname;
    if (name.length() <= depth || ch != name.charAt(depth))
      return 0 < size ? new int[size] : null;

    final int[] array = index + 1 < keywords.length ? rec(keywords, index + 1, ch, depth, size + 1) : new int[size + 1];
    array[size] = keywords[index];
    return array;
  }

  private static Keyword test(int[] keywords, final String token) {
    Keyword keyword = null;
    for (int i = 0; i < token.length(); i++) {
      final char ch = token.charAt(i);
      if (keywords != null) {
        final int found = Util.binarySearch(keywords, ch, 0, keywords.length, i);
        keyword = KEYWORDS[keywords[found]];
        if (i < keyword.children.length)
          keywords = keyword.children[i];
      }
      else if (keyword.lcname.length() <= i || keyword.lcname.charAt(i) != ch)
        return null;
    }

    return keyword;
  }
}