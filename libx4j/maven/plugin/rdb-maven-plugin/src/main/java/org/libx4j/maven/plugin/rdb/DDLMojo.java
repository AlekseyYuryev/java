/* Copyright (c) 2011 lib4j
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

package org.libx4j.maven.plugin.rdb;

import java.io.IOException;
import java.net.URL;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.lib4j.xml.XMLException;
import org.libx4j.maven.mojo.Manifest;
import org.libx4j.maven.mojo.ManifestMojo;
import org.safris.rdb.ddlx.Generator;
import org.safris.rdb.ddlx.GeneratorExecutionException;
import org.safris.rdb.vendor.DBVendor;

@Mojo(name = "ddl", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
@Execute(goal = "ddl")
public final class DDLMojo extends ManifestMojo {
  @Parameter(property = "vendor", required = true)
  private String vendor;

  @Override
  public void execute(final Manifest manifest) throws MojoExecutionException, MojoFailureException {
    try {
      for (final URL url : manifest.getSchemas())
        Generator.createDDL(url, DBVendor.valueOf(vendor), manifest.getDestdir());
    }
    catch (final GeneratorExecutionException | IOException | XMLException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }
}