<img src="https://www.cohesionfirst.org/logo.png" align="right"/>
## xml-maven-plugin<br>![mvn-plugin][mvn-plugin] <a href="https://www.cohesionfirst.org/"><img src="https://img.shields.io/badge/CohesionFirst%E2%84%A2--blue.svg"></a>
> Maven Plugin for XML-related goals

### Introduction

The `xml-maven-plugin` plugin is used for general XML-related goals.

### Goals Overview

* [`xml:validate`](#xmlvalidate) validates XML files.

### Usage

#### `xml:validate`

The `xml:validate` goal is bound to the `validate` phase, and is used to validate XML documents of types specified in the plugin's `configuration`.

##### Example 1

Execution with `includes` directive.

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>xml-maven-plugin</artifactId>
  <version>2.0.3</version>
  <configuration>
    <includes>
      <include>**/*.xds</include>
      <include>**/*.xjs</include>
      <include>**/*.xsd</include>
      <include>**/*.xml</include>
    </includes>
  </configuration>
</plugin>
```

##### Example 2

Execution with `includes` and `excludes` directives.

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>xml-maven-plugin</artifactId>
  <version>2.0.3</version>
  <configuration>
    <includes>
      <include>**/*.xds</include>
      <include>**/*.xjs</include>
      <include>**/*.xsd</include>
      <include>**/*.xml</include>
    </includes>
    <excludes>
      <exclude>**/willfail.xml</exclude>
    </excludes>
  </configuration>
</plugin>
```

#### Configuration Parameters

| Name                | Type    | Use      | Description                                               |
|:--------------------|:--------|:---------|:----------------------------------------------------------|
| `/skip`             | Boolean | Optional | Skip executioin. **Default:** `false`.                    |
| `/includes`         | Set     | Optional | Set of `include` directives. **Default:** `null`.         |
| `/includes/include` | String  | Optional | Fileset pattern of files to include. **Default:** `null`. |
| `/excludes`         | Set     | Optional | Set of `exclude` directives. **Default:** `null`.         |
| `/excludes/exclude` | String  | Optional | Fileset pattern of files to exclude. **Default:** `null`. |

#### Execution Options

1. Executing Maven in offline mode (`mvn -o`) will cause `xml:validate` to silently pass validation of XML files with remote `xsi:schemalocations`, thus avoiding remote calls.

### Known Issues

The `xml:validate` goal uses XML Schema v1 validation spec, which is obsoleted by v1.1. Work is underway to replace the XML parser and validation engine to support the v1.1 specification.

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[mvn-plugin]: https://img.shields.io/badge/mvn-plugin-lightgrey.svg