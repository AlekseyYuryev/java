package org.safris.xdb.xde;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.safris.commons.lang.Pair;
import org.safris.xdb.xdl.DBVendor;

public class XDEException extends SQLException {
  private static final long serialVersionUID = 400839108529773414L;
  private static final Map<DBVendor,Map<String,ErrorSpec>> errorSpecs = new HashMap<DBVendor,Map<String,ErrorSpec>>();

  public static class ErrorSpec {
    @SafeVarargs
    public ErrorSpec(final Pair<DBVendor,String> ... specs) {
      for (final Pair<DBVendor,String> spec : specs) {
        Map<String,ErrorSpec> sqlCodeToException = XDEException.errorSpecs.get(spec.a);
        if (sqlCodeToException == null)
          XDEException.errorSpecs.put(spec.a, sqlCodeToException = new HashMap<String,ErrorSpec>());

        sqlCodeToException.put(spec.b, this);
      }
    }
  }

  public static final ErrorSpec UNIQUE_VIOLATION = new ErrorSpec(new Pair<DBVendor,String>(DBVendor.POSTGRE_SQL, "23505"));
  public static final ErrorSpec UNDEFINED_TABLE = new ErrorSpec(new Pair<DBVendor,String>(DBVendor.POSTGRE_SQL, "42P01"));
  public static final ErrorSpec NOT_NULL_VIOLATION = new ErrorSpec(new Pair<DBVendor,String>(DBVendor.POSTGRE_SQL, "23502"));
  public static final ErrorSpec UNDEFINED_COLUMN = new ErrorSpec(new Pair<DBVendor,String>(DBVendor.POSTGRE_SQL, "42703"));

  public static final ErrorSpec UNIMPLEMENTED = new ErrorSpec();

  public static XDEException lookup(final SQLException e, final DBVendor vendor) {
    Map<String,ErrorSpec> sqlCodeToException = errorSpecs.get(vendor);
    if (sqlCodeToException == null) {
      System.err.println("[WARNING] THE VENDOR " + vendor + " DOES NOT EXIST IN THE XDEException library!!!!!!!!!!!!!!! Add it!!!!! http://www.postgresql.org/docs/8.3/static/errcodes-appendix.html");
      return new XDEException(e, UNIMPLEMENTED);
    }

    final ErrorSpec errorSpec = sqlCodeToException.get(e.getSQLState());
    if (errorSpec == null) {
      System.err.println("[WARNING] THE SQLSTATE " + e.getSQLState() + " FOR VENDOR " + vendor + " DOES NOT EXIST IN THE XDEException library!!!!!!!!!!!!!!! Add it!!!!! http://www.postgresql.org/docs/8.3/static/errcodes-appendix.html");
      return new XDEException(e, UNIMPLEMENTED);
    }

    return new XDEException(e, errorSpec);
  }

  public static XDEException lookup(final SQLException e) {
    return lookup(e, null);
  }

  private final ErrorSpec errorSpec;

  private XDEException(final SQLException e, final ErrorSpec errorSpec) {
    super(e.getMessage(), e.getSQLState(), e.getErrorCode(), e.getCause());
    setStackTrace(e.getStackTrace());
    this.errorSpec = errorSpec;
  }

  protected XDEException(final String message) {
    super(message);
    this.errorSpec = null;
  }

  public ErrorSpec getErrorSpec() {
    return errorSpec;
  }
}