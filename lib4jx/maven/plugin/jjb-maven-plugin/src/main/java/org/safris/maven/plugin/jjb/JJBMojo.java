/* Copyright (c) 2015 lib4j
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

package org.safris.maven.plugin.jjb;

import java.io.IOException;
import java.net.URL;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.safris.commons.xml.XMLException;
import org.safris.jjb.generator.Generator;
import org.safris.jjb.generator.GeneratorExecutionException;
import org.safris.maven.mojo.Manifest;
import org.safris.maven.mojo.ManifestMojo;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
@Execute(goal = "generate")
public class JJBMojo extends ManifestMojo {
  @Override
  public void execute(final Manifest manifest) throws MojoExecutionException, MojoFailureException {
    try {
      for (final URL url : manifest.getSchemas())
        Generator.generate(url, manifest.getDestdir(), manifest.getCompile());
    }
    catch (final GeneratorExecutionException | IOException | XMLException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }
}