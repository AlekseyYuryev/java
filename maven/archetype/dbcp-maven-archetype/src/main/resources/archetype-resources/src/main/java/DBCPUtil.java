package ${package};

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.safris.xsb.runtime.Bindings;
import org.safris.commons.dbcp.DataSources;
import org.safris.commons.dbcp.xe.dbcp_dbcp;
import org.safris.commons.xml.XMLException;
import org.xml.sax.InputSource;

public class DBCPUtil {
  public static DataSource createDataSource(final URL url) throws IOException, SQLException, XMLException {
    final dbcp_dbcp dbcp = (dbcp_dbcp)Bindings.parse(new InputSource(url.openStream()));
    return DataSources.createDataSource(dbcp);
  }
}