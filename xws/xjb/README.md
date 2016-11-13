<img src="http://safris.org/logo.png" align="right" />
# XJB [![CohesionFirst](http://safris.org/cf2.svg)](https://cohesionfirst.com/)
> eXtended JSON Binding

## Getting Started

XJB is a lightweight framework based on a XSD [XJB Schema](http://xws.safris.org/xjb.xsd) that allows one to create a schema for JSON classes. As there does not exist a formalized schema specification for JSON, developers easily make mistakes when designing JSON messages, encoding JSON objects, and decoding JSON strings. To its disadvantage, JavaScript is a highly non-cohesive language, resulting in errors always realized in runtime. The [XJB Schema](http://xws.safris.org/xjb.xsd) allows one to define JSON classes. It has constructs that allow for the definition of the entire range of possible JSON structures. Additionally, the schema offers abstract types, which provides one with the ability to use the OO principles of inheritance and polymorphism for JSON -- powerful paradigms which are not used in JSON as it is based on the "loosely Object Oriented" language of JavaScript. Once a developer creates a [XJB Schema](http://xws.safris.org/xjb.xsd), he would run his Maven build and invoke the [XJB Maven Plugin](https://github.com/SevaSafris/xjb-maven-plugin) plugin to generate Java classes that bind to the schema. A developer can use the generated classes to parse and marshal JSON messages, confident that all messages conform to the definition in the XJB. To make the XJB definition ever-so powerful, more advanced facets of the XJB schema have been defined. The XJB objects and properties allow one to provide JSON validation to execute upon encoding or decoding of JSON documents. Specifically, one can annotate properties in JSON as required/not-required, string properties conforming to Regex expressions, null/not-null, and numeric ranges for number types. This extension to XJB allows one to enforce explicit rules for the JSON that is sent between a client and a server. Such higher level of validation provides a mechanism of checking of errors in JSON documents. For instance, if a string property in JSON must conform to a specific Date format, its format can be expressed in the XJB as a Regex expression. Thereafter, if a document is received from a client that does not match this expression, it will fail early. Early failure allows a clear and direct, up-front realization of errors in the system, thus bringing higher confidence to the overall solution, both front-end and back-end. Using this framework, a team can write more complex code faster and with more confidence.

### Prerequisites

* [Maven](https://maven.apache.org/) - The dependency management system used to install XJB.

### Getting Started

Currently, Maven is the only method by which one can install XJB. To install XJB:

Add the mvn.repo.safris.org Maven Repositories to the POM.

```
<repositories>
  <repository>
    <id>mvn.repo.safris.org</id>
    <url>http://mvn.repo.safris.org/m2</url>
  </repository>
</repositories>
<pluginRepositories>
  <pluginRepository>
    <id>mvn.repo.safris.org</id>
    <url>http://mvn.repo.safris.org/m2</url>
  </pluginRepository>
</pluginRepositories>
```

[Create a json.xjb file](https://github.com/SevaSafris/xjb-maven-plugin/blob/master/src/test/resources/json.xjb), and put it in src/main/resources/json.xjb.

Add the [org.safris.maven.plugin:xjb-maven-plugin](https://github.com/SevaSafris/xjb-maven-plugin) to the POM.

```
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>xjb-maven-plugin</artifactId>
  <version>1.1.3</version>
  <executions>
    <execution>
      <phase>generate-sources</phase>
      <goals>
        <goal>generate</goal>
      </goals>
      <configuration>
        <manifest xmlns="http://maven.safris.org/common/manifest.xsd">
          <destdir>${project.build.directory}/generated-sources/xjb</destdir>
          <schemas>
            <schema>${basedir}/src/main/resources/json.xjb</schema>
          </schemas>
        </manifest>
      </configuration>
    </execution>
  </executions>
</plugin>
```

Add the org.safris.xws:xjb dependency to the POM.

```
<dependency>
  <groupId>org.safris.xws</groupId>
  <artifactId>xjb</artifactId>
  <version>1.1.3</version>
</dependency>
```

Run mvn install.

## Integration with XRS

XJB can be used as the BodyReader and BodyWriter for JSON object marshalling and parsing in a JAX-RS 2.0 server. Please [see here](https://github.com/SevaSafris/xrs#getting-started-1) for an example of how to initiate XJB as a Provider for your JAX-RS 2.0 application.

## License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.
