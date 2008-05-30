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

import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.AttributableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.AnyAttributeModel;
import org.safris.xml.generator.lexer.processor.model.element.AttributeGroupModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class AnyAttributeNormalizer extends Normalizer<AnyAttributeModel>
{
	public AnyAttributeNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	protected void stage1(AnyAttributeModel model)
	{
	}

	protected void stage2(AnyAttributeModel model)
	{
		// add the handler attribute to the attributeGroup
		if(model.getParent() instanceof AttributeGroupModel)
			((AttributeGroupModel)model.getParent()).addAttribute(model);
	}

	protected void stage3(AnyAttributeModel model)
	{
	}

	protected void stage4(AnyAttributeModel model)
	{
		// Add the handler to the Attributable class with a name
		Model parent = model;
		while((parent = parent.getParent()) != null)
		{
			if(parent instanceof AttributableModel && parent instanceof Nameable && ((Nameable)parent).getName() != null)
			{
				((AttributableModel)parent).addAttribute(model);
				break;
			}
		}
	}

	protected void stage5(AnyAttributeModel model)
	{
	}

	protected void stage6(AnyAttributeModel model)
	{
	}
}
