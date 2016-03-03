package org.safris.maven.plugin.version;

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