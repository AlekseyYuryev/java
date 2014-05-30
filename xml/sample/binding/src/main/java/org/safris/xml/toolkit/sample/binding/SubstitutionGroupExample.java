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

package org.safris.xml.toolkit.sample.binding;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.toolkit.sample.binding.substitutiongroup.$sg_productType;
import org.safris.xml.toolkit.sample.binding.substitutiongroup.sg_hat;
import org.safris.xml.toolkit.sample.binding.substitutiongroup.sg_shirt;
import org.safris.xml.toolkit.sample.binding.substitutiongroup.sg_stockList;
import org.safris.xml.toolkit.sample.binding.substitutiongroup.sg_umbrella;
import org.xml.sax.InputSource;

public class SubstitutionGroupExample {
  public static void main(final String[] args) throws Exception {
    new SubstitutionGroupExample().runExample();
  }

  public Binding runExample() throws Exception {
    File file = new File("src/main/resources/xml/substitutionGroup.xml");
    if (!file.exists())
      throw new Error("File " + file.getAbsolutePath() + " does not exist.");

    if (!file.canRead())
      throw new Error("File " + file.getAbsolutePath() + " is not readable.");

    sg_stockList stockList = (sg_stockList)Bindings.parse(new InputSource(new FileInputStream(file)));
    List<$sg_productType> products = stockList.sg_product();
    for ($sg_productType product : products) {
      if (product instanceof sg_shirt) {
        sg_shirt shirt = (sg_shirt)product;
        System.out.println("There are " + shirt._amount(0).text() + " of '" + shirt._name(0).text() + "' shirts colored " + shirt._color(0).text() + ", size " + shirt._size(0).text());
      }
      else if (product instanceof sg_hat) {
        sg_hat hat = (sg_hat)product;
        System.out.println("There are " + hat._amount(0).text() + " of '" + hat._name(0).text() + "' hats, size " + hat._size(0).text());
      }
      else if (product instanceof sg_umbrella) {
        sg_umbrella umbrella = (sg_umbrella)product;
        System.out.println("There are " + umbrella._amount(0).text() + " of '" + umbrella._name(0).text() + "' umbrellas");
      }
    }

    return stockList;
  }
}