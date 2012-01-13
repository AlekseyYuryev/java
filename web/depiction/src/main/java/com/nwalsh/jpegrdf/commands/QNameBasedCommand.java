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

/*
 * $Id: QNameBasedCommand.java,v 1.1 2004/02/16 15:19:04 nwalsh Exp $
 */
package com.nwalsh.jpegrdf.commands;

import com.hp.hpl.jena.rdf.model.Property;
import com.nwalsh.jpegrdf.RDFKit;

public abstract class QNameBasedCommand
  extends JpegCommand {

  private Property property;
  private String qname;
  private String value;

  public QNameBasedCommand(String qname) {
    this.qname = qname;
  }

  public QNameBasedCommand(String qname, String value) {
    this.qname = qname;
    this.value = value;
  }

  //public void setQName(String
  // public Property getProperty()
  public String getQName() {
    return qname;
  }

  public void setQName(String qname) {
    this.qname = qname;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getPrefix() {
    return qname.substring(0, qname.indexOf(":"));
  }

  public String getName() {
    return qname.substring(qname.indexOf(":") + 1);
  }

  public String getPropertyUri() {
    return (String) RDFKit.namespaces.get(getPrefix());
  }

}
