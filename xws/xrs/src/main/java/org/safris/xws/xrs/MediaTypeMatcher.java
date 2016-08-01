package org.safris.xws.xrs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class MediaTypeMatcher<T extends Annotation> {
  public static <T extends Annotation>T getMethodClassAnnotation(final Class<T> annotationClass, final Method method) {
    T annotation = method.getAnnotation(annotationClass);
    if (annotation != null)
      return annotation;

    return method.getDeclaringClass().getAnnotation(annotationClass);
  }

  private final T annotation;
  private MediaType[] mediaTypes;

  @SuppressWarnings("unchecked")
  public MediaTypeMatcher(final Method method, final Class<T> annotationClass) {
    if (annotationClass == Consumes.class)
      annotation = (T)getMethodClassAnnotation((Class<Consumes>)annotationClass, method);
    else if (annotationClass == Produces.class)
      annotation = (T)getMethodClassAnnotation((Class<Produces>)annotationClass, method);
    else
      throw new IllegalArgumentException("Expected @Consumes or @Produces, but got: " + annotationClass.getName());

    this.mediaTypes = annotation == null ? new MediaType[] {MediaType.WILDCARD_TYPE} : MediaTypeUtil.parse(annotation instanceof Consumes ? ((Consumes)annotation).value() : annotation instanceof Produces ? ((Produces)annotation).value() : null);
  }

  public boolean matches(final MediaType[] mediaTypes) {
    for (final MediaType required : this.mediaTypes)
      for (final MediaType test : mediaTypes)
        if (MediaTypeUtil.matches(required, test))
          return true;

    return false;
  }

  public T getAnnotation() {
    return annotation;
  }
}