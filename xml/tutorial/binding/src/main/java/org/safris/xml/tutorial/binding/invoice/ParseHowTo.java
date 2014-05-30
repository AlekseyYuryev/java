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
import java.util.List;

import org.safris.commons.xml.binding.Date;
import org.safris.commons.xml.binding.Decimal;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.xml.sax.InputSource;

public class ParseHowTo {
  public static void main(final String[] args) throws Exception {
    File file = new File("src/main/resources/xml/invoice.xml");
    if (!file.exists())
      throw new Error("File " + file.getAbsolutePath() + " does not exist.");

    if (!file.canRead())
      throw new Error("File " + file.getAbsolutePath() + " is not readable.");

    pv_invoice invoice = (pv_invoice)Bindings.parse(new InputSource(new FileInputStream(file)));

    Integer number = invoice._number(0).text();
    System.out.print("This invoice # " + number + " ");

    Date date = invoice._date(0).text();
    System.out.println("is established on " + date + " ");

    String billingName = invoice._billingAddress(0)._name(0).text();
    System.out.print("from " + billingName + ", ");

    String billingAddress = invoice._billingAddress(0)._address(0).text();
    System.out.print(billingAddress + ", ");

    String billingCity = invoice._billingAddress(0)._city(0).text();
    System.out.print(billingCity + ", ");

    Integer billingPostalCode = invoice._billingAddress(0)._postalCode(0).text();
    System.out.print(billingPostalCode + ", ");

    String billingCountry = invoice._billingAddress(0)._country(0).text();
    System.out.println(billingCountry + ".");

    String shippingName = invoice._shippingAddress(0)._name(0).text();
    System.out.print("Shipping address is: " + shippingName + ", ");

    String shippingAddress = invoice._shippingAddress(0)._address(0).text();
    System.out.print(shippingAddress + ", ");

    String shippingCity = invoice._shippingAddress(0)._city(0).text();
    System.out.print(shippingCity + ", ");

    Integer shippingPostalCode = invoice._shippingAddress(0)._postalCode(0).text();
    System.out.print(shippingPostalCode + ", ");

    String shippingCountry = invoice._shippingAddress(0)._country(0).text();
    System.out.println(shippingCountry + ".");

    System.out.println("The following items are included in this invoice:");
    for (final $pv_itemType item : (List<$pv_itemType>)invoice._billedItems(0)._item()) {
      Integer quantity = item._quantity(0).text();
      System.out.print(quantity + " ");

      String description = item._description(0).text();
      System.out.print(description + " ");

      Integer code = item._code(0).text();
      System.out.print("(#" + code + ") ");

      Decimal price = item._price(0).text();
      System.out.println("$" + price + " each.");
    }
  }
}