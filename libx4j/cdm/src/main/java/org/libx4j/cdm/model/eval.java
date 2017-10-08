package org.libx4j.cdm.model;

public interface eval {
  public interface Evaluation {
    public <T>Evaluation invokeMethod(final String name, final java.lang.Class<T> type);
  }

  public interface Cast extends Evaluation {
    public Evaluation getArgument();
  }
}