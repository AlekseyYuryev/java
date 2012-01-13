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

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.commons.xml.NamespaceURI;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.composite.SchemaComposite;
import org.safris.xml.generator.lexer.processor.composite.SchemaModelComposite;
import org.safris.xml.generator.lexer.processor.composite.SchemaNodeComposite;
import org.safris.xml.generator.lexer.processor.document.SchemaDocument;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ModelProcessor implements PipelineProcessor<GeneratorContext,SchemaComposite,Model> {
  private Model root;

  public Collection<Model> process(GeneratorContext pipelineContext, Collection<SchemaComposite> documents, PipelineDirectory<GeneratorContext,SchemaComposite, Model> directory) {
    root = new Model(null, null){};
    // Then we parse all of the schemas that have been included and imported
    final Collection<Model> schemaModels = new ArrayList<Model>();

    for (SchemaComposite schemaComposite : documents) {
      final SchemaModelComposite schemaModelComposite = (SchemaModelComposite)schemaComposite;
      final SchemaDocument schemaDocument = schemaModelComposite.getSchemaDocument();
      final SchemaModel model = recurse(root, schemaDocument.getSchemaReference().getNamespaceURI(), schemaDocument.getDocument().getChildNodes(), schemaDocument.getSchemaReference().getURL(), directory);
      if (model == null)
        throw new LexerError("We should have found a schema!");

      schemaModelComposite.setSchemaModel(model);
      schemaModels.add(model);
    }

    return schemaModels;
  }

  private final SchemaModel recurse(Model model, NamespaceURI targetNamespace, NodeList children, URL url, PipelineDirectory<GeneratorContext,SchemaComposite,Model> directory) {
    if (children == null || children.getLength() == 0)
      return null;

    // FIXME: This looks ugly!
    SchemaModel schema = null;
    if (model instanceof SchemaModel) {
      schema = (SchemaModel)model;
      if (model.getTargetNamespace() == null) {
        // This means that this is an included schema
        schema.setTargetNamespace(targetNamespace);
        URL schemaReference = model.lookupSchemaLocation(targetNamespace);
        if (schemaReference == null)
          model.registerSchemaLocation(targetNamespace, schemaReference = url);
      }
      else {
        model.registerSchemaLocation(targetNamespace, url);
      }

      schema.setURL(url);
      url = null;
    }

    Model current = null;
    for (int i = 0; i < children.getLength(); i++) {
      final Node child = children.item(i);
      if (child.getLocalName() == null)
        continue;

      final SchemaNodeComposite nodeComposite = new SchemaNodeComposite(child);
      final Model handler = (Model)directory.getEntity(nodeComposite, model);
      if (current != null) {
        handler.setPrevious(current);
        current.setNext(handler);
      }

      current = handler;

      final SchemaModel temp = recurse(handler, targetNamespace, child.getChildNodes(), url, directory);
      if (temp != null)
        schema = temp;
    }

    return schema;
  }
}
