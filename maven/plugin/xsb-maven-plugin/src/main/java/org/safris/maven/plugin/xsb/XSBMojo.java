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

package org.safris.maven.plugin.xsb;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.safris.commons.xml.NamespaceURI;
import org.safris.maven.mojo.Manifest;
import org.safris.maven.mojo.ManifestMojo;
import org.safris.xsb.compiler.processor.GeneratorContext;
import org.safris.xsb.compiler.processor.reference.SchemaReference;
import org.safris.xsb.generator.Generator;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
@Execute(goal = "generate")
public class XSBMojo extends ManifestMojo {
  // Contains all source paths for all executions of the generator in the single VM, such
  // that subsequent executions have a reference to the source paths of previous executions
  // so as to allow for bindings of excluded namespaces to be generated in prior executions
  private static final Set<File> sourcePath = new HashSet<File>();

  @Override
  public void execute(final Manifest manifest) throws MojoExecutionException, MojoFailureException {
    final Collection<SchemaReference> generatorBindings = new ArrayList<SchemaReference>();
    for (final URL schema : manifest.getSchemas())
      generatorBindings.add(new SchemaReference(schema, false));

    final Set<NamespaceURI> excludes;
    if (manifest.getExcludes() != null) {
      excludes = new HashSet<NamespaceURI>();
      for (final String exclude : manifest.getExcludes())
        excludes.add(NamespaceURI.getInstance(exclude));
    }
    else {
      excludes = null;
    }

    final Generator generator = new Generator(new GeneratorContext(manifest.getDestdir(), manifest.getOverwrite(), manifest.getCompile(), manifest.getPackage()), generatorBindings, excludes, sourcePath);
    generator.generate();
    sourcePath.add(manifest.getDestdir());
  }
}