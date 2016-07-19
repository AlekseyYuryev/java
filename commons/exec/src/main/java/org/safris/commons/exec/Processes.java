/* Copyright (c) 2006 Seva Safris
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
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
      teeStdin = Streams.teeAsync(processStdin, stdin, stdout);
    }

    InputStream teeStdout = null;
    if (stdout != null) {
      final InputStream processStdout = process.getInputStream();
      teeStdout = Streams.teeAsync(processStdout, stdout);
    }

    InputStream teeStderr = null;
    if (stderr != null) {
      final InputStream processStderr = process.getErrorStream();
      teeStderr = Streams.teeAsync(processStderr, stderr);
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
      teeStdin = Streams.teeAsync(processStdin, stdin, stdout);
    }

    InputStream teeStdout = null;
    if (stdout != null) {
      final InputStream processStdout = process.getInputStream();
      teeStdout = processStdout;
      Streams.pipeAsync(processStdout, stdout);
    }

    InputStream teeStderr = null;
    if (stderr != null) {
      final InputStream processStderr = process.getErrorStream();
      teeStderr = processStderr;
      Streams.pipeAsync(processStderr, stderr);
    }

    final Process subProcess = new PipedProcess(process, teeStdin, teeStdout, teeStderr);
    subProcess.waitFor();
    return subProcess;
  }

  public static Process forkAsync(final InputStream stdin, final OutputStream stdout, final OutputStream stderr, Map<String,String> props, final Class<?> clazz, final String ... args) throws IOException {
    final Process process = forkAsync(stdin, stdout, stderr, createJavaCommand(null, getSystemProperties(), clazz, args));
    return process;
  }

  public static Process forkAsync(final InputStream stdin, final OutputStream stdout, final OutputStream stderr, final String[] vmArgs, final Map<String,String> props, final Class<?> clazz, final String ... args) throws IOException {
    final Process process = forkAsync(stdin, stdout, stderr, createJavaCommand(vmArgs, getSystemProperties(), clazz, args));
    return process;
  }

  public static Process forkAsync(final InputStream stdin, final OutputStream stdout, final OutputStream stderr, final String[] vmArgs, final Class<?> clazz, final String ... args) throws IOException {
    return forkAsync(stdin, stdout, stderr, vmArgs, getSystemProperties(), clazz, args);
  }

  public static Process forkAsync(final InputStream stdin, final OutputStream stdout, final OutputStream stderr, final Class<?> clazz, final String ... args) throws IOException {
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

    @Override
    public OutputStream getOutputStream() {
      return stdin != null ? stdin : process.getOutputStream();
    }

    @Override
    public InputStream getInputStream() {
      return stdout != null ? stdout : process.getInputStream();
    }

    @Override
    public InputStream getErrorStream() {
      return stderr != null ? stderr : process.getErrorStream();
    }

    @Override
    public int waitFor() throws InterruptedException {
      return process.waitFor();
    }

    @Override
    public int exitValue() {
      return process.exitValue();
    }

    @Override
    public void destroy() {
      process.destroy();
    }
  }

  private Processes() {
  }
}