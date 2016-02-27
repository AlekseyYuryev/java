package org.safris.commons.test;

import java.lang.annotation.Annotation;
import java.util.List;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;

public class LoggableRunner extends BlockJUnit4ClassRunner {
  public LoggableRunner(final Class<?> klass) throws InitializationError {
    super(klass);
  }

  @Override
  protected TestClass createTestClass(final Class<?> testClass) {
    return new TestClass(testClass) {
      @Override
      public List<FrameworkField> getAnnotatedFields(final Class<? extends Annotation> annotationClass) {
        final List<FrameworkField> fields = super.getAnnotatedFields(annotationClass);
        for (final FrameworkField field : fields)
          field.getField().setAccessible(true);

        return fields;
      }

      @Override
      public List<FrameworkMethod> getAnnotatedMethods() {
        final List<FrameworkMethod> methods = super.getAnnotatedMethods();
        for (final FrameworkMethod method : methods)
          method.getMethod().setAccessible(true);

        return methods;
      }
    };
  }

  @Override
  protected void validateFields(final List<Throwable> errors) {
  }

  // This overrides the super method and removes the isPublic() check on @Before and @After methods
  @Override
  protected void validateInstanceMethods(List<Throwable> errors) {
    validateTestMethods(errors);

    if (computeTestMethods().size() == 0) {
      errors.add(new Exception("No runnable methods"));
    }
  }
}