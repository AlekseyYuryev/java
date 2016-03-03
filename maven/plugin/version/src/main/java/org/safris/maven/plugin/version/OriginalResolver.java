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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.util.io.NullOutputStream;

public class OriginalResolver {
  public static class Entry {
    public final FileHeader fileHeader;
    public final String text;

    public Entry(final FileHeader fileHeader, final String text) {
      this.fileHeader = fileHeader;
      this.text = text;
    }
  }

  private class CustomFormatter extends DiffFormatter {
    private final Map<String,Entry> entries;

    public CustomFormatter(final Repository repository, final Map<String,Entry> entries) {
      super(NullOutputStream.INSTANCE);
      setRepository(repository);
      this.entries = entries;
    }

    @Override
    public void format(final FileHeader head, final RawText a, final RawText b) throws IOException {
      if (a != null)
        entries.put(head.getNewPath(), new Entry(head, a.getString(0, a.size(), true)));

      super.format(head, a, b);
    }
  }

  private final Repository repository;

  private AbstractTreeIterator cachedOldTree = null;
  private AbstractTreeIterator cachedNewTree = null;
  private AbstractTreeIterator oldTree = null;
  private AbstractTreeIterator newTree = null;

  public OriginalResolver(final Repository repository) {
    this.repository = repository;
  }

  public Map<String,Entry> resolve() throws IOException, NoHeadException {
    if (cachedOldTree == null) {
      final ObjectId head = repository.resolve(Constants.HEAD + "^{tree}"); //$NON-NLS-1$
      if (head == null)
        throw new NoHeadException(JGitText.get().cannotReadTree);

      CanonicalTreeParser treeParser = new CanonicalTreeParser();
      try (ObjectReader reader = repository.newObjectReader()) {
        treeParser.reset(reader, head);
      }

      cachedOldTree = treeParser;
    }

    if (cachedNewTree == null)
      cachedNewTree = new DirCacheIterator(repository.readDirCache());

    if (oldTree == null)
      oldTree = new DirCacheIterator(repository.readDirCache());

    if (newTree == null)
      newTree = new FileTreeIterator(repository);

    final Map<String,Entry> entries = new HashMap<String,Entry>();
    try (final DiffFormatter formatter = new CustomFormatter(repository, entries)) {
      formatter.format(formatter.scan(cachedOldTree, cachedNewTree));
      formatter.format(formatter.scan(oldTree, newTree));
    }

    return entries;
  }
}