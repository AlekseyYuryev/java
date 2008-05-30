/*  Copyright 2008 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.maven.plugin.dependency;

import org.apache.maven.artifact.Artifact;

public class GroupArtifactVersionType
{
	private final String groupId;
	private final String artifactId;
	private final String version;
	private final String type;
	private final String scope;
	private final String path;

	public GroupArtifactVersionType(Artifact artifact)
	{
		this.groupId = artifact.getGroupId();
		this.artifactId = artifact.getArtifactId();
		this.version = artifact.getBaseVersion();
		this.type = artifact.getType();
		this.scope = artifact.getScope();
		this.path = artifact.getFile() != null ? artifact.getFile().getAbsolutePath() : null;
	}

	public final String getGroupId()
	{
		return groupId;
	}

	public final String getArtifactId()
	{
		return artifactId;
	}

	public final String getVersion()
	{
		return version;
	}

	public final String getType()
	{
		return type;
	}

	public final String getScope()
	{
		return scope;
	}

	public final String getPath()
	{
		return path;
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof GroupArtifact))
			return false;

		final GroupArtifact groupArtifact = (GroupArtifact)obj;
		return getGroupId().equals(groupArtifact.getGroupId()) && getArtifactId().equals(groupArtifact.getArtifactId()) && getVersion().equals(groupArtifact.getVersion()) && getType().equals(groupArtifact.getType());
	}

	public int hashCode()
	{
		return (getGroupId() + ":" + getArtifactId() + ":" + getVersion() + ":" + getType()).hashCode();
	}
}
