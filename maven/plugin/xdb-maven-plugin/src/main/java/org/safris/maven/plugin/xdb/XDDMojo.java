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

import javax.xml.transform.TransformerException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.safris.commons.lang.Resources;
import org.safris.commons.xml.transform.Transformer;
import org.safris.maven.mojo.ManifestMojo;

@Mojo(name = "data", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
@Execute(goal = "data")
public final class XDDMojo extends ManifestMojo {
  @Override
  public void execute(final File file, final File outDir) throws MojoExecutionException, MojoFailureException {
    try {
      Transformer.transform(Resources.getResource("data.xsl").getURL(), file.toURI().toURL(), new File(outDir, file.getName().replaceAll("\\.\\S+$", ".xsd")));
    }
    catch (final IOException | TransformerException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }
}