package org.safris.xml.generator.compiler.phase.plan;

import org.safris.xml.generator.compiler.lang.JavaBinding;
import org.safris.xml.generator.compiler.phase.plan.NamedPlan;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.plan.element.DocumentationPlan;
import org.safris.xml.generator.lexer.phase.model.AliasModel;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.element.SchemaModel;
import org.safris.xml.generator.module.phase.BindingQName;
import org.safris.xml.generator.module.phase.Nameable;

public abstract class AliasPlan<T extends AliasModel> extends NamedPlan<T> implements DocumentablePlan, Nameable<Plan>
{
	protected static String getClassName(AliasModel model, Model parent)
	{
		if(model == null || model.getName() == null)
			return null;

		if(model.getParent() instanceof SchemaModel || BindingQName.XS.getNamespaceURI().equals(model.getName().getNamespaceURI()))
			return model.getName().getNamespaceURI().getPackageName() + "." + JavaBinding.getClassSimpleName(model);

		Model check = model;
		while((check = check.getParent()) != null)
			if(check instanceof AliasModel && ((AliasModel)check).getName() != null)
				return getClassName((AliasModel)check, null) + "." + JavaBinding.getClassSimpleName(model);

		if(parent != null)
		{
			do
			{
				if(parent instanceof AliasModel && ((AliasModel)parent).getName() != null)
					return getClassName((AliasModel)parent, null) + "." + JavaBinding.getClassSimpleName(model);
			}
			while((parent = parent.getParent()) != null);
		}

		return model.getName().getNamespaceURI().getPackageName() + "." + JavaBinding.getClassSimpleName(model);
	}

	private DocumentationPlan documentation = null;

	private String packageName = null;
	private String instanceName = null;

	private String className = null;
	private String classSimpleName = null;
	private String schemaReference = null;
	private String xsdLocation = null;

	public AliasPlan(T model, Plan parent)
	{
		super(model, parent);
		if(getModel() != null)
			documentation = Plan.<DocumentationPlan>analyze(getModel().getDocumentation(), this);

		schemaReference = model.getSchema().getURL().toString();
		xsdLocation = model.getSchema().getTargetNamespaceSchemaLocationName();
	}

	public final String getXsdLocation()
	{
		return xsdLocation;
	}

	public final String getDocumentation()
	{
		if(documentation == null)
			return "";

		return documentation.getDocumentation();
	}

	public final String getInstanceName()
	{
		if(instanceName != null)
			return instanceName;

		return instanceName = JavaBinding.getInstanceName(getModel());
	}

	public final String getClassName(Plan parent)
	{
		if(className != null)
			return className;

		if(parent != null)
			return className = AliasPlan.getClassName(getModel(), parent.getModel());

		return className = AliasPlan.getClassName(getModel(), null);
	}

	public final String getClassSimpleName()
	{
		if(classSimpleName != null)
			return classSimpleName;

		return classSimpleName = JavaBinding.getClassSimpleName(getModel());
	}

	public final String getPackageName()
	{
		if(packageName != null)
			return packageName;

		return packageName = getName().getNamespaceURI().getPackageName().toString();
	}
}
