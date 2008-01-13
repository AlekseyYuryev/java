/*
 * $Id: AddLiteralCommand.java,v 1.1 2004/02/16 15:19:03 nwalsh Exp $
 */
package com.nwalsh.jpegrdf.commands;

import com.nwalsh.jpegrdf.SystemKit;


public class AddLiteralCommand
	extends QNameBasedCommand {


	public AddLiteralCommand(
		String qname,
		String literal) {
		super(qname, literal);
	}

	public void execute() {
		SystemKit.debug("AddLiteralCommand.execute()");
		getProcessor().addLiteral(getQName(), getValue());
	}

}
