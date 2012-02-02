/*  Copyright Safris Software 2012
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

package org.safris.xdb.xdl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.safris.commons.lang.PackageLoader;
import org.safris.commons.lang.PackageNotFoundException;
import org.safris.xdb.xdl.$xdl_tableType;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.generator.compiler.runtime.ComplexType;
import org.xml.sax.InputSource;

public abstract class XDLParser {
  static {
    try {
      PackageLoader.getSystemPackageLoader().loadPackage(xdl_database.class.getPackage().getName());
    }
    catch (PackageNotFoundException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  protected static xdl_database parseArguments(final File xdlFile, final File outDir) {
    if (xdlFile == null)
      throw new IllegalArgumentException("xdlFile == null");

    if (!xdlFile.exists())
      throw new IllegalArgumentException("!xdlFile.exists()");

    if (outDir != null && !outDir.exists())
      throw new IllegalArgumentException("!outDir.exists()");

    try {
      final InputStream in = xdlFile.toURL().openStream();
      final xdl_database database = (xdl_database)Bindings.parse(new InputSource(in));
      in.close();
      return database;
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected static void writeOutput(final String output, final File file) {
    try {
      if (file == null) {
        System.out.println(output);
      }
      else {
        final FileOutputStream out = new FileOutputStream(file);
        out.write(output.getBytes());
        out.close();
      }
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private final Map<String,$xdl_tableType> tableNameToTable = new HashMap<String,$xdl_tableType>();

  protected final xdl_database database;

  public XDLParser(final xdl_database database, final boolean flatten) {
    this.database = database;
    // First, register the table names to be referencable by the @extends attribute
    for ($xdl_tableType table : database.get_table())
      tableNameToTable.put(table.get_name$().getText(), table);

    for ($xdl_tableType table : database.get_table())
      mergeTable(table);
  }

  private final Set<String> mergedTables = new HashSet<String>();

  private void mergeTable(final $xdl_tableType table) {
    if (mergedTables.contains(table.get_name$().getText()))
      return;

    mergedTables.add(table.get_name$().getText());
    if (table.get_extends$() == null)
      return;

    final $xdl_tableType superTable = tableNameToTable.get(table.get_extends$().getText());
    mergeTable(superTable);
    if (table.get_column() != null) {
      table.get_column().addAll(0, superTable.get_column());
    }
    else {
      final List<$xdl_columnType<? extends ComplexType>> columns = superTable.get_column();
      for ($xdl_columnType<? extends ComplexType> column : columns)
        table.add_column(column);
    }

    if (superTable.get_constraints() == null)
      return;

    final List<$xdl_tableType._constraints> constraints = superTable.get_constraints();
    if (table.get_constraints() == null) {
      table.add_constraints(constraints.get(0));
      return;
    }

    if (constraints.get(0).get_primaryKey() == null)
      return;

    final List<$xdl_tableType._constraints> constraints2 = table.get_constraints();
    if (constraints2 != null) {
      if (constraints2.get(0).get_primaryKey() != null)
        throw new Error(table.get_name$().getText() + " has a primary key, which conflicts with the super type's primary key " + superTable.get_name$().getText());

      constraints2.get(0).add_primaryKey(constraints.get(0).get_primaryKey().get(0));
    }
  }
}
