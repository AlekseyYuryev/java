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

package org.safris.cdm.lexer;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.safris.cdm.Audit;
import org.safris.cdm.lexer.Lexer.Keyword;
import org.safris.commons.io.Files;

public final class LexerTest {
  private static Keyword testToken(int[] keywords, final String token) {
    Keyword keyword = null;
    for (int i = 0; i < token.length(); i++) {
      final char ch = token.charAt(i);
      if (keywords != null) {
        final int found = Util.binarySearch(keywords, ch, i);
        keyword = Keyword.values()[keywords[found]];
        if (i < keyword.children.length)
          keywords = keyword.children[i];
      }
      else if (keyword.lcname.length() <= i || keyword.lcname.charAt(i) != ch)
        return null;
    }

    return keyword;
  }

  private void assertEquals(final Keyword expected, final Keyword result) {
    if (result == null) {
      System.err.println("[FAIL] " + result + " == null");
      Assert.fail();
    }

    if (expected != result) {
      System.err.println("[FAIL] " + result + " != " + result);
      Assert.fail();
    }

//    System.out.println("[OK] " + result);
  }

  @Test
  public void testKeywords() {
    final String out = Util.print(Keyword.INDICES);
    final String expected = "abstract\n bstract\n ssert\n  stract\nassert\n bstract\n ssert\n  sert\nboolean\n oolean\n reak\n yte\n  olean\nbreak\n oolean\n reak\n yte\n  eak\nbyte\n oolean\n reak\n yte\n  te\ncase\n ase\n atch\n har\n lass\n onst\n ontinue\n  se\n  tch\n   e\ncatch\n ase\n atch\n har\n lass\n onst\n ontinue\n  se\n  tch\n   ch\nchar\n ase\n atch\n har\n lass\n onst\n ontinue\n  ar\nclass\n ase\n atch\n har\n lass\n onst\n ontinue\n  ass\nconst\n ase\n atch\n har\n lass\n onst\n ontinue\n  nst\n  ntinue\n   st\n   tinue\n    t\ncontinue\n ase\n atch\n har\n lass\n onst\n ontinue\n  nst\n  ntinue\n   st\n   tinue\n    inue\ndefault\n efault\n o\n ouble\n  fault\ndo\n efault\n o\n ouble\n  \n  uble\ndouble\n efault\n o\n ouble\n  \n  uble\nelse\n lse\n num\n xtends\n  se\nenum\n lse\n num\n xtends\n  um\nextends\n lse\n num\n xtends\n  tends\nfalse\n alse\n inal\n inally\n loat\n or\n  lse\nfinal\n alse\n inal\n inally\n loat\n or\n  nal\n  nally\n   al\n   ally\n    l\n    lly\n     \n     ly\nfinally\n alse\n inal\n inally\n loat\n or\n  nal\n  nally\n   al\n   ally\n    l\n    lly\n     \n     ly\nfloat\n alse\n inal\n inally\n loat\n or\n  oat\nfor\n alse\n inal\n inally\n loat\n or\n  r\ngoto\n oto\nif\n f\n mplements\n mport\n nstanceof\n nt\n nterface\n  \nimplements\n f\n mplements\n mport\n nstanceof\n nt\n nterface\n  plements\n  port\n   lements\n   ort\n    ements\nimport\n f\n mplements\n mport\n nstanceof\n nt\n nterface\n  plements\n  port\n   lements\n   ort\n    rt\ninstanceof\n f\n mplements\n mport\n nstanceof\n nt\n nterface\n  stanceof\n  t\n  terface\n   tanceof\nint\n f\n mplements\n mport\n nstanceof\n nt\n nterface\n  stanceof\n  t\n  terface\n   \n   erface\ninterface\n f\n mplements\n mport\n nstanceof\n nt\n nterface\n  stanceof\n  t\n  terface\n   \n   erface\nlong\n ong\nnative\n ative\n ew\n ull\n  tive\nnew\n ative\n ew\n ull\n  w\nnull\n ative\n ew\n ull\n  ll\npackage\n ackage\n rivate\n rotected\n ublic\n  ckage\nprivate\n ackage\n rivate\n rotected\n ublic\n  ivate\n  otected\n   vate\nprotected\n ackage\n rivate\n rotected\n ublic\n  ivate\n  otected\n   tected\npublic\n ackage\n rivate\n rotected\n ublic\n  blic\nreturn\n eturn\nshort\n hort\n tatic\n trictfp\n uper\n witch\n ynchronized\n  ort\nstatic\n hort\n tatic\n trictfp\n uper\n witch\n ynchronized\n  atic\n  rictfp\n   tic\nstrictfp\n hort\n tatic\n trictfp\n uper\n witch\n ynchronized\n  atic\n  rictfp\n   ictfp\nsuper\n hort\n tatic\n trictfp\n uper\n witch\n ynchronized\n  per\nswitch\n hort\n tatic\n trictfp\n uper\n witch\n ynchronized\n  itch\nsynchronized\n hort\n tatic\n trictfp\n uper\n witch\n ynchronized\n  nchronized\nthis\n his\n hrow\n hrows\n ransient\n rue\n ry\n  is\n  row\n  rows\n   s\nthrow\n his\n hrow\n hrows\n ransient\n rue\n ry\n  is\n  row\n  rows\n   ow\n   ows\n    w\n    ws\n     \n     s\nthrows\n his\n hrow\n hrows\n ransient\n rue\n ry\n  is\n  row\n  rows\n   ow\n   ows\n    w\n    ws\n     \n     s\ntransient\n his\n hrow\n hrows\n ransient\n rue\n ry\n  ansient\n  ue\n  y\n   nsient\ntrue\n his\n hrow\n hrows\n ransient\n rue\n ry\n  ansient\n  ue\n  y\n   e\ntry\n his\n hrow\n hrows\n ransient\n rue\n ry\n  ansient\n  ue\n  y\n   \nvoid\n oid\n olatile\n  id\n  latile\n   d\nvolatile\n oid\n olatile\n  id\n  latile\n   atile\nwhile\n hile";
    Assert.assertEquals(expected, out);

    for (final Keyword keyword : Keyword.values())
      assertEquals(keyword, testToken(Keyword.INDICES, keyword.lcname));
  }

  @Test
  public void testTokenize() throws IOException {
    final File file = new File("../../xml/generator/compiler/src/main/java/org/safris/xml/generator/compiler/runtime/Binding.java");
    final Audit audit = Lexer.tokenize(file);

    final String expected = new String(Files.getBytes(file));
    final String out = audit.toString();
    Assert.assertEquals(expected, out);
    /*for (int x = 0; x < indices.size(); x++) {
      final Index index = indices.get(x);
      System.out.println(Strings.padFixed(index.token + ":", 16) + new String(bytes, index.start, index.length + 1));
    }*/
  }
}