package org.safris.maven.plugin.version;

public class ManagedPOMFile extends ManagedReference {
  private final POMFile pomFile;

  public ManagedPOMFile(final POMFile manager, final DependencyType dependencyType, final POMFile pomFile) {
    super(manager, dependencyType);
    this.pomFile = pomFile;
  }

  public POMFile pomFile() {
    return pomFile;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof ManagedPOMFile) || !super.equals(obj))
      return false;

    final ManagedPOMFile that = (ManagedPOMFile)obj;
    return pomFile != null ? pomFile.equals(that.pomFile) : that.pomFile == null;
  }

  @Override
  public int hashCode() {
    return super.hashCode() * (pomFile != null ? pomFile.hashCode() : -5);
  }
}