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
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.safris.commons.lang.Bytes;
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
    WHITESPACE, SLASH('/'), LINE_COMMENT, BLOCK_COMMENT, CHARACTER, STRING, DOT('.'), COLON(':'), SEMI_COLON(';'), ASTERISK('*'), PAREN_OPEN('('), PAREN_CLOSE(')'), BRACKET_OPEN('['), BRACKET_CLOSE(']'), BRACE_OPEN('{'), BRACE_CLOSE('}'), COMMA(','), NUMBER, ARRAY('[', ']'), PLUS('+'), PLUS_PLUS('+', '+'), PLUS_EQ('+', '='), MINUS('-'), MINUS_MINUS('-', '-'), MINUS_EQ('-', '='), GT('>'), LT('<'), GTGT('>', '>'), GTGTGT('>', '>', '>'), LTLT('<', '<'), LTLTLT('<', '<', '<'), GTE('>', '='), LTE('<', '='), EQ('='), EQEQ('=', '='), CARAT('^'), PERCENT('%'), EXCLAMATION('!'), TILDE('~'), AMPERSAND('&'), AND('&', '&'), PIPE('|'), OR('|', '|'), AT('@'), QUESTION('?'), WORD;

    public final char[] ch;

    Flag(final char ... ch) {
      this.ch = ch;
    }

    Flag() {
      this.ch = null;
    }
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
        final int[] words = recurse(keywords, l, depth < name.length() ? name.charAt(depth) : '\0', depth, 0);
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
      char last = '\0';
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
    protected static void addToken(final List<Index> tokens, final Token token, final byte[] bytes, final int start, final int length) {
      if (0 < length && token != null)
        tokens.add(new Index(token, bytes, start - 1, length - 1));
    }

    public final Token token;
    public final byte[] bytes;
    public final int start;
    public final int length;

    private Index(final Token token, final byte[] bytes, final int start, final int length) {
      this.token = token;
      this.bytes = bytes;
      this.start = start;
      this.length = length;
    }
  }

  public static String toString(final List<Index> indices) {
    String out = "";
    for (final Index index : indices)
      out += index.token instanceof Flag && ((Flag)index.token).ch != null ? String.valueOf(((Flag)index.token).ch) : new String(index.bytes, index.start, index.length + 1);

    return out;
  }

  public static List<Index> tokenize(final File file, final byte[] bytes) throws IOException {
    final FileInputStream in = new FileInputStream(file);
    final List<Index> tokens = new ArrayList<Index>();
    int i = 0;
    char ch;
    Token token = null;
    int len = 0;
    while (i < bytes.length) {
      in.read(bytes, i, 1);
      ch = (char)bytes[i++];
      if ('0' <= ch && ch <= '9' && (token == null || token != Flag.WORD)) {
        if (token != Flag.NUMBER) {
          Index.addToken(tokens, token, bytes, i - len, len);
          len = 1;
          token = Flag.NUMBER;
        }
        else {
          ++len;
        }
      }
      else if (('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z') || (len != 0 && '0' <= ch && ch <= '9') || ch == '$' || ch == '_') {
        // TODO: Handle 0x0000 and 0b0000
        if (token == Flag.NUMBER && (ch == 'd' || ch == 'D' || ch == 'f' || ch == 'F' || ch == 'l' || ch == 'L')) {
          ++len;
        }
        else if (token == Flag.WORD) {
          ++len;
        }
        else if (token == null || token == Flag.WHITESPACE || token instanceof Flag) {
          Index.addToken(tokens, token, bytes, i - len, len);
          len = 1;

          final int found = Util.binarySearch(Keyword.INDICES, ch, 0);
          token = found < 0 ? Flag.WORD : Keyword.values()[found];
        }
        else {
          final int[] children = ((Keyword)token).children[len - 1];
          if (children != null) {
            final int found = Util.binarySearch(children, ch, len);
            token = found < 0 ? Flag.WORD : Keyword.values()[children[found]];
          }
          else if (token.name().length() <= len || ((Keyword)token).lcname.charAt(len) != ch) {
            token = Flag.WORD;
          }

          ++len;
        }
      }
      else if (ch == '\n' || ch == '\r' || ch == ' ' || ch == '\t') {
        if (token == Flag.WHITESPACE) {
          ++len;
        }
        else {
          Index.addToken(tokens, token, bytes, i - len, len);
          len = 1;
          token = Flag.WHITESPACE;
        }
      }
      else if (ch == '.') {
        if (token == null || token == Flag.WHITESPACE || token == Flag.BRACKET_OPEN || token == Flag.BRACE_OPEN) {
          len = 1;
          token = Flag.NUMBER;
        }
        else if (token == Flag.NUMBER) {
          ++len;
        }
        else {
          Index.addToken(tokens, token, bytes, i - len, len);
          len = 1;
          token = Flag.DOT;
        }
      }
      else if (ch == '&') {
        if (token == Flag.AMPERSAND) { // &&
          len = 2;
          token = Flag.AND;
        }
        else {
          Index.addToken(tokens, token, bytes, i - len, len);
          len = 1;
          token = Flag.AMPERSAND;
        }
      }
      else if (ch == '|') {
        if (token == Flag.PIPE) { // ||
          len = 2;
          token = Flag.OR;
        }
        else {
          Index.addToken(tokens, token, bytes, i - len, len);
          len = 1;
          token = Flag.PIPE;
        }
      }
      else if (ch == '=') {
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
          Index.addToken(tokens, token, bytes, i - len, len);
          len = 1;
          token = Flag.EQ;
        }
      }
      else if (ch == '<') {
        if (token == Flag.LT) { // <<
          len = 2;
          token = Flag.LTLT;
        }
        else if (token == Flag.LTLT) { // <<<
          len = 3;
          token = Flag.LTLTLT;
        }
        else {
          Index.addToken(tokens, token, bytes, i - len, len);
          len = 1;
          token = Flag.LT;
        }
      }
      else if (ch == '>') {
        if (token == Flag.GT) { // >>
          len = 2;
          token = Flag.GTGT;
        }
        else if (token == Flag.GTGT) { // >>>
          len = 3;
          token = Flag.GTGTGT;
        }
        else {
          Index.addToken(tokens, token, bytes, i - len, len);
          len = 1;
          token = Flag.GT;
        }
      }
      else if (ch == '-') {
        if (token == Flag.MINUS) { // --
          len = 2;
          token = Flag.MINUS_MINUS;
        }
        else {
          Index.addToken(tokens, token, bytes, i - len, len);
          len = 1;
          token = Flag.MINUS;
        }
      }
      else if (ch == '+') {
        if (token == Flag.PLUS) { // ++
          len = 2;
          token = Flag.PLUS_PLUS;
        }
        else {
          Index.addToken(tokens, token, bytes, i - len, len);
          len = 1;
          token = Flag.PLUS;
        }
      }
      else if (ch == '~') {
        Index.addToken(tokens, token, bytes, i - len, len);
        len = 1;
        token = Flag.TILDE;
      }
      else if (ch == '!') {
        Index.addToken(tokens, token, bytes, i - len, len);
        len = 1;
        token = Flag.EXCLAMATION;
      }
      else if (ch == '@') {
        Index.addToken(tokens, token, bytes, i - len, len);
        len = 1;
        token = Flag.AT;
      }
      else if (ch == '^') {
        Index.addToken(tokens, token, bytes, i - len, len);
        len = 1;
        token = Flag.CARAT;
      }
      else if (ch == '%') {
        Index.addToken(tokens, token, bytes, i - len, len);
        len = 1;
        token = Flag.PERCENT;
      }
      else if (ch == ',') {
        Index.addToken(tokens, token, bytes, i - len, len);
        len = 1;
        token = Flag.COMMA;
      }
      else if (ch == ';') {
        Index.addToken(tokens, token, bytes, i - len, len);
        len = 1;
        token = Flag.SEMI_COLON;
      }
      else if (ch == ':') {
        Index.addToken(tokens, token, bytes, i - len, len);
        len = 1;
        token = Flag.COLON;
      }
      else if (ch == '?') {
        Index.addToken(tokens, token, bytes, i - len, len);
        len = 1;
        token = Flag.QUESTION;
      }
      else if (ch == '(') {
        Index.addToken(tokens, token, bytes, i - len, len);
        len = 1;
        token = Flag.PAREN_OPEN;
      }
      else if (ch == ')') {
        Index.addToken(tokens, token, bytes, i - len, len);
        len = 1;
        token = Flag.PAREN_CLOSE;
      }
      else if (ch == '{') {
        Index.addToken(tokens, token, bytes, i - len, len);
        len = 1;
        token = Flag.BRACE_OPEN;
      }
      else if (ch == '}') {
        Index.addToken(tokens, token, bytes, i - len, len);
        len = 1;
        token = Flag.BRACE_CLOSE;
      }
      else if (ch == '[') {
        Index.addToken(tokens, token, bytes, i - len, len);
        len = 1;
        token = Flag.BRACKET_OPEN;
      }
      else if (ch == ']') {
        if (token == Flag.BRACKET_OPEN) { // []
          len = 2;
          token = Flag.ARRAY;
        }
        else {
          Index.addToken(tokens, token, bytes, i - len, len);
          len = 1;
          token = Flag.BRACKET_CLOSE;
        }
      }
      else if (ch == '/') {
        if (token == Flag.SLASH) { // Start of line comment
          // find end of line
          // index from // to end of comment, not including newline
          // this is the only situation where the token is added at time of detection of end of block, cause the eol char is not supposed to be a part of the
          // token
          len = eol.search(in, bytes, i);
          Index.addToken(tokens, Flag.LINE_COMMENT, bytes, i - 1, len + 2);
          i += len;
          len = 0;
          token = null;
        }
        else {
          Index.addToken(tokens, token, bytes, i - len, len);
          len = 1;
          token = Flag.SLASH;
        }
      }
      else if (ch == '*') {
        if (token == Flag.SLASH) { // Start of block comment
          // find end of block comment
          // index from /* to */ including any & all characters between
          i += len = closeComment.search(in, bytes, i);
          len += 2;
          token = Flag.BLOCK_COMMENT;
        }
        else {
          Index.addToken(tokens, token, bytes, i - len, len);
          len = 1;
          token = Flag.ASTERISK;
        }
      }
      else if (ch == '\'') {
        Index.addToken(tokens, token, bytes, i - len, len);
        len = 1;
        int t;
        i += t = singleQuote.search(in, bytes, i);
        len += t;
        // take care of '\'' situation
        // TODO: Handle '\u0000' and '\0'
        if (bytes[i - 2] == '\\' && len == 3) {
          i += t = singleQuote.search(in, bytes, i);
          len += t;
        }

        token = Flag.CHARACTER;
      }
      else if (ch == '"') {
        Index.addToken(tokens, token, bytes, i - len, len);
        len = 1;
        int l;
        i += l = doubleQuote.search(in, bytes, i);
        len += l;
        // take care of \" situation
        if (bytes[i - 2] == '\\') {
          i += l = doubleQuote.search(in, bytes, i);
          len += l;
        }

        token = Flag.STRING;
      }
      else {
        System.err.print(ch);
      }
    }

    // add the last token, because its final delimiter can be the EOF
    Index.addToken(tokens, token, bytes, i - len + 1, len);

    return tokens;
  }
}