/*
 * $Id: QueryResourceCommand.java,v 1.1 2004/02/16 15:19:04 nwalsh Exp $
 */
package com.nwalsh.jpegrdf.commands;

import com.nwalsh.jpegrdf.SystemKit;


public class QueryResourceCommand
	extends QNameBasedCommand {

	public QueryResourceCommand(String qname, String uri) {
		super(qname, uri);
	}

	public void execute() {
		SystemKit.debug("QueryResourceCommand.execute()");
		getProcessor().queryResource(getQName(), getValue());
	}

}
