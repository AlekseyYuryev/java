package org.safris.xml.generator.compiler.phase.plan;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.compiler.phase.plan.element.AllPlan;
import org.safris.xml.generator.compiler.phase.plan.element.AnnotationPlan;
import org.safris.xml.generator.compiler.phase.plan.element.AnyAttributePlan;
import org.safris.xml.generator.compiler.phase.plan.element.AnyPlan;
import org.safris.xml.generator.compiler.phase.plan.element.AppinfoPlan;
import org.safris.xml.generator.compiler.phase.plan.element.AttributeGroupPlan;
import org.safris.xml.generator.compiler.phase.plan.element.AttributePlan;
import org.safris.xml.generator.compiler.phase.plan.element.ChoicePlan;
import org.safris.xml.generator.compiler.phase.plan.element.ComplexContentPlan;
import org.safris.xml.generator.compiler.phase.plan.element.ComplexTypePlan;
import org.safris.xml.generator.compiler.phase.plan.element.DocumentationPlan;
import org.safris.xml.generator.compiler.phase.plan.element.ElementPlan;
import org.safris.xml.generator.compiler.phase.plan.element.EnumerationPlan;
import org.safris.xml.generator.compiler.phase.plan.element.ExtensionPlan;
import org.safris.xml.generator.compiler.phase.plan.element.FieldPlan;
import org.safris.xml.generator.compiler.phase.plan.element.FractionDigitsPlan;
import org.safris.xml.generator.compiler.phase.plan.element.GroupPlan;
import org.safris.xml.generator.compiler.phase.plan.element.HasFacetPlan;
import org.safris.xml.generator.compiler.phase.plan.element.HasPropertyPlan;
import org.safris.xml.generator.compiler.phase.plan.element.ImportPlan;
import org.safris.xml.generator.compiler.phase.plan.element.IncludePlan;
import org.safris.xml.generator.compiler.phase.plan.element.KeyPlan;
import org.safris.xml.generator.compiler.phase.plan.element.KeyrefPlan;
import org.safris.xml.generator.compiler.phase.plan.element.LengthPlan;
import org.safris.xml.generator.compiler.phase.plan.element.ListPlan;
import org.safris.xml.generator.compiler.phase.plan.element.MaxInclusivePlan;
import org.safris.xml.generator.compiler.phase.plan.element.MaxLengthPlan;
import org.safris.xml.generator.compiler.phase.plan.element.MinExclusivePlan;
import org.safris.xml.generator.compiler.phase.plan.element.MinInclusivePlan;
import org.safris.xml.generator.compiler.phase.plan.element.MinLengthPlan;
import org.safris.xml.generator.compiler.phase.plan.element.NotationPlan;
import org.safris.xml.generator.compiler.phase.plan.element.PatternPlan;
import org.safris.xml.generator.compiler.phase.plan.element.RedefinePlan;
import org.safris.xml.generator.compiler.phase.plan.element.RestrictionPlan;
import org.safris.xml.generator.compiler.phase.plan.element.SchemaPlan;
import org.safris.xml.generator.compiler.phase.plan.element.SelectorPlan;
import org.safris.xml.generator.compiler.phase.plan.element.SequencePlan;
import org.safris.xml.generator.compiler.phase.plan.element.SimpleContentPlan;
import org.safris.xml.generator.compiler.phase.plan.element.SimpleTypePlan;
import org.safris.xml.generator.compiler.phase.plan.element.UnionPlan;
import org.safris.xml.generator.compiler.phase.plan.element.UniquePlan;
import org.safris.xml.generator.compiler.phase.plan.element.WhiteSpacePlan;
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
import org.safris.xml.generator.module.phase.HandlerDirectory;

public class PlanDirectory<T> extends HandlerDirectory<Model,Class<? extends Plan>>
{
	private static final Map<Class<? extends Model>,Class<? extends Plan>> classes = new HashMap<Class<? extends Model>,Class<? extends Plan>>(39);
	private static final Collection<Class<? extends Model>> keys;
	private static final PlanDirectory instance = new PlanDirectory();

	static
	{
		classes.put(AllModel.class, AllPlan.class);
		classes.put(AnnotationModel.class, AnnotationPlan.class);
		classes.put(AnyAttributeModel.class, AnyAttributePlan.class);
		classes.put(AnyModel.class, AnyPlan.class);
		classes.put(AppinfoModel.class, AppinfoPlan.class);
		classes.put(AttributeGroupModel.class, AttributeGroupPlan.class);
		classes.put(AttributeModel.class, AttributePlan.class);
		classes.put(ChoiceModel.class, ChoicePlan.class);
		classes.put(ComplexContentModel.class, ComplexContentPlan.class);
		classes.put(ComplexTypeModel.class, ComplexTypePlan.class);
		classes.put(DocumentationModel.class, DocumentationPlan.class);
		classes.put(ElementModel.class, ElementPlan.class);
		classes.put(EnumerationModel.class, EnumerationPlan.class);
		classes.put(ExtensionModel.class, ExtensionPlan.class);
		classes.put(FieldModel.class, FieldPlan.class);
		classes.put(FractionDigitsModel.class, FractionDigitsPlan.class);
		classes.put(GroupModel.class, GroupPlan.class);
		classes.put(HasFacetModel.class, HasFacetPlan.class);
		classes.put(HasPropertyModel.class, HasPropertyPlan.class);
		classes.put(ImportModel.class, ImportPlan.class);
		classes.put(IncludeModel.class, IncludePlan.class);
		classes.put(KeyModel.class, KeyPlan.class);
		classes.put(KeyrefModel.class, KeyrefPlan.class);
		classes.put(LengthModel.class, LengthPlan.class);
		classes.put(ListModel.class, ListPlan.class);
		classes.put(MaxInclusiveModel.class, MaxInclusivePlan.class);
		classes.put(MaxLengthModel.class, MaxLengthPlan.class);
		classes.put(MinExclusiveModel.class, MinExclusivePlan.class);
		classes.put(MinInclusiveModel.class, MinInclusivePlan.class);
		classes.put(MinLengthModel.class, MinLengthPlan.class);
		classes.put(NotationModel.class, NotationPlan.class);
		classes.put(PatternModel.class, PatternPlan.class);
		classes.put(RedefineModel.class, RedefinePlan.class);
		classes.put(RestrictionModel.class, RestrictionPlan.class);
		classes.put(SchemaModel.class, SchemaPlan.class);
		classes.put(SelectorModel.class, SelectorPlan.class);
		classes.put(SequenceModel.class, SequencePlan.class);
		classes.put(SimpleContentModel.class, SimpleContentPlan.class);
		classes.put(SimpleTypeModel.class, SimpleTypePlan.class);
		classes.put(UnionModel.class, UnionPlan.class);
		classes.put(UniqueModel.class, UniquePlan.class);
		classes.put(WhiteSpaceModel.class, WhiteSpacePlan.class);
		keys = classes.keySet();
	}

	public static PlanDirectory instance()
	{
		return instance;
	}

	protected Class<? extends Plan> lookup(Model key)
	{
		if(!keys.contains(key.getClass()))
			throw new IllegalArgumentException("Unknown key: " + key.getClass().getSimpleName());

		return classes.get(key.getClass());
	}
}
