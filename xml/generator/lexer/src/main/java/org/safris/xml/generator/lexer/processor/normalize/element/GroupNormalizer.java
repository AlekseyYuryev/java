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
import org.safris.xml.generator.processor.BindingQName;

public class GroupNormalizer extends Normalizer<GroupModel>
{
	private final Map<BindingQName,GroupModel> all = new HashMap<BindingQName,GroupModel>();

	public GroupNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	public GroupModel parseGroup(BindingQName name)
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
