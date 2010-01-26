/*  Copyright 2010 Safris Technologies Inc.
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
 * $Id: JpegProcessor.java,v 1.5 2004/06/07 23:41:34 nwalsh Exp $
 */
package com.nwalsh.jpegrdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

import org.w3c.tools.jpeg.JpegCommentWriter;
import org.w3c.tools.jpeg.JpegException;
import org.w3c.tools.jpeg.JpegHeaders;

import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.SelectorImpl;
import com.nwalsh.jpegrdf.commands.MacroCommand;

public class JpegProcessor {

	final JpegRDF jpegRdf;
	// params
	String jpegFilename;
	boolean andQuery;
	boolean query;
	String initRDF;
	String baseUri;

	// local

	boolean found;
	String jpegUri;
	int subjCount;
	ExifHelper exifStuff;

	private MacroCommand commands;

	// needed in commands
	private Resource subject = null;
	private Model model;

	private Model showRDF = null;

	public JpegProcessor(
		JpegRDF jpegRdf,
		boolean andQuery,
		boolean query,
		String initRDF,
		String baseUri,

		ExifHelper exifStuff,
		MacroCommand commands) {
		this.jpegRdf = jpegRdf;

		this.andQuery = andQuery;
		this.query = query;
		this.initRDF = initRDF;
		this.baseUri = baseUri;

		this.exifStuff = exifStuff;
		this.commands = commands;
	}

  public Model process(String jpegFilename)
    throws JpegException, IOException {
    this.jpegFilename = jpegFilename;
    model = ModelFactory.createDefaultModel();
    found = andQuery; // why??
    subject = null;

    String normalizedUri = "";
    String jpegUri = baseUri + jpegFilename;

    // Normalize comparision of file:/ and file:/// URIs
    if (jpegUri.startsWith("file:///")) {
      jpegUri = "file:/" + jpegUri.substring(8);
    }

    SystemKit.debug("-----------------------");
    SystemKit.debug("jpegFilename = " + jpegFilename);
    SystemKit.debug("jpegUri = " + jpegUri);

    if (initRDF != null) {
      SystemKit.debug("Loading model from " + initRDF);
      model = RDFKit.loadModel(initRDF);
    } else {
      SystemKit.debug("Loading model from JPEG " + jpegFilename);
      model = getRDF(jpegFilename);
    }

    // If there's more than one subject, try to find the right one...
    int subjCount = 0;

    ResIterator ri = model.listSubjects();

    while (ri.hasNext()) {
      Resource curSubject = (Resource) ri.next();
      SystemKit.debug("Subject #" + subjCount + " = " + curSubject);

      // anonymous subjects don't count...
      if (curSubject.getURI() != null) {
	subjCount++;

	// Normalize comparision of file:/ and file:/// URIs
	normalizedUri = curSubject.getURI();
	if (normalizedUri.startsWith("file:///")) {
	  normalizedUri = "file:/" + normalizedUri.substring(8);
	}

	if (normalizedUri.equals(jpegUri) || subject == null) {
	  subject = curSubject;
	}
      }
    }

    SystemKit.debug("subject = " + subject);

    if (subject == null) {
      // No subjects? Must be an empty model...
      subject = model.createResource(jpegUri);
    }

    SystemKit.debug("subject URI = \"" + subject.getURI() + "\"");

    normalizedUri = subject.getURI();
    if (normalizedUri.startsWith("file:///")) {
      normalizedUri = "file:/" + normalizedUri.substring(8);
    }

    if (subjCount > 1 && !normalizedUri.equals(jpegUri)) {
      SystemKit.message(0,
			"No resource matches "
			+ baseUri
			+ jpegFilename
			+ ", skipping...");
      return null;
    }

    if (query) {
      SystemKit.message(2, "Querying model...");
    } else {
      SystemKit.message(2, "Updating model...");
    }

    boolean resource = false;
    boolean delete = false;

    commands.setReceiver(this);
    commands.execute();

    if (query) {
      if (found) {
	SystemKit.message(1, jpegFilename + " matches");

	Model showModel = RDFKit.copyModel(model, subject, jpegUri);

	if (showRDF != null) {
	  // Copy the properties to a new model where the
	  // subject has an explicit URI...
	  showRDF = RDFKit.mergeModel(showRDF, showModel);
	  return showRDF;
	} else {
	  showRDF = showModel;
	  return showRDF;
	}
      } else {
	SystemKit.message(1, jpegFilename + " does not match");
      }
      return null;
    }

    // Only put the relevant portions of the model into each JPEG
    Model outputModel = RDFKit.copyModel(model, subject, jpegUri);

    SystemKit.message(4, "Serialize model...");
    String comment = RDFKit.modelToString(outputModel, jpegUri);

    writeJpegComment(jpegFilename, comment);

    SystemKit.message(2, "Done.");
    return null;
  }

	public void addResource(
		String property,
		String value) {
		String prefix = "";

		Statement s =
			getStatementOfInterest(property, value, true);

		SystemKit.message(
			3,
			"\t"
				+ " add resource "
				+ ": "
				+ prefix
				+ ":"
				+ property
				+ "="
				+ value);

		model.add(s);
	}

  // is this new???
  public void addLiteral(String property, String value) {
    Statement s = getStatementOfInterest(property, value, false);

    SystemKit.message(3, "\tadd literal: "
		      + property
		      + "="
		      + value);

    model.add(s);
  }

	public void deleteResource(
		String property,
		String value) {
		String prefix = "";

		Statement s =
			getStatementOfInterest(property, value, true);

		SystemKit.message(
			3,
			"\t"
				+ " delete resource "
				+ ": "
				+ prefix
				+ ":"
				+ property
				+ "="
				+ value);

		model.remove(s);

	}

	public void deleteLiteral(
		String property,
		String value) {
		String prefix = "";

		Statement s =
			getStatementOfInterest(property, value, false);

		SystemKit.message(
			3,
			"\t"
				+ " delete literal "
				+ ": "
				+ prefix
				+ ":"
				+ property
				+ "="
				+ value);

		model.remove(s);

	}

	private Statement getStatementOfInterest(
		String property,
		String value,
		boolean resource) {
		int pos = property.indexOf(":");
		String prefix = property.substring(0, pos);
		property = property.substring(pos + 1);

		Property p = null;
		RDFNode v = null;

		String propertyUri =
			(String) RDFKit.namespaces.get(prefix);

		p = model.createProperty(propertyUri, property);

		if (resource) {
			v = getResourceOfInterest(value);
		} else {
			v = (RDFNode) model.createLiteral(value);
		}

		return model.createStatement(subject, p, v);
	}

	private RDFNode getResourceOfInterest(String value) {
		String rvalue = value;
		int pos = value.indexOf(":");
		if (pos > 0) {
			String rprefix = value.substring(0, pos);
			String localName = value.substring(pos + 1);
			if (!"".equals(localName)
				&& XMLKit.isXMLName(localName)) {
				if (RDFKit
					.namespaces
					.containsKey(rprefix)) {
					rvalue =
						(String) RDFKit.namespaces.get(
							rprefix)
							+ localName;

					if (!jpegRdf
						.getMappedQNames()
						.containsKey(value)) {
						jpegRdf.getMappedQNames().put(
							value,
							rvalue);

						SystemKit.message(
							1,
							"Resource "
								+ value
								+ " is "
								+ rvalue);
					}
				} else {
					if (!jpegRdf
						.getMappedQNames()
						.containsKey(value)) {
						jpegRdf.getMappedQNames().put(
							value,
							rvalue);
						SystemKit.message(
							1,
							"Prefix "
								+ rprefix
								+ " is undefined in resource value "
								+ value);
					}
				}
			}
		}
		return (RDFNode) model.createResource(rvalue);

	}

	public void found() {
		found = true;

	}

	public void doDelete(String property) {
		RDFKit.delete(model, subject, property);
	}

	private boolean queryQName(
		Resource subject,
		String property) {
		String prefix = "";
		boolean found = false;

		boolean foundLocal =
			RDFKit.findProperty(model, subject, property);

		if (foundLocal) {
			SystemKit.message(
				3,
				"\tfound any "
					+ ": "
					+ prefix
					+ ":"
					+ property);
			if (!andQuery)
				found = true;
		} else {
			SystemKit.message(
				3,
				"\tnot found any "
					+ ": "
					+ prefix
					+ ":"
					+ property);
			if (andQuery)
				found = false;
		}
		this.found = found;
		return found;
	}

	private boolean queryLiteral(
		Resource subject,
		String property,
		String value) {
		boolean found = false;
		String prefix = "";
		//	String property = (String) cmds.next();
		//	String value = (String) cmds.next();

		int pos = property.indexOf(":");
		prefix = property.substring(0, pos);
		property = property.substring(pos + 1);

		Property p = null;
		RDFNode v = null;

		String propertyUri =
			(String) RDFKit.namespaces.get(prefix);
		p = model.createProperty(propertyUri, property);

		v = (RDFNode) model.createLiteral(value);

		StmtIterator iter =
			model.listStatements(
				new SelectorImpl(subject, p, v));
		if (iter.hasNext()) {
			SystemKit.message(
				3,
				"\tfound literal "
					+ ": "
					+ prefix
					+ ":"
					+ property
					+ "="
					+ value);
			if (!andQuery)
				found = true;
		} else {
			SystemKit.message(
				3,
				"\tnot found literal "
					+ ": "
					+ prefix
					+ ":"
					+ property
					+ "="
					+ value);
			if (andQuery)
				found = false;
		}
		this.found = found;
		return found;
	}

	private boolean queryResource(
		Resource subject,
		String property,
		String value) {
		boolean found = false;
		String prefix = "";
		//	String property = (String) cmds.next();
		//	String value = (String) cmds.next();

		int pos = property.indexOf(":");
		prefix = property.substring(0, pos);
		property = property.substring(pos + 1);

		Property p = null;
		RDFNode v = null;

		String propertyUri =
			(String) RDFKit.namespaces.get(prefix);
		p = model.createProperty(propertyUri, property);

		v = (RDFNode) model.createResource(value);

		StmtIterator iter =
			model.listStatements(
				new SelectorImpl(subject, p, v));
		if (iter.hasNext()) {
			SystemKit.message(
				3,
				"\tfound resource "
					+ ": "
					+ prefix
					+ ":"
					+ property
					+ "="
					+ value);
			if (!andQuery)
				found = true;
		} else {
			SystemKit.message(
				3,
				"\tnot found resource "
					+ ": "
					+ prefix
					+ ":"
					+ property
					+ "="
					+ value);
			if (andQuery)
				found = false;
		}
		this.found = found;
		return found;
	}

	public void doNop() {
		// do nothing
	}

  public void writeJpegComment(String jpegFilename,
			       String comment) throws IOException {
    ByteArrayOutputStream jpegOS = new ByteArrayOutputStream();
    File jpegFile = new File(jpegFilename);
    JpegCommentWriter jcw = new JpegCommentWriter(jpegOS,
						  new FileInputStream(jpegFile));
    jcw.write(comment);
    jcw.close();
    SystemKit.message(1, "Storing " + jpegFilename + "...");

    FileOutputStream fos = new FileOutputStream(jpegFile);
    fos.write(jpegOS.toByteArray());
    fos.close();
  }

	public Model getModel() {
		return model;
	}

	public Resource getSubject() {
		return subject;
	}

	public void doExif() {
	  if(exifStuff.containsExif(model, subject)){
	    SystemKit.message(2,
			      "Ignoring -exif, image already has EXIF RDF");
	  }

	  exifStuff.doExif(model, subject);
	}

	public void updateExif() {
		exifStuff.update(model, subject);
	}

	public void queryLiteral(
		String property,
		String value) {
		queryLiteral(subject, property, value);
	}

	public void queryResource(
		String property,
		String value) {
		queryResource(subject, property, value);
	}

	public void queryQName(String property) {
		queryQName(subject, property);
	}

	public void associateNamespace(
		String prefix,
		String uri) {
		RDFKit.namespaces.put(prefix, uri);
	}

  public Model getRDF(String jpeg)
    throws JpegException, IOException {
    Model model = ModelFactory.createDefaultModel();
    String absURI;

    SystemKit.message(2, "Loading " + jpeg + "...");

    // Let's get the RDF out of this jpg
    File jpegFile = new File(jpeg);

    absURI = baseUri + jpeg;

    // HACK: This is necessary because exif has state!
    exifStuff.getExif().clearTags();

    FileInputStream jpegIn = new FileInputStream(jpegFile);
    JpegHeaders jh = new JpegHeaders(jpegIn, exifStuff.getExif());
    String comments[] = jh.getComments();
    int rdfComment = -1;
    for (int count = 0;
	 count < comments.length;
	 count++) {
      if (comments[count].indexOf("<rdf:RDF") >= 0) {
	rdfComment = count;
      }
    }
    jpegIn.close();

    String rdfString =
      "<rdf:RDF xmlns:rdf='http://www.w3.org/1999/02/22-rdf-syntax-ns#'><rdf:Description rdf:about=''/></rdf:RDF>";

    if (rdfComment >= 0) {
      SystemKit.message(2, "Found RDF comment...");
      rdfString = comments[rdfComment];
    } else {
      SystemKit.message(2, "Creating initial RDF comment...");
    }

    SystemKit.message(2, "Building RDF model..." + absURI);

    model.read(new StringReader(rdfString), absURI);
    return model;
  }
}
