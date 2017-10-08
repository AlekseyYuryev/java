package org.libx4j.cdm.model;

public interface kind {
  public interface Cast extends Evaluation {
    public Evaluation getArgument();
  }

  public interface Variable extends Code {
    public Variable setVisibility(final Visibility visibility);
    public Variable setFinal(final boolean value);
    public Variable setStatic(final boolean value);
    public Variable setTransient(final boolean value);
    public Variable setVolatile(final boolean value);
  }

  public interface Positioned {
    public void setPosition(final int l);
    public int getPosition();
  }

  public interface Code extends Positioned {
  }

  public interface Scope extends Code {
    public void addCode(final Code c);
  }

  public interface BlockScope extends Scope {
  }

  public interface ClassScope extends Scope {
  }

  public interface InterfaceScope extends Scope {
  }

  public interface AnnotationScope extends Scope {
  }

  public interface MethodScope extends Scope {
  }

  public interface ConstructorScope extends Scope {
  }

  public interface Statement {
  }

  public interface Evaluation extends Statement {
    public <T>Evaluation invokeMethod(final String name, final java.lang.Class<T> type);
  }

  public interface Declaration {
  }

  public interface Instance extends Evaluation {
  }

  public interface Declarable {
    public Declaration makeDeclaration();
  }

  public enum Visibility {
    PRIVATE,
    PUBLIC,
    PROTECTED,
    PACKAGE
  }

  public interface Package {
    public void setName(final String name);
    public String getName();
  }

  public interface Type extends Declarable, Scope {
    public void addAnnotation(final AnnotationInstance a);
    public void addInterface(final Interface i);
    public void setVisibility(final Visibility v);
    public void setStrictFp(final boolean b);
  }

  public interface Class extends Type, ClassScope {
    @Override
    public ClassDeclaration makeDeclaration();
  }

  public interface ClassDeclaration extends Declaration {
    public ClassInstance newInstance(final Evaluation ... evaluations);
  }

  public interface Interface extends Type, InterfaceScope {
    @Override
    public InterfaceDeclaration makeDeclaration();
  }

  public interface InterfaceDeclaration extends Declaration {
  }

  public interface Annotation extends Declarable, AnnotationScope {
    @Override
    public AnnotationDeclaration makeDeclaration();
  }

  public interface AnnotationDeclaration extends Declaration {
    public AnnotationInstance newInstance(final Evaluation ... evaluations);
  }

  public interface AnnotationInstance extends Instance {
  }

  public interface ClassInstance extends Instance {
  }

  public interface ArrayInstance extends Instance {
  }

  public interface Object {
    public Class getClassType();
  }
}