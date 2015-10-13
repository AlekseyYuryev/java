package org.safris.commons.json;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;

import json.Attachment;
import json.Message;
import json.Signature;

import org.junit.Assert;
import org.junit.Test;
import org.safris.commons.lang.Resources;

public class GeneratorTest {
  @Test
  public void testGenerator() throws Exception {
    Generator.generate(Resources.getResource("json.xml").getURL(), new File("target/generated-test-sources/json"));

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
    message.setRecipients("alex", "seva");
    message.setAttachment(att1, att2, att3, null);
    message.setSignature(signature);

    final String encoded = message.toString();
    System.out.println(encoded);

    final Message decoded = (Message)JSO.parse(new ByteArrayInputStream(encoded.getBytes(StandardCharsets.UTF_8)));
    final String reEncoded = decoded.toString();
    System.out.println(reEncoded);

    Assert.assertEquals(encoded, reEncoded);
  }
}