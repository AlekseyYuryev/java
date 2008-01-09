/*
 * $Id: ArgumentTaker.java,v 1.1 2004/02/16 15:19:03 nwalsh Exp $
 */
package com.nwalsh.jpegrdf;


/**
 * Classes that receive arguments should implement this method
 */
public interface ArgumentTaker {
	public String  nextArgument();
}
