package org.safris.maven.plugin.cert;

public enum DependencyType {
  DEPENDENCY("project/dependencies/dependency"),
  MANAGED_DEPENDENCY("project/dependencyManagement/dependencies/dependency"),
  PLUGIN("project/plugins/plugin"),
  MANAGED_PLUGIN("project/pluginManagement/plugins/plugin"),
  POM("project");

  public String scope;

  DependencyType(final String scope) {
    this.scope = scope;
  }
}