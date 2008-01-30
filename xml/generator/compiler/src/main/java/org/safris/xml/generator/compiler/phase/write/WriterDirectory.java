package org.safris.xml.generator.compiler.phase.write;

import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.phase.plan.Plan;
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
import org.safris.xml.generator.compiler.phase.write.element.AllWriter;
import org.safris.xml.generator.compiler.phase.write.element.AnnotationWriter;
import org.safris.xml.generator.compiler.phase.write.element.AnyAttributeWriter;
import org.safris.xml.generator.compiler.phase.write.element.AnyWriter;
import org.safris.xml.generator.compiler.phase.write.element.AppinfoWriter;
import org.safris.xml.generator.compiler.phase.write.element.AttributeGroupWriter;
import org.safris.xml.generator.compiler.phase.write.element.AttributeWriter;
import org.safris.xml.generator.compiler.phase.write.element.ChoiceWriter;
import org.safris.xml.generator.compiler.phase.write.element.ComplexContentWriter;
import org.safris.xml.generator.compiler.phase.write.element.ComplexTypeWriter;
import org.safris.xml.generator.compiler.phase.write.element.DocumentationWriter;
import org.safris.xml.generator.compiler.phase.write.element.ElementWriter;
import org.safris.xml.generator.compiler.phase.write.element.EnumerationWriter;
import org.safris.xml.generator.compiler.phase.write.element.ExtensionWriter;
import org.safris.xml.generator.compiler.phase.write.element.FieldWriter;
import org.safris.xml.generator.compiler.phase.write.element.FractionDigitsWriter;
import org.safris.xml.generator.compiler.phase.write.element.GroupWriter;
import org.safris.xml.generator.compiler.phase.write.element.HasFacetWriter;
import org.safris.xml.generator.compiler.phase.write.element.HasPropertyWriter;
import org.safris.xml.generator.compiler.phase.write.element.ImportWriter;
import org.safris.xml.generator.compiler.phase.write.element.IncludeWriter;
import org.safris.xml.generator.compiler.phase.write.element.KeyWriter;
import org.safris.xml.generator.compiler.phase.write.element.KeyrefWriter;
import org.safris.xml.generator.compiler.phase.write.element.LengthWriter;
import org.safris.xml.generator.compiler.phase.write.element.ListWriter;
import org.safris.xml.generator.compiler.phase.write.element.MaxInclusiveWriter;
import org.safris.xml.generator.compiler.phase.write.element.MaxLengthWriter;
import org.safris.xml.generator.compiler.phase.write.element.MinExclusiveWriter;
import org.safris.xml.generator.compiler.phase.write.element.MinInclusiveWriter;
import org.safris.xml.generator.compiler.phase.write.element.MinLengthWriter;
import org.safris.xml.generator.compiler.phase.write.element.NotationWriter;
import org.safris.xml.generator.compiler.phase.write.element.PatternWriter;
import org.safris.xml.generator.compiler.phase.write.element.RedefineWriter;
import org.safris.xml.generator.compiler.phase.write.element.RestrictionWriter;
import org.safris.xml.generator.compiler.phase.write.element.SchemaWriter;
import org.safris.xml.generator.compiler.phase.write.element.SelectorWriter;
import org.safris.xml.generator.compiler.phase.write.element.SequenceWriter;
import org.safris.xml.generator.compiler.phase.write.element.SimpleContentWriter;
import org.safris.xml.generator.compiler.phase.write.element.SimpleTypeWriter;
import org.safris.xml.generator.compiler.phase.write.element.UnionWriter;
import org.safris.xml.generator.compiler.phase.write.element.UniqueWriter;
import org.safris.xml.generator.compiler.phase.write.element.WhiteSpaceWriter;
import org.safris.xml.generator.processor.phase.ElementModule;
import org.safris.xml.generator.processor.phase.ProcessorDirectory;
import org.safris.xml.generator.processor.phase.ModuleProcessor;

public class WriterDirectory implements ProcessorDirectory<Plan,Writer>
{
	private final Map<Class<? extends Plan>,Class<? extends Writer>> classes = new HashMap<Class<? extends Plan>,Class<? extends Writer>>(39);
	private final Map<Class<? extends Plan>,Writer> instances = new HashMap<Class<? extends Plan>,Writer>(39);
	private final Collection<Class<? extends Plan>> keys;
	private final Writer writer = new Writer()
	{
		protected void appendDeclaration(StringWriter writer, Plan plan, Plan parent)
		{
		}

		protected void appendGetMethod(StringWriter writer, Plan plan, Plan parent)
		{
		}

		protected void appendSetMethod(StringWriter writer, Plan plan, Plan parent)
		{
		}

		protected void appendMarshal(StringWriter writer, Plan plan, Plan parent)
		{
		}

		protected void appendParse(StringWriter writer, Plan plan, Plan parent)
		{
		}

		protected void appendCopy(StringWriter writer, Plan plan, Plan parent, String variable)
		{
		}

		protected void appendEquals(StringWriter writer, Plan plan, Plan parent)
		{
		}

		protected void appendHashCode(StringWriter writer, Plan plan, Plan parent)
		{
		}

		protected void appendClass(StringWriter writer, Plan plan, Plan parent)
		{
		}
	};

	public WriterDirectory()
	{
		classes.put(AllPlan.class, AllWriter.class);
		classes.put(AnnotationPlan.class, AnnotationWriter.class);
		classes.put(AnyAttributePlan.class, AnyAttributeWriter.class);
		classes.put(AnyPlan.class, AnyWriter.class);
		classes.put(AppinfoPlan.class, AppinfoWriter.class);
		classes.put(AttributeGroupPlan.class, AttributeGroupWriter.class);
		classes.put(AttributePlan.class, AttributeWriter.class);
		classes.put(ChoicePlan.class, ChoiceWriter.class);
		classes.put(ComplexContentPlan.class, ComplexContentWriter.class);
		classes.put(ComplexTypePlan.class, ComplexTypeWriter.class);
		classes.put(DocumentationPlan.class, DocumentationWriter.class);
		classes.put(ElementPlan.class, ElementWriter.class);
		classes.put(EnumerationPlan.class, EnumerationWriter.class);
		classes.put(ExtensionPlan.class, ExtensionWriter.class);
		classes.put(FieldPlan.class, FieldWriter.class);
		classes.put(FractionDigitsPlan.class, FractionDigitsWriter.class);
		classes.put(GroupPlan.class, GroupWriter.class);
		classes.put(HasFacetPlan.class, HasFacetWriter.class);
		classes.put(HasPropertyPlan.class, HasPropertyWriter.class);
		classes.put(ImportPlan.class, ImportWriter.class);
		classes.put(IncludePlan.class, IncludeWriter.class);
		classes.put(KeyPlan.class, KeyWriter.class);
		classes.put(KeyrefPlan.class, KeyrefWriter.class);
		classes.put(LengthPlan.class, LengthWriter.class);
		classes.put(ListPlan.class, ListWriter.class);
		classes.put(MaxInclusivePlan.class, MaxInclusiveWriter.class);
		classes.put(MaxLengthPlan.class, MaxLengthWriter.class);
		classes.put(MinExclusivePlan.class, MinExclusiveWriter.class);
		classes.put(MinInclusivePlan.class, MinInclusiveWriter.class);
		classes.put(MinLengthPlan.class, MinLengthWriter.class);
		classes.put(NotationPlan.class, NotationWriter.class);
		classes.put(PatternPlan.class, PatternWriter.class);
		classes.put(RedefinePlan.class, RedefineWriter.class);
		classes.put(RestrictionPlan.class, RestrictionWriter.class);
		classes.put(SchemaPlan.class, SchemaWriter.class);
		classes.put(SelectorPlan.class, SelectorWriter.class);
		classes.put(SequencePlan.class, SequenceWriter.class);
		classes.put(SimpleContentPlan.class, SimpleContentWriter.class);
		classes.put(SimpleTypePlan.class, SimpleTypeWriter.class);
		classes.put(UnionPlan.class, UnionWriter.class);
		classes.put(UniquePlan.class, UniqueWriter.class);
		classes.put(WhiteSpacePlan.class, WhiteSpaceWriter.class);
		keys = classes.keySet();
	}

	public ElementModule<Writer> lookup(Plan key, Writer parent)
	{
		if(!keys.contains(key.getClass()))
			throw new IllegalArgumentException("Unknown key: " + key.getClass().getSimpleName());

		Writer writerInstance = instances.get(key.getClass());
		if(writerInstance != null)
			return writerInstance;

		final Class<? extends Writer> writerClass = classes.get(key.getClass());
		try
		{
			instances.put(key.getClass(), writerInstance = writerClass.newInstance());
			return writerInstance;
		}
		catch(Exception e)
		{
			throw new CompilerError(e);
		}
	}

	public ModuleProcessor<Plan, Writer> getProcessor()
	{
		return writer;
	}

	public void clear()
	{
		instances.clear();
	}
}
