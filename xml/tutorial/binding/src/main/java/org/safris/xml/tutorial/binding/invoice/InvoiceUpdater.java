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

package org.safris.xml.tutorial.binding.invoice;

import java.io.File;
import java.io.FileInputStream;
import org.safris.commons.xml.binding.Decimal;
import org.safris.commons.xml.dom.DOMStyle;
import org.safris.commons.xml.dom.DOMs;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.xml.sax.InputSource;

public class InvoiceUpdater {
    private static pv_invoice addItem(File invoiceFile, pv_invoice._billedItems._item item) throws Exception {
        final pv_invoice invoice = (pv_invoice)Bindings.parse(new InputSource(new FileInputStream(invoiceFile)));
        invoice.get_billedItems().get(0).add_item(item);
        return invoice;
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0)
            usage();

        final File file = new File(args[0]);

        final pv_invoice._billedItems._item item = new pv_invoice._billedItems._item();
        item.add_description(new pv_invoice._billedItems._item._description(args[1]));
        item.add_code(new pv_invoice._billedItems._item._code(Integer.parseInt(args[2])));
        item.add_quantity(new pv_invoice._billedItems._item._quantity(Integer.parseInt(args[3])));
        item.add_price(new pv_invoice._billedItems._item._price(new Decimal(Float.parseFloat(args[4]))));

        final pv_invoice invoice = addItem(file, item);
        DOMs.domToString(invoice.marshal(), DOMStyle.INDENT);
    }

    private static void usage() {
        System.err.println("Usage: InvoiceUpdater <invoice.xml>");
        System.exit(1);
    }
}
