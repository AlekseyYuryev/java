/*  Copyright Safris Software 2006
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.commons.xml.binding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * http://www.w3.org/TR/xmlschema11-2/#language
 */
public final class Language implements CharSequence {
  public static Language parseLanguage(String string) {
    if (string == null)
      throw new NullPointerException("string == null");

    string = string.trim();
    if (string.length() < LANGUAGE_FRAG_MIN_LENGTH)
      throw new IllegalArgumentException(string);

    final StringTokenizer tokenizer = new StringTokenizer(string, "-");
    final Collection<String> languages = new ArrayList<String>();
    while (tokenizer.hasMoreTokens())
      languages.add(tokenizer.nextToken());

    return new Language(languages);
  }

  private static final Pattern firstPattern = Pattern.compile("[a-zA-Z]{1,8}");
  private static final Pattern otherPattern = Pattern.compile("[a-zA-Z0-9]{1,8}");
  private static final int LANGUAGE_FRAG_MIN_LENGTH = 1;
  private final String[] language;
  private String encoded = null;

  public Language(String ... language) {
    if (language == null)
      throw new NullPointerException("language == null");

    if (language.length == 0)
      throw new IllegalArgumentException("language.length == 0");

    if (!firstPattern.matcher(language[0]).matches())
      throw new IllegalArgumentException(language[0]);

    for (int i = 1; i < language.length; i++)
      if (!otherPattern.matcher(language[i]).matches())
        throw new IllegalArgumentException(language[i]);

    this.language = language;
  }

  public Language(final Collection<String> language) {
    this(language != null ? language.toArray(new String[language.size()]) : null);
  }

  public String[] getLanguage() {
    return language;
  }

  public int length() {
    return toString().length();
  }

  public char charAt(final int index) {
    return toString().charAt(index);
  }

  public CharSequence subSequence(final int start, final int end) {
    return toString().subSequence(start, end);
  }

  public boolean equals(final Object obj) {
    if (obj == this)
      return true;

    if (!(obj instanceof Year))
      return false;

    final Language that = (Language)obj;
    return this.language != null ? Arrays.equals(this.language, that.language) : that.language == null;
  }

  public int hashCode() {
    return language != null ? Arrays.hashCode(language) : -1;
  }

  public String toString() {
    if (encoded != null)
      return encoded;

    if (language == null || language.length == 0)
      return encoded = "";

    final StringBuffer buffer = new StringBuffer();
    for (final String string : language)
      buffer.append("-").append(string);

    return encoded = buffer.substring(1);
  }
}