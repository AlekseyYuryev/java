package org.libx4j.cdm.lexer;

import static org.libx4j.cdm.cdm.*;

import java.math.BigInteger;

import org.junit.Test;
import org.libx4j.cdm.model.Visibility;
import org.libx4j.cdm.model.code;

public class ModelTest {
  @Test
  public void test() {
    final code.Class cls = CLASS(DeclarationExample.class).setVisibility(Visibility.PUBLIC)
    .addAnnotation(ANNOTATION(SuppressWarnings.class).makeDeclaration(ARRAY(STRING("cast"), STRING("unused"))))
    .append(BOOLEAN().declare("bool"))
    .append(BYTE().declare("b").setVisibility(Visibility.PRIVATE).set(LITERAL("0b0")))
    .append(SHORT().declare("sh").set(LITERAL("0xF")))
    .append(INT().declare("i").setVisibility(Visibility.PROTECTED).setFinal(true))
    .append(LONG().declare("l").setVisibility(Visibility.PUBLIC))
    .append(FLOAT().declare("f").setVisibility(Visibility.PRIVATE))
    .append(DOUBLE().declare("d").setVisibility(Visibility.PRIVATE))
    .append(CLASS(BigInteger.class).declare("bi").setVisibility(Visibility.PRIVATE).set(CLASS(BigInteger.class).toType().instantiate(STRING("7382"))))
    .append(CLASS(String.class).declare("string").setVisibility(Visibility.PRIVATE).setStatic(true).set(STRING("string")))
    .append(CLASS(String.class).declare("finalString").setVisibility(Visibility.PROTECTED).setStatic(true).setFinal(true).set(STRING("string")))
    .append(ARRAY(SHORT(), "shortArray").set(ARRAY(SHORT())))
    .append(ARRAY(INT(), "intArray").set(ARRAY(INT(), LITERAL("0__1"), LITERAL("2"), LITERAL("3"))))
    .append(ARRAY(LONG(), "longArray").setTransient(true).set(ARRAY(LONG(), LITERAL("'\0'"), CAST(SHORT(), LITERAL("1")), CAST(INT(), LITERAL("2")), LITERAL("3l"), CAST(LONG(), LITERAL("4d")), FLOAT().toType().instantiate(LITERAL("5f")).invokeMethod("longValue", long.class))))
    .append(ARRAY(FLOAT(), "floatArray").setVolatile(true).set(ARRAY(FLOAT(), 3)))
    .append(BLOCK().append(INT()));
  }
}