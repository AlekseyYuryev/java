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

import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleTest extends AbstractTest
{
	public static void main(String[] args)
	{
		new SimpleTest().testExample();
	}

	@Test
	public void testExample()
	{
		assertTrue(verifyBinding(new SimpleExample().runExample()));
	}
}
