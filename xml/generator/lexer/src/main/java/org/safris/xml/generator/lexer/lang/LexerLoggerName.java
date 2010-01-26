/*  Copyright 2010 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.xml.generator.lexer.lang;

import org.safris.commons.logging.LoggerName;

public class LexerLoggerName extends LoggerName
{
	public static final LexerLoggerName COMPOSITE = new LexerLoggerName("COMPOSITE");
	public static final LexerLoggerName DECISION = new LexerLoggerName("DECISION");
	public static final LexerLoggerName DOCUMENT = new LexerLoggerName("DOCUMENT");
	public static final LexerLoggerName MODEL = new LexerLoggerName("MODEL");
	public static final LexerLoggerName NORMALIZE = new LexerLoggerName("NORMALIZE");
	public static final LexerLoggerName REFERENCE = new LexerLoggerName("REFERENCE");

	public LexerLoggerName(String name)
	{
		super(name);
	}
}
