package org.safris.commons.jci;/*  Copyright Safris Software 2011
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



import java.net.URI;
import java.nio.CharBuffer;
import javax.tools.SimpleJavaFileObject;

public class CharSequenceJavaFileObject extends SimpleJavaFileObject {
  /**
   * CharSequence representing the source code to be compiled
   */
  private CharSequence content;

  /**
   * This constructor will store the source code in the
   * internal "content" variable and register it as a
   * source code, using a URI containing the class full name
   *
   * @param className
   *            name of the public class in the source code
   * @param content
   *            source code to compile
   */
  public CharSequenceJavaFileObject(final String className, final CharSequence content) {
    super(URI.create("string:///" + className.replace('.', '/') + SimpleJavaFileObject.Kind.SOURCE.extension), SimpleJavaFileObject.Kind.SOURCE);
    this.content = content;
  }

  /**
   * Answers the CharSequence to be compiled. It will give
   * the source code stored in variable "content"
   */
  public CharSequence getCharContent(final boolean ignoreEncodingErrors) {
    return CharBuffer.wrap(content);
  }
}
