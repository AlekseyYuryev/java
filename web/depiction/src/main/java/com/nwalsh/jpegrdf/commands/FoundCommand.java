/*
 * $Id: FoundCommand.java,v 1.1 2004/02/16 15:19:04 nwalsh Exp $
 */
package com.nwalsh.jpegrdf.commands;

import com.nwalsh.jpegrdf.SystemKit;


public class FoundCommand extends JpegCommand {

	public FoundCommand() {
		super();
	}

	public void execute() {
		SystemKit.debug("FoundCommand.execute()");
		getProcessor().found();
	}

}
