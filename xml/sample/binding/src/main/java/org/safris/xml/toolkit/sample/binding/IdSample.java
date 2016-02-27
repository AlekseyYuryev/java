/* Copyright (c) 2006 Seva Safris
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

package org.safris.xml.toolkit.sample.binding;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.toolkit.sample.binding.id.$id_bookType;
import org.safris.xml.toolkit.sample.binding.id.id_directory;
import org.w3.x2001.xmlschema.$xs_IDREFS;
import org.xml.sax.InputSource;

public class IdSample {
  public static void main(final String[] args) throws Exception {
    new IdSample().runSample();
  }

  public Binding runSample() throws Exception {
    File file = new File("src/main/resources/xml/id.xml");
    if (!file.exists())
      throw new Error("File " + file.getAbsolutePath() + " does not exist.");

    if (!file.canRead())
      throw new Error("File " + file.getAbsolutePath() + " is not readable.");

    id_directory directory = (id_directory)Bindings.parse(new InputSource(new FileInputStream(file)));
    List<$id_bookType> books = directory._book();
    for ($id_bookType book : books) {
      String shortName = book._author(0).text();
      id_directory._author._id$ authorId = id_directory._author._id$.lookupId(shortName);
      id_directory._author author = (id_directory._author)authorId.owner();
      System.out.print(author._name(0).text() + " is the author of " + book._title(0).text() + ".");
      if (book._co_authors() != null) {
        $xs_IDREFS co_authors = book._co_authors(0);
        if (co_authors.text() != null) {
          StringBuffer buffer = new StringBuffer();
          for (Object co_authorHandle : co_authors.text()) {
            id_directory._author._id$ co_authorId = id_directory._author._id$.lookupId((String)co_authorHandle);
            id_directory._author co_author = (id_directory._author)co_authorId.owner();
            buffer.append(", ").append(co_author._name(0).text());
          }

          System.out.print(" " + buffer.substring(2));
          if (co_authors.text().size() == 1)
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