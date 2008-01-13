/*
 * $Id: DeleteCommand.java,v 1.1 2004/02/16 15:19:03 nwalsh Exp $
 */
package com.nwalsh.jpegrdf.commands;

import com.nwalsh.jpegrdf.SystemKit;


public class DeleteCommand extends QNameBasedCommand {

	public DeleteCommand(String qname){
		super(qname);
	}
	
	public void execute() {
		SystemKit.debug("DeleteCommand.execute()");
		getProcessor().doDelete(getQName());
	}

}
