package org.safris.xml.generator.lexer.phase.normalize.element;

import java.util.ArrayList;
import java.util.Collection;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.element.ListModel;
import org.safris.xml.generator.lexer.phase.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.phase.model.element.UnionModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;
import org.safris.xml.generator.lexer.phase.normalize.NormalizerDirectory;
import org.safris.xml.generator.processor.phase.BindingQName;
import org.safris.xml.generator.processor.phase.ProcessorDirectory;

public class UnionNormalizer extends Normalizer<UnionModel>
{
	private final SimpleTypeNormalizer simpleTypeNormalizer = (SimpleTypeNormalizer)getDirectory().lookup(SimpleTypeModel.class);

	public UnionNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	protected void stage1(UnionModel model)
	{
	}

	protected void stage2(UnionModel model)
	{
		final Collection<SimpleTypeModel> memberTypes = model.getMemberTypes();
		final Collection<SimpleTypeModel> resolvedMemberTypes = new ArrayList<SimpleTypeModel>(memberTypes.size());
		SimpleTypeModel resolvedMemberType;
		if(memberTypes != null)
		{
			for(SimpleTypeModel memberType : memberTypes)
			{
				if(memberType instanceof SimpleTypeModel.Reference)
				{
					resolvedMemberType = simpleTypeNormalizer.parseSimpleType(memberType.getName());
					if(resolvedMemberType == null)
					{
						if(!BindingQName.XS.getNamespaceURI().equals(memberType.getName().getNamespaceURI()))
							throw new LexerError("type == null for " + memberType.getName());

						resolvedMemberType = SimpleTypeModel.Undefined.parseSimpleType(memberType.getName());
					}
				}
				else
					resolvedMemberType = memberType;

				resolvedMemberTypes.add(resolvedMemberType);
			}

			model.getMemberTypes().clear();
			model.getMemberTypes().addAll(resolvedMemberTypes);
		}
	}

	protected void stage3(UnionModel model)
	{
	}

	protected void stage4(UnionModel model)
	{
	}

	protected void stage5(UnionModel model)
	{
		if(model.getMemberTypes() == null || model.getMemberTypes().size() == 0)
			throw new LexerError("I dont think this can happen.");

		Model parent = model;
		while((parent = parent.getParent()) != null)
		{
			// Either there is a higher level union that we want to combine into this union
			if(parent instanceof UnionModel)
			{
				((UnionModel)parent).addUnion(model);
				break;
			}
			// Or there is a list that of a union
			else if(parent instanceof ListModel)
			{
				if(((ListModel)parent).getItemType() != null)
					throw new LexerError("Dont know how this happened, but the <list> has a memberType already and it has a union under it also.");

				((ListModel)parent).setItemType(model);
				break;
			}
		}
	}

	protected void stage6(UnionModel model)
	{
		if(model.getMemberTypes() == null || model.getMemberTypes().size() == 0)
			throw new LexerError("I dont think this can happen.");

		Model parent = model;
		while((parent = parent.getParent()) != null)
		{
			// If this union defines a named simpleType
			if(parent instanceof SimpleTypeModel && ((SimpleTypeModel)parent).getName() != null)
			{
				final SimpleTypeModel simpleTypeModel = (SimpleTypeModel)parent;
				simpleTypeModel.setSuperType(SimpleTypeModel.Undefined.parseSimpleType(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "anySimpleType")));
				simpleTypeModel.setItemTypes(model.getNormalizedMemberTypes());
				break;
			}
		}
	}
}
