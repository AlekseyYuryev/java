/*
 * $Id: QueryLiteralCommand.java,v 1.1 2004/02/16 15:19:04 nwalsh Exp $
 */
package com.nwalsh.jpegrdf.commands;

import com.nwalsh.jpegrdf.SystemKit;


public class QueryLiteralCommand
	extends QNameBasedCommand {

	public QueryLiteralCommand(String qname, String literal) {
		super(qname, literal);
	}

	public void execute() {
		SystemKit.debug("QueryLiteralCommand.execute()");
		getProcessor().queryLiteral(getQName(), getValue());
	}

}
