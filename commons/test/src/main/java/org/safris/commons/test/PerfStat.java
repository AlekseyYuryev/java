package org.safris.commons.test;

import java.util.HashMap;
import java.util.Map;

import org.safris.commons.lang.Strings;

public final class PerfStat {
  public static void mark(final String cls, final String method) {
    String log = "";
    final long time1 = System.currentTimeMillis();
    final Long time0 = times.put(cls + method, time1);
    if (time0 != null)
      log += "[" + cls + "] Time: " + Strings.padFixed((time1 - time0) + "ms", 6, false);
    else
      log += "[" + cls + "] Time: ------";

    final long mem1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    final Long mem0 = mems.put(cls + method, mem1);
    if (mem0 != null)
      log += " Mem: " + Strings.padFixed((mem1 - mem0) / 1024 + "kb", 8, false) + " [" + method + "]";
    else
      log += " Mem: --------" + " [" + method + "]";

    if (time0 != null || mem0 != null)
      System.err.println(log);
  }

  private static final Map<String,Long> mems = new HashMap<String,Long>();
  private static final Map<String,Long> times = new HashMap<String,Long>();

  private PerfStat() {
  }
}