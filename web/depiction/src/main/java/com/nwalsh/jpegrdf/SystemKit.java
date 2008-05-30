/*  Copyright 2008 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
