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
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.processor.model.ElementableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.GroupModel;
import org.safris.xml.generator.lexer.processor.model.element.RedefineModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;
import org.safris.xml.generator.lexer.lang.UniqueQName;

public class GroupNormalizer extends Normalizer<GroupModel>
{
	private final Map<UniqueQName,GroupModel> all = new HashMap<UniqueQName,GroupModel>();

	public GroupNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	public GroupModel parseGroup(UniqueQName name)
	{
		return all.get(name);
	}

	protected void stage1(GroupModel model)
	{
		if(model.getName() == null)
			return;

		final GroupModel groupModel = parseGroup(model.getName());
		if(groupModel == null)
			all.put(model.getName(), model);
	}

	protected void stage2(GroupModel model)
	{
		if(model.getRef() == null || !(model.getRef() instanceof GroupModel.Reference))
			return;

		GroupModel ref = parseGroup(model.getRef().getName());
		if(ref == null)
			ref = parseGroup(model.getName());

		if(ref == null)
			throw new LexerError("ref == null for " + model.getName());

		model.setRef(ref);
	}

	protected void stage3(GroupModel model)
	{
		if(model.getRef() == null)
			return;

		Model parent = model;
		while((parent = parent.getParent()) != null)
		{
			if(parent.getParent() instanceof RedefineModel && parent instanceof GroupModel && model.getRef().getName().equals(((GroupModel)parent).getName()))
			{
				model.getRef().setRedefine((GroupModel)parent);
				break;
			}
		}
	}

	protected void stage4(GroupModel model)
	{
		if(model.getRef() == null)
			return;

		Model parent = model;
		while((parent = parent.getParent()) != null)
		{
			if(parent instanceof ElementableModel)
			{
				((ElementableModel)parent).addMultiplicableModel(model.getRef());
				break;
			}
		}
	}

	protected void stage5(GroupModel model)
	{
	}

	protected void stage6(GroupModel model)
	{
	}
}
