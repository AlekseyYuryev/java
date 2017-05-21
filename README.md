<img src="https://www.cohesionfirst.org/logo.png" align="right">

## java<br>![java-enterprise][java-enterprise] <a href="https://www.cohesionfirst.org/"><img src="https://img.shields.io/badge/CohesionFirst%E2%84%A2--blue.svg"></a>

### Introduction

[SevaSafris][SevaSafris] / [java][java] is a unified source base for Java projects at [safris.org][safris.org].

### Projects

#### [CohesionFirst™](https://www.cohesionfirst.org/)

CohesionFirst™ is an engineering approach that guides engineers to do more quality work, with more confidence, and higher efficiency. CohesionFirst™ is a set of best practices and guidelines that allow engineers to excel their skills. Based on many well known conventions, the principles of CohesionFirst™ are not rocket science, but rather a set of clear, consistent, and logical standards curated specifically for engineering and design.

* [RDB (Relational Data Binding)][rdb]
  * [DDLx][rdb-ddlx] - Validating XML-based vendor-agnostic descriptor language for RDBMS SQL Schemas.
  * [DMLx][rdb-dmlx] - Validating XML-based vendor-agnostic entry language for RDBMS SQL Data.
  * [jSQL][rdb-jsql] - Lightweight ORM solution with strongly typed DML semantics.
* [jJB (Java <-> JSON Binding)][jjb] - Validating XML-based descriptor schema for JSON classes.
* [XSB (Xml Schema Binding)][xsb] - Complete solution for binding of XML Schemas to the Java language.
* [XRS (jaX REST Server)][xrs] - Simple, easyily debuggable and lightweight JAX-RS 2.0 Server implementation pure to the specification.

#### **Commons**

* [commons-cli][commons-cli] - Validating XML-based descriptor and API for the CLI facet of applications.
* [commons-dbcp][commons-dbcp] - Validating XML-based descriptor and API for initialization of JDBC Database Connection Pools.
* [commons-jetty][commons-jetty] - Embedded server wrapper and initializer of the [Jetty Servlet Container][jetty].

#### **Maven Plugins**

* [cert-maven-plugin][cert-maven-plugin] - Maven plugin that imports HTTPS server certificates into the keystore's trust chain.
* [codegen-maven-plugin][codegen-maven-plugin] - Maven plugin to execute code-generating libraries, such as [`istenum`][ISTEnumGenerator.java].
* [version-maven-plugin][version-maven-plugin] - Maven plugin for management of artifact versions of single and multi-module Maven projects in GIT SCM.
* [rdb-maven-plugin][rdb-maven-plugin] - Maven plugin to execute [RDB][rdb] code generator.
* [jjb-maven-plugin][jjb-maven-plugin] - Maven plugin to execute [jJB][jjb] code generator.
* [xml-maven-plugin][xml-maven-plugin] - Maven plugin to execute XML tasks, such as `validate`.
* [xsb-maven-plugin][xsb-maven-plugin] - Maven plugin to execute [XSB][xsb] code generator.

#### **Maven Archetypes**

* [cli-maven-archetype][cli-maven-archetype] - Quick-start Maven Archetype for [commons-cli][commons-cli].
* [dbcp-maven-archetype][dbcp-maven-archetype] - Quick-start Maven Archetype for [commons-dbcp][commons-dbcp].
* [rdb-maven-archetype][rdb-maven-archetype] - Quick-start Maven Archetype for [RDB][rdb] framework.

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[cert-maven-plugin]: https://github.com/SevaSafris/cert-maven-plugin
[cli-maven-archetype]: https://github.com/SevaSafris/cli-maven-archetype
[codegen-maven-plugin]: https://github.com/SevaSafris/codegen-maven-plugin
[commons-cli]: https://github.com/SevaSafris/commons-cli
[commons-dbcp]: https://github.com/SevaSafris/commons-dbcp
[commons-jetty]: https://github.com/SevaSafris/commons-jetty
[dbcp-maven-archetype]: https://github.com/SevaSafris/dbcp-maven-archetype
[ISTEnumGenerator.java]: https://github.com/SevaSafris/java/blob/master/commons/search/src/main/java/org/safris/commons/search/ISTEnumGenerator.java
[java-enterprise]: https://img.shields.io/badge/java-enterprise-blue.svg
[java]: https://github.com/SevaSafris/java
[jetty]: http://www.eclipse.org/jetty/
[jjb-maven-plugin]: https://github.com/SevaSafris/jjb-maven-plugin
[jjb]: https://github.com/SevaSafris/jjb
[rdb-ddlx]: https://github.com/SevaSafris/rdb/blob/master/ddlx
[rdb-dmlx]: https://github.com/SevaSafris/rdb/blob/master/dmlx
[rdb-jsql]: https://github.com/SevaSafris/rdb/blob/master/jsql
[rdb-maven-archetype]: https://github.com/SevaSafris/rdb-maven-archetype
[rdb-maven-plugin]: https://github.com/SevaSafris/rdb-maven-plugin
[rdb]: https://github.com/SevaSafris/rdb
[safris.org]: https://www.safris.org/
[SevaSafris]: https://github.com/SevaSafris
[version-maven-plugin]: https://github.com/SevaSafris/version-maven-plugin
[xml-maven-plugin]: https://github.com/SevaSafris/xml-maven-plugin
[xrs]: https://github.com/SevaSafris/xrs
[xsb-maven-plugin]: https://github.com/SevaSafris/xsb-maven-plugin
[xsb]: https://github.com/SevaSafris/xsb