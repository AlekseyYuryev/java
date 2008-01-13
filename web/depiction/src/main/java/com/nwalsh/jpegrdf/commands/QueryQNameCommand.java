/*
 * $Id: QueryQNameCommand.java,v 1.1 2004/02/16 15:19:04 nwalsh Exp $
 */
package com.nwalsh.jpegrdf.commands;

import com.nwalsh.jpegrdf.SystemKit;


public class QueryQNameCommand extends QNameBasedCommand {

	public QueryQNameCommand(String qname){
		super(qname);
	}
		
	public void execute() {
		SystemKit.debug("QueryQNameCommand.execute()");
		getProcessor().queryQName(getQName());
	}

}
