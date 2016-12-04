<img src="https://www.cohesionfirst.org/logo.png" align="right"/>
## xsb-maven-plugin<br>![mvn-plugin][mvn-plugin] <a href="https://www.cohesionfirst.org/"><img src="https://img.shields.io/badge/CohesionFirst%E2%84%A2--blue.svg"></a>
> Maven Plugin for [XSB][xsb] framework

### Introduction

The `xsb-maven-plugin` plugin is used to generate XML bindings with the [XSB][xsb] framework.

### Goals Overview

* [`xsb:generate`](#xsbgenerate) generates XSB bindings.

### Usage

#### `xsb:generate`

The `xsb:generate` goal is bound to the `generate-sources` phase, and is used to generate XSB bindings for XSD documents in the `manifest`. To configure the generation of XSB bindings for desired XML Schemas, add a `manifest` element to the plugin's configuration.

##### Example 1

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>xsb-maven-plugin</artifactId>
  <version>2.1.2</version>
  <configuration>
    <manifest xmlns="http://maven.safris.org/common/manifest.xsd">
      <destdir explodeJars="true">${project.build.directory}/generated-sources/xsb</destdir>
      <schemas>
        <schema>${basedir}/src/main/resources/config.xsd</schema>
      </schemas>
    </manifest>
  </configuration>
</plugin>
```

##### Example 2

Alternatively, an external `xsb.xml` can be specified:

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>xsb-maven-plugin</artifactId>
  <version>2.1.2</version>
  <configuration>
    <manifest xmlns="http://maven.safris.org/common/manifest.xsd" href="${basedir}/src/main/resources/xsb.xml"/>
  </configuration>
</plugin>
```

The `manifest` element can therefore be externally defined in `src/main/resources/xsb.xml`:

```xml
<manifest
  xmlns="http://maven.safris.org/common/manifest.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.safris.org/common/manifest.xsd http://maven.safris.org/common/manifest.xsd">
  <destdir explodeJars="true">${project.build.directory}/generated-sources/xsb</destdir>
  <schemas>
    <schema>${basedir}/src/main/resources/config.xsd</schema>
  </schemas>
</manifest>
```

#### Configuration Parameters

| Name                             | Type    | Use      | Description                                                                   |
|:---------------------------------|:--------|:---------|:------------------------------------------------------------------------------|
| `/manifest`                      | Object  | Required | Manifest descriptor.                                                          |
| `/manifest/@href`                | String  | Optional | External manifest reference pointer.                                          |
| `/manifest/destdir`              | String  | Required | Destination path of generated bindings.                                       |
| `/manifest/destdir/@explodeJars` | Boolean | Optional | Explode generated jars in the source-path of `destdir`. **Default:** `false`. |
| `/manifest/schemas`              | List    | Required | List of `schema` elements.                                                    |
| `/manifest/schemas/schema`       | String  | Required | File path of XML Schema.                                                      |

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[mvn-plugin]: https://img.shields.io/badge/mvn-plugin-lightgrey.svg
[xsb]: https://github.com/SevaSafris/xsb