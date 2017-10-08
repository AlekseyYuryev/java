package org.libx4j.cdm.model;

public interface code {
  public interface Declarable {
    public type.Type toType();
  }

  public interface Block extends Statement {
  }

  public interface NamedBlock extends Declarable, Block {
    public NamedBlock addAnnotation(final decl.Annotation a);
    public void addInterface(final Interface i);
    public NamedBlock setVisibility(final Visibility v);
    public void setStrictFp(final boolean b);
  }

  public interface PrimitiveNumericClass extends PrimitiveClass {
    public Class preIncrement();
    public Class postIncrement();
    public Class preDecrement();
    public Class postDecrement();
//
//    public Class
  }

  public interface PrimitiveClass extends Class {
  }

  public interface Class extends NamedBlock {
    @Override
    public Class setVisibility(final Visibility v);

    @Override
    public type.Class toType();

    public decl.Class declare(final String name);

    @Override
    public Class append(final Statement statement);

    @Override
    public Class addAnnotation(final decl.Annotation a);
  }

  public interface Interface extends NamedBlock {
    @Override
    public type.Interface toType();

    public decl.Interface makeDeclaration(final String name);
  }

  public interface Enum extends NamedBlock {
    @Override
    public type.Enum toType();

    public decl.Enum makeDeclaration(final String name);
  }

  public interface Annotation extends NamedBlock {
    @Override
    public type.Annotation toType();

    public decl.Annotation makeDeclaration(final eval.Evaluation ... evaluations); // FIXME: Restrict to only what's legal for annotations
  }
}