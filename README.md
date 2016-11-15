<img src="http://safris.org/logo.png" align="right" />
# java [![CohesionFirst](http://safris.org/cf2.svg)](https://cohesionfirst.com/)
> safris.org

## Introduction

[SevaSafris](https://github.com/SevaSafris) / [java](https://github.com/SevaSafris/java) is a unified source base for Java projects at [safris.org](https://www.safris.org/).

## Projects

### [CohesionFirst™](https://cohesionfirst.com/)

* [XSB (Xml Schema Binding)](https://github.com/SevaSafris/java/blob/master/cf/xsb) - Complete solution for binding of XML Schemas to the Java language.
* [XDE (eXtended Data Entities)](https://github.com/SevaSafris/java/blob/master/cf/xde) - Lightweight ORM solution with strongly typed DML semantics.
* [XDL (eXtended Data Language)](https://github.com/SevaSafris/java/blob/master/cf/xdl) - Validating XML-based vendor-agnostic descriptor language for RDBMS SQL Schemas.
* [XJB (eXtended JSON Binding)](https://github.com/SevaSafris/java/blob/master/cf/xjb) - Validating XML-based descriptor schema for JSON classes.
* [XRS (jaX REST Server)](https://github.com/SevaSafris/java/blob/master/cf/xrs) - Simple, easyily debuggable and lightweight JAX-RS 2.0 Server implementation pure to the specification.

### [Commons](https://github.com/SevaSafris/java/blob/master/commons)

* [CLI](https://github.com/SevaSafris/java/blob/master/commons/cli) - Validating XML-based descriptor and API for the CLI facet of applications.
* [DBCP](https://github.com/SevaSafris/java/blob/master/commons/dbcp) - Validating XML-based descriptor and API for initialization of JDBC Database Connection Pools.
* [Jetty](https://github.com/SevaSafris/java/blob/master/commons/jetty) - Embedded server wrapper and initializer of the [Jetty Servlet Container](http://www.eclipse.org/jetty/).

### [Maven Plugins](https://github.com/SevaSafris/java/blob/master/maven/plugin)

* [cert-maven-plugin](https://github.com/SevaSafris/java/tree/master/maven/plugin/cert-maven-plugin) - Maven plugin that imports HTTPS server certificates into the keystore's trust chain.
* [codegen-maven-plugin](https://github.com/SevaSafris/java/tree/master/maven/plugin/codegen-maven-plugin) - Maven plugin to execute code-generating libraries, such as [`istenum`](https://github.com/SevaSafris/java/blob/master/commons/search/src/main/java/org/safris/commons/search/ISTEnumGenerator.java).
* [version-maven-plugin](https://github.com/SevaSafris/java/tree/master/maven/plugin/version-maven-plugin) - Maven plugin for management of artifact versions of single and multi-module Maven projects in GIT SCM.
* [xdb-maven-plugin](https://github.com/SevaSafris/java/tree/master/maven/plugin/xdb-maven-plugin) - Maven plugin to execute [XDL](https://github.com/SevaSafris/java/tree/master/cf/xdl) and [XDE](https://github.com/SevaSafris/java/tree/master/cf/xde) code generators.
* [xjb-maven-plugin](https://github.com/SevaSafris/java/tree/master/maven/plugin/xjb-maven-plugin) - Maven plugin to execute [XJB](https://github.com/SevaSafris/java/tree/master/cf/xjb) code generator.
* [xml-maven-plugin](https://github.com/SevaSafris/java/tree/master/maven/plugin/xml-maven-plugin) - Maven plugin to execute XML tasks, such as `validate`.
* [xsb-maven-plugin](https://github.com/SevaSafris/java/tree/master/maven/plugin/xsb-maven-plugin) - Maven plugin to execute [XSB](https://github.com/SevaSafris/java/tree/master/cf/xsb) code generator.

## License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.
