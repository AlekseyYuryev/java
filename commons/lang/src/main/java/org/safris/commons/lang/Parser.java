package org.safris.commons.lang;

public class Parser {
  public static void main(final String[] args) {
  }
  
  public void parse(final byte[] bytes) {
    final IntArrayList blocks = Bytes.Byte.indicesOf(bytes, '{', '}', ';');
  }
}
