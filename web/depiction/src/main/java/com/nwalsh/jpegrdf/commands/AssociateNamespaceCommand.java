/*
 * $Id: AssociateNamespaceCommand.java,v 1.1 2004/02/16 15:19:03 nwalsh Exp $
 */
package com.nwalsh.jpegrdf.commands;

import com.nwalsh.jpegrdf.SystemKit;


public class AssociateNamespaceCommand extends JpegCommand{

	private String prefix;
	private String uri;
	
	public AssociateNamespaceCommand(String prefix, String uri){
		this.prefix = prefix;
		this.uri = uri;
	}
	
	public void execute() {
		SystemKit.debug("AssociateNamespaceCommand.execute()");
		getProcessor().associateNamespace(prefix, uri);
	}

}
