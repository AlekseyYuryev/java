package org.safris.commons.lang;

import org.junit.Assert;
import org.junit.Test;
import org.safris.commons.test.LoggableTest;

@SuppressWarnings("unused")
public class HashCodeEqualsToStringsTest extends LoggableTest {
  private static enum E {
    a, b, c
  }

  private static class A {
    private final boolean bool = false;
    private transient byte b = 0x55;
    private volatile char c = 'c';
    private short s = (short)35;
    private int i = 439802;
    private long l = 382948392L;
    private float f = 3234.34923f;
    private double d = 38923432432.438498239d;
  }

  private static class B extends A {
    @NotHashable
    @NotEqualable
    private final Object o;

    public B(final String o) {
      this.o = o;
    }

    public B() {
      this("b");
    }
  }

  private static class C extends B {
    private final E e;

    public C(final E e) {
      this.e = e;
    }
  }

  private static class D extends C {
    @Equalable
    @Hashable
    private final Integer i = null;

    public D(final E e) {
      super(e);
    }
  }

  private static class F extends D {
    public F() {
      super(null);
    }
  }

  @Test
  public void testHashCode() {
    final int a = HashCodes.hashCode(new A());
    final int b = HashCodes.hashCode(new B());
    final int c = HashCodes.hashCode(new C(E.a));
    Assert.assertEquals(a, b);
    Assert.assertNotEquals(a, c);
    Assert.assertEquals(HashCodes.hashCode(new D(E.a)), HashCodes.hashCode(new D(E.b)));
  }

  @Test
  public void testEquals() {
    Assert.assertTrue(Equals.equals(new A(), new A()));
    Assert.assertTrue(Equals.equals(new B("a"), new B("b")));
    Assert.assertFalse(Equals.equals(new C(E.a), new C(E.b)));
    Assert.assertTrue(Equals.equals(new D(E.a), new D(E.b)));
  }

  @Test
  public void testToStrings() {
    log(ToStrings.toString(new A()));
    log(ToStrings.toString(new B()));
    log(ToStrings.toString(new C(E.a)));
    log(ToStrings.toString(new D(E.b)));
    log(ToStrings.toString(new F()));
  }
}