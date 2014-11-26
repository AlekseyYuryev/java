/* Copyright (c) 2014 Seva Safris
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

package org.safris.xdb.xde;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.safris.commons.lang.Resources;
import org.safris.commons.lang.Strings;
import org.safris.commons.lang.reflect.Classes;
import org.safris.xdb.xde.column.BigInt;
import org.safris.xdb.xde.column.Blob;
import org.safris.xdb.xde.column.Char;
import org.safris.xdb.xde.column.DateTime;
import org.safris.xdb.xde.column.Decimal;
import org.safris.xdb.xde.column.Int;
import org.safris.xdb.xde.column.MediumInt;
import org.safris.xdb.xde.column.SmallInt;
import org.safris.xdb.xde.column.Timestamp;
import org.safris.xdb.xde.column.TinyInt;
import org.safris.xdb.xde.column.Varchar;
import org.safris.xdb.xdl.$xdl_bigint;
import org.safris.xdb.xdl.$xdl_blob;
import org.safris.xdb.xdl.$xdl_boolean;
import org.safris.xdb.xdl.$xdl_char;
import org.safris.xdb.xdl.$xdl_columnType;
import org.safris.xdb.xdl.$xdl_date;
import org.safris.xdb.xdl.$xdl_dateTime;
import org.safris.xdb.xdl.$xdl_decimal;
import org.safris.xdb.xdl.$xdl_enum;
import org.safris.xdb.xdl.$xdl_float;
import org.safris.xdb.xdl.$xdl_int;
import org.safris.xdb.xdl.$xdl_mediumint;
import org.safris.xdb.xdl.$xdl_namedType;
import org.safris.xdb.xdl.$xdl_smallint;
import org.safris.xdb.xdl.$xdl_tableType;
import org.safris.xdb.xdl.$xdl_time;
import org.safris.xdb.xdl.$xdl_timestamp;
import org.safris.xdb.xdl.$xdl_tinyint;
import org.safris.xdb.xdl.$xdl_varchar;
import org.safris.xdb.xdl.DBVendor;
import org.safris.xdb.xdl.DDL;
import org.safris.xdb.xdl.DDLTransform;
import org.safris.xdb.xdl.xdl_database;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.w3.x2001.xmlschema.$xs_anySimpleType;
import org.xml.sax.InputSource;

public class EntityGenerator {
  private static final Map<String,$xdl_tableType> tableNameToTable = new HashMap<String,$xdl_tableType>();

  public static void go(final String[] args) throws Exception {
    final xdl_database database = (xdl_database)Bindings.parse(new InputSource(Resources.getResource("/library.xdl").getURL().openStream()));
    for (final $xdl_tableType table : database._table())
      tableNameToTable.put(table._name$().text(), table);

    final File dir = new File("target/generated-test-sources/xde");
    final String pkg = "xdb.xde";

    final File outDir = new File(dir, pkg.replace('.', '/'));
    if (!outDir.exists())
      if (!outDir.mkdirs())
        throw new Error("Unable to create output dir: " + outDir.getAbsolutePath());

    final String classSimpleName = database._name$().text();
    String code = "package xdb.xde;\n\n";
    code += "public final class " + classSimpleName + " extends " + Schema.class.getName() + " {\n";
    code += "  private static final " + Entity.class.getName() + "[] identity = new " + Entity.class.getName() + "[] {";
    DDL[][] ddls = null;
    for (int i = 0; i < DBVendor.values().length; i++) {
      final DBVendor vendor = DBVendor.values()[i];
      DDL[] ddl = DDLTransform.createDDL(database, vendor, null);
      if (ddls == null)
        ddls = new DDL[ddl.length][DBVendor.values().length];

      for (int j = 0; j < ddl.length; j++)
        ddls[j][i] = ddl[j];
    }

    String identity = "";
    for (final DDL[] ddl : ddls)
      identity += ", " + Strings.toTitleCase(ddl[0].name) + ".identity";

    code += identity.substring(2) + "};\n\n";

    code += "  public static void dropDDL() throws " + SQLException.class.getName() + " {\n";
    code += "    " + Schema.class.getName() + ".dropDDL(getConnection(" + classSimpleName + ".class), identity);\n";
    code += "  }\n\n";

    code += "  public static void createDDL() throws " + SQLException.class.getName() + " {\n";
    code += "    " + Schema.class.getName() + ".createDDL(getConnection(" + classSimpleName + ".class), identity);\n";
    code += "  }\n\n";

    String tables = "";
    // First create the abstract entities
    for (final $xdl_tableType table : database._table())
      if (table._abstract$().text())
        tables += "\n\n" + makeTable(table, null);

    // Then, in proper inheritance order, the real entities
    for (final DDL[] ddl : ddls)
      tables += "\n\n" + makeTable(tableNameToTable.get(ddl[0].name), ddl);

    code += tables.substring(2) + "\n\n";
    code += "  private " + classSimpleName + "() {\n  }\n}";

    final File javaFile = new File(outDir, classSimpleName + ".java");
    final FileOutputStream out = new FileOutputStream(javaFile);
    out.write(code.getBytes());
    out.close();
  }

  private static final Object THIS = new Object();

  private static class Type {
    private static Type getType(final $xdl_tableType table, final $xdl_columnType column) {
      final Class<?> cls = column.getClass().getSuperclass();
      final Object _default = getDefault(column);
      final Object[] params = new Object[] {THIS, Strings.toInstanceCase(column._name$().text()), column._name$().text(), _default, isUnique(table, column), isPrimary(table, column), column._null$().text()};
      if (column instanceof $xdl_blob) {
        final $xdl_blob type = ($xdl_blob)column;
        return new Type(column, Blob.class, params);
      }

      if (column instanceof $xdl_boolean) {
        final $xdl_boolean type = ($xdl_boolean)column;
        return new Type(column, org.safris.xdb.xde.column.Boolean.class, params);
      }

      if (column instanceof $xdl_char) {
        final $xdl_char type = ($xdl_char)column;
        return new Type(column, Char.class, params);
      }

      if (column instanceof $xdl_date) {
        final $xdl_date type = ($xdl_date)column;
        return new Type(column, org.safris.xdb.xde.column.Date.class, params);
      }

      if (column instanceof $xdl_dateTime) {
        final $xdl_dateTime type = ($xdl_dateTime)column;
        return new Type(column, DateTime.class, params);
      }

      if (column instanceof $xdl_time) {
        final $xdl_time type = ($xdl_time)column;
        return new Type(column, org.safris.xdb.xde.column.Time.class, params);
      }

      if (column instanceof $xdl_timestamp) {
        final $xdl_timestamp type = ($xdl_timestamp)column;
        return new Type(column, Timestamp.class, params);
      }

      if (column instanceof $xdl_float) {
        final $xdl_float type = ($xdl_float)column;
        return new Type(column, Char.class, params);
      }

      if (column instanceof $xdl_decimal) {
        final $xdl_decimal type = ($xdl_decimal)column;
        return new Type(column, Decimal.class, params, type._precision$().text(), type._decimal$().text(), type._unsigned$().text(), type._min$().text(), type._max$().text());
      }

      if (column instanceof $xdl_int) {
        final $xdl_int type = ($xdl_int)column;
        return new Type(column, Int.class, params, type._precision$().text(), type._unsigned$().text(), type._min$().text(), type._max$().text());
      }

      if (column instanceof $xdl_mediumint) {
        final $xdl_mediumint type = ($xdl_mediumint)column;
        return new Type(column, MediumInt.class, params, type._precision$().text(), type._unsigned$().text(), type._min$().text(), type._max$().text());
      }

      if (column instanceof $xdl_smallint) {
        final $xdl_smallint type = ($xdl_smallint)column;
        return new Type(column, SmallInt.class, params, type._precision$().text(), type._unsigned$().text(), type._min$().text(), type._max$().text());
      }

      if (column instanceof $xdl_tinyint) {
        final $xdl_tinyint type = ($xdl_tinyint)column;
        return new Type(column, TinyInt.class, params, type._precision$().text(), type._unsigned$().text(), type._min$().isNull() ? null : new Short(type._min$().text().shortValue()), type._max$().isNull() ? null : new Short(type._max$().text().shortValue()));
      }

      if (column instanceof $xdl_bigint) {
        final $xdl_bigint type = ($xdl_bigint)column;
        return new Type(column, BigInt.class, params, type._precision$().text(), type._unsigned$().text(), type._min$().text(), type._max$().text());
      }

      if (column instanceof $xdl_varchar) {
        final $xdl_varchar type = ($xdl_varchar)column;
        return new Type(column, Varchar.class, params, type._length$().text());
      }

      if (column instanceof $xdl_enum) {
        final $xdl_enum type = ($xdl_enum)column;
        return new Type(column, org.safris.xdb.xde.column.Enum.class, params);
      }

      throw new IllegalArgumentException("Unknown type: " + cls);
    }

    private final $xdl_columnType column;
    private final Class<? extends Column> type;
    private final Object[] commonParams;
    private final Object[] customParams;

    public Type(final $xdl_columnType column, final Class<? extends Column> type, final Object[] commonParams, final Object ... params) {
      this.column = column;
      this.type = type;
      this.commonParams = commonParams;
      this.customParams = params;
    }

    private String serializeParams() {
      String out = "";
      for (final Object param : commonParams)
        out += ", " + (param == THIS ? "this" : Serializer.serialize(param));

      if (customParams != null)
        for (final Object param : customParams)
          out += ", " + (param == THIS ? "this" : Serializer.serialize(param));

      return out.substring(2);
    }

    public String getType() {
      return type.getName() + (type == org.safris.xdb.xde.column.Enum.class ? "<" + Strings.toTitleCase(column._name$().text()) + ">" : "");
    }

    public String toString() {
      return "new " + getType() + "(" + serializeParams() + (type == org.safris.xdb.xde.column.Enum.class ? ", " + Strings.toTitleCase(column._name$().text()) + ".class" : "") + ")";
    }
  }

  public static Object getDefault(final $xdl_columnType column) {
    try {
      if (column instanceof $xdl_blob /* FIXME: and TEXT too */)
        return null;

      final Method method = Classes.getDeclaredMethodDeep(column.getClass(), "_default$");
      final $xs_anySimpleType value = ($xs_anySimpleType)method.invoke(column);
      if (value.isNull())
        return null;

      if (column instanceof $xdl_enum)
        return Strings.toTitleCase(column._name$().text()) + "." + String.valueOf(value.text());

      if (column instanceof $xdl_bigint)
        return new BigInteger(String.valueOf(value.text()));

      if (column instanceof $xdl_int)
        return Long.valueOf(String.valueOf(value.text()));

      if (column instanceof $xdl_tinyint)
        return Short.valueOf(String.valueOf(value.text()));

      return value.text();
    }
    catch (final Exception e) {
      throw new Error(e);
    }
  }

  private static int getColumnCount($xdl_tableType table, final boolean deep) {
    int count = 0;
    do {
      count += table._column().size();
    }
    while (deep && (table = tableNameToTable.get(table._extends$().text())) != null);
    return count;
  }

  private static int getPrimaryColumnCount($xdl_tableType table, final boolean deep) {
    int count = 0;
    do {
      if (!table._constraints(0)._primaryKey(0).isNull())
        count += table._constraints(0)._primaryKey(0)._column().size();
    }
    while (deep && (table = tableNameToTable.get(table._extends$().text())) != null);
    return count;
  }

  public static String makeTable(final $xdl_tableType table, final DDL[] ddl) {
    final String ext = !table._extends$().isNull() ? Strings.toTitleCase(table._extends$().text()) : Entity.class.getName();
    String out = "";
    String abs = "";
    if (table._abstract$().text())
      abs = table._abstract$().text() ? " abstract" : "";

    final String entityName = Strings.toTitleCase(table._name$().text());
    final int totalColumnCount = getColumnCount(table, true);
    final int totalPrimaryCount = getPrimaryColumnCount(table, true);
    final int localPrimaryCount = getPrimaryColumnCount(table, false);
    out += "  public static" + abs + " class " + entityName + " extends " + ext + " {\n";
    if (!table._abstract$().text()) {
      out += "    protected static final " + DDL.class.getName() + "[] ddl = " + serialize(ddl) + ";\n";
      out += "    protected static final " + Strings.toTitleCase(table._name$().text()) + " identity = new " + Strings.toTitleCase(table._name$().text()) + "();\n\n";
      out += "    protected final " + Column.class.getName() + "<?>[] column;\n";
      out += "    protected final " + Column.class.getName() + "<?>[] primary;\n\n";
      out += "    protected " + DDL.class.getName() + "[] ddl() {\n";
      out += "      return ddl;\n";
      out += "    }\n\n";
      out += "    protected " + Column.class.getName() + "<?>[] column() {\n";
      out += "      return column;\n";
      out += "    }\n\n";
      out += "    protected " + Column.class.getName() + "<?>[] primary() {\n";
      out += "      return primary;\n";
      out += "    }\n\n";
      out += "    protected " + String.class.getName() + " name() {\n";
      out += "      return \"" + table._name$().text() + "\";\n";
      out += "    }\n\n";
      out += "    public " + Strings.toTitleCase(table._name$().text()) + "() {\n";
      out += "      this(new " + Column.class.getName() + "[" + totalColumnCount + "], new " + Column.class.getName() + "[" + totalPrimaryCount + "]);\n";
      out += "    }\n\n";
    }

    String defs = "";
    out += "    protected " + Strings.toTitleCase(table._name$().text()) + "(final " + Column.class.getName() + "<?>[] column, final " + Column.class.getName() + "<?>[] primary) {\n";
    out += "      super(column, primary);\n";
    if (!table._abstract$().text()) {
      out += "      this.column = column;\n";
      out += "      this.primary = primary;\n";
    }

    defs = "";
    int primaryIndex = 0;
    for (int i = 0; i < table._column().size(); i++) {
      final $xdl_columnType column = table._column().get(i);
      final String columnName = Strings.toCamelCase(column._name$().text());
      defs += "\n      column[" + (totalColumnCount - (table._column().size() - i)) + "] = " + (isPrimary(table, column) ? "primary[" + (totalPrimaryCount - (localPrimaryCount - primaryIndex++)) + "] = " : "") + columnName + ";";
    }

    out += defs.substring(1) + "\n    }\n";

    for (int i = 0; i < table._column().size(); i++) {
      final $xdl_columnType column = table._column().get(i);
      out += makeColumn(table, column, i == table._column().size());
    }

    out += "\n\n";
    out += "    public boolean equals(final " + Object.class.getName() + " obj) {\n";
    out += "      if (obj == this)\n        return true;\n\n";
    out += "      if (!(obj instanceof " + entityName + ")" + (!table._extends$().isNull() ? " || !super.equals(obj)" : "") + ")\n        return false;\n\n";
    out += "      final " + entityName + " that = (" + entityName + ")obj;\n";

    String eq = "";
    for (final $xdl_columnType column : table._column())
      eq += " && (this." + Strings.toInstanceCase(column._name$().text()) + ".get() != null ? this." + Strings.toInstanceCase(column._name$().text()) + ".get().equals(that." + Strings.toInstanceCase(column._name$().text()) + ".get()) : that." + Strings.toInstanceCase(column._name$().text()) + ".get() == null)";

    out += "      return " + eq.substring(4) + ";\n    }";
    out += "\n  }";
    return out;
  }

  private static boolean isPrimary(final $xdl_tableType table, final $xdl_columnType column) {
    final $xdl_tableType._constraints._primaryKey constraint = table._constraints(0)._primaryKey(0);
    if (constraint.isNull())
      return false;

    for (final $xdl_namedType col : constraint._column())
      if (column._name$().text().equals(col._name$().text()))
        return true;

    return false;
  }

  private static boolean isUnique(final $xdl_tableType table, final $xdl_columnType column) {
    final $xdl_tableType._constraints._unique constraint = table._constraints(0)._unique(0);
    if (constraint.isNull())
      return false;

    for (final $xdl_namedType col : constraint._column())
      if (column._name$().text().equals(col._name$().text()))
        return true;

    return false;
  }

  private static String serialize(final DDL[] ddls) {
    String out = "";
    for (final DDL ddl : ddls)
      out += ", new " + DDL.class.getName() + "(" + Serializer.serialize(ddl.name) + ", " + Serializer.serialize(ddl.drop) + ", " + Serializer.serialize(ddl.create) + ")";

    return "new " + DDL.class.getName() + "[] {" + out.substring(2) + "}";
  }

  public static String makeColumn(final $xdl_tableType table, final $xdl_columnType column, boolean isLast) {
    final String columnName = Strings.toCamelCase(column._name$().text());
    final String typeName = Strings.toTitleCase(column._name$().text());
    String out = "";
    final Type type = Type.getType(table, column);
    if (column instanceof $xdl_enum) {
      out += "\n    public static enum " + typeName + " {";
      String values = "";
      for (final String value : (($xdl_enum)column)._values$().text())
        values += ", " + value;

      out += values.substring(2) + "}";
    }

    return out + "\n    public final " + type.getType() + " " + columnName + " = " + type + ";";
  }
}