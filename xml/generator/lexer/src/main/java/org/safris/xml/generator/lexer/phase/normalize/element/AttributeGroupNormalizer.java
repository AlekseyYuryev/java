package org.safris.xml.generator.lexer.phase.normalize.element;

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.phase.model.AttributableModel;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.element.AttributeGroupModel;
import org.safris.xml.generator.lexer.phase.model.element.RedefineModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;
import org.safris.xml.generator.lexer.phase.normalize.NormalizerDirectory;
import org.safris.xml.generator.processor.phase.BindingQName;
import org.safris.xml.generator.processor.phase.Nameable;

public class AttributeGroupNormalizer extends Normalizer<AttributeGroupModel>
{
	private final Map<BindingQName,AttributeGroupModel> all = new HashMap<BindingQName,AttributeGroupModel>();

	public AttributeGroupNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	public AttributeGroupModel parseAttributeGroup(BindingQName name)
	{
		return all.get(name);
	}

	protected void stage1(AttributeGroupModel model)
	{
		if(model.getName() == null || model.getParent() instanceof RedefineModel)
			return;

		if(parseAttributeGroup(model.getName()) == null)
			all.put(model.getName(), model);
	}

	protected void stage2(AttributeGroupModel model)
	{
		if(!(model.getRef() instanceof AttributeGroupModel.Reference))
			return;

		final AttributeGroupModel ref = parseAttributeGroup(model.getRef().getName());
		if(ref == null)
			throw new LexerError("ref == null for " + model.getRef().getName());

		model.setRef(ref);

		// If the parent of this attributeGroup is also an attributeGroup, then propogate the attributes
		if(model.getParent() instanceof AttributeGroupModel)
			((AttributeGroupModel)model.getParent()).getAttributes().addAll(model.getRef().getAttributes());
	}

	protected void stage3(AttributeGroupModel model)
	{
		if(model.getRef() == null)
			return;

		Model parent = model;
		while((parent = parent.getParent()) != null)
		{
			if(parent.getParent() instanceof RedefineModel && parent instanceof AttributeGroupModel && model.getRef().getName().equals(((AttributeGroupModel)parent).getName()))
			{
				model.getRef().setRedefine((AttributeGroupModel)parent);
				break;
			}
		}
	}

	protected void stage4(AttributeGroupModel model)
	{
		if(model.getRef() == null)
			return;

		Model parent = model;
		while((parent = parent.getParent()) != null)
		{
			if(parent instanceof AttributableModel && parent instanceof Nameable && ((Nameable)parent).getName() != null)
			{
				if(model.getRef().getRedefine() != null && model.getRef().getRedefine() != parent)
					((AttributableModel)parent).getAttributes().addAll(model.getRef().getRedefine().getAttributes());
				else
					((AttributableModel)parent).getAttributes().addAll(model.getRef().getAttributes());

				break;
			}
		}
	}

	protected void stage5(AttributeGroupModel model)
	{
	}

	protected void stage6(AttributeGroupModel model)
	{
	}
}
