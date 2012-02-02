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

package org.safris.xml.toolkit.sample.binding;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.generator.compiler.runtime.ComplexType;
import org.safris.xml.toolkit.sample.binding.id.$id_bookType;
import org.safris.xml.toolkit.sample.binding.id.id_directory;
import org.w3.x2001.xmlschema.$xs_IDREFS;
import org.xml.sax.InputSource;

public class IdExample {
  public static void main(String[] args) throws Exception {
    new IdExample().runExample();
  }

  public Binding runExample() throws Exception {
    File file = new File("src/main/resources/xml/id.xml");
    if (!file.exists())
      throw new Error("File " + file.getAbsolutePath() + " does not exist.");

    if (!file.canRead())
      throw new Error("File " + file.getAbsolutePath() + " is not readable.");

    id_directory directory = (id_directory)Bindings.parse(new InputSource(new FileInputStream(file)));
    List<$id_bookType<? extends ComplexType>> books = directory.get_book();
    for ($id_bookType<? extends ComplexType> book : books) {
      String shortName = book.get_author().get(0).getText();
      id_directory._author._id$ authorId = id_directory._author._id$.lookupId(shortName);
      id_directory._author author = (id_directory._author)authorId.owner();
      System.out.print(author.get_name().get(0).getText() + " is the author of " + book.get_title().get(0).getText() + ".");
      if (book.get_co_authors() != null) {
        $xs_IDREFS co_authors = book.get_co_authors().get(0);
        if (co_authors.getText() != null) {
          StringBuffer buffer = new StringBuffer();
          for (Object co_authorHandle : co_authors.getText()) {
            id_directory._author._id$ co_authorId = id_directory._author._id$.lookupId((String)co_authorHandle);
            id_directory._author co_author = (id_directory._author)co_authorId.owner();
            buffer.append(", ").append(co_author.get_name().get(0).getText());
          }

          System.out.print(" " + buffer.substring(2));
          if (co_authors.getText().size() == 1)
            System.out.print(" is the co-author.");
          else
            System.out.print(" are co-authors.");
        }
      }

      System.out.println();
    }

    return directory;
  }
}
