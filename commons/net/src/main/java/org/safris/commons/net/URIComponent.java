package org.safris.commons.net;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URIComponent {
  /**
   * Decodes the passed UTF-8 String using a specification that's compatible with
   * JavaScript's <code>decodeURIComponent</code> function. Returns
   * <code>null</code> if the String is <code>null</code>.
   *
   * @param uri The UTF-8 encoded String to be decoded
   * @param   enc   The name of a supported
   *    <a href="../lang/package-summary.html#charenc">character
   *    encoding</a>.
   * @return the decoded String
   */
  public static String decode(final String uri, final String encoding) throws UnsupportedEncodingException {
    return uri != null ? URLDecoder.decode(uri, encoding) : null;
  }

  /**
   * Encodes the passed String as UTF-8 using a specification that's compatible
   * with JavaScript's <code>encodeURIComponent</code> function. Returns
   * <code>null</code> if the String is <code>null</code>.
   *
   * @param uri The String to be encoded
   * @param   enc   The name of a supported
   *    <a href="../lang/package-summary.html#charenc">character
   *    encoding</a>.
   * @return the encoded String
   */
  public static String encode(final String uri, final String encoding) throws UnsupportedEncodingException {
    return uri != null ? URLEncoder.encode(uri, encoding).replace("+", "%20") : null; //.replace("%21", "!").replace("%27", "'").replace("%28", "(").replace("%29", ")").replace("%7E", "~") : null;
  }

  private URIComponent() {
  }
}