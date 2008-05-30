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

package org.safris.xml.toolkit.test.binding.regression;

import java.io.FileInputStream;
import org.junit.Ignore;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.xml.sax.InputSource;

@Ignore("Make this a real test!")
public class UnitTest extends Metadata
{
	public static void main(String[] args)
	{
		try
		{
			Binding marchalledBinding = Bindings.parse(new InputSource(new FileInputStream("marshalledOut.txt")));
			Binding unmarchalledBinding = Bindings.parse(new InputSource(new FileInputStream("unmarshalledOut.txt")));
			if(!marchalledBinding.equals(unmarchalledBinding))
			{
				System.err.println("!equals()");
				System.exit(1);
			}

			String marshalledString = marchalledBinding.toString();
			String unmarshalledString = marchalledBinding.toString();
			System.out.println(marshalledString);
			System.out.println("-----------------------------------------");
			System.out.println(unmarshalledString);
			if(marshalledString != null && marshalledString.equals(unmarshalledString))
				System.out.println("PASS");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
