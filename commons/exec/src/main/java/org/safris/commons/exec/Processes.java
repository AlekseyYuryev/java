/*  Copyright Safris Software 2006
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

package org.safris.commons.exec;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.safris.commons.io.Streams;
import org.safris.commons.lang.ClassLoaders;

public final class Processes {
  public static final Class<?> getExecutedClass() {
    try {
      final Field field = ClassLoader.class.getDeclaredField("classes");
      field.setAccessible(true);
      final Vector<Class<?>> classes = (Vector<Class<?>>)field.get(ClassLoader.getSystemClassLoader());
      for (final Class<?> cls : classes)  {
        if ((cls.getModifiers() & Modifier.ABSTRACT) != Modifier.ABSTRACT && cls.getName().startsWith("com.barclaysglobal"))
          return cls;
      }
    }
    catch (final Exception e) {
    }

    return null;
  }

  public static int getPID() {
    final String pidAtHost = ManagementFactory.getRuntimeMXBean().getName();
    if (pidAtHost == null)
      return -1;

    try {
      return Integer.parseInt(pidAtHost.substring(0, pidAtHost.indexOf("@")));
    }
    catch (final NumberFormatException e) {
      return -1;
    }
  }

  public static Process forkAsync(final InputStream stdin, final OutputStream stdout, final OutputStream stderr, final String ... args) throws IOException {
    final Collection<String> notNullArgs = new ArrayList<String>(args.length);
    for (final String arg : args)
      if (arg != null)
        notNullArgs.add(arg);

    if (notNullArgs.size() == 0)
      throw new IllegalArgumentException("empty argument list");

    final Process process = Runtime.getRuntime().exec(notNullArgs.toArray(new String[notNullArgs.size()]));
    OutputStream teeStdin = null;
    if (stdin != null) {
      final OutputStream processStdin = process.getOutputStream();
      teeStdin = Streams.tee(processStdin, stdin, stdout);
    }

    InputStream teeStdout = null;
    if (stdout != null) {
      final InputStream processStdout = process.getInputStream();
      teeStdout = Streams.tee(processStdout, stdout);
    }

    InputStream teeStderr = null;
    if (stderr != null) {
      final InputStream processStderr = process.getErrorStream();
      teeStderr = Streams.tee(processStderr, stderr);
    }

    return new PipedProcess(process, teeStdin, teeStdout, teeStderr);
  }

  public static Process forkSync(final InputStream stdin, final OutputStream stdout, final OutputStream stderr, final String ... args) throws IOException, InterruptedException {
    final Collection<String> notNullArgs = new ArrayList<String>(args.length);
    for (final String arg : args)
      if (arg != null)
        notNullArgs.add(arg);

    if (notNullArgs.size() == 0)
      throw new IllegalArgumentException("empty argument list");

    final Process process = Runtime.getRuntime().exec(notNullArgs.toArray(new String[notNullArgs.size()]));
    OutputStream teeStdin = null;
    if (stdin != null) {
      final OutputStream processStdin = process.getOutputStream();
      teeStdin = Streams.tee(processStdin, stdin, stdout);
    }

    InputStream teeStdout = null;
    if (stdout != null) {
      final InputStream processStdout = process.getInputStream();
      teeStdout = processStdout;
      Streams.pipe(processStdout, stdout);
    }

    InputStream teeStderr = null;
    if (stderr != null) {
      final InputStream processStderr = process.getErrorStream();
      teeStderr = processStderr;
      Streams.pipe(processStderr, stderr);
    }

    final Process subProcess = new PipedProcess(process, teeStdin, teeStdout, teeStderr);
    subProcess.waitFor();
    return subProcess;
  }

  public static Process forkAsync(final InputStream stdin, final OutputStream stdout, final OutputStream stderr, Map<String,String> props, final Class<?> clazz, final String ... args) throws IOException, InterruptedException {
    final Process process = forkAsync(stdin, stdout, stderr, createJavaCommand(null, getSystemProperties(), clazz, args));
    return process;
  }

  public static Process forkAsync(final InputStream stdin, final OutputStream stdout, final OutputStream stderr, final String[] vmArgs, final Map<String,String> props, final Class<?> clazz, final String ... args) throws IOException, InterruptedException {
    final Process process = forkAsync(stdin, stdout, stderr, createJavaCommand(vmArgs, getSystemProperties(), clazz, args));
    return process;
  }

  public static Process forkAsync(final InputStream stdin, final OutputStream stdout, final OutputStream stderr, final String[] vmArgs, final Class<?> clazz, final String ... args) throws IOException, InterruptedException {
    return forkAsync(stdin, stdout, stderr, vmArgs, getSystemProperties(), clazz, args);
  }

  public static Process forkAsync(final InputStream stdin, final OutputStream stdout, final OutputStream stderr, final Class<?> clazz, final String ... args) throws IOException, InterruptedException {
    return forkAsync(stdin, stdout, stderr, null, getSystemProperties(), clazz, args);
  }

  public static Process forkSync(final InputStream stdin, final OutputStream stdout, final OutputStream stderr, final String[] vmArgs, final Map<String,String> props, final Class<?> clazz, final String ... args) throws IOException, InterruptedException {
    final Process process = forkAsync(stdin, stdout, stderr, createJavaCommand(vmArgs, getSystemProperties(), clazz, args));
    process.waitFor();
    return process;
  }

  public static Process forkSync(final InputStream stdin, final OutputStream stdout, final OutputStream stderr, final Map<String,String> props, final Class<?> clazz, final String ... args) throws IOException, InterruptedException {
    final Process process = forkAsync(stdin, stdout, stderr, createJavaCommand(null, getSystemProperties(), clazz, args));
    process.waitFor();
    return process;
  }

  public static Process forkSync(final InputStream stdin, final OutputStream stdout, final OutputStream stderr, final String[] vmArgs, final Class<?> clazz, final String ... args) throws IOException, InterruptedException {
    return forkSync(stdin, stdout, stderr, vmArgs, getSystemProperties(), clazz, args);
  }

  public static Process forkSync(final InputStream stdin, final OutputStream stdout, final OutputStream stderr, final Class<?> clazz, final String ... args) throws IOException, InterruptedException {
    return forkSync(stdin, stdout, stderr, getSystemProperties(), clazz, args);
  }

  private static String[] createJavaCommand(final String[] vmArgs, final Map<String,String> props, final Class<?> clazz, final String ... args) {
    final URL[] classpathURLs = ClassLoaders.getClassPath();
    final StringBuffer classpath = new StringBuffer();
    if (classpathURLs != null && classpathURLs.length != 0)
      for (final URL url : classpathURLs)
        classpath.append(File.pathSeparatorChar).append(url.getPath());

    final String[] options = new String[(args != null ? args.length : 0) + (vmArgs != null ? vmArgs.length : 0) + (props != null ? props.size() : 0) + 4];
    int i = -1;
    options[++i] = "java";
    if (vmArgs != null && vmArgs.length != 0)
      for (final String vmArg : vmArgs)
        options[++i] = vmArg;

    if (props != null && props.size() != 0)
      for (final Map.Entry<String,String> property : props.entrySet())
        options[++i] = "-D" + property.getKey() + "=" + property.getValue();

    options[++i] = "-cp";
    options[++i] = classpath.length() != 0 ? classpath.substring(1) : "";
    options[++i] = clazz.getName();
    System.arraycopy(args, 0, options, ++i, args.length);

    return options;
  }

  @SuppressWarnings("rawtypes")
  private static Map<String,String> getSystemProperties() {
    if (System.getProperties().size() == 0)
      return new HashMap<String,String>(0);

    final Map<String,String> properties = new HashMap<String,String>(7);
    for (final Map.Entry property : System.getProperties().entrySet()) {
      final String key = (String)property.getKey();
      final String value = (String)property.getValue();
      if (value.trim().length() != 0 && !value.contains(" ") && !key.contains(" "))
        properties.put(key, value.trim());
    }

    return properties;
  }

  private static final class PipedProcess extends Process {
    private final Process process;
    private final OutputStream stdin;
    private final InputStream stdout;
    private final InputStream stderr;

    public PipedProcess(final Process process, final OutputStream stdin, InputStream stdout, final InputStream stderr) {
      this.process = process;
      this.stdin = stdin;
      this.stdout = stdout;
      this.stderr = stderr;
    }

    public OutputStream getOutputStream() {
      return stdin != null ? stdin : process.getOutputStream();
    }

    public InputStream getInputStream() {
      return stdout != null ? stdout : process.getInputStream();
    }

    public InputStream getErrorStream() {
      return stderr != null ? stderr : process.getErrorStream();
    }

    public int waitFor() throws InterruptedException {
      return process.waitFor();
    }

    public int exitValue() {
      return process.exitValue();
    }

    public void destroy() {
      process.destroy();
    }
  }

  private Processes() {
  }
}