/*  Copyright Safris Software 2006
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
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


