package org.safris.xml.generator.lexer.phase.normalize.element;

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

public class ListNormalizer extends Normalizer<ListModel>
{
	private final SimpleTypeNormalizer simpleTypeNormalizer = (SimpleTypeNormalizer)getDirectory().lookup(SimpleTypeModel.class);

	public ListNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	protected void stage1(ListModel model)
	{
	}

	protected void stage2(ListModel model)
	{
		final Collection<SimpleTypeModel> itemTypes = model.getItemType();
		if(itemTypes == null || itemTypes.size() != 1)
			throw new LexerError("This should not happen, right?!");

		final SimpleTypeModel itemType = itemTypes.iterator().next();
		SimpleTypeModel type = itemType;
		if(type instanceof SimpleTypeModel.Reference)
		{
			type = simpleTypeNormalizer.parseSimpleType(type.getName());
			if(type == null)
			{
				if(!BindingQName.XS.getNamespaceURI().equals(itemType.getName().getNamespaceURI()))
					throw new LexerError("type == null for " + type.getName());

				type = SimpleTypeModel.Undefined.parseSimpleType(itemType.getName());
			}

			model.setItemType(type);
		}
	}

	protected void stage3(ListModel model)
	{
	}

	protected void stage4(ListModel model)
	{
		if(model.getItemType() == null)
			throw new LexerError("This cant happen.");

		Model parent = model;
		while((parent = parent.getParent()) != null)
		{
			// If there is a higher level union, then this list is one of the memberTypes
			if(parent instanceof UnionModel)
			{
				((UnionModel)parent).getMemberTypes().addAll(model.getItemType());
				break;
			}
		}
	}

	protected void stage5(ListModel model)
	{
	}

	protected void stage6(ListModel model)
	{
		if(model.getItemType() == null)
			throw new LexerError("This cant happen.");

		Model parent = model;
		while((parent = parent.getParent()) != null)
		{
			// If this list defines a named simpleType
			if(parent instanceof SimpleTypeModel && ((SimpleTypeModel)parent).getName() != null)
			{
				final SimpleTypeModel simpleTypeModel = (SimpleTypeModel)parent;
				simpleTypeModel.setSuperType(SimpleTypeModel.Undefined.parseSimpleType(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "anySimpleType")));
				simpleTypeModel.setItemTypes(model.getItemType());
				simpleTypeModel.setList(true);
				break;
			}
		}
	}
}
