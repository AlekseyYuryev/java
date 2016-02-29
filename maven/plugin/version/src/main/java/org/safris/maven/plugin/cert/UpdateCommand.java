package org.safris.maven.plugin.cert;

public class UpdateCommand extends ModuleId {
  private final String xpath;
  private final String newVersion;

  public UpdateCommand(final String xpath, final ModuleId moduleId, final String newVersion) {
    super(moduleId.groupId(), moduleId.artifactId(), moduleId.version());
    this.xpath = xpath;
    this.newVersion = newVersion;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof UpdateCommand) || !super.equals(obj))
      return false;

    final UpdateCommand that = (UpdateCommand)obj;
    return xpath != null ? xpath.equals(that.xpath) : that.xpath == null;
  }

  @Override
  public int hashCode() {
    return super.hashCode() * (xpath != null ? 13 * xpath.hashCode() : -73);
  }

  @Override
  public String toString() {
    return xpath + " : " + super.toString() + " --> " + newVersion;
  }
}