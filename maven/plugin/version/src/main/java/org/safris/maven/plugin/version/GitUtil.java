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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;

public class GitUtil {
  public static File findRepositoryDir(final File dir) {
    File repoDir = dir;
    while (repoDir != null && !new File(repoDir, ".git").isDirectory())
      repoDir = repoDir.getParentFile();

    return repoDir;
  }

  private static void filterSubpaths(final Set<String> paths, final String localPath) {
    final Iterator<String> iterator = paths.iterator();
    while (iterator.hasNext())
      if (!iterator.next().startsWith(localPath))
        iterator.remove();
  }

  public static Set<String> lookupChangedFiles(final File dir, final File repoDir, final Git git) throws GitAPIException {
    final Status status = git.status().call();
    final Set<String> changes = new HashSet<String>();
    changes.addAll(status.getChanged());
    changes.addAll(status.getAdded());
    changes.addAll(status.getRemoved());

    String localPath = dir.getAbsolutePath().substring(repoDir.getAbsolutePath().length());
    if (localPath.startsWith(File.separator))
      localPath = localPath.substring(1);

    filterSubpaths(changes, localPath);
    return changes;
  }

  private GitUtil() {
  }
}