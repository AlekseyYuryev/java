/*
 * $Id: QNameBasedCommand.java,v 1.1 2004/02/16 15:19:04 nwalsh Exp $
 */
package com.nwalsh.jpegrdf.commands;

import com.hp.hpl.jena.rdf.model.Property;
import com.nwalsh.jpegrdf.RDFKit;

public abstract class QNameBasedCommand
	extends JpegCommand {

	private Property property;
	private String qname;
	private String value;

	public QNameBasedCommand(String qname) {
		this.qname = qname;
	}

	public QNameBasedCommand(String qname, String value) {
		this.qname = qname;
		this.value = value;
	}

	//public void setQName(String
	// public Property getProperty()
	public String getQName() {
		return qname;
	}

	public void setQName(String qname) {
		this.qname = qname;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPrefix() {
		return qname.substring(0, qname.indexOf(":"));
	}

	public String getName() {
		return qname.substring(qname.indexOf(":") + 1);
	}

	public String getPropertyUri() {
		return (String) RDFKit.namespaces.get(getPrefix());
	}

}
