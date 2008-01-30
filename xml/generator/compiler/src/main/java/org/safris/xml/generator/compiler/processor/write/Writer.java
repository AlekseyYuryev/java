package org.safris.xml.generator.compiler.processor.write;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashSet;
import org.safris.commons.format.SourceFormat;
import org.safris.commons.util.Files;
import org.safris.commons.util.logging.Logger;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.lang.CompilerLoggerName;
import org.safris.xml.generator.compiler.lang.JavaBinding;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.processor.ElementModule;
import org.safris.xml.generator.processor.ProcessorDirectory;

public abstract class Writer<T extends Plan> implements ElementModule<Writer>
{
	protected static final Logger logger = Logger.getLogger(CompilerLoggerName.WRITE);
	private final Collection<String> messages = new HashSet<String>();

	protected void writeFile(Writer writer, Plan plan, File destDir)
	{
		if(!(plan instanceof Nameable))
			return;

		final String display = Files.relativePath(Files.getCwd(), new File(plan.getModel().getSchema().getURL().getFile()).getAbsoluteFile());
		final String message = "Compiling {" + plan.getModel().getTargetNamespace() + "} from " + display;
		if(!messages.contains(message))
		{
			logger.info(message);
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
		((Writer)directory.getModule(plan, null)).appendDeclaration(writer, plan, parent);
	}

	public static void writeGetMethod(StringWriter writer, Plan plan, Plan parent)
	{
		((Writer)directory.getModule(plan, null)).appendGetMethod(writer, plan, parent);
	}

	public static void writeSetMethod(StringWriter writer, Plan plan, Plan parent)
	{
		((Writer)directory.getModule(plan, null)).appendSetMethod(writer, plan, parent);
	}

	public static void writeMarshal(StringWriter writer, Plan plan, Plan parent)
	{
		((Writer)directory.getModule(plan, null)).appendMarshal(writer, plan, parent);
	}

	public static void writeParse(StringWriter writer, Plan plan, Plan parent)
	{
		((Writer)directory.getModule(plan, null)).appendParse(writer, plan, parent);
	}

	public static void writeCopy(StringWriter writer, Plan plan, Plan parent, String variable)
	{
		((Writer)directory.getModule(plan, null)).appendCopy(writer, plan, parent, variable);
	}

	public static void writeEquals(StringWriter writer, Plan plan, Plan parent)
	{
		((Writer)directory.getModule(plan, null)).appendEquals(writer, plan, parent);
	}

	public static void writeHashCode(StringWriter writer, Plan plan, Plan parent)
	{
		((Writer)directory.getModule(plan, null)).appendHashCode(writer, plan, parent);
	}

	public static void writeClass(StringWriter writer, Plan plan, Plan parent)
	{
		((Writer)directory.getModule(plan, null)).appendClass(writer, plan, parent);
	}

	protected static ProcessorDirectory<Plan,Writer> directory = null;

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
