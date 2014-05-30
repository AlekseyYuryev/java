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

import org.safris.commons.xml.binding.Date;
import org.safris.commons.xml.binding.Decimal;
import org.safris.commons.xml.dom.DOMStyle;
import org.safris.commons.xml.dom.DOMs;

public class MarshalHowTo {
  public static void main(final String[] args) throws Exception {
    pv_invoice invoice = new pv_invoice();
    invoice._date(new pv_invoice._date(new Date(2003, 1, 7)));

    invoice._number(new pv_invoice._number(1458));

    pv_invoice._billingAddress billingAddress = new pv_invoice._billingAddress();
    billingAddress._name(new pv_invoice._billingAddress._name("Ian Barking"));
    billingAddress._address(new pv_invoice._billingAddress._address("123 Kennel Street"));
    billingAddress._city(new pv_invoice._billingAddress._city("Dachshund City"));
    billingAddress._postalCode(new pv_invoice._billingAddress._postalCode(98765));
    billingAddress._country(new pv_invoice._billingAddress._country("US"));

    invoice._billingAddress(billingAddress);

    pv_invoice._shippingAddress shippingAddress = new pv_invoice._shippingAddress();
    shippingAddress._name(new pv_invoice._billingAddress._name("Retail Dept."));
    shippingAddress._address(new pv_invoice._billingAddress._address("888 Dogbowl Street"));
    shippingAddress._city(new pv_invoice._billingAddress._city("Pet City"));
    shippingAddress._postalCode(new pv_invoice._billingAddress._postalCode(98765));
    shippingAddress._country(new pv_invoice._billingAddress._country("US"));

    invoice._shippingAddress(shippingAddress);

    pv_invoice._billedItems billedItems = new pv_invoice._billedItems();

    pv_invoice._billedItems._item item = new pv_invoice._billedItems._item();
    item._description(new pv_invoice._billedItems._item._description("Studded Collar"));
    item._code(new pv_invoice._billedItems._item._code(45342));
    item._quantity(new pv_invoice._billedItems._item._quantity(10));
    item._price(new pv_invoice._billedItems._item._price(new Decimal(11.95)));

    billedItems._item(item);

    item = new pv_invoice._billedItems._item();
    item._description(new pv_invoice._billedItems._item._description("K9 Pet Coat"));
    item._code(new pv_invoice._billedItems._item._code(25233));
    item._quantity(new pv_invoice._billedItems._item._quantity(5));
    item._price(new pv_invoice._billedItems._item._price(new Decimal(25.01)));

    billedItems._item(item);

    invoice._billedItems(billedItems);

    System.out.println(DOMs.domToString(invoice.marshal(), DOMStyle.INDENT));
  }
}
