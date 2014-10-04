/* Copyright (c) 2008 Seva Safris
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