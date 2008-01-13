/*
 * $Id: DeleteResourceCommand.java,v 1.1 2004/02/16 15:19:04 nwalsh Exp $
 */
package com.nwalsh.jpegrdf.commands;

import com.nwalsh.jpegrdf.SystemKit;


public class DeleteResourceCommand
	extends QNameBasedCommand {

	public DeleteResourceCommand(String qname, String uri){
		super(qname, uri);
	}
	
	public void execute() {
		SystemKit.debug("DeleteResourceCommand.execute()");
		getProcessor().deleteResource(getQName(), getValue());
	}

}
