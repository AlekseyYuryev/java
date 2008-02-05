package org.safris.xml.generator.lexer.processor.normalize;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.AllModel;
import org.safris.xml.generator.lexer.processor.model.element.AnnotationModel;
import org.safris.xml.generator.lexer.processor.model.element.AnyAttributeModel;
import org.safris.xml.generator.lexer.processor.model.element.AnyModel;
import org.safris.xml.generator.lexer.processor.model.element.AppinfoModel;
import org.safris.xml.generator.lexer.processor.model.element.AttributeGroupModel;
import org.safris.xml.generator.lexer.processor.model.element.AttributeModel;
import org.safris.xml.generator.lexer.processor.model.element.ChoiceModel;
import org.safris.xml.generator.lexer.processor.model.element.ComplexContentModel;
import org.safris.xml.generator.lexer.processor.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.processor.model.element.DocumentationModel;
import org.safris.xml.generator.lexer.processor.model.element.ElementModel;
import org.safris.xml.generator.lexer.processor.model.element.EnumerationModel;
import org.safris.xml.generator.lexer.processor.model.element.ExtensionModel;
import org.safris.xml.generator.lexer.processor.model.element.FieldModel;
import org.safris.xml.generator.lexer.processor.model.element.FractionDigitsModel;
import org.safris.xml.generator.lexer.processor.model.element.GroupModel;
import org.safris.xml.generator.lexer.processor.model.element.HasFacetModel;
import org.safris.xml.generator.lexer.processor.model.element.HasPropertyModel;
import org.safris.xml.generator.lexer.processor.model.element.ImportModel;
import org.safris.xml.generator.lexer.processor.model.element.IncludeModel;
import org.safris.xml.generator.lexer.processor.model.element.KeyModel;
import org.safris.xml.generator.lexer.processor.model.element.KeyrefModel;
import org.safris.xml.generator.lexer.processor.model.element.LengthModel;
import org.safris.xml.generator.lexer.processor.model.element.ListModel;
import org.safris.xml.generator.lexer.processor.model.element.MaxInclusiveModel;
import org.safris.xml.generator.lexer.processor.model.element.MaxLengthModel;
import org.safris.xml.generator.lexer.processor.model.element.MinExclusiveModel;
import org.safris.xml.generator.lexer.processor.model.element.MinInclusiveModel;
import org.safris.xml.generator.lexer.processor.model.element.MinLengthModel;
import org.safris.xml.generator.lexer.processor.model.element.NotationModel;
import org.safris.xml.generator.lexer.processor.model.element.PatternModel;
import org.safris.xml.generator.lexer.processor.model.element.RedefineModel;
import org.safris.xml.generator.lexer.processor.model.element.RestrictionModel;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.safris.xml.generator.lexer.processor.model.element.SelectorModel;
import org.safris.xml.generator.lexer.processor.model.element.SequenceModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleContentModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.processor.model.element.UnionModel;
import org.safris.xml.generator.lexer.processor.model.element.UniqueModel;
import org.safris.xml.generator.lexer.processor.model.element.WhiteSpaceModel;
import org.safris.xml.generator.lexer.processor.normalize.element.AllNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.AnnotationNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.AnyAttributeNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.AnyNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.AppinfoNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.AttributeGroupNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.AttributeNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.ChoiceNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.ComplexContentNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.ComplexTypeNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.DocumentationNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.ElementNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.EnumerationNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.ExtensionNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.FieldNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.FractionDigitsNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.GroupNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.HasFacetNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.HasPropertyNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.ImportNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.IncludeNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.KeyNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.KeyrefNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.LengthNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.ListNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.MaxInclusiveNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.MaxLengthNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.MinExclusiveNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.MinInclusiveNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.MinLengthNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.NotationNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.PatternNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.RedefineNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.RestrictionNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.SchemaNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.SelectorNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.SequenceNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.SimpleContentNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.SimpleTypeNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.UnionNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.UniqueNormalizer;
import org.safris.xml.generator.lexer.processor.normalize.element.WhiteSpaceNormalizer;
import org.safris.xml.generator.processor.ElementModule;
import org.safris.xml.generator.processor.ModuleProcessor;
import org.safris.xml.generator.processor.ProcessorDirectory;

public class NormalizerDirectory implements ProcessorDirectory<Model,Normalizer>
{
	private final Map<Class<? extends Model>,Class<? extends Normalizer>> classes = new HashMap<Class<? extends Model>,Class<? extends Normalizer>>(39);
	private final Map<Class<? extends Model>,Normalizer> instances = new HashMap<Class<? extends Model>,Normalizer>(39);
	private final Collection<Class<? extends Model>> keys;
	private final NormalizerProcessor processor = new NormalizerProcessor();

	public NormalizerDirectory()
	{
		classes.put(AllModel.class, AllNormalizer.class);
		classes.put(AnnotationModel.class, AnnotationNormalizer.class);
		classes.put(AnyAttributeModel.class, AnyAttributeNormalizer.class);
		classes.put(AnyModel.class, AnyNormalizer.class);
		classes.put(AppinfoModel.class, AppinfoNormalizer.class);
		classes.put(AttributeGroupModel.class, AttributeGroupNormalizer.class);
		classes.put(AttributeModel.class, AttributeNormalizer.class);
		classes.put(ChoiceModel.class, ChoiceNormalizer.class);
		classes.put(ComplexContentModel.class, ComplexContentNormalizer.class);
		classes.put(ComplexTypeModel.class, ComplexTypeNormalizer.class);
		classes.put(DocumentationModel.class, DocumentationNormalizer.class);
		classes.put(ElementModel.class, ElementNormalizer.class);
		classes.put(EnumerationModel.class, EnumerationNormalizer.class);
		classes.put(ExtensionModel.class, ExtensionNormalizer.class);
		classes.put(FieldModel.class, FieldNormalizer.class);
		classes.put(FractionDigitsModel.class, FractionDigitsNormalizer.class);
		classes.put(GroupModel.class, GroupNormalizer.class);
		classes.put(HasFacetModel.class, HasFacetNormalizer.class);
		classes.put(HasPropertyModel.class, HasPropertyNormalizer.class);
		classes.put(ImportModel.class, ImportNormalizer.class);
		classes.put(IncludeModel.class, IncludeNormalizer.class);
		classes.put(KeyModel.class, KeyNormalizer.class);
		classes.put(KeyrefModel.class, KeyrefNormalizer.class);
		classes.put(LengthModel.class, LengthNormalizer.class);
		classes.put(ListModel.class, ListNormalizer.class);
		classes.put(MaxInclusiveModel.class, MaxInclusiveNormalizer.class);
		classes.put(MaxLengthModel.class, MaxLengthNormalizer.class);
		classes.put(MinExclusiveModel.class, MinExclusiveNormalizer.class);
		classes.put(MinInclusiveModel.class, MinInclusiveNormalizer.class);
		classes.put(MinLengthModel.class, MinLengthNormalizer.class);
		classes.put(NotationModel.class, NotationNormalizer.class);
		classes.put(PatternModel.class, PatternNormalizer.class);
		classes.put(RedefineModel.class, RedefineNormalizer.class);
		classes.put(RestrictionModel.class, RestrictionNormalizer.class);
		classes.put(SchemaModel.class, SchemaNormalizer.class);
		classes.put(SelectorModel.class, SelectorNormalizer.class);
		classes.put(SequenceModel.class, SequenceNormalizer.class);
		classes.put(SimpleContentModel.class, SimpleContentNormalizer.class);
		classes.put(SimpleTypeModel.class, SimpleTypeNormalizer.class);
		classes.put(UnionModel.class, UnionNormalizer.class);
		classes.put(UniqueModel.class, UniqueNormalizer.class);
		classes.put(WhiteSpaceModel.class, WhiteSpaceNormalizer.class);
		keys = classes.keySet();
	}

	public ElementModule<Normalizer> getModule(Model module, Normalizer parent)
	{
		return lookup(module.getClass());
	}

	public ElementModule<Normalizer> lookup(Class<? extends Model> clazz)
	{
		if(!keys.contains(clazz))
			throw new IllegalArgumentException("Unknown key: " + clazz.getSimpleName());

		Normalizer normalizerInstance = instances.get(clazz);
		if(normalizerInstance != null)
			return normalizerInstance;

		final Class<? extends Normalizer> normalizerClass = classes.get(clazz);
		try
		{
			final Constructor<? extends Normalizer> constructor = normalizerClass.getConstructor(NormalizerDirectory.class);
			instances.put(clazz, normalizerInstance = constructor.newInstance(this));
			return normalizerInstance;
		}
		catch(Exception e)
		{
			throw new LexerError(e);
		}
	}

	public ModuleProcessor<Model, Normalizer> getProcessor()
	{
		return processor;
	}

	public void clear()
	{
		instances.clear();
	}
}