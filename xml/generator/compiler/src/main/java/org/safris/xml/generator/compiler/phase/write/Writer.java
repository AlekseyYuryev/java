package org.safris.xml.generator.compiler.phase.write;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.safris.commons.format.SourceFormat;
import org.safris.commons.util.Files;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.lang.JavaBinding;
import org.safris.xml.generator.compiler.phase.plan.NestablePlan;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.plan.element.SimpleTypePlan;
import org.safris.xml.generator.module.phase.BindingParameters;
import org.safris.xml.generator.module.phase.Nameable;
import org.safris.xml.generator.module.phase.Phase;

public abstract class Writer<T extends Plan> extends Phase<Plan>
{
	private static final Collection<String> messages = new HashSet<String>();
	private static final Map<Class<? extends Plan>,Writer> instances = new HashMap<Class<? extends Plan>,Writer>();
	private static final Writer instance = new Writer()
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

	public static Writer instance()
	{
		return instance;
	}

	private static void writeFile(Writer writer, Plan plan, File destDir)
	{
		if(!(plan instanceof Nameable))
			return;

		final String display = Files.relativePath(Files.getCwd(), new File(plan.getModel().getSchema().getURL().getFile()).getAbsoluteFile());
		final String message = "Compiling {" + plan.getModel().getTargetNamespace() + "} from " + display;
		if(!messages.contains(message))
		{
			writer.logger().info(message);
			messages.add(message);
		}

		Nameable nameable = (Nameable)plan;
		try
		{
			String pkg = nameable.getName().getNamespaceURI().getPackageName().toString();
			if(pkg == null)
			{
				System.err.println("The binding configuration does not specify package for " + ((Nameable)plan).getName().getNamespaceURI());
				System.exit(1);
			}

			File directory = new File(destDir, pkg.replace('.', '/'));
			directory.mkdirs();

			String absoluteFilePath = directory.getAbsolutePath() + File.separator + JavaBinding.getClassSimpleName(plan.getModel()) + ".java";
			StringWriter stringWriter = new StringWriter();
			writer.appendClass(stringWriter, plan, null);
			String text = SourceFormat.getDefaultFormat().format(stringWriter.toString());
			FileOutputStream out = new FileOutputStream(absoluteFilePath);
			out.write("// SAFRIS.org, XML Toolkit\n".getBytes());
			out.write(text.getBytes());
			out.flush();
			out.close();
			// used for the process that determines if the generated code is up-to-date
			directory.setLastModified(System.currentTimeMillis() - 60000);
		}
		catch(Exception e)
		{
			throw new CompilerError(e);
		}
	}

	public static void writeDeclaration(StringWriter writer, Plan plan, Plan parent)
	{
		WriterDirectory.instance().lookup(plan).appendDeclaration(writer, plan, parent);
	}

	public static void writeGetMethod(StringWriter writer, Plan plan, Plan parent)
	{
		WriterDirectory.instance().lookup(plan).appendGetMethod(writer, plan, parent);
	}

	public static void writeSetMethod(StringWriter writer, Plan plan, Plan parent)
	{
		WriterDirectory.instance().lookup(plan).appendSetMethod(writer, plan, parent);
	}

	public static void writeMarshal(StringWriter writer, Plan plan, Plan parent)
	{
		WriterDirectory.instance().lookup(plan).appendMarshal(writer, plan, parent);
	}

	public static void writeParse(StringWriter writer, Plan plan, Plan parent)
	{
		WriterDirectory.instance().lookup(plan).appendParse(writer, plan, parent);
	}

	public static void writeCopy(StringWriter writer, Plan plan, Plan parent, String variable)
	{
		WriterDirectory.instance().lookup(plan).appendCopy(writer, plan, parent, variable);
	}

	public static void writeEquals(StringWriter writer, Plan plan, Plan parent)
	{
		WriterDirectory.instance().lookup(plan).appendEquals(writer, plan, parent);
	}

	public static void writeHashCode(StringWriter writer, Plan plan, Plan parent)
	{
		WriterDirectory.instance().lookup(plan).appendHashCode(writer, plan, parent);
	}

	public static void writeClass(StringWriter writer, Plan plan, Plan parent)
	{
		WriterDirectory.instance().lookup(plan).appendClass(writer, plan, parent);
	}

	protected final void tailRecurse(Collection<Plan> models, BindingParameters share)
	{
		if(models == null || models.size() == 0)
			return;

		for(Plan model : models)
		{
			if(model == null)
				continue;

			tailRecurse(disclose(model, share), share);
		}
	}

	public Collection<Writer> manipulate(Collection<Plan> documents, BindingParameters share)
	{
		tailRecurse(documents, share);
		return null;
	}

	protected Collection<Plan> disclose(Plan plan, BindingParameters share)
	{
		if(!(plan instanceof SimpleTypePlan) || (plan instanceof NestablePlan && ((NestablePlan)plan).isNested()))
			return null;

		if(((Nameable)plan).getName().getNamespaceURI() == null)
			throw new CompilerError("Cannot generate classes for schema with no targetNamespace.");

		writeFile(WriterDirectory.instance().lookup(plan), plan, share.getDestDir());

		return null;
	}

	protected abstract void appendDeclaration(StringWriter writer, T plan, Plan parent);
	protected abstract void appendGetMethod(StringWriter writer, T plan, Plan parent);
	protected abstract void appendSetMethod(StringWriter writer, T plan, Plan parent);
	protected abstract void appendMarshal(StringWriter writer, T plan, Plan parent);
	protected abstract void appendParse(StringWriter writer, T plan, Plan parent);
	protected abstract void appendCopy(StringWriter writer, T plan, Plan parent, String variable);
	protected abstract void appendEquals(StringWriter writer, T plan, Plan parent);
	protected abstract void appendHashCode(StringWriter writer, T plan, Plan parent);
	protected abstract void appendClass(StringWriter writer, T plan, Plan parent);
}
