package org.safris.maven.plugin.dependency;

import java.io.File;
import java.util.List;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactCollector;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.archiver.ArchiverException;
import org.codehaus.plexus.archiver.UnArchiver;
import org.codehaus.plexus.archiver.manager.ArchiverManager;
import org.codehaus.plexus.archiver.manager.NoSuchArchiverException;
import org.codehaus.plexus.components.io.fileselectors.IncludeExcludeFileSelector;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.StringUtils;

public abstract class AbstractDependencyMojo extends AbstractMojo
{
    /**
     * Used to look up Artifacts in the remote repository.
     *
     * @parameter expression="${component.org.apache.maven.artifact.factory.ArtifactFactory}"
     * @required
     * @readonly
     */
    protected ArtifactFactory factory;

    /**
     * Used to look up Artifacts in the remote repository.
     *
     * @parameter expression="${component.org.apache.maven.artifact.resolver.ArtifactResolver}"
     * @required
     * @readonly
     */
    protected ArtifactResolver resolver;

    /**
     * Artifact collector, needed to resolve dependencies.
     *
     * @component role="org.apache.maven.artifact.resolver.ArtifactCollector"
     * @required
     * @readonly
     */
    protected ArtifactCollector artifactCollector;

    /**
     * @component role="org.apache.maven.artifact.metadata.ArtifactMetadataSource"
     *            hint="maven"
     * @required
     * @readonly
     */
    protected ArtifactMetadataSource artifactMetadataSource;

    /**
     * Location of the local repository.
     *
     * @parameter expression="${localRepository}"
     * @readonly
     * @required
     */
    protected ArtifactRepository local;

    /**
     * List of Remote Repositories used by the resolver
     *
     * @parameter expression="${project.remoteArtifactRepositories}"
     * @readonly
     * @required
     */
    protected List remoteRepos;

    /**
     * To look up Archiver/UnArchiver implementations
     *
     * @parameter expression="${component.org.codehaus.plexus.archiver.manager.ArchiverManager}"
     * @required
     * @readonly
     */
    protected ArchiverManager archiverManager;

    /**
     * POM
     *
     * @parameter expression="${project}"
     * @readonly
     * @required
     */
    protected MavenProject project;

    /**
     * Contains the full list of projects in the reactor.
     *
     * @parameter expression="${reactorProjects}"
     * @required
     * @readonly
     */
    protected List reactorProjects;

    /**
     * Output absolute filename for resolved artifacts
     *
     * @optional
     * @since 2.0
     * @parameter expression="${outputAbsoluteArtifactFilename}"
     *            default-value="false"
     */
    protected boolean outputAbsoluteArtifactFilename;

    /**
     * @return Returns the log.
     */
    public Log getLog()
    {
        return super.getLog();
    }

    /**
     * @return Returns the archiverManager.
     */
    public ArchiverManager getArchiverManager()
    {
        return this.archiverManager;
    }

    /**
     * Does the actual copy of the file and logging.
     *
     * @param artifact represents the file to copy.
     * @param destFile file name of destination file.
     *
     * @throws MojoExecutionException with a message if an
     *             error occurs.
     */
    protected void copyFile(File artifact, File destFile) throws MojoExecutionException
    {
        try
        {
            getLog().info("Copying " + (this.outputAbsoluteArtifactFilename ? artifact.getAbsolutePath() : artifact.getName()) + " to " + destFile);
            FileUtils.copyFile(artifact, destFile);
        }
        catch(Exception e)
        {
            throw new MojoExecutionException("Error copying artifact from " + artifact + " to " + destFile, e);
        }
    }

    protected void unpack(File file, File location) throws MojoExecutionException
    {
        unpack(file, location, null, null);
    }

    /**
     * Unpacks the archive file.
     *
     * @param file File to be unpacked.
     * @param location Location where to put the unpacked
     *            files.
     * @param includes Comma separated list of file patterns
     *            to include i.e. **\/*.xml,
     *            **\/*.properties
     * @param excludes Comma separated list of file patterns
     *            to exclude i.e. **\/*.xml,
     *            **\/*.properties
     */
    protected void unpack(File file, File location, String includes, String excludes) throws MojoExecutionException
    {
        try
        {
            getLog().info("Unpacking " + file.getPath() + "to\n " + location.getPath() + "\nwith Includes " + includes + " and excludes:" + excludes);

            location.mkdirs();

            final UnArchiver unArchiver = archiverManager.getUnArchiver(file);
            unArchiver.setSourceFile(file);
            unArchiver.setDestDirectory(location);
            if(StringUtils.isNotEmpty(excludes) || StringUtils.isNotEmpty(includes))
            {
                // Create the selectors that will filter
                // based on include/exclude parameters
                // MDEP-47
                IncludeExcludeFileSelector[] selectors = new IncludeExcludeFileSelector[] { new IncludeExcludeFileSelector() };

                if(StringUtils.isNotEmpty(excludes))
                    selectors[0].setExcludes(excludes.split(","));

                if(StringUtils.isNotEmpty(includes))
                    selectors[0].setIncludes(includes.split(","));

                unArchiver.setFileSelectors(selectors);
            }

            unArchiver.extract();
        }
        catch(NoSuchArchiverException e)
        {
            throw new MojoExecutionException("Unknown archiver type", e);
        }
        catch(ArchiverException e)
        {
            e.printStackTrace();
            throw new MojoExecutionException("Error unpacking file: " + file + " to: " + location + "\r\n" + e.toString(), e);
        }
    }

    /**
     * @return Returns the factory.
     */
    public ArtifactFactory getFactory()
    {
        return this.factory;
    }

    /**
     * @param factory The factory to set.
     */
    public void setFactory(ArtifactFactory factory)
    {
        this.factory = factory;
    }

    /**
     * @return Returns the project.
     */
    public MavenProject getProject()
    {
        return this.project;
    }

    /**
     * @return Returns the local.
     */
    public ArtifactRepository getLocal()
    {
        return this.local;
    }

    /**
     * @param local The local to set.
     */
    public void setLocal(ArtifactRepository local)
    {
        this.local = local;
    }

    /**
     * @return Returns the remoteRepos.
     */
    public java.util.List getRemoteRepos()
    {
        return this.remoteRepos;
    }

    /**
     * @param remoteRepos The remoteRepos to set.
     */
    public void setRemoteRepos(java.util.List remoteRepos)
    {
        this.remoteRepos = remoteRepos;
    }

    /**
     * @return Returns the resolver.
     */
    public ArtifactResolver getResolver()
    {
        return this.resolver;
    }

    /**
     * @param resolver The resolver to set.
     */
    public void setResolver(ArtifactResolver resolver)
    {
        this.resolver = resolver;
    }

    /**
     * @param archiverManager The archiverManager to set.
     */
    public void setArchiverManager(ArchiverManager archiverManager)
    {
        this.archiverManager = archiverManager;
    }

    /**
     * @return Returns the artifactCollector.
     */
    public ArtifactCollector getArtifactCollector()
    {
        return this.artifactCollector;
    }

    /**
     * @param theArtifactCollector The artifactCollector to
     *            set.
     */
    public void setArtifactCollector(ArtifactCollector theArtifactCollector)
    {
        this.artifactCollector = theArtifactCollector;
    }

    /**
     * @return Returns the artifactMetadataSource.
     */
    public ArtifactMetadataSource getArtifactMetadataSource()
    {
        return this.artifactMetadataSource;
    }

    /**
     * @param theArtifactMetadataSource The
     *            artifactMetadataSource to set.
     */
    public void setArtifactMetadataSource(ArtifactMetadataSource theArtifactMetadataSource)
    {
        this.artifactMetadataSource = theArtifactMetadataSource;
    }
}
