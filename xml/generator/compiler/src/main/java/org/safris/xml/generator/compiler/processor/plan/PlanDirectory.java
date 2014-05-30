/*  Copyright Safris Software 2008
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.xml.generator.compiler.processor.plan;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.processor.plan.element.AllPlan;
import org.safris.xml.generator.compiler.processor.plan.element.AnnotationPlan;
import org.safris.xml.generator.compiler.processor.plan.element.AnyAttributePlan;
import org.safris.xml.generator.compiler.processor.plan.element.AnyPlan;
import org.safris.xml.generator.compiler.processor.plan.element.AppinfoPlan;
import org.safris.xml.generator.compiler.processor.plan.element.AttributeGroupPlan;
import org.safris.xml.generator.compiler.processor.plan.element.AttributePlan;
import org.safris.xml.generator.compiler.processor.plan.element.ChoicePlan;
import org.safris.xml.generator.compiler.processor.plan.element.ComplexContentPlan;
import org.safris.xml.generator.compiler.processor.plan.element.ComplexTypePlan;
import org.safris.xml.generator.compiler.processor.plan.element.DocumentationPlan;
import org.safris.xml.generator.compiler.processor.plan.element.ElementPlan;
import org.safris.xml.generator.compiler.processor.plan.element.EnumerationPlan;
import org.safris.xml.generator.compiler.processor.plan.element.ExtensionPlan;
import org.safris.xml.generator.compiler.processor.plan.element.FieldPlan;
import org.safris.xml.generator.compiler.processor.plan.element.FractionDigitsPlan;
import org.safris.xml.generator.compiler.processor.plan.element.GroupPlan;
import org.safris.xml.generator.compiler.processor.plan.element.HasFacetPlan;
import org.safris.xml.generator.compiler.processor.plan.element.HasPropertyPlan;
import org.safris.xml.generator.compiler.processor.plan.element.ImportPlan;
import org.safris.xml.generator.compiler.processor.plan.element.IncludePlan;
import org.safris.xml.generator.compiler.processor.plan.element.KeyPlan;
import org.safris.xml.generator.compiler.processor.plan.element.KeyrefPlan;
import org.safris.xml.generator.compiler.processor.plan.element.LengthPlan;
import org.safris.xml.generator.compiler.processor.plan.element.ListPlan;
import org.safris.xml.generator.compiler.processor.plan.element.MaxInclusivePlan;
import org.safris.xml.generator.compiler.processor.plan.element.MaxLengthPlan;
import org.safris.xml.generator.compiler.processor.plan.element.MinExclusivePlan;
import org.safris.xml.generator.compiler.processor.plan.element.MinInclusivePlan;
import org.safris.xml.generator.compiler.processor.plan.element.MinLengthPlan;
import org.safris.xml.generator.compiler.processor.plan.element.NotationPlan;
import org.safris.xml.generator.compiler.processor.plan.element.PatternPlan;
import org.safris.xml.generator.compiler.processor.plan.element.RedefinePlan;
import org.safris.xml.generator.compiler.processor.plan.element.RestrictionPlan;
import org.safris.xml.generator.compiler.processor.plan.element.SchemaPlan;
import org.safris.xml.generator.compiler.processor.plan.element.SelectorPlan;
import org.safris.xml.generator.compiler.processor.plan.element.SequencePlan;
import org.safris.xml.generator.compiler.processor.plan.element.SimpleContentPlan;
import org.safris.xml.generator.compiler.processor.plan.element.SimpleTypePlan;
import org.safris.xml.generator.compiler.processor.plan.element.UnionPlan;
import org.safris.xml.generator.compiler.processor.plan.element.UniquePlan;
import org.safris.xml.generator.compiler.processor.plan.element.WhiteSpacePlan;
import org.safris.xml.generator.lexer.processor.GeneratorContext;
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

public final class PlanDirectory implements PipelineDirectory<GeneratorContext,Model,Plan<?>> {
  private final Map<Class<?>,Class<?>> classes = new HashMap<Class<?>,Class<?>>(39);
  private final Collection<Class<?>> keys;
  private final PlanProcessor processor = new PlanProcessor();

  public PlanDirectory() {
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

  public PipelineEntity getEntity(final Model entity, final Plan<?> parent) {
    if (!keys.contains(entity.getClass()))
      throw new IllegalArgumentException("Unknown key: " + entity.getClass().getSimpleName());

    final Class<?> parserClass = classes.get(entity.getClass());
    Plan<?> plan = null;
    try {
      final Constructor<?> constructor = parserClass.getConstructor(entity.getClass(), Plan.class);
      plan = (Plan<?>)constructor.newInstance(entity, parent);
      return plan;
    }
    catch (final Exception e) {
      throw new CompilerError(e);
    }
  }

  public PipelineProcessor<GeneratorContext,Model,Plan<?>> getProcessor() {
    return processor;
  }

  public void clear() {
  }
}