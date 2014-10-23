package org.safris.cdm;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.safris.cdm.lexer.Lexer;
import org.safris.cdm.lexer.Lexer.Delimiter;
import org.safris.cdm.lexer.Lexer.Token;

public class Audit {
  public static class Index {
    public final Token token;
    public Index close;
    public final int start;
    public final int length;

    private Index(final Token token, final int start, final int length) {
      this.token = token;
      this.start = start;
      this.length = length;
    }
  }

  public static class Scope {
    private final Stack<Index> openParen = new Stack<Index>();
    private final Stack<Index> openBracket = new Stack<Index>();
    private final Stack<Index> openBrace = new Stack<Index>();

    public Index push(final Index item) {
      if (item.token == Delimiter.PAREN_OPEN)
        return openParen.push(item);

      if (item.token == Delimiter.BRACKET_OPEN)
        return openBracket.push(item);

      if (item.token == Delimiter.BRACE_OPEN)
        return openBrace.push(item);

      if (item.token == Delimiter.PAREN_CLOSE)
        return openParen.pop().close = item;

      if (item.token == Delimiter.BRACKET_CLOSE)
        return openBracket.pop().close = item;

      if (item.token == Delimiter.BRACE_CLOSE)
        return openBrace.pop().close = item;

      return null;
    }
  }

  public final File file;
  public final char[] chars;
  public final List<Index> indices = new LinkedList<Index>();
  private final Scope scope = new Scope();

  public Audit(final File file, final char[] chars) {
    this.file = file;
    this.chars = chars;
  }

  public void push(final Lexer.Token token, final int start, final int length) {
    final Index index = new Index(token, start - 1, length);
    indices.add(index);
    scope.push(index);
  }

  public String toString() {
    final StringBuffer buffer = new StringBuffer();
    for (final Index index : indices)
      if (index.token instanceof Delimiter)
        buffer.append(((Delimiter)index.token).ch);
      else
        buffer.append(chars, index.start, index.length);

    return buffer.toString();
  }
}