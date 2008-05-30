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

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.processor.model.element.NotationModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;
import org.safris.xml.generator.lexer.lang.UniqueQName;

public class NotationNormalizer extends Normalizer<NotationModel>
{
	private final Map<UniqueQName,NotationModel> all = new HashMap<UniqueQName,NotationModel>();

	public NotationNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	public NotationModel parseNotation(UniqueQName name)
	{
		return all.get(name);
	}

	protected void stage1(NotationModel model)
	{
		if(model.getName() == null)
			return;

		final NotationModel notationModel = parseNotation(model.getName());
		if(notationModel == null)
			all.put(model.getName(), model);
	}

	protected void stage2(NotationModel model)
	{
	}

	protected void stage3(NotationModel model)
	{
	}

	protected void stage4(NotationModel model)
	{
	}

	protected void stage5(NotationModel model)
	{
	}

	protected void stage6(NotationModel model)
	{
	}
}
