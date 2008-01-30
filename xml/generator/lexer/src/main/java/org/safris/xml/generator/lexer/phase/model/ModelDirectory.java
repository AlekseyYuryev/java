package org.safris.xml.generator.lexer.phase.model;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.phase.composite.SchemaComposite;
import org.safris.xml.generator.lexer.phase.composite.SchemaNodeComposite;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.element.AllModel;
import org.safris.xml.generator.lexer.phase.model.element.AnnotationModel;
import org.safris.xml.generator.lexer.phase.model.element.AnyAttributeModel;
import org.safris.xml.generator.lexer.phase.model.element.AnyModel;
import org.safris.xml.generator.lexer.phase.model.element.AppinfoModel;
import org.safris.xml.generator.lexer.phase.model.element.AttributeGroupModel;
import org.safris.xml.generator.lexer.phase.model.element.AttributeModel;
import org.safris.xml.generator.lexer.phase.model.element.ChoiceModel;
import org.safris.xml.generator.lexer.phase.model.element.ComplexContentModel;
import org.safris.xml.generator.lexer.phase.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.phase.model.element.DocumentationModel;
import org.safris.xml.generator.lexer.phase.model.element.ElementModel;
import org.safris.xml.generator.lexer.phase.model.element.EnumerationModel;
import org.safris.xml.generator.lexer.phase.model.element.ExtensionModel;
import org.safris.xml.generator.lexer.phase.model.element.FieldModel;
import org.safris.xml.generator.lexer.phase.model.element.FractionDigitsModel;
import org.safris.xml.generator.lexer.phase.model.element.GroupModel;
import org.safris.xml.generator.lexer.phase.model.element.HasFacetModel;
import org.safris.xml.generator.lexer.phase.model.element.HasPropertyModel;
import org.safris.xml.generator.lexer.phase.model.element.ImportModel;
import org.safris.xml.generator.lexer.phase.model.element.IncludeModel;
import org.safris.xml.generator.lexer.phase.model.element.KeyModel;
import org.safris.xml.generator.lexer.phase.model.element.KeyrefModel;
import org.safris.xml.generator.lexer.phase.model.element.LengthModel;
import org.safris.xml.generator.lexer.phase.model.element.ListModel;
import org.safris.xml.generator.lexer.phase.model.element.MaxInclusiveModel;
import org.safris.xml.generator.lexer.phase.model.element.MaxLengthModel;
import org.safris.xml.generator.lexer.phase.model.element.MinExclusiveModel;
import org.safris.xml.generator.lexer.phase.model.element.MinInclusiveModel;
import org.safris.xml.generator.lexer.phase.model.element.MinLengthModel;
import org.safris.xml.generator.lexer.phase.model.element.NotationModel;
import org.safris.xml.generator.lexer.phase.model.element.PatternModel;
import org.safris.xml.generator.lexer.phase.model.element.RedefineModel;
import org.safris.xml.generator.lexer.phase.model.element.RestrictionModel;
import org.safris.xml.generator.lexer.phase.model.element.SchemaModel;
import org.safris.xml.generator.lexer.phase.model.element.SelectorModel;
import org.safris.xml.generator.lexer.phase.model.element.SequenceModel;
import org.safris.xml.generator.lexer.phase.model.element.SimpleContentModel;
import org.safris.xml.generator.lexer.phase.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.phase.model.element.UnionModel;
import org.safris.xml.generator.lexer.phase.model.element.UniqueModel;
import org.safris.xml.generator.lexer.phase.model.element.WhiteSpaceModel;
import org.safris.xml.generator.module.phase.ElementModule;
import org.safris.xml.generator.module.phase.ProcessorDirectory;
import org.safris.xml.generator.module.phase.ModuleProcessor;
import org.w3c.dom.Node;

public class ModelDirectory implements ProcessorDirectory<SchemaComposite,Model>
{
	private final Map<String,Class<? extends Model>> classes = new HashMap<String,Class<? extends Model>>(39);
	private final Collection<String> keys;

	public ModelDirectory()
	{
		classes.put("all", AllModel.class);
		classes.put("annotation", AnnotationModel.class);
		classes.put("anyAttribute", AnyAttributeModel.class);
		classes.put("any", AnyModel.class);
		classes.put("appinfo", AppinfoModel.class);
		classes.put("attributeGroup", AttributeGroupModel.class);
		classes.put("attribute", AttributeModel.class);
		classes.put("choice", ChoiceModel.class);
		classes.put("complexContent", ComplexContentModel.class);
		classes.put("complexType", ComplexTypeModel.class);
		classes.put("documentation", DocumentationModel.class);
		classes.put("element", ElementModel.class);
		classes.put("enumeration", EnumerationModel.class);
		classes.put("extension", ExtensionModel.class);
		classes.put("field", FieldModel.class);
		classes.put("fractiondigits", FractionDigitsModel.class);
		classes.put("group", GroupModel.class);
		classes.put("hagetsFacet", HasFacetModel.class);
		classes.put("hasProperty", HasPropertyModel.class);
		classes.put("import", ImportModel.class);
		classes.put("include", IncludeModel.class);
		classes.put("key", KeyModel.class);
		classes.put("keyref", KeyrefModel.class);
		classes.put("length", LengthModel.class);
		classes.put("list", ListModel.class);
		classes.put("maxInclusive", MaxInclusiveModel.class);
		classes.put("maxLength", MaxLengthModel.class);
		classes.put("minExclusive", MinExclusiveModel.class);
		classes.put("minInclusive", MinInclusiveModel.class);
		classes.put("minLength", MinLengthModel.class);
		classes.put("notation", NotationModel.class);
		classes.put("pattern", PatternModel.class);
		classes.put("redefine", RedefineModel.class);
		classes.put("restriction", RestrictionModel.class);
		classes.put("schema", SchemaModel.class);
		classes.put("selector", SelectorModel.class);
		classes.put("sequence", SequenceModel.class);
		classes.put("simpleContent", SimpleContentModel.class);
		classes.put("simpleType", SimpleTypeModel.class);
		classes.put("union", UnionModel.class);
		classes.put("unique", UniqueModel.class);
		classes.put("whiteSpace", WhiteSpaceModel.class);
		keys = classes.keySet();
	}

	public ElementModule<Model> lookup(SchemaComposite key, Model parent)
	{
		if(!(key instanceof SchemaNodeComposite))
			return null;

		SchemaNodeComposite schemaNodeComposite = (SchemaNodeComposite)key;
		final String elementName = schemaNodeComposite.getNode().getLocalName();
		if(elementName == null)
			throw new IllegalArgumentException("Node key without local name");

		if(!keys.contains(elementName))
			throw new IllegalArgumentException("Unknown key: " + elementName);

		final Class<? extends Model> modelClass = classes.get(elementName);
		Model handler = null;
		try
		{
			final Constructor<? extends Model> constructor = modelClass.getDeclaredConstructor(Node.class, Model.class);
			constructor.setAccessible(true);
			handler = constructor.newInstance(schemaNodeComposite.getNode(), parent);
			return handler;
		}
		catch(Exception e)
		{
			throw new LexerError(e);
		}
	}

	public ModuleProcessor<SchemaComposite, Model> getProcessor()
	{
		return new Model(null, null){};
	}

	public void clear()
	{
	}
}
