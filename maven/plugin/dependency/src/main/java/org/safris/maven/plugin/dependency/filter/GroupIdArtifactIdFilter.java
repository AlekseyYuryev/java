package org.safris.maven.plugin.dependency.filter;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.dependency.utils.filters.AbstractArtifactFeatureFilter;

public class GroupIdArtifactIdFilter extends AbstractArtifactFeatureFilter
{
    public GroupIdArtifactIdFilter(String include, String exclude)
    {
        super(include, exclude, "Artifact");
    }

    protected String getArtifactFeature(Artifact artifact)
    {
        return artifact.getGroupId() + ":" + artifact.getArtifactId();
    }
}
