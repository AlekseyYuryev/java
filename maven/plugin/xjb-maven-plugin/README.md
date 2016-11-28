<img src="http://safris.org/logo.png" align="right"/>
## xjb-maven-plugin<br>[![JavaCommons](https://img.shields.io/badge/mvn-plugin-lightgrey.svg)](https://cohesionfirst.com/) [![CohesionFirst](https://img.shields.io/badge/CohesionFirst%E2%84%A2--blue.svg)](https://cohesionfirst.com/)
> Maven Plugin for [XJB](https://github.com/SevaSafris/xjb) framework

### Introduction

The `xjb-maven-plugin` plugin is used to generate JSON bindings with the [XJB](https://github.com/SevaSafris/java/tree/master/xjb) framework.

### Goals Overview

* [`xjb:generate`](#xjbgenerate) generates XJB bindings.

### Usage

#### `xjb:generate`

The `xjb:generate` goal is bound to the `generate-sources` phase, and is used to generate XJB bindings for XJB documents in the `manifest`. To configure the generation of XJB bindings for desired XJB schemas, add a `manifest` element to the plugin's configuration.

##### Example 1

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>xjb-maven-plugin</artifactId>
  <version>1.1.3</version>
  <configuration>
    <manifest xmlns="http://maven.safris.org/common/manifest.xsd">
      <destdir explodeJars="true">${project.build.directory}/generated-sources/xjb</destdir>
      <schemas>
        <schema>${basedir}/src/main/resources/json.xjs</schema>
      </schemas>
    </manifest>
  </configuration>
</plugin>
```

##### Example 2

Alternatively, an external `xjs.xml` can be specified:

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>xjb-maven-plugin</artifactId>
  <version>1.1.3</version>
  <configuration>
    <manifest xmlns="http://maven.safris.org/common/manifest.xsd" href="${basedir}/src/main/resources/xjs.xml"/>
  </configuration>
</plugin>
```

The `manifest` element can therefore be externally defined in `src/main/resources/xjs.xml`:

```xml
<manifest
  xmlns="http://maven.safris.org/common/manifest.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.safris.org/common/manifest.xsd http://maven.safris.org/common/manifest.xsd">
  <destdir explodeJars="true">${project.build.directory}/generated-sources/xjb</destdir>
  <schemas>
    <schema>${basedir}/src/main/resources/json.xjs</schema>
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