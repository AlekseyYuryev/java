/* Copyright (c) 2017 lib4j
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

package org.safris.rdb.dmlx;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.transform.TransformerException;

import org.libx4j.xsb.compiler.processor.GeneratorContext;
import org.libx4j.xsb.compiler.processor.reference.SchemaReference;
import org.libx4j.xsb.generator.Generator;
import org.libx4j.xsb.runtime.Bindings;
import org.safris.commons.lang.Resources;
import org.safris.commons.util.Collections;
import org.safris.commons.xml.NamespaceURI;
import org.safris.commons.xml.XMLException;
import org.safris.rdb.ddlx.Schemas;
import org.safris.rdb.ddlx.xe.ddlx_schema;
import org.safris.rdb.dmlx.xe.$dmlx_data;
import org.xml.sax.InputSource;

public abstract class DMLxTest {
  private static final File sourcesDestDir = new File("target/generated-test-sources/xsb");
  protected static final File resourcesDestDir = new File("target/generated-test-resources/rdb");

  protected static void createSchemas(final String name) throws IOException, TransformerException {
    final URL ddlx = Resources.getResource(name + ".ddlx").getURL();
    final File destFile = new File(resourcesDestDir, name + ".xsd");
    Datas.createXSD(ddlx, destFile);

    final Set<NamespaceURI> excludes = Collections.asCollection(HashSet.class, NamespaceURI.getInstance("http://rdb.safris.org/dmlx.xsd"), NamespaceURI.getInstance("http://commons.safris.org/xml/datatypes.xsd"));
    final GeneratorContext generatorContext = new GeneratorContext(sourcesDestDir, true, true, false, null, excludes);
    new Generator(generatorContext, java.util.Collections.singleton(new SchemaReference(destFile.toURI().toURL(), false)), null).generate();
  }

  protected static void testData(final Connection connection, final String name) throws IOException, SQLException, XMLException {
    final ddlx_schema schema;
    try (final InputStream in = Resources.getResource(name + ".ddlx").getURL().openStream()) {
      schema = (ddlx_schema)Bindings.parse(new InputSource(in));
    }

    Schemas.truncate(connection, Schemas.tables(schema));

    final URL dmlx = Resources.getResource(name + ".dmlx").getURL();
    final $dmlx_data data;
    try (final InputStream in = dmlx.openStream()) {
      data = ($dmlx_data)Bindings.parse(new InputSource(in));
    }

    Datas.loadData(connection, data);
  }
}