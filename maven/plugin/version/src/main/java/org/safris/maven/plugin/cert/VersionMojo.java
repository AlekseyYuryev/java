/* Copyright (c) 2015 Seva Safris
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

package org.safris.maven.plugin.cert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.maven.MavenExecutionException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.eclipse.jgit.api.Git;
import org.safris.commons.lang.Paths;
import org.safris.commons.maven.AdvancedMojo;
import org.safris.commons.maven.Log;
import org.safris.maven.plugin.cert.OriginalResolver.Entry;

/**
 * @goal update
 * @phase validate
 * @requiresDependencyResolution test
 */
public final class VersionMojo extends AdvancedMojo {
  /**
   * @parameter default-value="${project}"
   * @readonly
   * @required
   */
  private MavenProject project;

  private final File cwd = new File(System.getProperty("user.dir"));
  private final File repoDir = GitUtil.findRepositoryDir(cwd);

  private POMFile changePomVersion(final MavenProject project, final POMFile pomFile, final Map<String,Entry> originals, final Set<POMFile> forUpdate, final Set<POMFile> alreadyUpdated) throws IOException, MavenExecutionException, MojoExecutionException {
    if (pomFile.version() != null) {
//      if (pomFile.version().endsWith("-SNAPSHOT")) {
//        getLog().info("  SNAPSHOT version detected, not auto-incrementing.");
//        alreadyUpdated.add(pomFile);
//        return null;
//      }

      if ("RELEASE".equals(pomFile.version()) || "LATEST".equals(pomFile.version())) {
        getLog().warn("  " + pomFile.version() + " is a reserved meta-version.");
        return null;
      }
    }

    POMFile parentPOMFile = pomFile.getParent();

    getLog().info("Checking " + project.getGroupId() + ":" + project.getArtifactId() + ":" + project.getVersion() + " [" + pomFile.file().getPath().substring(cwd.getAbsolutePath().length() + 1) + "]");

    if (pomFile.groupId() == null)
      getLog().warn("  project does not have an explicit groupId.");

    if (pomFile.version() == null) {
      getLog().warn("  project does not have an explicit version, will increase version of parent POM.");
      return parentPOMFile;
    }

    final OriginalResolver.Entry oldEntry = originals.get(Paths.canonicalize(pomFile.file().getAbsolutePath().substring(repoDir.getAbsolutePath().length() + 1)));
    final POMFile oldRef = oldEntry == null ? null : POMFile.parse(null, oldEntry.text);
    if (oldRef != null) {
      final int comparison = oldRef.version() != null ? pomFile.version().compareTo(oldRef.version()) : pomFile.version() == null ? 0 : -1;
      if (comparison < 0) {
        getLog().error(" new version of " + pomFile.version() + " detected to be less than prior version of " + oldRef.version());
        throw new MojoExecutionException("Inconsistent version");
      }

      if (comparison > 0) {
        getLog().info("  new version detected, not auto-incrementing.");
        alreadyUpdated.add(pomFile);
        return null;
      }
    }

    getLog().info("  updated version to " + VersionUtil.increaseVersion(pomFile.version()));
    forUpdate.add(pomFile);
    return null;
  }

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    try {
      final POMFile pomFile = POMFile.parse(project.getFile(), new String(Files.readAllBytes(project.getFile().toPath())));
      pomFile.increaseVersion();
      for (final UpdateCommand update : POMFile.getPendingUpdates()) {
        getLog().info("  " + update);
      }
    }
    catch (final Exception e) {
      throw new MojoFailureException(e.getMessage(), e);
    }

    if (true)
      return;

    try {
      final Git git = Git.open(repoDir);
      final Set<String> changes = GitUtil.lookupChangedFiles(cwd, repoDir, git);
      if (changes.size() == 0) {
        getLog().info("Detected 0 changed files staged to commit. Will skip POM version check.");
        return;
      }

      final Map<String,Entry> originals = new OriginalResolver(git.getRepository()).resolve();
      getLog().info("Detected " + changes.size() + " changed files staged to commit. Will check POM version...");
      final Set<POMFile> forUpdate = new HashSet<POMFile>();
      final Set<POMFile> alreadyUpdated = new HashSet<POMFile>();
      MavenProject parent = project;
      POMFile pomFile = POMFile.parse(project.getFile(), new String(Files.readAllBytes(project.getFile().toPath())));
      while ((pomFile = changePomVersion(parent, pomFile, originals, forUpdate, alreadyUpdated)) != null && (parent = parent.getParent()) != null);

      if (forUpdate.size() == 0) {
        if (alreadyUpdated.size() == 0) {
          getLog().error("No POM was discovered with an updatable version.");
          throw new MojoExecutionException("Inconsistent version");
        }

        getLog().info("POM version [OK]");
      }
      else {
        try {
          for (final POMFile ref : forUpdate)
            ref.commit(forUpdate.contains(ref.getParent()));

          getLog().info("POM version has been updated. Please run mvn command again.");
          System.exit(0);
        }
        catch (final IOException e) {
          getLog().error("Rolling back changes due to exception", e);
          for (final POMFile ref : forUpdate)
            ref.rollback();
        }
      }
    }
    catch (final Exception e) {
      throw new MojoFailureException(e.getMessage(), e);
    }
  }
}