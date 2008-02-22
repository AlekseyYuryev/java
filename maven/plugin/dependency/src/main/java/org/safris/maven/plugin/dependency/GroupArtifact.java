package org.safris.maven.plugin.dependency;

import org.apache.maven.artifact.Artifact;

public class GroupArtifact extends GroupArtifactVersionType
{
	public GroupArtifact(Artifact artifact)
	{
		super(artifact);
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof GroupArtifact))
			return false;

		final GroupArtifact groupArtifact = (GroupArtifact)obj;
		return getGroupId().equals(groupArtifact.getGroupId()) && getArtifactId().equals(groupArtifact.getArtifactId());
	}

	public int hashCode()
	{
		return (getGroupId() + ":" + getArtifactId()).hashCode();
	}
}
