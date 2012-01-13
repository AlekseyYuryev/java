package org.safris.maven.cert;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.maven.model.Repository;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal import
 * @phase compile
 */
public class CertMojo extends AbstractMojo {
  public static final Set<String> checkedURLs = new HashSet<String>();

  /**
   * @parameter expression="${project.repositories}" default-value=""
   * @optional
   */
  private List<Repository> repositories;

  /**
   * Scope to include. An Empty string indicates all scopes (default).
   */
  public List<Repository> getRepositories() {
    return repositories;
  }

  public void execute() throws MojoExecutionException, MojoFailureException {
    for (Repository repository : getRepositories()) {
      if (repository.getUrl().startsWith("https") && !checkedURLs.contains(repository.getUrl())) {
        try {
          final URL url = new URL(repository.getUrl());
          getLog().info(url.getHost() + ":" + url.getPort());
          String arg = url.getHost();
          if (url.getPort() != -1)
            arg += ":" + url.getPort();

          InstallCert.main(new String[]{arg});
          checkedURLs.add(repository.getUrl());
        }
        catch (FileNotFoundException e) {
          if (!e.getMessage().contains("(Permission denied)"))
            throw new MojoExecutionException("Failure due to InstallCert", e);

          getLog().error("Attempting to modify JDK CA certificates file " + e.getMessage());
          getLog().error("Please run the same command as root, via \"sudo\".");
          System.exit(0);
        }
        catch (Exception e) {
            throw new MojoExecutionException("Failure due to InstallCert", e);
        }
      }
    }
  }
}
