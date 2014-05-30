/*  Copyright Safris Software 2008
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

package org.safris.maven.plugin.dependency;

import org.apache.maven.artifact.Artifact;

public class GroupArtifactVersionType {
  private final String groupId;
  private final String artifactId;
  private final String version;
  private final String type;
  private final String scope;
  private final String path;

  public GroupArtifactVersionType(final Artifact artifact) {
    this.groupId = artifact.getGroupId();
    this.artifactId = artifact.getArtifactId();
    this.version = artifact.getBaseVersion();
    this.type = artifact.getType();
    this.scope = artifact.getScope();
    this.path = artifact.getFile() != null ? artifact.getFile().getAbsolutePath() : null;
  }

  public final String getGroupId() {
    return groupId;
  }

  public final String getArtifactId() {
    return artifactId;
  }

  public final String getVersion() {
    return version;
  }

  public final String getType() {
    return type;
  }

  public final String getScope() {
    return scope;
  }

  public final String getPath() {
    return path;
  }

  public boolean equals(final Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof GroupArtifact))
      return false;

    final GroupArtifact groupArtifact = (GroupArtifact)obj;
    return getGroupId().equals(groupArtifact.getGroupId()) && getArtifactId().equals(groupArtifact.getArtifactId()) && getVersion().equals(groupArtifact.getVersion()) && getType().equals(groupArtifact.getType());
  }

  public int hashCode() {
    return (getGroupId() + ":" + getArtifactId() + ":" + getVersion() + ":" + getType()).hashCode();
  }
}