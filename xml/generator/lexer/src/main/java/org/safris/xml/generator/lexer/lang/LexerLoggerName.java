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

package org.safris.xml.generator.lexer.lang;

import org.safris.commons.logging.LoggerName;

public final class LexerLoggerName extends LoggerName {
  public static final LexerLoggerName COMPOSITE = new LexerLoggerName("COMPOSITE");
  public static final LexerLoggerName DECISION = new LexerLoggerName("DECISION");
  public static final LexerLoggerName DOCUMENT = new LexerLoggerName("DOCUMENT");
  public static final LexerLoggerName MODEL = new LexerLoggerName("MODEL");
  public static final LexerLoggerName NORMALIZE = new LexerLoggerName("NORMALIZE");
  public static final LexerLoggerName REFERENCE = new LexerLoggerName("REFERENCE");

  public LexerLoggerName(final String name) {
    super(name);
  }
}