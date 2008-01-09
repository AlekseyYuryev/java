/*
 * $Id: Command.java,v 1.1 2004/02/16 15:19:03 nwalsh Exp $
 */
package com.nwalsh.jpegrdf.commands;


public interface Command {

	public void setReceiver(Object receiver); // maybe remove later
	public void execute();
}
