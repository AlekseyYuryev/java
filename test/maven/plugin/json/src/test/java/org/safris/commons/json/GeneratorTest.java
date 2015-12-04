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

import json.Attachment;
import json.Message;
import json.Signature;

import org.junit.Assert;
import org.junit.Test;
import org.safris.commons.util.Collections;

public class GeneratorTest {
  @Test
  public void testGenerator() throws Exception {
    //Generator.generate(Resources.getResource("json.xml").getURL(), new File("target/generated-test-sources/json"));

    final Attachment att1 = new Attachment();
    att1.setFilename("data1.text");
    att1.setSerial(2);

    final Attachment att2 = new Attachment();
    att2.setData("data2");
    att2.setSerial(-2.424242424);

    final Attachment att3 = new Attachment();
    att3.setFilename("data3.text");
    att3.setData("data3");
    att3.setSerial(99999);

    final Signature signature = new Signature();
    signature.setPubRsa("pub_rsa");
    signature.setXmldsig("xmldsig");

    final Message message = new Message();
    message.setSubject("Test subject");
    message.setImortant(true);
    message.setRecipients(Collections.asCollection(ArrayList.class, "alex", "seva"));
    message.setAttachment(Collections.asCollection(ArrayList.class, att1, att2, att3, null));
    message.setSignature(signature);

    final String encoded = message.toString();
    System.out.println(encoded);

    final Message decoded = (Message)JSObject.parse(new ByteArrayInputStream(encoded.getBytes(StandardCharsets.UTF_8)));
    final String reEncoded = decoded.toString();
    System.out.println(reEncoded);

    Assert.assertEquals(encoded, reEncoded);
  }
}