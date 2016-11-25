/* Copyright (c) 2011 Seva Safris
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

package org.safris.maven.plugin.xdb;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.safris.cf.xdb.xde.generator.EntityGenerator;
import org.safris.commons.xml.XMLException;
import org.safris.maven.mojo.ManifestMojo;

@Mojo(name = "xde", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
@Execute(goal = "xde")
public final class XDEMojo extends ManifestMojo {
  @Override
  public void execute(final File file, final File outDir) throws MojoExecutionException, MojoFailureException {
    try {
      EntityGenerator.generate(file.toURI().toURL(), outDir);
    }
    catch (final IOException | XMLException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }
}