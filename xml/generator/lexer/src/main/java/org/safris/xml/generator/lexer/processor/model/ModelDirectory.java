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

package org.safris.xml.generator.lexer.processor.model;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.safris.commons.logging.Logger;
import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.lang.LexerLoggerName;
import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.composite.SchemaComposite;
import org.safris.xml.generator.lexer.processor.composite.SchemaNodeComposite;
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
import org.safris.xml.generator.lexer.processor.model.element.UnknownModel;
import org.safris.xml.generator.lexer.processor.model.element.WhiteSpaceModel;
import org.w3c.dom.Node;

public class ModelDirectory implements PipelineDirectory<GeneratorContext,SchemaComposite,Model> {
  protected static final Logger logger = Logger.getLogger(LexerLoggerName.MODEL);

  private final Map<String,Class<? extends Model>> classes = new HashMap<String,Class<? extends Model>>(39);
  private final Collection<String> keys;
  private final ModelProcessor processor = new ModelProcessor();

  public ModelDirectory() {
    classes.put(null, UnknownModel.class);
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
    classes.put("fractionDigits", FractionDigitsModel.class);
    classes.put("group", GroupModel.class);
    classes.put("hasFacet", HasFacetModel.class);
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

  public PipelineEntity<Model> getEntity(final SchemaComposite entity, final Model parent) {
    if (!(entity instanceof SchemaNodeComposite))
      return null;

    final SchemaNodeComposite schemaNodeComposite = (SchemaNodeComposite)entity;
    String elementName = schemaNodeComposite.getNode().getLocalName();
    if (elementName == null)
      throw new IllegalArgumentException("Node key without local name");

    if (!keys.contains(elementName)) {
      logger.warning("Unknown schema element <" + (schemaNodeComposite.getNode().getPrefix() != null ? schemaNodeComposite.getNode().getPrefix() + ":" : "") + elementName + ">");
      elementName = null;
    }

    final Class<? extends Model> modelClass = classes.get(elementName);
    Model handler = null;
    try {
      final Constructor<? extends Model> constructor = modelClass.getDeclaredConstructor(Node.class, Model.class);
      constructor.setAccessible(true);
      handler = constructor.newInstance(schemaNodeComposite.getNode(), parent);
      return handler;
    }
    catch (final Exception e) {
      throw new LexerError(e);
    }
  }

  public PipelineProcessor<GeneratorContext,SchemaComposite,Model> getProcessor() {
    return processor;
  }

  public void clear() {
  }
}