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



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

public class MemoryJavaFileObject extends SimpleJavaFileObject {
  /**
   * Byte code created by the compiler will be stored in this
   * ByteArrayOutputStream so that we can later get the
   * byte array out of it
   * and put it in the memory as an instance of our class.
   */
  private final ByteArrayOutputStream bos = new ByteArrayOutputStream();

  /**
   * Registers the compiled class object under URI
   * containing the class full name
   *
   * @param name
   *            Full name of the compiled class
   * @param kind
   *            Kind of the data. It will be CLASS in our case
   */
  public MemoryJavaFileObject(final String name, final JavaFileObject.Kind kind) {
    super(URI.create("string:///" + name.replace('.', '/') + kind.extension), kind);
  }

  /**
   * Will be used by our file manager to get the byte code that
   * can be put into memory to instantiate our class
   *
   * @return compiled byte code
   */
  public byte[] getBytes() {
    return bos.toByteArray();
  }

  /**
   * Will provide the compiler with an output stream that leads
   * to our byte array. This way the compiler will write everything
   * into the byte array that we will instantiate later
   */
  public OutputStream openOutputStream() throws IOException {
    return bos;
  }
}
