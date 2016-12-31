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

package org.safris.maven.plugin.xjb;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.safris.commons.lang.Resources;
import org.safris.commons.test.LoggableTest;
import org.safris.commons.util.Collections;
import org.safris.xjb.runtime.DecodeException;
import org.safris.xjb.runtime.EncodeException;
import org.safris.xjb.runtime.JSArray;
import org.safris.xjb.runtime.JSObject;

import xjb.api;

public class JSObjectTest extends LoggableTest {
  @Test
  public void testJSObject() throws Exception {
    //Generator.generate(Resources.getResource("json.xml").getURL(), new File("target/generated-test-sources/json"));

    final api.Message.Attachment att1 = new api.Message.Attachment();
    att1.serial(2);
    final api.Message.Attachment.Data data1 = new api.Message.Attachment.Data();
    att1.data(data1);
    data1.a("\"1A");
    data1.b("\\1B");
    data1.c("1C");

    try {
      att1.toString();
    }
    catch (final EncodeException e) {
      if (!e.getMessage().startsWith("\"filename\" is required"))
        throw e;
    }

    att1.filename(null);

    try {
      att1.toString();
    }
    catch (final EncodeException e) {
      if (!e.getMessage().startsWith("\"filename\" cannot be null"))
        throw e;
    }

    att1.filename("data1.txt");

    final api.Message.Attachment att2 = new api.Message.Attachment();
    final api.Message.Attachment.Data data2 = new api.Message.Attachment.Data();
    att2.data(data2);
    data2.a("2X");
    data2.b("2B");
    data2.c("2C");

    att2.filename("data2.txt");
    att2.serial(-2.424242424);

    try {
      att2.toString();
    }
    catch (final EncodeException e) {
      if (!e.getMessage().startsWith("\"a\" does not match pattern"))
        throw e;
    }

    data2.a("2A");

    final api.Message.Attachment att3 = new api.Message.Attachment();
    att3.filename("data3.txt");
    final api.Message.Attachment.Data data3 = new api.Message.Attachment.Data();
    att3.data(data3);
    data3.a("\"3A");
    data3.b("\\3B");
    data3.c("3C");
    att3.serial(99999);

    final api.Signature signature = new api.Signature();
    signature.pubRsa("pub_rsa");
    signature.xmldsig("xmldsig");

    final api.Message message = new api.Message();
    final String subject = "Test subject";
    message.subject(subject);
    final String url = "http://www.thesaurus.com/browse/cool?s=t";
    message.url(url);
    message.important(true);
    final Collection<String> recipients = Collections.asCollection(ArrayList.class, "alex", "seva");
    message.recipients(recipients);
    message.emptyarray(new ArrayList<String>());
    final api.Message.Attachment[] attachment = {att1, att2, att3, null};
    message.attachment(attachment);
    message.signature(signature);

    String encoded;
    try {
      encoded = message.toString();
    }
    catch (final EncodeException e) {
      if (!e.getMessage().startsWith("\"requiredArray\" is required"))
        throw e;
    }

    message.requiredArray((Collection<Boolean>)null);
    try {
      encoded = message.toString();
    }
    catch (final EncodeException e) {
      if (!e.getMessage().startsWith("\"requiredArray\" cannot be null"))
        throw e;
    }

    message.requiredArray(new ArrayList<Boolean>());

    message.notRequired(true);
    message.notRequired.clear();

    message.notRequiredArray((Collection<Boolean>)null);
    message.notRequiredArray.clear();

    encoded = message.toString();
    if (encoded.indexOf("notRequired") != -1)
      Assert.fail("message.notRequired or message.notRequiredArray should not be present in the encoded string");

    log(encoded);

    try {
      JSObject.parse(api.Message.class, new StringReader(encoded.replace("438DA4", "XXX")));
    }
    catch (final DecodeException e) {
      if (!e.getMessage().startsWith("\"data\" does not match pattern"))
        throw e;
    }

    try {
      JSObject.parse(api.Message.class, new StringReader(encoded.replace("\"filename\": \"data1.txt\", ", "")));
    }
    catch (final DecodeException e) {
      if (!e.getMessage().startsWith("\"filename\" is missing"))
        throw e;
    }

    final api.Message decoded = JSObject.parse(api.Message.class, new StringReader(encoded));
    final String reEncoded = decoded.toString();
    log(reEncoded);

    Assert.assertEquals(encoded, reEncoded);
    Assert.assertEquals(subject, decoded.subject());
    Assert.assertEquals(url, decoded.url());
    Assert.assertEquals(true, decoded.important());
    Assert.assertEquals(recipients, decoded.recipients());
    Assert.assertEquals(0, decoded.emptyarray().size());
    Assert.assertArrayEquals(attachment, decoded.attachment().toArray());
    Assert.assertEquals(signature, decoded.signature());
  }

  @Test
  public void testPayPalObject() throws Exception {
    JSObject.parse(api.PayPalEvent.class, new InputStreamReader(Resources.getResource("paypal.json").getURL().openStream()));
  }

  @Test
  public void testGiphyObject() throws Exception {
    final api.Giphy giphy = JSObject.parse(api.Giphy.class, new InputStreamReader(Resources.getResource("giphy.json").getURL().openStream()));
    final String expected = "{\n  \"data\": [{\n    \"id\": \"iuHaJ0D7macZq\",\n    \"url\": \"http://giphy.com/gifs/cat-day-tomorrow-iuHaJ0D7macZq\"\n  }, {\n    \"id\": \"Z1kpfgtHmpWHS\",\n    \"url\": \"http://giphy.com/gifs/cat-way-make-Z1kpfgtHmpWHS\"\n  }, {\n    \"id\": \"ZpWJhFSusGSac\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-ZpWJhFSusGSac\"\n  }, {\n    \"id\": \"dLd4DTFSeGwc8\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-dLd4DTFSeGwc8\"\n  }, {\n    \"id\": \"gWFhtJxDgDUXK\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-gWFhtJxDgDUXK\"\n  }, {\n    \"id\": \"cTaC7ryiBZIxG\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-cTaC7ryiBZIxG\"\n  }, {\n    \"id\": \"ZjrZpeIkBclpK\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-ZjrZpeIkBclpK\"\n  }, {\n    \"id\": \"dir7Th3EEsA6s\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-dir7Th3EEsA6s\"\n  }, {\n    \"id\": \"bKrynkeJjTKow\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-bKrynkeJjTKow\"\n  }, {\n    \"id\": \"csBIJ9q2AXx1m\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-csBIJ9q2AXx1m\"\n  }, {\n    \"id\": \"Xnlz8u878NR8A\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-Xnlz8u878NR8A\"\n  }, {\n    \"id\": \"SBIDrovnm0wOA\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-SBIDrovnm0wOA\"\n  }, {\n    \"id\": \"QJVrZI6VHmt4Q\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-QJVrZI6VHmt4Q\"\n  }, {\n    \"id\": \"VIcgtjYlDLfkk\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-VIcgtjYlDLfkk\"\n  }, {\n    \"id\": \"VDYtzvOm5fQcM\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-VDYtzvOm5fQcM\"\n  }, {\n    \"id\": \"OvJuHbDfem9Co\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-OvJuHbDfem9Co\"\n  }, {\n    \"id\": \"PFifISGfIz2vK\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-PFifISGfIz2vK\"\n  }, {\n    \"id\": \"Mp592a0PuVzbi\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-Mp592a0PuVzbi\"\n  }, {\n    \"id\": \"MYXU72VlYV3i\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-MYXU72VlYV3i\"\n  }, {\n    \"id\": \"GOWYG0kaBDkwo\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-GOWYG0kaBDkwo\"\n  }, {\n    \"id\": \"FMppCCrk97sB2\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-FMppCCrk97sB2\"\n  }, {\n    \"id\": \"HHp5GPMG5PwHu\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-HHp5GPMG5PwHu\"\n  }, {\n    \"id\": \"DDqfQ5YNY6yLC\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-DDqfQ5YNY6yLC\"\n  }, {\n    \"id\": \"Cw8m4xnyJJQ7m\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-Cw8m4xnyJJQ7m\"\n  }, {\n    \"id\": \"BVwi1Pwm6kaT6\",\n    \"url\": \"http://giphy.com/gifs/funny-cat-BVwi1Pwm6kaT6\"\n  }]\n}";
    Assert.assertEquals(expected, giphy.toString());
  }

  private static void assertJSObject(final JSObject object, final Class<?> type) throws Exception {
    final String string = object.toString();
    final JSObject parsed = JSObject.parse(type, new StringReader(string));
    Assert.assertEquals(object, parsed);
    Assert.assertEquals(string, parsed.toString());
  }

  @Test
  public void testArray() throws Exception {
    final JSArray<String> array1 = new JSArray<String>();
    array1.add("a");
    array1.add("b");
    array1.add("c");
    assertJSObject(array1, String.class);

    final JSArray<Number> array2 = new JSArray<Number>();
    array2.add(1);
    array2.add(2);
    array2.add(3);
    log(array2.toString());

    final JSArray<api.Dsig> array3 = new JSArray<api.Dsig>();
    api.Dsig dsig = new api.Dsig();
    dsig.xmldsig("one");
    array3.add(dsig);

    dsig = new api.Dsig();
    dsig.xmldsig("two");
    array3.add(dsig);

    dsig = new api.Dsig();
    dsig.xmldsig("three");
    array3.add(dsig);
    log(array3.toString());
  }
}