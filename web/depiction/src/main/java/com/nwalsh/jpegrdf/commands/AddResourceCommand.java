/*
 * $Id: AddResourceCommand.java,v 1.1 2004/02/16 15:19:03 nwalsh Exp $
 */
package com.nwalsh.jpegrdf.commands;

import com.nwalsh.jpegrdf.SystemKit;


public class AddResourceCommand
	extends QNameBasedCommand {

	public AddResourceCommand(String qname, String uri){
		super(qname, uri);
	}
	
	public void execute() {
		SystemKit.debug("AddResourceCommand.execute()");
		getProcessor().addResource(getQName(), getValue());
	}

}
