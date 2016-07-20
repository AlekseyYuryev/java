package org.safris.commons.exec;

import org.safris.commons.io.Streams;

public class ProcessesTest {
  public static void main(final String[] args) throws Exception {
    final Process process = Processes.forkAsync(null, System.out, null, true, "src/test/resources/test");
    Streams.pipeAsync(process.getInputStream(), null);
  }
}