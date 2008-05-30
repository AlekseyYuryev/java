/*  Copyright 2008 Safris Technologies Inc.
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

package org.safris.xml.generator.lexer.processor.normalize.element;

import java.util.Collection;
import java.util.HashSet;
import org.safris.xml.generator.lexer.processor.model.element.ImportModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class ImportNormalizer extends Normalizer<ImportModel>
{
	private final Collection<String> messages = new HashSet<String>();

	public ImportNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	protected void stage1(ImportModel model)
	{
	}

	protected void stage2(ImportModel model)
	{
		final String message = "Importing " + model.getSchemaLocation() + " for {" + model.getTargetNamespace() + "}";
		if(messages.contains(message))
			return;

		messages.add(message);
		logger.info(message);
	}

	protected void stage3(ImportModel model)
	{
	}

	protected void stage4(ImportModel model)
	{
	}

	protected void stage5(ImportModel model)
	{
	}

	protected void stage6(ImportModel model)
	{
	}
}
