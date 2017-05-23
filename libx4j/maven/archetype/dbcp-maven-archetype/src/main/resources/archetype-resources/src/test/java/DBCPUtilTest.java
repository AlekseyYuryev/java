package ${package};

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;
import org.safris.commons.lang.Resource;
import org.safris.commons.lang.Resources;
import org.safris.commons.xml.XMLException;

public class DBCPUtilTest {
  @Test
  public void testCreateDataSource() throws IOException, SQLException, XMLException {
    final Resource resource = Resources.getResourceOrFile("dbcp.xml");
    try {
      DBCPUtil.createDataSource(resource.getURL());
    }
    catch (final SQLException e) {
      // Expect "Cannot load JDBC driver class" message, as this means the API is working
      if (!"Cannot load JDBC driver class 'org.postgresql.Driver'".equals(e.getMessage()))
        throw e;
    }
  }
}