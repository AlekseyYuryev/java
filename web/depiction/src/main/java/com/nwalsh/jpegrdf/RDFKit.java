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
 * $Id: RDFKit.java,v 1.7 2005/04/27 10:18:29 nwalsh Exp $
 */
package com.nwalsh.jpegrdf;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.SelectorImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * Static helper methods for handling RDF
 */
public class RDFKit
{
	public static Map<String,String> namespaces = new Hashtable<String,String>();

	/**
	 * Don't instantiate
	 */
	private RDFKit()
	{
	}

	public static boolean findProperty(Model model, Resource subject, String property)
	{
		boolean foundLocal = false;

		try
		{
			int pos = property.indexOf(":");
			String prefix = property.substring(0, pos);
			property = property.substring(pos + 1);

			String uri = RDFKit.namespaces.get(prefix);

			Property p = null;
			if(property.length() != 0)
			{
				p = model.createProperty(uri, property);
			}

			StmtIterator iter = model.listStatements(new SelectorImpl(subject, p, (RDFNode) null));
			while(iter.hasNext() && !foundLocal)
			{
				Statement stmt = (Statement) iter.next();
				Property sp = stmt.getPredicate();

				if(uri.equals(sp.getNameSpace()) && (property.length() == 0 || sp.getLocalName().equals(property)))
				{
					foundLocal = true;
				}
			}
		}
		catch(Exception e)
		{
			// nop
		}

		return foundLocal;
	}

	public static Model copyModel(Model model,
	Resource subject, String uri)
	{
		try
		{
			Model newModel = ModelFactory.createDefaultModel();
			Resource newSubject = newModel.createResource(uri);

			// Copy prefix mappings to the new model...
			newModel.setNsPrefixes(model.getNsPrefixMap());

			copyToModel(model, subject, newModel, newSubject);

			return newModel;
		}
		catch(Exception e)
		{
			System.err.println("Failed: " + e);
			e.printStackTrace();
			System.exit(2);
		}

		return null;
	}

	public static void copyToModel(Model srcModel, Resource srcRsrc, Model destModel, Resource destRsrc)
	{
		try
		{
			StmtIterator iter = srcModel.listStatements(new SelectorImpl(srcRsrc, null, (RDFNode) null));
			while(iter.hasNext())
			{
				Statement stmt = (Statement) iter.next();
				RDFNode obj = stmt.getObject();

				if(obj instanceof Resource)
				{
					Resource robj = (Resource) obj;
					if(robj.isAnon())
					{
						Resource destSubResource = destModel.createResource();
						copyToModel(srcModel, robj, destModel, destSubResource);
						obj = destSubResource;
					}
				}

				Statement newStmt = destModel.createStatement(destRsrc, stmt.getPredicate(), obj);
				destModel.add(newStmt);
			}
		}
		catch(Exception e)
		{
			System.err.println("Failed: " + e);
			e.printStackTrace();
			System.exit(2);
		}
	}

	public static Model loadModel(String filename)
	{
		// I used to pass encoding in here, but that's dumb. I'm reading XML
		// which is self-describing.

		Model model = ModelFactory.createDefaultModel();

		SystemKit.message(2, "Loading " + filename + "...");
		try
		{
			File inputFile = new File(filename);
			FileInputStream input = new FileInputStream(inputFile);
			if(input == null)
			{
				SystemKit.abort(1, "Failed to open " + filename);
			}

			model.read(input, "file://" + inputFile.getAbsolutePath());
			input.close();

		}
		catch(FileNotFoundException fnfe)
		{
			SystemKit.message(1, "No file: " + filename);
			fnfe.printStackTrace();
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			SystemKit.message(1, "Failed to read: " + filename);
			e.printStackTrace();
		}
		return model;
	}

	public static Model mergeModel(Model model, Model newModel)
	{
		try
		{
			ResIterator ri = newModel.listSubjects();
			while(ri.hasNext())
			{
				Resource newSubject = (Resource) ri.next();
				Resource subject;
				if(newSubject.isAnon())
				{
					// nevermind; copyToModel will handle this case recursively
				}
				else
				{
					subject = model.createResource(newSubject.getURI());
					copyToModel(newModel, newSubject, model, subject);
				}
			}
		}
		catch(Exception e)
		{
			System.err.println("Failed: " + e);
			e.printStackTrace();
			System.exit(2);
		}
		return model;
	}

	public static void deleteLiteral(Model model, Resource subject, String property, String value)
	{
		String prefix = "";

		int pos = property.indexOf(":");
		prefix = property.substring(0, pos);
		property = property.substring(pos + 1);

		try
		{
			String uri = namespaces.get(prefix);
			Property p = model.createProperty(uri, property);
			RDFNode v = model.createLiteral(value);
			Statement s = model.createStatement(subject, p, v);
			model.remove(s);
		}
		catch(Exception e)
		{
			// nop;
		}
	}

	public static String queryLiteral(Model model, Resource subject, String property)
	{
		String prefix = "";

		int pos = property.indexOf(":");
		prefix = property.substring(0, pos);
		property = property.substring(pos + 1);

		try
		{
			String uri = namespaces.get(prefix);
			Property p = model.createProperty(uri, property);
			RDFNode v = null;

			StmtIterator iter = model.listStatements(new SelectorImpl(subject, p, v));
			while(iter.hasNext())
			{
				Statement stmt = (Statement) iter.next();
				RDFNode obj = stmt.getObject();
				if(obj instanceof Literal)
				{
					return obj.toString();
				}
			}
		}
		catch(Exception e)
		{
			// nop;
		}

		return null;
	}

	public static void updateLiteral(Model model, Resource subject, String property, String value)
	{

		try
		{
			String rdfValue =
			queryLiteral(model, subject, property);

			if(value != null && !value.equals(rdfValue))
			{
				SystemKit.message(2, "Updating " + property + "=" + value);
				deleteLiteral(model, subject, property, rdfValue);

				String prefix = "";
				int pos = property.indexOf(":");
				prefix = property.substring(0, pos);
				property = property.substring(pos + 1);

				String uri = namespaces.get(prefix);
				Property p = model.createProperty(uri, property);
				RDFNode v = model.createLiteral(value);

				Statement s = model.createStatement(subject, p, v);

				model.add(s);
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in ul");
			e.printStackTrace();
			// nop;
		}
	}

	public static String getNamespacePrefix(String namespace)
	{
		if(namespaces.containsValue(namespace))
		{
			// find it...
			Set<String> keys = namespaces.keySet();
			for(String key : keys)
			{
				if(namespace.equals(namespaces.get(key)))
				{
					return key;
				}
			}
			System.err.println("Internal error: this can't happen.");
			return "XXX";
		}
		else if(!namespaces.containsKey("exif") && "http://nwalsh.com/rdf/exif#".equals(namespace))
		{
			// Special case
			namespaces.put("exif", namespace);
			return "exif";
		}
		else if(!namespaces.containsKey("exifi") && "http://nwalsh.com/rdf/exif-intrinsic#".equals(namespace))
		{
			// Special case
			namespaces.put("exifi", namespace);
			return "exifi";
		}
		else
		{
			// add it...
			String p = "dp";
			int num = 0;
			String prefix = p + num;
			while(namespaces.containsKey(prefix))
			{
				num++;
				prefix = p + num;
			}
			namespaces.put(prefix, namespace);
			return prefix;
		}
	}

	public static String modelToString(Model showRDF, String baseURI)
	{
		StringOutputStream stringOutput = new StringOutputStream();
		showRDF.write(stringOutput, "RDF/XML-ABBREV", baseURI);
		String rawString = stringOutput.toString();

		// The rawString contains the octets of the utf-8 representation of the
		// data as individual characters. This is really unusual, but it's true.
		byte[] utf8octets = new byte[rawString.length()];
		for(int i = 0; i < rawString.length(); i++)
		{
			utf8octets[i] = (byte) rawString.charAt(i);
		}

		// Turn these octets back into a proper utf-8 string.
		try
		{
			rawString = new String(utf8octets, "utf-8");
		}
		catch(UnsupportedEncodingException uee)
		{
			// this can't happen
		}

		// Now encode it "safely" as XML
		String rdfString = XMLKit.xmlEncode(rawString);

		//System.err.println("Serialized RDF: " + rdfString);

		return rdfString;
	}

	public static void delete(Model model, Resource subject, String property)
	{

		String prefix = "";

		int pos = property.indexOf(":");
		prefix = property.substring(0, pos);
		property = property.substring(pos + 1);

		Property p = null;
		String uri =
		(String) RDFKit.namespaces.get(prefix);
		if(!"".equals(property))
		{
			p = model.createProperty(uri, property);
		}

		StmtIterator iter =
		model.listStatements(
		new SelectorImpl(
		subject,
		p,
		(RDFNode) null));

		while(iter.hasNext())
		{
			Statement stmt =
			(Statement) iter.next();

			p = stmt.getPredicate();

			if(p.getNameSpace() == null)
			{
				continue;
			}

			if(p.getNameSpace().equals(uri))
			{
				String type = "literal";
				if(stmt.getObject()
				instanceof Resource)
				{
					type = "resource";
				}
				SystemKit.message(
				3,
				"\tdelete "
				+ type
				+ ": "
				+ prefix
				+ ":"
				+ p.getLocalName()
				+ "="
				+ stmt
				.getObject()
				.toString());
				model.remove(stmt);
			}
		}
	}

}
