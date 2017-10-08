package org.libx4j.cdm;

import java.lang.annotation.Annotation;

import org.libx4j.cdm.model.code;
import org.libx4j.cdm.model.decl;
import org.libx4j.cdm.model.eval;
import org.libx4j.cdm.model.inst;

public final class cdm {
  public static code.Block BLOCK() {
    return null;
  }

  public static eval.Cast CAST(final code.Class cls, final eval.Evaluation evaluation) {
    return null;
  }

  public static eval.Evaluation LITERAL(final String string) {
    return null;
  }

  public static eval.Evaluation STRING(final String string) {
    return null;
  }

  @SafeVarargs
  public static <E extends eval.Evaluation>eval.Evaluation ARRAY(final E ... args) {
    return null;
  }

  public static <E extends eval.Evaluation>decl.Array ARRAY(final code.Class cls, final String name) {
    return null;
  }

  public static <E extends eval.Evaluation>inst.Array ARRAY(final code.Class cls, final eval.Evaluation ... evaluations) {
    return null;
  }

  public static <E extends eval.Evaluation>inst.Array ARRAY(final code.Class cls, final int length) {
    return null;
  }

  public static code.Class CLASS(final Class<?> cls) {
    return null;
  }

  public static code.PrimitiveClass BOOLEAN() {
    return null;
  }

  public static code.PrimitiveNumericClass BYTE() {
    return null;
  }

  public static code.PrimitiveNumericClass SHORT() {
    return null;
  }

  public static code.PrimitiveNumericClass INT() {
    return null;
  }

  public static code.PrimitiveNumericClass LONG() {
    return null;
  }

  public static code.PrimitiveNumericClass FLOAT() {
    return null;
  }

  public static code.PrimitiveNumericClass DOUBLE() {
    return null;
  }

  public static code.Annotation ANNOTATION(final Package pkg, final String simpleName) {
    return null;
  }

  public static code.Annotation ANNOTATION(final String className) {
    return null;
  }

  public static code.Annotation ANNOTATION(final Class<? extends Annotation> cls) {
    return null;
  }

  private cdm() {
  }
}