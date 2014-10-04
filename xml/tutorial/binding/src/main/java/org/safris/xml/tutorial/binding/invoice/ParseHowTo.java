/* Copyright (c) 2006 Seva Safris
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
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