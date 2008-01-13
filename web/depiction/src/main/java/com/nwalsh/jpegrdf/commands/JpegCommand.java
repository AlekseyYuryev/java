/*
 * $Id: JpegCommand.java,v 1.1 2004/02/16 15:19:04 nwalsh Exp $
 */
package com.nwalsh.jpegrdf.commands;

import com.nwalsh.jpegrdf.JpegProcessor;


public abstract class JpegCommand implements Command {

	public Object receiver;
	
	public JpegCommand() {
		super();
	}

	public Object getReceiver() {
		return receiver;
	}

	public void setReceiver(Object receiver) {
		this.receiver = receiver;
	}
	
	public JpegProcessor getProcessor(){
		return (JpegProcessor)receiver;
	}
}
