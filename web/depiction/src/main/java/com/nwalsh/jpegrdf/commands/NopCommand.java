/*
 * $Id: NopCommand.java,v 1.1 2004/02/16 15:19:04 nwalsh Exp $
 */
package com.nwalsh.jpegrdf.commands;

import com.nwalsh.jpegrdf.SystemKit;


public class NopCommand extends JpegCommand {

	public NopCommand() {
		super();
	}

	public void execute() {
		SystemKit.debug("NopCommand.execute()");
	}

}
