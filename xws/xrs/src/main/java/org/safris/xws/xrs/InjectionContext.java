package org.safris.xws.xrs;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.safris.commons.lang.reflect.Classes;

public class InjectionContext implements Cloneable {
  private static InjectionContext injectionContextPrototype = new InjectionContext(new Class<?>[] {
    HttpServletRequest.class,
    HttpServletResponse.class,
    ContainerRequestContext.class,
    ContainerResponseContext.class,
    SecurityContext.class
  });

  public static InjectionContext createInjectionContext() {
    return injectionContextPrototype.clone();
  }

  public static boolean allowsInjectableClass(final Class<?> type, final Class<?> injectableClass) {
    return injectionContextPrototype.allowsInjectableClass(injectableClass);
  }

  private final Set<Class<?>> allowedInjectableClasses = new HashSet<Class<?>>();
  private final Class<?>[] allowedClasses;

  private InjectionContext(final Class<?>[] allowedClasses) {
    this.allowedClasses = allowedClasses;
    for (final Class<?> allowedClass : allowedClasses)
      allowedInjectableClasses.add(allowedClass);
  }

  private final Map<Class<?>,Object> injectableClassToObject = new HashMap<Class<?>,Object>();

  private boolean allowsInjectableClass(final Class<?> injectableClass) {
    return getAllowedInjectableClass(injectableClass) != null;
  }

  private Class<?> getAllowedInjectableClass(final Class<?> injectableClass) {
    for (final Class<?> allowedInjectableClass : allowedInjectableClasses)
      if (allowedInjectableClass.isAssignableFrom(injectableClass))
        return allowedInjectableClass;

    return null;
  }

  public void addInjectableObject(final Object object) {
    final Class<?> allowedInjectibleClass = getAllowedInjectableClass(object.getClass());
    if (allowedInjectibleClass == null)
      throw new IllegalArgumentException("InjectionContext configuration does not allow injection of object of class " + object.getClass().getName());

    if (injectableClassToObject.put(allowedInjectibleClass, object) != null)
      throw new IllegalStateException("InjectableContext already contains injectable object of class " + object.getClass().getName());
  }

  @SuppressWarnings("unchecked")
  public <T>T getInjectableObject(final Class<T> injectableClass) {
    final Class<?> allowedInjectibleClass = getAllowedInjectableClass(injectableClass);
    if (allowedInjectibleClass == null)
      throw new IllegalArgumentException("InjectionContext configuration does not allow injection of object of class " + injectableClass.getName());

    return (T)injectableClassToObject.get(allowedInjectibleClass);
  }

  private <T>T testOrInject(final Class<T> targetClass, final boolean inject) {
    try {
      final T object = targetClass.newInstance();
      final Field[] fields = Classes.getDeclaredFieldsDeep(targetClass);
      for (final Field field : fields) {
        if (field.isAnnotationPresent(Context.class)) {
          final Object injectableObject = getInjectableObject(field.getType());
          if (injectableObject == null)
            throw new UnsupportedOperationException("Unsupported @Context type: " + field.getType().getName() + " on: " + targetClass.getName() + "." + field.getName());

          if (inject) {
            field.setAccessible(true);
            field.set(object, injectableObject);
          }
        }
      }

      return object;
    }
    catch (final IllegalArgumentException | ReflectiveOperationException e) {
      throw new WebApplicationException(e);
    }
  }

  public void test(final Class<?> targetClass) {
    testOrInject(targetClass, false);
  }

  public <T>T inject(final Class<T> targetClass) {
    return testOrInject(targetClass, true);
  }

  @Override
  public InjectionContext clone() {
    return new InjectionContext(allowedClasses);
  }
}
