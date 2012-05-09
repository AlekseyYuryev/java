package org.safris.commons.lang;

public class Pair<A,B> {
  public final A a;
  public final B b;

  public Pair(final A a, final B b) {
    this.a = a;
    this.b = b;
  }

  public boolean equals(final Object obj) {
    if (obj == this)
      return true;

    if (!(obj instanceof Pair))
      return false;

    final Pair that = (Pair)obj;
    return (a != null ? a.equals(that.a) : that.a == null) && (b != null ? b.equals(that.b) : that.b == null);
  }

  public int hashCode() {
    return (a != null ? a.hashCode() : 0) + (b != null ? b.hashCode() : 0);
  }
}
