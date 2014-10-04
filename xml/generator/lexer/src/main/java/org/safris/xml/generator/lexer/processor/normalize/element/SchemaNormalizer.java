/* Copyright (c) 2008 Seva Safris
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

package org.safris.xml.generator.lexer.processor.normalize.element;

import java.io.File;

import org.safris.commons.io.Files;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public final class SchemaNormalizer extends Normalizer<SchemaModel> {
  private static File CWD = null;

  public SchemaNormalizer(final NormalizerDirectory directory) {
    super(directory);
  }

  protected void stage1(final SchemaModel model) {
    if (CWD == null)
      CWD = Files.getCwd();

    if (model.getURL() == null)
      return;

    final String display = Files.relativePath(CWD.getAbsoluteFile(), new File(model.getURL().getFile()).getAbsoluteFile());
    logger.info("Lexing {" + model.getTargetNamespace() + "} from " + display);
  }

  protected void stage2(final SchemaModel model) {
  }

  protected void stage3(final SchemaModel model) {
  }

  protected void stage4(final SchemaModel model) {
  }

  protected void stage5(final SchemaModel model) {
  }

  protected void stage6(final SchemaModel model) {
  }
}