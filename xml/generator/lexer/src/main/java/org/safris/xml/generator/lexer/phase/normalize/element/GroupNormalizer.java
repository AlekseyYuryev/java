package org.safris.xml.generator.lexer.phase.normalize.element;

import java.util.HashMap;
import java.util.Map;
import org.safris.commons.util.xml.BindingQName;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.phase.model.ElementableModel;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.element.GroupModel;
import org.safris.xml.generator.lexer.phase.model.element.RedefineModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;
import org.safris.xml.generator.module.phase.Nameable;

public class GroupNormalizer extends Normalizer<GroupModel>
{
	private static final Map<BindingQName,GroupModel> all = new HashMap<BindingQName,GroupModel>();

	public static GroupModel parseGroup(BindingQName name)
	{
		return all.get(name);
	}

	protected void stage1(GroupModel model)
	{
		if(model.getName() == null)
			return;

		final GroupModel groupModel = GroupNormalizer.parseGroup(model.getName());
		if(groupModel == null)
			GroupNormalizer.all.put(model.getName(), model);
	}

	protected void stage2(GroupModel model)
	{
		if(model.getRef() == null || !(model.getRef() instanceof GroupModel.Reference))
			return;

		GroupModel ref = GroupNormalizer.parseGroup(model.getRef().getName());
		if(ref == null)
			ref = GroupNormalizer.parseGroup(model.getName());

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
