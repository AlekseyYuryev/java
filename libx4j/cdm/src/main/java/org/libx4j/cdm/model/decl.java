package org.libx4j.cdm.model;

public interface decl {
  public interface Declaration extends Statement {
    public Declaration setVisibility(final Visibility visibility);
    public Declaration setFinal(final boolean value);
    public Declaration setStatic(final boolean value);
    public Declaration setTransient(final boolean value);
    public Declaration setVolatile(final boolean value);
    public Statement set(final eval.Evaluation evaluation);
  }

  public interface Array extends Declaration {
    public inst.Array instantiate(final int length);
    public inst.Array instantiate(final eval.Evaluation ... evaluations);
  }

  public interface Class extends Declaration {
    public inst.Class newInstance(final eval.Evaluation ... evaluations);
  }

  public interface Interface extends Declaration {
    public inst.Class newInstance(final code.Block block);
  }

  public interface Enum extends Declaration {
  }

  public interface Annotation extends Declaration {
  }
}