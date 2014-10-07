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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.safris.commons.util.StreamSearcher;

public class Lexer {
  private static final StreamSearcher eol = new StreamSearcher(new byte[] {'\r'}, new byte[] {'\n'});
  private static final StreamSearcher closeComment = new StreamSearcher(new byte[] {'*', '/'});
  private static final StreamSearcher singleQuote = new StreamSearcher(new byte[] {'\''});
  private static final StreamSearcher doubleQuote = new StreamSearcher(new byte[] {'"'});
  
  private interface Token {
    public String name();
    public int ordinal();
  }
  
  public static enum Flag implements Token {
    SLASH, LINE_COMMENT, BLOCK_COMMENT, CHARACTER, STRING, DOT, COLON, SEMI_COLON, ASTERISK, PAREN_OPEN, PAREN_CLOSE, BRACKET_OPEN, BRACKET_CLOSE, BRACE_OPEN, BRACE_CLOSE, COMMA, NUMBER, ARRAY, PLUS, PLUS_PLUS, PLUS_EQ, MINUS, MINUS_MINUS, MINUS_EQ, GT, LT, GTGT, GTGTGT, LTLT, LTLTLT, GTE, LTE, EQ, EQEQ, CARAT, PERCENT, EXCLAMATION, TILDE, AMPERSAND, AND, PIPE, OR, AT, QUESTION, WORD;
  }

  public static enum Keyword implements Token {
    ABSTRACT, ASSERT, BOOLEAN, BREAK, BYTE, CASE, CATCH, CHAR, CLASS, CONST, CONTINUE, DEFAULT, DO, DOUBLE, ELSE, ENUM, EXTENDS, FALSE, FINAL, FINALLY, FLOAT, FOR, GOTO, IF, IMPLEMENTS, IMPORT, INSTANCEOF, INT, INTERFACE, LONG, NATIVE, NEW, NULL, PACKAGE, PRIVATE, PROTECTED, PUBLIC, RETURN, SHORT, STATIC, STRICTFP, SUPER, SWITCH, SYNCHRONIZED, THIS, THROW, THROWS, TRANSIENT, TRUE, TRY, VOID, VOLATILE, WHILE;

    protected static final int[] INDICES = new int[Keyword.values().length];
    
    static {
      for (int i = 0; i < INDICES.length; i++)
        INDICES[i] = i;
      
      Keyword.init(Keyword.INDICES, 0);
    }

    protected static void init(final int[] keywords, final int depth) {
      traverse(keywords, depth);
      for (final int keyword : keywords) {
        final int[][] children = Keyword.values()[keyword].children;
        if (children[depth] != null)
          init(children[depth], depth + 1);
      }
    }

    private static void traverse(final int[] keywords, final int depth) {
      if (keywords.length <= 1)
        return;

      int l = 0;
      while (l < keywords.length) {
        final String name = Keyword.values()[keywords[l]].lcname;
        final int[] words = recurse(keywords, l, depth < name.length() ? name.charAt(depth) : '\u0000', depth, 0);
        if (words == null)
          break;
        
        for (final int word : words)
          Keyword.values()[word].children[depth] = words;
        
        l += words.length;
      }
    }
    
    private static int[] recurse(final int[] keywords, final int index, final char ch, final int depth, final int size) {
      final String name = Keyword.values()[keywords[index]].lcname;
      if (name.length() <= depth || ch != name.charAt(depth))
        return 0 < size ? new int[size] : null;

      final int[] array = index + 1 < keywords.length ? recurse(keywords, index + 1, ch, depth, size + 1) : new int[size + 1];
      array[size] = keywords[index];
      return array;
    }

    protected static int[] shrink(final int[] keywords, final int size) {
      final int[] min = new int[size];
      char last = '\u0000';
      int index = 0;
      for (final int keyword : keywords) {
        final char ch = Keyword.values()[keyword].name().charAt(0);
        if (last == ch)
          continue;

        last = ch;
        min[index++] = keyword;
      }

      return min;
    }

    public final String lcname;
    public final int[][] children = new int[name().length() + 1][];

    Keyword() {
      this.lcname = name().toLowerCase();
    }
    
    public String toString() {
      return lcname;
    }
  }

  protected static class Index {
    private static void addToken(final List<Index> tokens, final Token token, final int start, final int length) {
      if (0 < length && token != null)
        tokens.add(new Index(token, start - 1, length - 1));
    }

    public final int start;
    public final int length;
    public final Token token;

    private Index(final Token token, final int start, final int length) {
      this.token = token;
      this.start = start;
      this.length = length;
    }
  }
  
  public static List<Index> tokenize(final File file, final byte[] bytes) throws IOException {
    final FileInputStream in = new FileInputStream(file);
    final List<Index> tokens = new ArrayList<Index>();
    int i = 0;
    char c;
    Token token = null;
    int len = 0;
    while (i < bytes.length) {
      in.read(bytes, i, 1);
      c = (char)bytes[i++];
      if ('0' <= c && c <= '9' && (token == null || token.ordinal() < Flag.WORD.ordinal())) {
        if (token != Flag.NUMBER) {
          Index.addToken(tokens, token, i - len, len);
          len = 1;
          token = Flag.NUMBER;
        }
        else {
          ++len;
        }
      }
      else if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || (len != 0 && '0' <= c && c <= '9') || c == '$' || c == '_') {
        if (token == Flag.WORD) {
          ++len;
        }
        else if (token == null || token instanceof Flag) {
          Index.addToken(tokens, token, i - len, len);
          len = 1;

          final int found = Util.binarySearch(Keyword.INDICES, c, 0);
          token = found < 0 ? Flag.WORD : Keyword.values()[found];
        }
        else {
          final int[] children = ((Keyword)token).children[len - 1];
          if (children != null) {
            final int found = Util.binarySearch(children, c, len);
            token = found < 0 ? Flag.WORD : Keyword.values()[children[found]];
          }
          else if (token.name().length() <= len || ((Keyword)token).lcname.charAt(len) != c) {
            token = Flag.WORD;
          }
          
          ++len;
        }
      }
      else if (c == '\n' || c == '\r' || c == ' ' || c == '\t') {
        if (token != null) {
          Index.addToken(tokens, token, i - len, len);
          len = 0;
          token = null;
        }
      }
      else if (c == '.') {
        if (token == null || token == Flag.BRACKET_OPEN || token == Flag.BRACE_OPEN) {
          len = 1;
          token = Flag.NUMBER;
        }
        else if (token == Flag.NUMBER) {
          ++len;
        }
        else {
          Index.addToken(tokens, token, i - len, len);
          len = 1;
          token = Flag.DOT;
        }
      }
      else if (c == '&') {
        if (token == Flag.AMPERSAND) { // &&
          len = 2;
          token = Flag.AND;
        }
        else {
          Index.addToken(tokens, token, i - len, len);
          len = 1;
          token = Flag.AMPERSAND;
        }
      }
      else if (c == '|') {
        if (token == Flag.PIPE) { // ||
          len = 2;
          token = Flag.OR;
        }
        else {
          Index.addToken(tokens, token, i - len, len);
          len = 1;
          token = Flag.PIPE;
        }
      }
      else if (c == '=') {
        if (token == Flag.LT) { // <=
          len = 2;
          token = Flag.LTE;
        }
        else if (token == Flag.GT) { // >=
          len = 2;
          token = Flag.GTE;
        }
        else if (token == Flag.EQ) { // ==
          len = 2;
          token = Flag.EQEQ;
        }
        else if (token == Flag.MINUS) { // -=
          len = 2;
          token = Flag.MINUS_EQ;
        }
        else if (token == Flag.PLUS) { // +=
          len = 2;
          token = Flag.PLUS_EQ;
        }
        else {
          Index.addToken(tokens, token, i - len, len);
          len = 1;
          token = Flag.EQ;
        }
      }
      else if (c == '<') {
        if (token == Flag.LT) { // <<
          len = 2;
          token = Flag.LTLT;
        }
        else if (token == Flag.LTLT) { // <<<
          len = 3;
          token = Flag.LTLTLT;
        }
        else {
          Index.addToken(tokens, token, i - len, len);
          len = 1;
          token = Flag.LT;
        }
      }
      else if (c == '>') {
        if (token == Flag.GT) { // >>
          len = 2;
          token = Flag.GTGT;
        }
        else if (token == Flag.GTGT) { // >>>
          len = 3;
          token = Flag.GTGTGT;
        }
        else {
          Index.addToken(tokens, token, i - len, len);
          len = 1;
          token = Flag.GT;
        }
      }
      else if (c == '-') {
        if (token == Flag.MINUS) { // --
          len = 2;
          token = Flag.MINUS_MINUS;
        }
        else {
          Index.addToken(tokens, token, i - len, len);
          len = 1;
          token = Flag.MINUS;
        }
      }
      else if (c == '+') {
        if (token == Flag.PLUS) { // ++
          len = 2;
          token = Flag.PLUS_PLUS;
        }
        else {
          Index.addToken(tokens, token, i - len, len);
          len = 1;
          token = Flag.PLUS;
        }
      }
      else if (c == '~') {
        Index.addToken(tokens, token, i - len, len);
        len = 1;
        token = Flag.TILDE;
      }
      else if (c == '!') {
        Index.addToken(tokens, token, i - len, len);
        len = 1;
        token = Flag.EXCLAMATION;
      }
      else if (c == '@') {
        Index.addToken(tokens, token, i - len, len);
        len = 1;
        token = Flag.AT;
      }
      else if (c == '^') {
        Index.addToken(tokens, token, i - len, len);
        len = 1;
        token = Flag.CARAT;
      }
      else if (c == '%') {
        Index.addToken(tokens, token, i - len, len);
        len = 1;
        token = Flag.PERCENT;
      }
      else if (c == ',') {
        Index.addToken(tokens, token, i - len, len);
        len = 1;
        token = Flag.COMMA;
      }
      else if (c == ';') {
        Index.addToken(tokens, token, i - len, len);
        len = 1;
        token = Flag.SEMI_COLON;
      }
      else if (c == ':') {
        Index.addToken(tokens, token, i - len, len);
        len = 1;
        token = Flag.COLON;
      }
      else if (c == '?') {
        Index.addToken(tokens, token, i - len, len);
        len = 1;
        token = Flag.QUESTION;
      }
      else if (c == '(') {
        Index.addToken(tokens, token, i - len, len);
        len = 1;
        token = Flag.PAREN_OPEN;
      }
      else if (c == ')') {
        Index.addToken(tokens, token, i - len, len);
        len = 1;
        token = Flag.PAREN_CLOSE;
      }
      else if (c == '{') {
        Index.addToken(tokens, token, i - len, len);
        len = 1;
        token = Flag.BRACE_OPEN;
      }
      else if (c == '}') {
        Index.addToken(tokens, token, i - len, len);
        len = 1;
        token = Flag.BRACE_CLOSE;
      }
      else if (c == '[') {
        Index.addToken(tokens, token, i - len, len);
        len = 1;
        token = Flag.BRACKET_OPEN;
      }
      else if (c == ']') {
        if (token == Flag.BRACKET_OPEN) { // []
          len = 2;
          token = Flag.ARRAY;
        }
        else {
          Index.addToken(tokens, token, i - len, len);
          len = 1;
          token = Flag.BRACKET_CLOSE;
        }
      }
      else if (c == '/') {
        if (token == Flag.SLASH) { // Start of line comment
          // find end of line
          // index from // to end of comment, not including newline
          // this is the only situation where the token is added at time of detection of end of block, cause the eol char is not supposed to be a part of the token
          len = eol.search(in, bytes, i);
          Index.addToken(tokens, Flag.LINE_COMMENT, i - 1, len + 1);
          i += len;
          len = 0;
          token = null;
        }
        else {
          Index.addToken(tokens, token, i - len, len);
          len = 1;
          token = Flag.SLASH;
        }
      }
      else if (c == '*') {
        if (token == Flag.SLASH) { // Start of block comment
          // find end of block comment
          // index from /* to */ including any & all characters between
          i += len = closeComment.search(in, bytes, i);
          len += 2;
          token = Flag.BLOCK_COMMENT;
        }
        else {
          Index.addToken(tokens, token, i - len, len);
          len = 1;
          token = Flag.ASTERISK;
        }
      }
      else if (c == '\'') {
        Index.addToken(tokens, token, i - len, len);
        len = 1;
        int t;
        i += t = singleQuote.search(in, bytes, i);
        len += t;
        // take care of '\'' situation
        if (bytes[i - 2] == '\\' && len == 3) {
          i += t = singleQuote.search(in, bytes, i);
          len += t;
        }

        token = Flag.CHARACTER;
      }
      else if (c == '"') {
        Index.addToken(tokens, token, i - len, len);
        len = 1;
        int l;
        i += l = doubleQuote.search(in, bytes, i);
        len += l;
        // take care of "\"" situation
        if (bytes[i - 2] == '\\' && len == 3) {
          i += l = doubleQuote.search(in, bytes, i);
          len += l;
        }

        token = Flag.STRING;
      }
      else {
        System.err.print(c);
      }
    }
    
    // add the last token, because its final delimiter can be the EOF
    Index.addToken(tokens, token, i - len + 1, len);
    
    return tokens;
  }
}