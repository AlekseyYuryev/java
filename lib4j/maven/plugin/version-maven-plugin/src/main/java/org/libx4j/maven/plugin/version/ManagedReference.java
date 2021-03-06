/* Copyright (c) 2016 lib4j
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

package org.libx4j.maven.plugin.version;

public abstract class ManagedReference {
  private final POMFile manager;
  private final DependencyType dependencyType;

  public ManagedReference(final POMFile manager, final DependencyType dependencyType) {
    this.manager = manager;
    this.dependencyType = dependencyType;
  }

  public POMFile manager() {
    return manager;
  }

  public DependencyType dependencyType() {
    return dependencyType;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof ManagedReference))
      return false;

    final ManagedReference that = (ManagedReference)obj;
    return (manager != null ? manager.equals(that.manager) : that.manager == null) && (dependencyType == that.dependencyType);
  }

  @Override
  public int hashCode() {
    return (manager != null ? manager.hashCode() : -5) ^ (dependencyType != null ? dependencyType.ordinal() : -7);
  }
}