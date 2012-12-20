package org.safris.commons.jci;

import java.io.File;
import java.util.Collection;
import java.util.List;
import javax.tools.JavaFileObject;

public class InMemoryCompiler {
  public static void compile(final javax.tools.JavaCompiler compiler, final MemoryJavaFileManager fileManager, final Collection<String> options, final List<JavaFileObject> javaFiles, final File outDir) throws Exception {
    // We specify a task to the compiler. Compiler should use our file
    // manager and our list of "files".
    // Then we run the compilation with call()

    // any other options you want
    //options.addAll(Arrays.asList(options));

    compiler.getTask(null, fileManager, null, options, null, javaFiles).call();
    for (final JavaFileObject javaFileObject : javaFiles) {
      final String className = javaFileObject.getName().substring(1, javaFileObject.getName().length() - 5).replace(File.separatorChar, '.');
//      System.out.println(className);
//      fileManager.getClassLoader(null).loadClass(className);
    }

    fileManager.outputClasses(outDir);
    fileManager.outputClasses(new File(outDir.getParentFile().getParentFile(), "classes"));
  }
}
