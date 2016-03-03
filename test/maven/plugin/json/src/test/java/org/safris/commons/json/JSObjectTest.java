/* Copyright (c) 2015 Seva Safris
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

package org.safris.commons.json;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.safris.commons.test.LoggableTest;
import org.safris.commons.util.Collections;

import json.api;

public class JSObjectTest extends LoggableTest {
  @Test
  public void testJSObject() throws Exception {
    //Generator.generate(Resources.getResource("json.xml").getURL(), new File("target/generated-test-sources/json"));

    final api.Attachment att1 = new api.Attachment();
    att1.setSerial(2);
    att1.setData("AAA332");

    try {
      att1.toString();
    }
    catch (final EncodeException e) {
      if (!e.getMessage().startsWith("\"filename\" cannot be null"))
        throw e;
    }

    att1.setFilename("data1.txt");

    final api.Attachment att2 = new api.Attachment();
    att2.setData("data2");
    att2.setFilename("data2.txt");
    att2.setSerial(-2.424242424);

    try {
      att2.toString();
    }
    catch (final EncodeException e) {
      if (!e.getMessage().startsWith("\"data\" does not match pattern"))
        throw e;
    }

    att2.setData("438DA4");

    final api.Attachment att3 = new api.Attachment();
    att3.setFilename("data3.txt");
    att3.setData("8A8CEF");
    att3.setSerial(99999);

    final api.Signature signature = new api.Signature();
    signature.setPubRsa("pub_rsa");
    signature.setXmldsig("xmldsig");

    final api.Message message = new api.Message();
    message.setSubject("Test subject");
    message.setImortant(true);
    message.setRecipients(Collections.asCollection(ArrayList.class, "alex", "seva"));
    message.setAttachment(Collections.asCollection(ArrayList.class, att1, att2, att3, null));
    message.setSignature(signature);

    String encoded = message.toString();
    log(encoded);

    try {
      JSObject.parse(new ByteArrayInputStream(encoded.replace("438DA4", "XXX").getBytes(StandardCharsets.UTF_8)));
    }
    catch (final DecodeException e) {
      if (!e.getMessage().startsWith("\"data\" does not match pattern"))
        throw e;
    }

    try {
      JSObject.parse(new ByteArrayInputStream(encoded.replace("\"filename\": \"data1.txt\", ", "").getBytes(StandardCharsets.UTF_8)));
    }
    catch (final DecodeException e) {
      if (!e.getMessage().startsWith("\"filename\" is missing"))
        throw e;
    }

    final api.Message decoded = (api.Message)JSObject.parse(new ByteArrayInputStream(encoded.getBytes(StandardCharsets.UTF_8)));
    final String reEncoded = decoded.toString();
    log(reEncoded);

    Assert.assertEquals(encoded, reEncoded);
  }
}