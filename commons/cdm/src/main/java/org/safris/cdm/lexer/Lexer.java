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
import java.rmi.UnexpectedException;

import org.safris.cdm.Audit;
import org.safris.commons.util.StreamSearcher;

public class Lexer {
  private static final StreamSearcher.Char eol = new StreamSearcher.Char(new char[] {'\r'}, new char[] {'\n'});
  private static final StreamSearcher.Char closeComment = new StreamSearcher.Char(new char[] {'*', '/'});
  private static final StreamSearcher.Char singleQuote = new StreamSearcher.Char(new char[] {'\''});
  private static final StreamSearcher.Char doubleQuote = new StreamSearcher.Char(new char[] {'"'});

  public interface Token {
    public String name();
    public int ordinal();
  }

  public static enum Delimiter implements Token {
    SLASH('/'), DOT('.'), COLON(':'), SEMI_COLON(';'), ASTERISK('*'), PAREN_OPEN('('), PAREN_CLOSE(')'), BRACKET_OPEN('['), BRACKET_CLOSE(']'), BRACE_OPEN('{'), BRACE_CLOSE('}'), COMMA(','), ARRAY('[', ']'), PLUS('+'), PLUS_PLUS('+', '+'), PLUS_EQ('+', '='), MINUS('-'), MINUS_MINUS('-', '-'), MINUS_EQ('-', '='), GT('>'), LT('<'), GTGT('>', '>'), GTGTGT('>', '>', '>'), LTLT('<', '<'), LTLTLT('<', '<', '<'), GTE('>', '='), LTE('<', '='), EQ('='), EQEQ('=', '='), CARAT('^'), PERCENT('%'), EXCLAMATION('!'), TILDE('~'), AMPERSAND('&'), AND('&', '&'), PIPE('|'), OR('|', '|'), AT('@'), QUESTION('?');

    public final char[] ch;

    Delimiter(final char ... ch) {
      this.ch = ch;
    }
  }

  public static enum Span implements Token {
    WHITESPACE, LINE_COMMENT, BLOCK_COMMENT, NUMBER, CHARACTER, STRING, WORD;
  }

  public static enum Keyword implements Token {
    // NOTE: The declaration list of Keyword(s) must be in sorted alphabetical order!
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

    /*protected static int[] shrink(final int[] keywords, final int size) {
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
    }*/

    public final String lcname;
    public final int[][] children = new int[name().length() + 1][];

    Keyword() {
      this.lcname = name().toLowerCase();
    }

    public String toString() {
      return lcname;
    }
  }

  public static Audit tokenize(final File file) throws IOException {
    final char[] chars = new char[(int)file.length()];
    final Audit audit = new Audit(file, chars);
    final FileInputStream in = new FileInputStream(file);
    int i = 0;
    int b = -1;
    char ch;
    Token token = null;
    int len = 0;

    while (i < chars.length) {
      if ((b = in.read()) == -1)
        throw new UnexpectedException("Unexpected end of stream.");

      ch = chars[i++] = (char)b;
      if ('0' <= ch && ch <= '9' && (token == null || token != Span.WORD)) {
        if (token != Span.NUMBER) {
          audit.push(token, i - len, len);
          len = 1;
          token = Span.NUMBER;
        }
        else {
          ++len;
        }
      }
      else if (('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z') || (len != 0 && '0' <= ch && ch <= '9') || ch == '$' || ch == '_') {
        // TODO: Handle 0x0000 and 0b0000
        if (token == Span.NUMBER && (ch == 'd' || ch == 'D' || ch == 'f' || ch == 'F' || ch == 'l' || ch == 'L')) {
          ++len;
        }
        else if (token == Span.WORD) {
          ++len;
        }
        else if (token == null || token == Span.WHITESPACE || !(token instanceof Keyword)) {
          audit.push(token, i - len, len);
          len = 1;

          final int found = Util.binarySearch(Keyword.INDICES, ch, 0);
          token = found < 0 ? Span.WORD : Keyword.values()[found];
        }
        else {
          final int[] children = ((Keyword)token).children[len - 1];
          if (children != null) {
            final int found = Util.binarySearch(children, ch, len);
            token = found < 0 ? Span.WORD : Keyword.values()[children[found]];
          }
          else if (token.name().length() <= len || ((Keyword)token).lcname.charAt(len) != ch) {
            token = Span.WORD;
          }

          ++len;
        }
      }
      else if (ch == '\n' || ch == '\r' || ch == ' ' || ch == '\t') {
        if (token == Span.WHITESPACE) {
          ++len;
        }
        else {
          audit.push(token, i - len, len);
          len = 1;
          token = Span.WHITESPACE;
        }
      }
      else if (ch == '.') {
        if (token == null || token == Span.WHITESPACE || token == Delimiter.BRACKET_OPEN || token == Delimiter.BRACE_OPEN) {
          len = 1;
          token = Span.NUMBER;
        }
        else if (token == Span.NUMBER) {
          ++len;
        }
        else {
          audit.push(token, i - len, len);
          len = 1;
          token = Delimiter.DOT;
        }
      }
      else if (ch == '&') {
        if (token == Delimiter.AMPERSAND) { // &&
          len = 2;
          token = Delimiter.AND;
        }
        else {
          audit.push(token, i - len, len);
          len = 1;
          token = Delimiter.AMPERSAND;
        }
      }
      else if (ch == '|') {
        if (token == Delimiter.PIPE) { // ||
          len = 2;
          token = Delimiter.OR;
        }
        else {
          audit.push(token, i - len, len);
          len = 1;
          token = Delimiter.PIPE;
        }
      }
      else if (ch == '=') {
        if (token == Delimiter.LT) { // <=
          len = 2;
          token = Delimiter.LTE;
        }
        else if (token == Delimiter.GT) { // >=
          len = 2;
          token = Delimiter.GTE;
        }
        else if (token == Delimiter.EQ) { // ==
          len = 2;
          token = Delimiter.EQEQ;
        }
        else if (token == Delimiter.MINUS) { // -=
          len = 2;
          token = Delimiter.MINUS_EQ;
        }
        else if (token == Delimiter.PLUS) { // +=
          len = 2;
          token = Delimiter.PLUS_EQ;
        }
        else {
          audit.push(token, i - len, len);
          len = 1;
          token = Delimiter.EQ;
        }
      }
      else if (ch == '<') {
        if (token == Delimiter.LT) { // <<
          len = 2;
          token = Delimiter.LTLT;
        }
        else if (token == Delimiter.LTLT) { // <<<
          len = 3;
          token = Delimiter.LTLTLT;
        }
        else {
          audit.push(token, i - len, len);
          len = 1;
          token = Delimiter.LT;
        }
      }
      else if (ch == '>') {
        if (token == Delimiter.GT) { // >>
          len = 2;
          token = Delimiter.GTGT;
        }
        else if (token == Delimiter.GTGT) { // >>>
          len = 3;
          token = Delimiter.GTGTGT;
        }
        else {
          audit.push(token, i - len, len);
          len = 1;
          token = Delimiter.GT;
        }
      }
      else if (ch == '-') {
        if (token == Delimiter.MINUS) { // --
          len = 2;
          token = Delimiter.MINUS_MINUS;
        }
        else {
          audit.push(token, i - len, len);
          len = 1;
          token = Delimiter.MINUS;
        }
      }
      else if (ch == '+') {
        if (token == Delimiter.PLUS) { // ++
          len = 2;
          token = Delimiter.PLUS_PLUS;
        }
        else {
          audit.push(token, i - len, len);
          len = 1;
          token = Delimiter.PLUS;
        }
      }
      else if (ch == '~') {
        audit.push(token, i - len, len);
        len = 1;
        token = Delimiter.TILDE;
      }
      else if (ch == '!') {
        audit.push(token, i - len, len);
        len = 1;
        token = Delimiter.EXCLAMATION;
      }
      else if (ch == '@') {
        audit.push(token, i - len, len);
        len = 1;
        token = Delimiter.AT;
      }
      else if (ch == '^') {
        audit.push(token, i - len, len);
        len = 1;
        token = Delimiter.CARAT;
      }
      else if (ch == '%') {
        audit.push(token, i - len, len);
        len = 1;
        token = Delimiter.PERCENT;
      }
      else if (ch == ',') {
        audit.push(token, i - len, len);
        len = 1;
        token = Delimiter.COMMA;
      }
      else if (ch == ';') {
        audit.push(token, i - len, len);
        len = 1;
        token = Delimiter.SEMI_COLON;
      }
      else if (ch == ':') {
        audit.push(token, i - len, len);
        len = 1;
        token = Delimiter.COLON;
      }
      else if (ch == '?') {
        audit.push(token, i - len, len);
        len = 1;
        token = Delimiter.QUESTION;
      }
      else if (ch == '(') {
        audit.push(token, i - len, len);
        len = 1;
        token = Delimiter.PAREN_OPEN;
      }
      else if (ch == ')') {
        audit.push(token, i - len, len);
        len = 1;
        token = Delimiter.PAREN_CLOSE;
      }
      else if (ch == '{') {
        audit.push(token, i - len, len);
        len = 1;
        token = Delimiter.BRACE_OPEN;
      }
      else if (ch == '}') {
        audit.push(token, i - len, len);
        len = 1;
        token = Delimiter.BRACE_CLOSE;
      }
      else if (ch == '[') {
        audit.push(token, i - len, len);
        len = 1;
        token = Delimiter.BRACKET_OPEN;
      }
      else if (ch == ']') {
        if (token == Delimiter.BRACKET_OPEN) { // []
          len = 2;
          token = Delimiter.ARRAY;
        }
        else {
          audit.push(token, i - len, len);
          len = 1;
          token = Delimiter.BRACKET_CLOSE;
        }
      }
      else if (ch == '/') {
        if (token == Delimiter.SLASH) { // Start of line comment
          // find end of line
          // index from // to end of comment, not including newline
          // this is the only situation where the token is added at time of detection of end of block, cause the eol char is not supposed to be a part of the
          // token
          len = eol.search(in, chars, i);
          audit.push(Span.LINE_COMMENT, i - 1, len + 2);
          i += len;
          len = 0;
          token = null;
        }
        else {
          audit.push(token, i - len, len);
          len = 1;
          token = Delimiter.SLASH;
        }
      }
      else if (ch == '*') {
        if (token == Delimiter.SLASH) { // Start of block comment
          // find end of block comment
          // index from /* to */ including any & all characters between
          i += len = closeComment.search(in, chars, i);
          len += 2;
          token = Span.BLOCK_COMMENT;
        }
        else {
          audit.push(token, i - len, len);
          len = 1;
          token = Delimiter.ASTERISK;
        }
      }
      else if (ch == '\'') {
        audit.push(token, i - len, len);
        len = 1;
        int t;
        i += t = singleQuote.search(in, chars, i);
        len += t;
        // take care of '\'' situation
        // TODO: Handle '\u0000' and '\0'
        if (chars[i - 2] == '\\' && len == 3) {
          i += t = singleQuote.search(in, chars, i);
          len += t;
        }

        token = Span.CHARACTER;
      }
      else if (ch == '"') {
        audit.push(token, i - len, len);
        len = 1;
        int l;
        i += l = doubleQuote.search(in, chars, i);
        len += l;
        // take care of \" situation
        if (chars[i - 2] == '\\') {
          i += l = doubleQuote.search(in, chars, i);
          len += l;
        }

        token = Span.STRING;
      }
      else {
        System.err.print(ch);
      }
    }

    // add the last token, because its final delimiter can be the EOF
    audit.push(token, i - len + 1, len);

    return audit;
  }
}