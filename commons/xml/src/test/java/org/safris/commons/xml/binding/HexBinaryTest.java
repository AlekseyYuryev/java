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

package org.safris.commons.xml.binding;

import org.junit.Assert;
import org.junit.Test;

public class HexBinaryTest {
    private static void assertEquals(String data, String base64) {
        final HexBinary hexBinary = new HexBinary(data.getBytes());
        final String hexString = hexBinary.toString();
        Assert.assertEquals(base64, hexString);
        final HexBinary unmarshalled = HexBinary.parseHexBinary(hexString);
        Assert.assertEquals(data, new String(unmarshalled.getBytes()));
    }

    public static void main(String[] args) {
        new HexBinaryTest().testHexBinary();
    }

    @Test
    public void testHexBinary() {
        assertEquals("Bonjour", "426F6E6A6F7572");
        assertEquals("Hello World", "48656C6C6F20576F726C64");
        assertEquals("The quick brown fox jumps over the lazy dog", "54686520717569636B2062726F776E20666F78206A756D7073206F76657220746865206C617A7920646F67");
    }
}
