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

package com.nwalsh.jpegrdf;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFException;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.SelectorImpl;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.vocabulary.RDF;
import com.nwalsh.jpegrdf.commands.AddLiteralCommand;
import com.nwalsh.jpegrdf.commands.AddResourceCommand;
import com.nwalsh.jpegrdf.commands.AssociateNamespaceCommand;
import com.nwalsh.jpegrdf.commands.DeleteCommand;
import com.nwalsh.jpegrdf.commands.DeleteLiteralCommand;
import com.nwalsh.jpegrdf.commands.DeleteResourceCommand;
import com.nwalsh.jpegrdf.commands.ExifCommand;
import com.nwalsh.jpegrdf.commands.FoundCommand;
import com.nwalsh.jpegrdf.commands.MacroCommand;
import com.nwalsh.jpegrdf.commands.NopCommand;
import com.nwalsh.jpegrdf.commands.QueryLiteralCommand;
import com.nwalsh.jpegrdf.commands.QueryQNameCommand;
import com.nwalsh.jpegrdf.commands.QueryResourceCommand;
import com.nwalsh.jpegrdf.commands.UpdateCommand;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.safris.web.depiction.Comment;
import org.w3c.tools.jpeg.JpegException;
import org.w3c.tools.jpeg.TagDecoder;

/**
 * The main application class.
 *
 * @author Norman Walsh
 * @version $Revision: 1.9 $
 */
public class JpegRDF implements ArgumentTaker
{
  public static String version = "2.3.0";

  private Map mappedQNames = new HashMap();
  private Model model = null;

  // needs localizing
  private Resource subject = null;

  private static final String nsJpegRDF =
  "http://nwalsh.com/rdf/jpegrdf#";

  private static final String cfg_type =
  "http://nwalsh.com/rdf/configuration#jpegrdf";

  //	private Exif exif = new Exif();
  private ExifHelper exifStuff = new ExifHelper();
  private String configFilename = null;
  private Model config = null;
  private String encoding = "UTF-8";
  private String baseUri = "file://" + System.getProperty("user.dir") + "/";
  //@@TODO !!

  private String args[];
  //-- flags
  boolean query = false;
  boolean andQuery = false;

  boolean update = false;
  boolean copyExif = false;
  boolean updateExif = false;

  private MacroCommand commands;

  // (re)move?
  String initRDF = null;
  Model showRDF = null;

  List<String> jpegfiles = new ArrayList<String>();

  private ArgumentsKit optionsMap;
  /**
   * commandsMap is used exactly as optionsMap split is arbitrary to help see
   * what's happening
   */
  private ArgumentsKit commandsMap;

  private int argCount;

  public static void main(String args[])
  {
    // Instantiate a real object
    try
    {
      JpegRDF j = new JpegRDF(args);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public JpegRDF(String args[]) throws JpegException, IOException
  {
    this.args = args;
    exifStuff = new ExifHelper();
    commands = new MacroCommand();
    initOptionsMap();
    initCommandsMap();

    // See usage()
    boolean inOptions = true;
    boolean inCommands = true;

    try
    {
      for(argCount = 0; argCount < args.length; argCount++)
      {
        String arg = args[argCount];

        if(inOptions)
        {
          if(optionsMap.invoke(arg))
          {
            continue;
          }
          inOptions = false;
          exifStuff.initExifPrefixes();
          initializeConfig();
        }

        if(inCommands && arg.equals("--"))
        {
          inCommands = false;
          continue;
        }

        if(!inCommands)
        {
          addJpeg(arg);
          continue;
        }

        if(commandsMap.invoke(arg))
        {
          // errm, already done
        }
        else if(arg.startsWith("-"))
        {
          SystemKit.abort(1, "Unrecognized command: " + arg);
        }
        else
        {
          inCommands = false;
          addJpeg(arg);
        }
      }
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      SystemKit.abort(1, "Unparseable arguments");
    }

    if(commands.size() == 0)
    {
      if(showRDF != null)
      {
        commands.add(new FoundCommand());
      }
      else if(initRDF != null)
      {
        commands.add(new NopCommand());
      }
      else
      {
        SystemKit.abort(1, "No commands? Try -h for help.");
      }
    }

    if(jpegfiles.size() == 0)
    {
      SystemKit.abort(1, "No JPEG files? Try -h for help.");
    }

    if(query && update)
    {
      SystemKit.abort(1, "You can't simultaneously query and update.");
    }

    if(copyExif && updateExif)
    {
      SystemKit.abort(1, "The -exif and -update options are mutually exclusive.");
    }

    // This used to be done only in the query case; now I always do it
    // so that the default namespaces with -exif and other update commands
    // come out right.
    populateModelNamespacePrefixes();

    Iterator jpegs = jpegfiles.iterator();

    // N.B. This refactoring has introduced a non-obvious side-effect
    // When a JpegProcessor is created, it has a private RDF model
    // Each call to process() may augment that model. That model
    // is always returned. It has to be that way in order for
    // the show command to show the RDF for all the matching files.
    JpegProcessor dojpeg = new JpegProcessor(this, andQuery, query, initRDF, baseUri, exifStuff, commands);
    while(jpegs.hasNext())
    {
      String jpegFilename = (String) jpegs.next();
      showRDF = dojpeg.process(jpegFilename);
    }

    if(showRDF != null)
    {
      // Always use cwd as the base here. That makes cwd-based URIs simple,
      // but preserves the full URI if an alternate baseUri was specified.
      String cwd = System.getProperty("user.dir");
      String rdfString = RDFKit.modelToString(showRDF, "file://" + cwd + "/");
//			System.out.println(rdfString);
      this.rdfString = rdfString;
    }
  }

  private String rdfString = null;

  public List<Comment> getRDF(String qname)
  {
    if(qname == null || rdfString == null)
      return null;

    int index1 = rdfString.indexOf(qname);
    int index2 = 0;
    List<Comment> list = new LinkedList<Comment>();
    String string = null;
    try
    {
      do
      {
        index2 = rdfString.indexOf(qname, index1 + 1);
        string = rdfString.substring(index1 + qname.length() + 1, index2 - 2);
        list.add(Comment.decode(string));
        index1 = rdfString.indexOf(qname, index2 + 1);
      }
      while(index1 > 0);
    }
    catch(StringIndexOutOfBoundsException e)
    {
      e.printStackTrace();
    }

    return list;
  }

  public void addAssociateNamespaceCommand()
  {
    String prefix = args[++argCount];
    String uri = args[++argCount];

    // old
    RDFKit.namespaces.put(prefix, uri);
    // new
    commands.add(new AssociateNamespaceCommand(prefix, uri));
  }

  public void addQueryQNameCommand()
  {
    query = true;
    XMLKit.checkPrefix(args[argCount + 1]);
    commands.add(new QueryQNameCommand(args[++argCount]));
  }

  public void addQueryResourceCommand()
  {
    query = true;
    XMLKit.checkQName(args[argCount + 1]);

    String qname = args[++argCount];
    String uri = XMLKit.xmlDecode(args[++argCount]);

    commands.add(new QueryResourceCommand(qname, uri));
  }

  public void addQueryLiteralCommand()
  {
    query = true;
    XMLKit.checkQName(args[argCount + 1]);

    String qname = args[++argCount];
    String literal = XMLKit.xmlDecode(args[++argCount]);

    commands.add(new QueryLiteralCommand(qname, literal));
  }

  public void addDeleteCommand()
  {
    update = true;
    XMLKit.checkPrefix(args[argCount + 1]);

    String qname = args[++argCount];
    commands.add(new DeleteCommand(qname));
  }

  public void addDeleteResourceCommand()
  {
    update = true;
    XMLKit.checkQName(args[argCount + 1]);

    String qname = args[++argCount];
    String uri = XMLKit.xmlDecode(args[++argCount]);

    commands.add(new DeleteResourceCommand(qname, uri));
  }

  public void addDeleteLiteralCommand()
  {
    update = true;
    XMLKit.checkQName(args[argCount + 1]);

    String qname = args[++argCount];
    String literal = XMLKit.xmlDecode(args[++argCount]);

    commands.add(new DeleteLiteralCommand(qname, literal));
  }

  public void addAddResourceCommand()
  {
    update = true;
    XMLKit.checkQName(args[argCount + 1]);

    String qname = args[++argCount];
    String uri = XMLKit.xmlDecode(args[++argCount]);

    commands.add(new AddResourceCommand(qname, uri));
  }

  public void addAddLiteralCommand()
  {
    update = true;
    XMLKit.checkQName(args[argCount + 1]);

    String qname = args[++argCount];
    String literal = XMLKit.xmlDecode(args[++argCount]);

    commands.add(new AddLiteralCommand(qname, literal));
  }

  public void addUpdateCommand()
  {
    commands.add(new UpdateCommand());
  }

  public void addExifCommand()
  {
    commands.add(new ExifCommand());
  }
  private void addFoundCommand()
  {
    commands.add(new FoundCommand());
  }

  public void setUpdate()
  {
    updateExif = true;
    addUpdateCommand();
    update = true;
  }

  public void setExif()
  {
    copyExif = true;
    addExifCommand();
    update = true;
  }
  // end commands stuff

  public void addJpeg(String arg)
  {
    File jpegFile = new File(arg);
    if(!jpegFile.exists())
    {
      SystemKit.abort(1, "Does not exist: " + arg);
    }
    jpegfiles.add(arg);
  }

  public String nextArgument()
  {
    return args[++argCount];
  }

  public void setRdfFile(String rdfFilename)
  {
    if(initRDF != null)
    {
      SystemKit.abort(1, "Specify -rdf exactly once");
    }
    initRDF = rdfFilename;
  }

  public void setShow()
  {
    query = true;
    addFoundCommand();
  }

  public void initializeConfig()
  {
    SystemKit.debug("initializeConfig()");
    loadConfig();
    // Extract info from the configuration file...
    analyzeConfig(config);
  }

  public void increaseVerbosity()
  {
    SystemKit.debug("increaseVerbosity()");
    SystemKit.verbose++;
  }

  public void showHelpMessage()
  {
    System.out.println(SystemKit.getUsageMessage());
    System.exit(0);
  }

  private void analyzeConfig(Model config)
  {
    SystemKit.debug("analyzeConfig()");

    try
    {
      SystemKit.message(1, "Processing configuration...");

      // First, we have to have a schema for the exif: properties. First,
      // let's check, did the user load one of their own? If there are
      // any exif:Property's in the configuration, assume yes.
      Property exifProperty = config.createProperty(ExifHelper.nsExif, "Property");
      StmtIterator iter = config.listStatements(new SelectorImpl(null, RDF.type, exifProperty));

      // If not, go get them...
      if(!iter.hasNext())
      {
        loadSchema(config, ExifHelper.nsExif, "/com/nwalsh/rdf/exif");
      }

      // Similarly, we have to have a schema for the exif: intrinsic
      // properties.
      Property exifIProperty = config.createProperty(ExifHelper.nsExifI, "Property");

      iter = config.listStatements(new SelectorImpl(null, RDF.type, exifIProperty));

      // If not, go get them...
      if(!iter.hasNext())
      {
        loadSchema(config, ExifHelper.nsExifI, "/com/nwalsh/rdf/exif-intrinsic");
      }

      // Finally, we have to have a schema for the exif: GPS properties.
      Property exifGpsProperty = config.createProperty(ExifHelper.nsExifGps, "Property");

      iter = config.listStatements(new SelectorImpl(null, RDF.type, exifGpsProperty));

      // If not, go get them...
      if(!iter.hasNext())
      {
        loadSchema(config, ExifHelper.nsExifGps, "/com/nwalsh/rdf/exif-gps");
      }

      // xxxx
      /*
       iter = config.listStatements();
       while (iter.hasNext()) {
       Statement stmt = (Statement) iter.next();
       System.err.println("<" + stmt.getSubject().toString() + "> <"
       + stmt.getPredicate().toString() + "> <"
       + stmt.getObject().toString() + ">");

       }
       */
      // xxxx

      boolean addedExif = false;
      boolean addedExifI = false;

      Property decoderProperty = config.createProperty(nsJpegRDF, "decoder");

      // Find the configuration resource....
      Resource configRsrc = null;
      Resource cfgType = config.createResource(cfg_type);
      iter = config.listStatements(new SelectorImpl(null, RDF.type, cfgType));
      if(iter.hasNext())
      {
        Statement stmt = (Statement) iter.next();
        configRsrc = stmt.getSubject();
      }
      else
      {
        // Just make one up...it doesn't matter what it is
        configRsrc = config.createResource();
        SystemKit.message(4, "No configuration resource.");
      }

      Property exifPtr = config.createProperty(nsJpegRDF, "exif");
      iter = config.listStatements(new SelectorImpl(configRsrc, exifPtr, (RDFNode) null));

      while(iter.hasNext())
      {
        Statement stmt = (Statement) iter.next();

        // Now find the resource pointed to...
        Resource rsrc =
        config.getResource(stmt.getObject().toString());

        if(config.contains(rsrc, RDF.type, exifProperty))
        {
          // An EXIF property
          addedExif = true;
          int tagNo = getTagNumber(rsrc, config.createProperty(ExifHelper.nsExif, "tagNumber"));

          if(tagNo >= 0)
          {
            exifStuff.addTag(tagNo, rsrc.getLocalName());

            // Load decoders for this tag......
            StmtIterator diter = config.listStatements(new SelectorImpl(rsrc, decoderProperty, (RDFNode) null));

            while(diter.hasNext())
            {
              Statement dstmt = (Statement)diter.next();

              RDFNode obj = dstmt.getObject();
              if(obj instanceof Literal)
              {
                String className = obj.toString();
                TagDecoder decoder = null;

                try
                {
                	decoder = (TagDecoder) Class.forName(className).newInstance();
                }
                catch(ClassNotFoundException cnfe)
                {
                	System.err.println("Class not found: " + className);
                }
                catch(InstantiationException cnfe)
                {
                	System.err.println("Failed to instantiate " + className);
                }
                catch(IllegalAccessException cnfe)
                {
                	System.err.println("Illegal access instantiating " + className);
                }
                catch(ClassCastException cnfe)
                {
                	System.err.println("Class " + className + " is not a TagDecoder");
                }

                String ns = decoder.getNamespace();
                if(ns != null && !"".equals(ns))
                {
                	Property classPtr = config.createProperty(nsJpegRDF, className);
                	String makeModel = null;

                	if(config.contains(rsrc, classPtr))
                	{
                		Statement d2stmt = config.getProperty(rsrc, classPtr);
                		RDFNode dobj = d2stmt.getObject();
                		if(dobj instanceof Literal)
                		{
                			makeModel = dobj.toString();
                		}
                	}

                	String field = rsrc.getLocalName();
                	if(makeModel != null)
                	{
                		field += "/" + makeModel;
                	}

                	SystemKit.message(3, "Added decoder for " + field + ": " + className);

                	String prefix = RDFKit.getNamespacePrefix(ns);
                	decoder.setPrefix(prefix);
                	exifStuff.addDecoder(field, decoder);
                }
                else
                {
                	System.err.println("Decoder " + className + " reports invalid namespace; ignored.");
                }
              }
              else
              {
                System.err.println("Unexpected decoder: " + obj);
              }
            }
          }
        }
        else if(config.contains(rsrc, RDF.type, exifIProperty))
        {
          // An EXIF Intrinsic property
          addedExifI = true;
          int tagNo = getTagNumber(rsrc, config.createProperty(ExifHelper.nsExifI, "tagNumber"));
          exifStuff.addTag(rsrc, tagNo);

        }
        else if(config.contains(rsrc, RDF.type, exifGpsProperty))
        {
          // An EXIF GPS property
          int tagNo = getTagNumber(rsrc, config.createProperty(ExifHelper.nsExifGps, "tagNumber"));
          String prefix = RDFKit.getNamespacePrefix(ExifHelper.nsExifGps);
          if(tagNo >= 0)
          {
            exifStuff.addGpsTag(tagNo, prefix + ":" + rsrc.getLocalName());
          }
        }
        else
        {
          // What the heck is this?
          System.err.println(rsrc + " has unknown type; ignored.");
        }
      }

      if(!addedExif)
      {
        // Add them all...
        iter = config.listStatements(new SelectorImpl(null, RDF.type, exifProperty));

        while(iter.hasNext())
        {
          Statement stmt = (Statement) iter.next();
          Resource rsrc = stmt.getSubject();
          int tagNo = getTagNumber(rsrc,
          config.createProperty(ExifHelper.nsExif, "tagNumber"));
          if(tagNo >= 0)
          {
            exifStuff.addTag(tagNo, rsrc.getLocalName());
          }
        }
      }

      if(!addedExifI)
      {
        // Add them all...
        iter = config.listStatements(new SelectorImpl(null, RDF.type, exifIProperty));
        while(iter.hasNext())
        {
          Statement stmt = (Statement) iter.next();
          Resource rsrc = stmt.getSubject();
          int tagNo = getTagNumber(rsrc, config.createProperty(ExifHelper.nsExifI, "tagNumber"));
          exifStuff.addTag(rsrc, tagNo);
        }
      }
    }
    catch(RDFException e)
    {
      e.printStackTrace();
    }
  }

  private void populateModelNamespacePrefixes()
  {
    // Now make sure the prefixes in our config file are mapped in our models
    PrefixMapping pm = null;

    // This only works in Jena 2.1. Surely there's a better way to test that?
    try
    {
      pm = ModelFactory.getDefaultModelPrefixes();

      Set<String> nsKeys = RDFKit.namespaces.keySet();
      for(String key : nsKeys)
      {
        String uri = RDFKit.namespaces.get(key);
        if(pm.getNsPrefixURI(key) == null)
        {
          SystemKit.debug("Populate NS prefix: " + key + "=" + uri);
          pm.setNsPrefix(key, uri);
        }
      }
    }
    catch(NoSuchMethodError nsme)
    {
      // nop
    }
  }

  private void loadSchema(Model config, String nsURI, String resLoc)
  {
    String resURI = "";

    // First, try to get it from our Jar file...
    URL url = this.getClass().getResource(resLoc);

    try
    {
      if(url == null)
      {
        // Nope, go to the web!
        resURI = nsURI;
        int pos = resURI.indexOf("#");
        if(pos >= 0)
        {
          resURI = resURI.substring(0, pos);
        }
        url = new URL(resURI);
      }

      InputStream input = url.openStream();
      if(input == null)
      {
        SystemKit.abort(1, "Failed to open schema " + url.toString());
      }

      SystemKit.message(2, "Loading schema..." + url.toString());
      config.read(new InputStreamReader(input, "utf-8"), "");
      input.close();
    }
    catch(MalformedURLException mue)
    {
      SystemKit.abort(0, "Malformed URI: " + resURI);
    }
    catch(Exception e)
    {
      SystemKit.abort(0, "Failed to load schema: " + url.toString());
    }
  }

  private int getTagNumber(Resource rsrc, Property tagp)
  {
    try
    {
      Model model = rsrc.getModel();
      Statement tstmt = model.getProperty(rsrc, tagp);
      RDFNode obj = tstmt.getObject();

      if(obj == null)
      {
        System.err.println(rsrc + " has no " + tagp + "?");
      }
      else if(obj instanceof Literal)
      {
        String tagNumber = obj.toString();
        try
        {
          if(tagNumber.startsWith("0x") || tagNumber.startsWith("0X"))
          {
            return Integer.parseInt(tagNumber.substring(2), 16);
          }
          else
          {
            return Integer.parseInt(tagNumber);
          }
        }
        catch(Exception e)
        {
          System.err.println(rsrc + " has unparseable " + tagp + ": " + tagNumber);
        }
      }
      else
      {
        System.err.println(rsrc + " has non-literal " + tagp + "?");
      }
    }
    catch(RDFException e)
    {
      // nop;
    }
    return -1;
  }

  public void loadConfig()
  {
    SystemKit.debug("loadConfig()");
    config = ModelFactory.createDefaultModel();
    String configFilename = getConfigFilename();

    if(configFilename == null)
    {
      configFilename = System.getProperty("user.home") + System.getProperty("file.separator") + ".jpegrdf.rdf";
    }

    SystemKit.message(2, "Loading configuration..." + configFilename);

    // Load the default configuration file...
    config = RDFKit.loadModel(configFilename);
    XMLKit.getNamespaces(configFilename);
  }

  public String getConfigFilename()
  {
    return configFilename;
  }

  public void setConfigFilename(String configFilename)
  {
    SystemKit.debug("setConfigFilename = " + configFilename);
    this.configFilename = configFilename;
  }

  public String getBaseUri()
  {
    return baseUri;
  }

  public void setBaseUri(String baseUri)
  {
    SystemKit.debug("setBaseUri = " + baseUri);
    this.baseUri = baseUri;
  }

  public String getEncoding()
  {
    return encoding;
  }

  public void setEncoding(String encoding)
  {
    SystemKit.debug("setEncoding = " + encoding);
    this.encoding = encoding;
  }

  // @@TODO must go!
  public void setAndQuery()
  {
    andQuery = true;
    query = true;
  }

  public void setQuery()
  {
    query = true;
  }

  private void initOptionsMap()
  {
    optionsMap = new ArgumentsKit(this);

    optionsMap.put("-help", "showHelpMessage");
    optionsMap.put("-h", "showHelpMessage");

    optionsMap.put("-verbose", "increaseVerbosity");
    optionsMap.put("-v", "increaseVerbosity");

    optionsMap.put("-base-uri", "setBaseUri");
    optionsMap.put("-b", "setBaseUri");

    optionsMap.put("-config", "setConfigFilename");
    optionsMap.put("-c", "setConfigFilename");

    optionsMap.put("-encoding", "setEncoding");
    optionsMap.put("-e", "setEncoding");

    optionsMap.put("-and", "setAndQuery");
    optionsMap.put("-a", "setAndQuery");

    optionsMap.put("-show", "setShow");
    optionsMap.put("-s", "setShow");

    optionsMap.put("-update", "setUpdate");
    optionsMap.put("-u", "setUpdate");

    optionsMap.put("-exif", "setExif");
    optionsMap.put("-update", "setUpdate");
    optionsMap.put("-rdf", "setRdfFile");
  }

  public void initCommandsMap()
  {
    commandsMap = new ArgumentsKit(this);

    commandsMap.put("-namespace", "addAssociateNamespaceCommand");
    commandsMap.put("-ns", "addAssociateNamespaceCommand");

    commandsMap.put("-add-literal", "addAddLiteralCommand");
    commandsMap.put("-al", "addAddLiteralCommand");

    commandsMap.put("-add-resource", "addAddResourceCommand");
    commandsMap.put("-ar", "addAddResourceCommand");

    commandsMap.put("-delete-literal", "addDeleteLiteralCommand");
    commandsMap.put("-dl", "addDeleteLiteralCommand");

    commandsMap.put("-delete-resource", "addDeleteResourceCommand");
    commandsMap.put("-dr", "addDeleteResourceCommand");

    commandsMap.put("-delete", "addDeleteCommand");
    commandsMap.put("-d", "addDeleteCommand");

    commandsMap.put("-query-literal", "addQueryLiteralCommand");
    commandsMap.put("-ql", "addQueryLiteralCommand");

    commandsMap.put("-query-resource", "addQueryResourceCommand");
    commandsMap.put("-qr", "addQueryResourceCommand");

    commandsMap.put("-query", "addQueryQNameCommand");
    commandsMap.put("-q", "addQueryQNameCommand");
  }

  public Map getMappedQNames()
  {
    return mappedQNames;
  }

  public void setMappedQNames(Map mappedQNames)
  {
    this.mappedQNames = mappedQNames;
  }
}
