/*  Copyright 2008 Safris Technologies Inc.
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

package org.safris.xml.toolkit.sample.binding;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.generator.compiler.runtime.ComplexType;
import org.safris.xml.toolkit.sample.binding.substitutiongroup.$sg_productType;
import org.safris.xml.toolkit.sample.binding.substitutiongroup.sg_hat;
import org.safris.xml.toolkit.sample.binding.substitutiongroup.sg_shirt;
import org.safris.xml.toolkit.sample.binding.substitutiongroup.sg_stockList;
import org.safris.xml.toolkit.sample.binding.substitutiongroup.sg_umbrella;
import org.xml.sax.InputSource;

public class SubstitutionGroupExample
{
	public static void main(String[] args) throws Exception
	{
		new SubstitutionGroupExample().runExample();
	}

	public Binding runExample() throws Exception
	{
		File file = new File("src/main/resources/xml/substitutionGroup.xml");
		if(!file.exists())
			throw new Error("File " + file.getAbsolutePath() + " does not exist.");

		if(!file.canRead())
			throw new Error("File " + file.getAbsolutePath() + " is not readable.");

		sg_stockList stockList = (sg_stockList)Bindings.parse(new InputSource(new FileInputStream(file)));
		List<$sg_productType<? extends ComplexType>> products = stockList.getsg_product();
		for($sg_productType<? extends ComplexType> product : products)
		{
			if(product instanceof sg_shirt)
			{
				sg_shirt shirt = (sg_shirt)product;
				System.out.println("There are " + shirt.get_amount().get(0).getText() + " of '" + shirt.get_name().get(0).getText() + "' shirts colored " + shirt.get_color().get(0).getText() + ", size " + shirt.get_size().get(0).getText());
			}
			else if(product instanceof sg_hat)
			{
				sg_hat hat = (sg_hat)product;
				System.out.println("There are " + hat.get_amount().get(0).getText() + " of '" + hat.get_name().get(0).getText() + "' hats, size " + hat.get_size().get(0).getText());
			}
			else if(product instanceof sg_umbrella)
			{
				sg_umbrella umbrella = (sg_umbrella)product;
				System.out.println("There are " + umbrella.get_amount().get(0).getText() + " of '" + umbrella.get_name().get(0).getText() + "' umbrellas");
			}
		}

		return stockList;
	}
}
