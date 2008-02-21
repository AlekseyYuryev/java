package org.safris.maven.plugin.dependency;

import java.io.File;
import org.apache.maven.artifact.Artifact;

public class GroupArtifactVersionType
{
	private final String groupId;
	private final String artifactId;
	private final String version;
	private final String type;

	public GroupArtifactVersionType(String groupId, String artifactId, String version, String type)
	{
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
		this.type = type;
	}

	public GroupArtifactVersionType(Artifact artifact)
	{
		this.groupId = artifact.getGroupId();
		this.artifactId = artifact.getArtifactId();
		this.version = artifact.getVersion();
		this.type = artifact.getType();
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

	public final String getRelativePath()
	{
		// FIXME: This is not always a jar file.
		return getGroupId().replace('.', File.separatorChar) + File.separator + getArtifactId() + File.separator + getVersion() + File.separator + getArtifactId() + "-" + getVersion() + ".jar";
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
