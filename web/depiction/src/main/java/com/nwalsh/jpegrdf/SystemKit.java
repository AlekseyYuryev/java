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

/*
 * $Id: SystemKit.java,v 1.3 2004/05/27 13:40:03 nwalsh Exp $
 */
package com.nwalsh.jpegrdf;

public class SystemKit {

  public static int verbose = 0;
  public static boolean debug = false;

  public static void abort(int rc, String message) {
    System.err.println(message);
    System.exit(rc);
  }

  public static void message(int level, String message) {
    if (SystemKit.verbose >= level) {
      System.err.println(message);
    }
  }

  public static String getUsageMessage() {
    String lines[] =
      {
  "JpegRDF Version " + JpegRDF.version,
  "",
  "Usage: com.nwalsh.jpegrdf.JpegRDF [options] [commands] jpegfile(s)",
  "",
  "Where options are:",
  "",
  "  -h               Show this help message",
  "  -v               Verbose mode, use more than once for higher values",
  "  -a               For query, all queries must match",
  "                    (default is one query must match)",
  "  -s               Show RDF",
  "  -rdf <rdffile>   Put specified RDF in the jpeg file(s)",
  "  -e               Load <rdffile> using the specified encoding",
  "",
  "And commands are:",
  "",
  "  -ns <prefix> <uri>    Associate <uri> with namespace prefix <prefix>",
  "  -ar <qname> <uri>     Add resource <qname> with value <uri>",
  "  -al <qname> <literal> Add literal <qname> with value <literal>",
  "  -dr <qname> <uri>     Delete resource <qname> with value <uri>",
  "  -dl <qname> <literal> Delete literal <qname> with value <literal>",
  "  -qr <qname> <uri>     Query resource <qname> with value <uri>",
  "  -ql <qname> <literal> Query literal <qname> with value <literal>",
  "  -q  <qname>           Query for <qname>* property with any value",
  "  -d  <qname>           Delete all properties named <qname>*",
  "  --                    Stop processing commands, treat remaining arguments",
  "                        as filenames",
      };

    String usage = "";
    for (int count = 0;
   count < lines.length;
   count++) {
      usage += lines[count] + "\n";
    }

    return usage;
  }

  public static void debug(String string) {
    if (SystemKit.debug) {
      System.out.println(
       "<!-- [debug] " + string + " -->");
    }
  }
}
