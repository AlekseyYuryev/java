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

public class Base64BinaryTest {
    private static void assertEquals(String data, String base64) {
        final Base64Binary base64Binary = new Base64Binary(data.getBytes());
        final String base64String = base64Binary.toString();
        Assert.assertEquals(base64, base64String);
        final Base64Binary unmarshalled = Base64Binary.parseBase64Binary(base64String);
        Assert.assertEquals(data, new String(unmarshalled.getBytes()));
    }

    public static void main(String[] args) {
        new Base64BinaryTest().testBase64Binary();
    }

    @Test
    public void testBase64Binary() {
        assertEquals("Bonjour", "Qm9uam91cg==\n");
        assertEquals("Hello World", "SGVsbG8gV29ybGQ=\n");
        assertEquals("The quick brown fox jumps over the lazy dog", "VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZw==\n");
    }
}
