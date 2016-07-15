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

package org.safris.maven.plugin.version;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.eclipse.jgit.api.Git;
import org.safris.commons.lang.Arrays;
import org.safris.commons.lang.Enums;
import org.safris.commons.lang.Paths;
import org.safris.maven.common.AdvancedMojo;
import org.safris.maven.plugin.version.OriginalResolver.Entry;

@Mojo(name = "update", defaultPhase = LifecyclePhase.VALIDATE)
@Execute(goal = "update")
public final class VersionMojo extends AdvancedMojo {
  private static final File cwd = new File(System.getProperty("user.dir"));
  private static final File repoDir = GitUtil.findRepositoryDir(cwd);

  private static Version getPreviousVersion(final POMFile pomFile, final Map<String,Entry> originals) throws IOException, MavenExecutionException {
    final OriginalResolver.Entry oldEntry = originals.get(Paths.canonicalize(pomFile.file().getAbsolutePath().substring(repoDir.getAbsolutePath().length() + 1)));
    return oldEntry == null ? null : POMFile.parse(null, oldEntry.text).version();
  }

  @Parameter(property = "incrementPart", required = true)
  private Version.Part incrementPart;

  @Parameter(property = "incrementSnapshot", defaultValue = "false")
  private Boolean incrementSnapshot;

  @Component
  private MavenSession session;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    try {
      final Git git = Git.open(repoDir);
      final Set<String> changedFilePaths = GitUtil.lookupChangedFiles(cwd, repoDir, git);
      if (changedFilePaths.size() == 0) {
        getLog().info("Detected 0 changed files staged to commit. Will skip POM version check.");
        return;
      }

      final Map<String,Entry> originals = new OriginalResolver(git.getRepository()).resolve();
      getLog().info("Detected " + changedFilePaths.size() + " changed files staged to commit. Will check POM version...");

      // Ensure maven is being run as: "mvn validate"
      final List<String> goals = session.getRequest().getGoals();
      final LifecyclePhase[] phases = Enums.valueOf(LifecyclePhase.class, Arrays.<String>filter(new Arrays.Filter<String>() {
        @Override
        public String filter(final String value) {
          return value.toUpperCase();
        }
      }, goals.toArray(new String[goals.size()])));

      final POMFile pomFile = POMFile.entry(new File("pom.xml"));
      pomFile.checkIncreaseVersion(incrementPart);
      final Set<POMFile> finalUpdate = new HashSet<POMFile>();
      for (final POMFile update : POMFile.getPendingUpdates()) {
        final Version previousVersion = getPreviousVersion(update, originals);
        if (!incrementSnapshot && update.version().suffix().endsWith("SNAPSHOT")) {
          getLog().info("[SKIPPING] " + ModuleId.toString(update));
          getLog().info("   Reason: Not incrementing SNAPSHOT versions; see -DincrementSnapshot");
        }
        else if (previousVersion == null) {
          getLog().info("[SKIPPING] " + ModuleId.toString(update));
          getLog().info("   Reason: New module.");
        }
        else {
          final int comparison = update.version().compareTo(previousVersion);
          if (comparison == 0) {
            getLog().info("[UPDATING] " + ModuleId.toString(update) + " -> " + update.newVersion());
            getLog().info("   Reason: Current version not updated since last git commit.");
            finalUpdate.add(update);
          }
          else if (comparison > 0) {
            getLog().info("[SKIPPING] " + ModuleId.toString(update));
            getLog().info("   Reason: Current version is higher than in last git commit.");
          }
          else if (comparison < 0) {
            getLog().info("[UPDATING] " + ModuleId.toString(update));
            getLog().info("   Reason: Current version is lower than in last git commit.");
            finalUpdate.add(update);
          }
        }
      }

      if (finalUpdate.size() != 0) {
        for (final LifecyclePhase phase : phases) {
          if (LifecyclePhase.VALIDATE.ordinal() < phase.ordinal()) {
            final String error = "Maven must be executed with validate phase only for the version plugin to update POM versions.";
            getLog().error(error);
            throw new MojoExecutionException(error);
          }
        }
      }

      final Transaction transaction = new Transaction();
      for (final POMFile update : finalUpdate) {
        update.addToTransaction(transaction);
      }

      transaction.commit();
    }
    catch (final Exception e) {
      throw new MojoFailureException(e.getMessage(), e);
    }
  }
}