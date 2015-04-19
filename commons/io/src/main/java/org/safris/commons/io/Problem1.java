package org.safris.commons.io;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Problem1 {
  public static void main(final String[] args) throws Exception {
    x("apple");
  }

  public static String work(final int value) {
    final String string = String.valueOf(value);
    String ret = "";
    int i;
    for (i = string.length() - 3; i >= 0; i -= 3)
      ret = string.substring(i, i + 3) + "," + ret;

    if (i > -3)
      ret = string.substring(0, 3 + i) + "," + ret;

    return ret.substring(0, ret.length() - 1);
  }
  
  public static void x(final String w) throws Exception {
    final Map<String,List<String>> keyToWords = new HashMap<String,List<String>>();
    byte[] bytes = Files.getBytes(new File("/Users/seva/Downloads/wl.txt"));
    final String library = new String(bytes);
    final StringTokenizer tokenizer = new StringTokenizer(library, "\n");
    while (tokenizer.hasMoreTokens()) {
      final String word = tokenizer.nextToken();
      final String key = key(word);
      List<String> list = keyToWords.get(key);
      if (list == null)
        keyToWords.put(key, list = new ArrayList<String>());
      
      list.add(word);
    }
    
    final List<String> an = keyToWords.get(key(w));
    if (an != null)
      for (final String a : an)
        System.out.println(a);
  }
  
  private static String key(final String s) {
    final int[] letters = new int[26];
    for (int i = 0; i < s.length(); i++) {
      final char c = s.charAt(i);
      letters[c - 'a']++;
    }
    
    String k = "";
    for (int i = 0; i < letters.length; i++)
      if (letters[i] != 0)
        k += (char)i + letters[i];
    
    return k;
  }
}