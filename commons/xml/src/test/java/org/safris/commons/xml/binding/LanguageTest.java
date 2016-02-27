package org.safris.commons.xml.binding;

import org.junit.Assert;
import org.junit.Test;
import org.safris.commons.test.LoggableTest;

public class LanguageTest extends LoggableTest {
  @Test
  public void testLanguage() {
    try {
      Language.parseLanguage(null);
      Assert.fail("Expected a NullPointerException");
    }
    catch (final NullPointerException e) {
    }

    try {
      Language.parseLanguage("");
      Assert.fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Language.parseLanguage("11");
      Assert.fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Language.parseLanguage("superlong");
      Assert.fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Language.parseLanguage("witha1");
      Assert.fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Language.parseLanguage("witha-another#");
      Assert.fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Language.parseLanguage("witha-and-another#");
      Assert.fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Language.parseLanguage("721-fhajdo-f1h");
      Assert.fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    final String[] languages = new String[] {"art-lojban", "az-Arab", "az-Cyrl", "az-Latn", "be-Latn", "bs-Cyrl", "bs-Latn", "cel-gaulish", "de-1901", "de-1996", "de-AT-1901", "de-AT-1996", "de-CH-1901", "de-CH-1996", "de-DE-1901", "de-DE-1996", "en-boont", "en-GB-oed", "en-scouse", "es-419", "i-ami", "i-bnn", "i-default", "i-enochian", "i-hak", "i-klingon", "i-lux", "i-mingo", "i-navajo", "i-pwn", "i-tao", "i-tay", "i-tsu", "iu-Cans", "iu-Latn", "mn-Cyrl", "mn-Mong", "no-bok", "no-nyn", "sgn-BE-fr", "sgn-BE-nl", "sgn-BR", "sgn-CH-de", "sgn-CO", "sgn-DE", "sgn-DK", "sgn-ES", "sgn-FR", "sgn-GB", "sgn-GR", "sgn-IE", "sgn-IT", "sgn-JP", "sgn-MX", "sgn-NL", "sgn-NO", "sgn-PT", "sgn-SE", "sgn-US", "sgn-ZA", "sl-rozaj", "sr-Cyrl", "sr-Latn", "tg-Arab", "tg-Cyrl", "uz-Cyrl", "uz-Latn", "yi-latn", "zh-cmn", "zh-cmn-Hans", "zh-cmn-Hant", "zh-gan", "zh-guoyu", "zh-hakka", "zh-Hans", "zh-Hans-CN", "zh-Hans-HK", "zh-Hans-MO", "zh-Hans-SG", "zh-Hans-TW", "zh-Hant", "zh-Hant-CN", "zh-Hant-HK", "zh-Hant-MO", "zh-Hant-SG", "zh-Hant-TW", "zh-min", "zh-min-nan", "zh-wuu", "zh-xiang", "zh-yue"};
    for (final String language : languages)
      Assert.assertEquals(language, Language.parseLanguage(language).toString());
  }
}