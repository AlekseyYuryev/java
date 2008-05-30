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
import org.safris.xml.generator.lexer.processor.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.processor.model.element.RedefineModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;
import org.safris.xml.generator.lexer.lang.UniqueQName;

public class ComplexTypeNormalizer extends Normalizer<ComplexTypeModel>
{
	protected final Map<UniqueQName,ComplexTypeModel> all = new HashMap<UniqueQName,ComplexTypeModel>();

	public ComplexTypeNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	public ComplexTypeModel parseComplexType(UniqueQName name)
	{
		return all.get(name);
	}

	protected void stage1(ComplexTypeModel model)
	{
		if(model.getName() == null || model.getParent() instanceof RedefineModel)
			return;

		ComplexTypeModel complexTypeModel = parseComplexType(model.getName());
		if(complexTypeModel == null)
			all.put(model.getName(), model);
	}

	protected void stage2(ComplexTypeModel model)
	{
	}

	protected void stage3(ComplexTypeModel model)
	{
	}

	protected void stage4(ComplexTypeModel model)
	{
	}

	protected void stage5(ComplexTypeModel model)
	{
	}

	protected void stage6(ComplexTypeModel model)
	{
		if(model.getName() == null)
			return;

		if(model.getSuperType() == null)
			model.setSuperType(ComplexTypeModel.Undefined.parseComplexType(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "anySimpleType")));
	}
}
