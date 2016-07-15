/* Copyright (c) 2006 Seva Safris
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

package org.safris.ant.task.xsb;

import org.apache.tools.ant.Project;
import org.safris.maven.common.Resolver;

public class AntPropertyResolver implements Resolver<String> {
  private final Project project;

  public AntPropertyResolver(final Project project) {
    this.project = project;
  }

  @Override
  public String resolve(final String string) {
    if (string == null)
      return null;

    final int start = string.indexOf("${");
    if (start == -1)
      return string;

    final int end = string.indexOf("}", start + 2);
    if (end == -1)
      return string;

    if (project == null)
      return string;

    final String resolved = project.getProperty(string.substring(start + 2, end));
    if (resolved == null)
      return string.substring(0, end + 1) + resolve(string.substring(end + 1, string.length()));

    return string.substring(0, start) + resolved + resolve(string.substring(end + 1, string.length()));
  }
}