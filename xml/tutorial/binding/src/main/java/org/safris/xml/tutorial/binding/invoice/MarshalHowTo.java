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

import org.safris.commons.xml.binding.Date;
import org.safris.commons.xml.binding.Decimal;
import org.safris.commons.xml.dom.DOMStyle;
import org.safris.commons.xml.dom.DOMs;

public class MarshalHowTo {
    public static void main(String[] args) throws Exception {
        pv_invoice invoice = new pv_invoice();
        invoice.add_date(new pv_invoice._date(new Date(2003, 1, 7)));

        invoice.add_number(new pv_invoice._number(1458));

        pv_invoice._billingAddress billingAddress = new pv_invoice._billingAddress();
        billingAddress.add_name(new pv_invoice._billingAddress._name("Ian Barking"));
        billingAddress.add_address(new pv_invoice._billingAddress._address("123 Kennel Street"));
        billingAddress.add_city(new pv_invoice._billingAddress._city("Dachshund City"));
        billingAddress.add_postalCode(new pv_invoice._billingAddress._postalCode(98765));
        billingAddress.add_country(new pv_invoice._billingAddress._country("US"));

        invoice.add_billingAddress(billingAddress);

        pv_invoice._shippingAddress shippingAddress = new pv_invoice._shippingAddress();
        shippingAddress.add_name(new pv_invoice._billingAddress._name("Retail Dept."));
        shippingAddress.add_address(new pv_invoice._billingAddress._address("888 Dogbowl Street"));
        shippingAddress.add_city(new pv_invoice._billingAddress._city("Pet City"));
        shippingAddress.add_postalCode(new pv_invoice._billingAddress._postalCode(98765));
        shippingAddress.add_country(new pv_invoice._billingAddress._country("US"));

        invoice.add_shippingAddress(shippingAddress);

        pv_invoice._billedItems billedItems = new pv_invoice._billedItems();

        pv_invoice._billedItems._item item = new pv_invoice._billedItems._item();
        item.add_description(new pv_invoice._billedItems._item._description("Studded Collar"));
        item.add_code(new pv_invoice._billedItems._item._code(45342));
        item.add_quantity(new pv_invoice._billedItems._item._quantity(10));
        item.add_price(new pv_invoice._billedItems._item._price(new Decimal(11.95)));

        billedItems.add_item(item);

        item = new pv_invoice._billedItems._item();
        item.add_description(new pv_invoice._billedItems._item._description("K9 Pet Coat"));
        item.add_code(new pv_invoice._billedItems._item._code(25233));
        item.add_quantity(new pv_invoice._billedItems._item._quantity(5));
        item.add_price(new pv_invoice._billedItems._item._price(new Decimal(25.01)));

        billedItems.add_item(item);

        invoice.add_billedItems(billedItems);

        System.out.println(DOMs.domToString(invoice.marshal(), DOMStyle.INDENT));
    }
}
