<img src="https://www.cohesionfirst.org/logo.png" align="right">

## xsb-maven-plugin<br>![mvn-plugin][mvn-plugin] <a href="https://www.cohesionfirst.org/"><img src="https://img.shields.io/badge/CohesionFirst%E2%84%A2--blue.svg"></a>
> Maven Plugin for [XSB][xsb] framework

### Introduction

The `xsb-maven-plugin` plugin is used to generate XML bindings with the [XSB][xsb] framework.

### Goals Overview

* [`xsb:generate`](#xsbgenerate) generates XSB bindings.

### Usage

#### `xsb:generate`

The `xsb:generate` goal is bound to the `generate-sources` phase, and is used to generate XSB bindings for XSD documents in the `manifest`. To configure the generation of XSB bindings for desired XML Schemas, add a `manifest` element to the plugin's configuration.

##### Example

```xml
<plugin>
  <groupId>org.libx4j.maven.plugin</groupId>
  <artifactId>xsb-maven-plugin</artifactId>
  <version>2.1.2</version>
  <configuration>
    <manifest xmlns="http://maven.lib4j.org/common/manifest.xsd">
      <destdir explodeJars="true">generated-sources/xsb</destdir>
      <resources>
        <resource>src/main/resources/config.xsd</resource>
      </resources>
    </manifest>
  </configuration>
</plugin>
```

#### Configuration Parameters

| Name                             | Type    | Use      | Description                                                                   |
|:---------------------------------|:--------|:---------|:------------------------------------------------------------------------------|
| `/manifest`                      | Object  | Required | Manifest descriptor.                                                          |
| `/manifest/destdir`              | String  | Required | Destination path of generated bindings.                                       |
| `/manifest/destdir/@explodeJars` | Boolean | Optional | Explode generated jars in the source-path of `destdir`. **Default:** `false`. |
| `/manifest/resources`            | List    | Required | List of `resource` elements.                                                  |
| `/manifest/resources/resource`   | String  | Required | File path of XML Schema.                                                      |

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[mvn-plugin]: https://img.shields.io/badge/mvn-plugin-lightgrey.svg
[xsb]: https://github.com/libx4j/xsb