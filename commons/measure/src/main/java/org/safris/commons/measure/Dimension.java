/*  Copyright Safris Software 2014
 *
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.commons.measure;

import java.util.HashMap;
import java.util.Map;

public abstract class Dimension {
  protected static abstract class Unit {
    private static final Map<String,Ratio<?,?>> ratios = new HashMap<String,Ratio<?,?>>();

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <N extends Unit,D extends Unit>Ratio<N,D> ratio(final N numerator, final D denominator) {
      final String name = numerator + "/" + denominator;
      Ratio unit = ratios.get(name);
      if (unit != null)
        return unit;

      synchronized (ratios) {
        unit = ratios.get(name);
        if (unit != null)
          return unit;

        ratios.put(name, unit = new Ratio<N,D>(name, numerator.factor / denominator.factor, numerator, denominator));
      }

      return unit;
    }

    protected static class Ratio<N extends Unit,D extends Unit> extends Unit {
      private final Unit denominator;

      protected Ratio(final String name, final double factor, final N numerator, final D denominator) {
        super(name, factor, numerator);
        this.denominator = denominator;
      }

      protected double getFactor(final Ratio<N,D> basis) {
        return getFactor((Unit)basis) / getFactor(basis.denominator);
      }
    }

    private static final Map<String,Product<?,?>> products = new HashMap<String,Product<?,?>>();

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <F extends Unit,S extends Unit>Product<F,S> produc(final F first, final S second) {
      final String name = first + "/" + second;
      Product unit = products.get(name);
      if (unit != null)
        return unit;

      synchronized (products) {
        unit = products.get(name);
        if (unit != null)
          return unit;

        products.put(name, unit = new Product<F,S>(name, first.factor / second.factor, first, second));
      }

      return unit;
    }

    protected static class Product<F extends Unit,S extends Unit> extends Unit {
      private final Unit second;

      protected Product(final String name, final double factor, final F first, final S second) {
        super(name, factor, first);
        this.second = second;
      }

      protected double getFactor(final Product<F,S> basis) {
        return getFactor((Unit)basis) * getFactor(basis.second);
      }
    }

    private static final Map<Unit,Map<Unit,Double>> basisToUnitFactors = new HashMap<Unit,Map<Unit,Double>>();

    private static Map<Unit,Double> register(final Unit from, final Unit to, final double factor) {
      Map<Unit,Double> unitToFactor = basisToUnitFactors.get(to);
      if (unitToFactor == null)
        basisToUnitFactors.put(to, unitToFactor = new HashMap<Unit,Double>());

      unitToFactor.put(from, factor);
      return unitToFactor;
    }

    private static void cascade(final Map<Unit,Double> unitToFactor, final Unit from, final double factor) {
      for (final Map.Entry<Unit,Double> entry : unitToFactor.entrySet()) {
        if (entry.getKey() != from) {
          register(from, entry.getKey(), from.factor / entry.getValue());
          register(entry.getKey(), from, entry.getValue() / from.factor);
        }
      }
    }

    public static void printConversionTable() {
      for (final Map.Entry<Unit,Map<Unit,Double>> entry : basisToUnitFactors.entrySet())
        for (final Map.Entry<Unit,Double> entry2 : entry.getValue().entrySet())
          System.out.println("1 " + entry2.getKey().name + " = " + entry2.getValue() + " * " + entry.getKey().name);
    }

    // FIXME: This is not used yet
    private static final Map<Class<?>,Unit> defaults = new HashMap<Class<?>,Unit>();
    
    protected final String name;
    protected final double factor;

    protected Unit(final String name, final double factor, final Unit basis) {
      this.name = name;
      this.factor = factor;
      if (basis != null) {
        register(basis, this, 1 / factor);
        final Map<Unit,Double> unitToFactor = register(this, basis, factor);
        cascade(unitToFactor, this, factor);
      }
      else {
        if (defaults.containsKey(getClass().getDeclaringClass()))
          throw new IllegalArgumentException("Attempted to assign two default Unit(s) for " + getClass().getDeclaringClass());
        
        defaults.put(getClass().getDeclaringClass(), basis);
      }
      
      // printConversionTable();
      // System.out.println("-------------");
    }

    protected double getFactor(final Unit basis) {
      if (basis == null)
        throw new IllegalArgumentException("basis == null");

      if (this == basis)
        return 1;
      
      Map<Unit,Double> unitToFactor = basisToUnitFactors.get(basis);
      Double factor = null;
      if (unitToFactor != null)
        factor = unitToFactor.get(this);

      if (factor != null)
        return factor;

      unitToFactor = basisToUnitFactors.get(this);
      if (unitToFactor != null)
        factor = unitToFactor.get(this);

      return factor != null ? 1 / factor : 1;
    }

    public String toString() {
      return name;
    }
  }

  protected static abstract class Scalar<U extends Unit> {
    private final double value;
    protected final U unit;

    protected Scalar(final double value, final U unit) {
      if (unit == null)
        throw new NullPointerException("unit == null");

      this.unit = unit;
      this.value = value;
    }
    
    protected Scalar<U> replicate(final double value) {
      try {
        return (Scalar<U>)getClass().getConstructor(double.class, unit.getClass()).newInstance(value, unit);
      }
      catch (final Exception e) {
        throw new Error(e);
      }
    }
    
    public double value(final U unit) {
      return value * this.unit.getFactor(unit);
    }

    public boolean equals(final Object obj) {
      return this == obj || (obj instanceof Scalar && ((Scalar<?>)obj).value == value && ((Scalar<?>)obj).unit == unit);
    }

    public int hashCode() {
      return (int)Double.doubleToLongBits(value);
    }

    public String toString() {
      return value + " " + unit.name;
    }
  }
  
  protected static abstract class Vector<I extends Scalar<? extends Unit>,J extends Scalar<? extends Unit>> {
    /*private static double scalar(final Scalar<?> s, final Unit unit) {
      return s.value * s.unit.getFactor(unit);
    }*/
    
    public final I i;
    public final J j;

    protected Vector(final I i, final J j) {
      this.i = i;
      this.j = j;
    }

    public boolean equals(final Object obj) {
      return this == obj || (obj instanceof Vector && super.equals(obj) && (i != null ? i.equals(((Vector<?,?>)obj).i) : ((Vector<?,?>)obj).j == null) && (j != null ? j.equals(((Vector<?,?>)obj).j) : ((Vector<?,?>)obj).j == null));
    }

    public int hashCode() {
      return super.hashCode() + (i != null ? i.hashCode() : -1) + (j != null ? j.hashCode() : -1);
    }

    public String toString() {
      return "(" + (i != null ? i.toString() : "null") + ", " + (j != null ? j.toString() : "null") + ")";
    }
  }
}