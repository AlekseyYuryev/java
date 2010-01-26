/*  Copyright 2010 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

// JpegCommentHandler.java
// $Id: JpegCommentHandler.java,v 1.1 2003/12/28 23:44:51 nwalsh Exp $
// (c) COPYRIGHT MIT, INRIA and Keio, 1999.
// Please first read the full copyright statement in file COPYRIGHT.html

package org.w3c.tools.jpeg;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import java.util.Vector;

public class JpegCommentHandler {

    protected File jpegfile;
    protected InputStream  in;

    /**
     * Get this image reader
     */
    public Reader getReader() throws IOException,JpegException {
	return new StringReader(getComment());
    }

    public String getComment() throws IOException,JpegException {
	JpegHeaders jpeghead = new JpegHeaders(in);
	StringBuffer sb = new StringBuffer();
	// get the comments out of the jpeg file
	String comms[] = jpeghead.getComments();
	// and dump them in one big string
	for (int i=0; i< comms.length; i++) {
	    sb.append(comms[i]);
	}
	return sb.toString();
    }

    /**
     * Get this image writer
     */
    public Writer getOutputStreamWriter(OutputStream out, String enc) 
	throws UnsupportedEncodingException
    {
	return new JpegCommentWriter(out, in, enc);
    }

    /**
     * Get this image writer
     */
    public Writer getOutputStreamWriter(OutputStream out) {
	return new JpegCommentWriter(out, in);
    }

    /**
     * create it out of a File
     */
    public JpegCommentHandler(File jpegfile) 
	throws FileNotFoundException
    {
	this.in = 
	    new BufferedInputStream( new FileInputStream(jpegfile));
	this.jpegfile = jpegfile;
    }

    /**
     * create it from an input stream
     */
    public JpegCommentHandler(InputStream in) {
	this.in = in;
    }
}


