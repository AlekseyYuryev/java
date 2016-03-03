package org.safris.maven.plugin.version;

public enum DependencyType {
  DEPENDENCY("project/dependencies/dependency", "project/profiles/profile/dependencies/dependency"),
  MANAGED_DEPENDENCY("project/dependencyManagement/dependencies/dependency", "project/profiles/profile/dependencyManagement/dependencies/dependency"),
  PLUGIN("project/plugins/plugin", "project/profiles/profile/plugins/plugin"),
  MANAGED_PLUGIN("project/pluginManagement/plugins/plugin", "project/profiles/profile/pluginManagement/plugins/plugin"),
  POM("project");

  private final String[] scope;

  DependencyType(final String ... scope) {
    this.scope = scope;
  }

  public String[] scope() {
    return scope;
  }
}