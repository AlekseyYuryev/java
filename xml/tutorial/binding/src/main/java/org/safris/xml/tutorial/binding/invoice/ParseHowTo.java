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
import org.safris.xml.generator.compiler.runtime.ComplexType;
import org.xml.sax.InputSource;

public class ParseHowTo {
  public static void main(String[] args) throws Exception {
    File file = new File("src/main/resources/xml/invoice.xml");
    if (!file.exists())
      throw new Error("File " + file.getAbsolutePath() + " does not exist.");

    if (!file.canRead())
      throw new Error("File " + file.getAbsolutePath() + " is not readable.");

    pv_invoice invoice = (pv_invoice)Bindings.parse(new InputSource(new FileInputStream(file)));

    Integer number = invoice.get_number().get(0).getText();
    System.out.print("This invoice # " + number + " ");

    Date date = invoice.get_date().get(0).getText();
    System.out.println("is established on " + date + " ");

    String billingName = invoice.get_billingAddress().get(0).get_name().get(0).getText();
    System.out.print("from " + billingName + ", ");

    String billingAddress = invoice.get_billingAddress().get(0).get_address().get(0).getText();
    System.out.print(billingAddress + ", ");

    String billingCity = invoice.get_billingAddress().get(0).get_city().get(0).getText();
    System.out.print(billingCity + ", ");

    Integer billingPostalCode = invoice.get_billingAddress().get(0).get_postalCode().get(0).getText();
    System.out.print(billingPostalCode + ", ");

    String billingCountry = invoice.get_billingAddress().get(0).get_country().get(0).getText();
    System.out.println(billingCountry + ".");

    String shippingName = invoice.get_shippingAddress().get(0).get_name().get(0).getText();
    System.out.print("Shipping address is: " + shippingName + ", ");

    String shippingAddress = invoice.get_shippingAddress().get(0).get_address().get(0).getText();
    System.out.print(shippingAddress + ", ");

    String shippingCity = invoice.get_shippingAddress().get(0).get_city().get(0).getText();
    System.out.print(shippingCity + ", ");

    Integer shippingPostalCode = invoice.get_shippingAddress().get(0).get_postalCode().get(0).getText();
    System.out.print(shippingPostalCode + ", ");

    String shippingCountry = invoice.get_shippingAddress().get(0).get_country().get(0).getText();
    System.out.println(shippingCountry + ".");

    System.out.println("The following items are included in this invoice:");
    for ($pv_itemType<? extends ComplexType> item : (List<$pv_itemType<? extends ComplexType>>)invoice.get_billedItems().get(0).get_item()) {
      Integer quantity = item.get_quantity().get(0).getText();
      System.out.print(quantity + " ");

      String description = item.get_description().get(0).getText();
      System.out.print(description + " ");

      Integer code = item.get_code().get(0).getText();
      System.out.print("(#" + code + ") ");

      Decimal price = item.get_price().get(0).getText();
      System.out.println("$" + price + " each.");
    }
  }
}
