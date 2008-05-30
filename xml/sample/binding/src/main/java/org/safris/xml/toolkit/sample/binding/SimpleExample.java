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

import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.toolkit.sample.binding.simple.simple_fruit;
import org.safris.xml.toolkit.sample.binding.simple.simple_fruitBasket;

public class SimpleExample
{
	public static void main(String[] args)
	{
		new SimpleExample().runExample();
	}

	public Binding runExample()
	{
		simple_fruit strawberry = new simple_fruit();
		strawberry.set_name$(new simple_fruit._name$("strawberry"));
		strawberry.set_sweet$(new simple_fruit._sweet$(true));

		simple_fruit jackfruit = new simple_fruit();
		jackfruit.set_name$(new simple_fruit._name$("jackfruit"));
		jackfruit.set_sweet$(new simple_fruit._sweet$(false));
		jackfruit.set_dry$(new simple_fruit._dry$(false));

		simple_fruitBasket._fruits fruits = new simple_fruitBasket._fruits();
		fruits.addsimple_fruit(strawberry);
		fruits.addsimple_fruit(jackfruit);

		simple_fruitBasket genericBasket = new simple_fruitBasket();
		genericBasket.add_fruits(fruits);

		// Now verify the integrity of the code representing this XML structure.
		return genericBasket;
	}
}
