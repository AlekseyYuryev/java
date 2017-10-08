package org.libx4j.cdm.model;

public interface type {
  public interface Type {
  }

  public interface Class extends Type {
    public inst.Class instantiate(final eval.Evaluation ... evaluations);
  }

  public interface Interface extends Type {
  }

  public interface Enum extends Type {
  }

  public interface Annotation extends Type {
  }
}