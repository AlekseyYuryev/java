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

package org.safris.xml.tutorial.binding.invoice;

import java.io.File;
import java.io.FileInputStream;

import org.safris.commons.xml.binding.Decimal;
import org.safris.commons.xml.dom.DOMStyle;
import org.safris.commons.xml.dom.DOMs;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.xml.sax.InputSource;

public class InvoiceUpdater {
  private static pv_invoice addItem(final File invoiceFile, final pv_invoice._billedItems._item item) throws Exception {
    final pv_invoice invoice = (pv_invoice)Bindings.parse(new InputSource(new FileInputStream(invoiceFile)));
    invoice._billedItems(0)._item(item);
    return invoice;
  }

  public static void main(final String[] args) throws Exception {
    if (args.length == 0)
      usage();

    final File file = new File(args[0]);

    final pv_invoice._billedItems._item item = new pv_invoice._billedItems._item();
    item._description(new pv_invoice._billedItems._item._description(args[1]));
    item._code(new pv_invoice._billedItems._item._code(Integer.parseInt(args[2])));
    item._quantity(new pv_invoice._billedItems._item._quantity(Integer.parseInt(args[3])));
    item._price(new pv_invoice._billedItems._item._price(new Decimal(Float.parseFloat(args[4]))));

    final pv_invoice invoice = addItem(file, item);
    DOMs.domToString(invoice.marshal(), DOMStyle.INDENT);
  }

  private static void usage() {
    System.err.println("Usage: InvoiceUpdater <invoice.xml>");
    System.exit(1);
  }
}