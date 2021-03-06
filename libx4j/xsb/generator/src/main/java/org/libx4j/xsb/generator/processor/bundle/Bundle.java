/* Copyright (c) 2006 lib4j
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

package org.libx4j.xsb.generator.processor.bundle;

import java.io.File;

import org.lib4j.pipeline.PipelineEntity;

public final class Bundle implements PipelineEntity {
  private final File file;

  public Bundle(final File file) {
    this.file = file;
  }

  public File getFile() {
    return file;
  }
}