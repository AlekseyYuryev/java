<img src="https://www.cohesionfirst.org/logo.png" align="right">

## XSB<br>![java-enterprise][java-enterprise] <a href="https://www.cohesionfirst.org/"><img src="https://img.shields.io/badge/CohesionFirst%E2%84%A2--blue.svg"></a>
> Xml Schema Binding

### Introduction

**XSB** is a Java framework for binding to XML Schemas. Based on the CohesionFirst™ approach, the **XSB** framework provides a complete solution for cohesive integration of Java applications to XML Documents via XML Schemas. The framework provides a [XSB Java Source Code Generator][generator] and a [XSB Runtime][runtime] to parse and marshal XML Documents to and from Java objects. The **XSB** framework is a complete implementation and covers the entirety of the [XSD 1.1 Specification][xsd-spec].

### Why **XSB**?

#### CohesionFirst™

Developed with the CohesionFirst™ approach, **XSB** is reliably designed, consistently implemented, and straightforward to use. Made possible by the rigorous conformance to design patterns and best practices in every line of its implementation, **XSB** is a complete binding solution of the entire XSD specification. The **XSB** solution differentiates itself from the rest with the strength of its cohesion to the Java language and the XML Schema model.

#### Supports entire XSD Specification

**XSB** supports all directives of the XSD Specification, and generates bindings with highest degree of cohesion possible with the Java language. **XSB** supports the following:

##### Namespaces

**XSB** provides complete binding to the namespaces and types defined in XML Schemas (`import` and `include`), and preserves prefix definitions.

##### Structural

**XSB** provides binding to `simpleType`, `complexType`, `element`, `group`, `attribute`, `attributeGroup`, `notation`, `any` and `anyAttribute` structural types.

##### Non-structural

**XSB** provides binding to `annotation`, `documentation`, `key`, `keyref` and `unique`.

##### Relational

**XSB** provides binding to `xs:type`, `xs:ref`, `all`, `sequence`, `choice`, 

##### Definition facets

**XSB** provides binding to the `complexContent`, `restriction`, `extension`, `list` and `union` facets.

##### `restriction` facets

**XSB** provides binding to the `enumeration`, `pattern` and `[min|max][Inclusive|Exclusive]`.

##### `xsi:type` and `redefine` facets

**XSB** properly binds to elements that use the `xsi:type` and `redefine` directives, which is represented by Java's class inheritance model.

### Getting Started

#### Prerequisites

* [Java 8][jdk8-download] - The minimum required JDK version.
* [Maven][maven] - The dependency management system.

#### Example

1. In your preferred development directory, create a [`maven-archetype-quickstart`][maven-archetype-quickstart] project.

  ```tcsh
  mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
  ```

2. Add the `mvn.repo.lib4j.org` Maven repositories to the POM.

  ```xml
  <repositories>
    <repository>
      <id>mvn.repo.lib4j.org</id>
      <url>http://mvn.repo.lib4j.org/m2</url>
    </repository>
  </repositories>
  <pluginRepositories>
    <pluginRepository>
      <id>mvn.repo.lib4j.org</id>
      <url>http://mvn.repo.lib4j.org/m2</url>
    </pluginRepository>
  </pluginRepositories>
  ```

3. Create a `example.xsd` XML Schema and put it in `src/main/resources/`.

  ```xml
  <xs:schema
    elementFormDefault="qualified"
    targetNamespace="http://mycompany.com/app/example.xsd"
    xmlns:xs="http://www.w3.org/2001/XMLSchema">

    <xs:element name="example">
      <xs:complexType>
        <xs:sequence>
          <xs:element name="hello">
            <xs:complexType>
              <xs:attribute name="world" type="xs:boolean" use="required"/>
            </xs:complexType>
          </xs:element>
        </xs:sequence>
      </xs:complexType>
    </xs:element>

  </xs:schema>
  ```
  
4. Add the [`org.libx4j.maven.plugin:xsb-maven-plugin`][xsb-maven-plugin] to the POM.

  ```xml
  <plugin>
    <groupId>org.libx4j.maven.plugin</groupId>
    <artifactId>xsb-maven-plugin</artifactId>
    <version>2.1.2</version>
    <executions>
      <execution>
        <phase>generate-sources</phase>
        <goals>
          <goal>generate</goal>
        </goals>
        <configuration>
          <manifest xmlns="http://maven.lib4j.org/common/manifest.xsd">
            <destdir explodeJars="true">generated-sources/xsb</destdir>
            <resources>
              <resource>src/main/resources/example.xsd</resource>
            </resources>
          </manifest>
        </configuration>
      </execution>
    </executions>
  </plugin>
  ```

5. Add the `org.libx4j.xsb:xsb-runtime` dependency to the POM.

  ```xml
  <dependency>
    <groupId>org.libx4j.xsb</groupId>
    <artifactId>xsb-runtime</artifactId>
    <version>2.1.3-SNAPSHOT</version>
  </dependency>
  ```

6. Run `mvn install`. Upon successful execution of the `xsb-maven-plugin`, a new path will be generated in `target/generated-sources/xsb`. Add this path to your Build Paths in your IDE to integrate into your project. A class by the name of `com.mycompany.app.example.xe` contains the bindings to `example.xsd`.

7. Create a XML Document in `src/test/resources`.

  ```xml
  <example
    xmlns="http://mycompany.com/app/example.xsd"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://mycompany.com/app/example.xsd ../../main/resources/example.xsd">

    <hello world="true"/>

  </example>
  ```

8. To parse the XML file into Java objects. In your `App.java`:

  ```java
  final ex_example example = (ex_example)Bindings.parse(new InputSource(Resources.getResourceOrFile("example.xml").getURL().openStream());
  ```

9. To marshal XSB Java objects to a XML String:

  ```java
  System.out.println(DOMs.domToString(Bindings.marshal(example), DOMStyle.INDENT));
  ```

#### Samples

Samples are provided in the XSB source code:

```tcsh
git clone git@github.com:libx4j/xsb.git
cd xsb/sample
mvn install
```

#### Tutorials

Tutorials are provided in the XSB source code:

```tcsh
git clone git@github.com:libx4j/xsb.git
cd xsb/tutorial
mvn install
```

### Known Issues

The **XSB** framework is not suitable for processing of large XML Documents (in the 100s of MBs). Built on top of Java's `org.w3c.dom` implementation of the [Document Object Model (DOM) Level 3 Core Specification][dom3], XML Documents parsed and marshalled by **XSB** consume a significant amount of memory. Work is underway for **XSB** v3 to use [Java's SAX Parser][sax-parser], which will allow the framework to process XML Documents of unbounded size.

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[dom3]: http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407
[java-enterprise]: https://img.shields.io/badge/java-enterprise-blue.svg
[jdk8-download]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[maven-archetype-quickstart]: http://maven.apache.org/archetypes/maven-archetype-quickstart/
[maven]: https://maven.apache.org/
[sax-parser]: https://docs.oracle.com/javase/tutorial/jaxp/sax/
[generator]: https://github.com/libx4j/xsb/tree/master/generator
[xsb-maven-plugin]: https://github.com/libx4j/xsb-maven-plugin
[runtime]: https://github.com/libx4j/xsb/tree/master/runtime
[xsd-spec]: https://www.w3.org/TR/xmlschema11-1/