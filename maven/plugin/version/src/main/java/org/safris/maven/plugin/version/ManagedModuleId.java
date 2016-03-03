package org.safris.maven.plugin.version;

public class ManagedModuleId extends ManagedReference {
  private final ModuleId moduleId;

  public ManagedModuleId(final String groupId, final String artifactId, final Version version, final POMFile manager, final DependencyType dependencyType) {
    super(manager, dependencyType);
    this.moduleId = new ModuleId(groupId, artifactId, version);
  }

  public ManagedModuleId(final ModuleId moduleId) {
    this(moduleId.groupId(), moduleId.artifactId(), moduleId.version(), null, null);
  }

  public ModuleId moduleId() {
    return moduleId;
  }
}