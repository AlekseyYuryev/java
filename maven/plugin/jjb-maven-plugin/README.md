<img src="https://www.cohesionfirst.org/logo.png" align="right">

## jjb-maven-plugin<br>![mvn-plugin][mvn-plugin] <a href="https://www.cohesionfirst.org/"><img src="https://img.shields.io/badge/CohesionFirst%E2%84%A2--blue.svg"></a>
> Maven Plugin for [JJB][jjb] framework

### Introduction

The `jjb-maven-plugin` plugin is used to generate JSON bindings with the [JJB][jjb] framework.

### Goals Overview

* [`jjb:generate`](#jjbgenerate) generates JJB bindings.

### Usage

#### `jjb:generate`

The `jjb:generate` goal is bound to the `generate-sources` phase, and is used to generate JJB bindings for JJB documents in the `manifest`. To configure the generation of JJB bindings for desired JJB schemas, add a `manifest` element to the plugin's configuration.

##### Example 1

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>jjb-maven-plugin</artifactId>
  <version>0.9.6</version>
  <configuration>
    <manifest xmlns="http://maven.safris.org/common/manifest.xsd">
      <destdir explodeJars="true">${project.build.directory}/generated-sources/jjb</destdir>
      <resources>
        <resource>${basedir}/src/main/resources/json.jsonx</resource>
      </resources>
    </manifest>
  </configuration>
</plugin>
```

##### Example 2

Alternatively, an external `jsonx.xml` can be specified:

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>jjb-maven-plugin</artifactId>
  <version>0.9.6</version>
  <configuration>
    <manifest xmlns="http://maven.safris.org/common/manifest.xsd" href="${basedir}/src/main/resources/jsonx.xml"/>
  </configuration>
</plugin>
```

The `manifest` element can therefore be externally defined in `src/main/resources/jsonx.xml`:

```xml
<manifest
  xmlns="http://maven.safris.org/common/manifest.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.safris.org/common/manifest.xsd http://maven.safris.org/common/manifest.xsd">
  <destdir explodeJars="true">${project.build.directory}/generated-sources/jjb</destdir>
  <resources>
    <resource>${basedir}/src/main/resources/json.jsonx</resource>
  </resources>
</manifest>
```

#### Configuration Parameters

| Name                             | Type    | Use      | Description                                                                   |
|:---------------------------------|:--------|:---------|:------------------------------------------------------------------------------|
| `/manifest`                      | Object  | Required | Manifest descriptor.                                                          |
| `/manifest/@href`                | String  | Optional | External manifest reference pointer.                                          |
| `/manifest/destdir`              | String  | Required | Destination path of generated bindings.                                       |
| `/manifest/destdir/@explodeJars` | Boolean | Optional | Explode generated jars in the source-path of `destdir`. **Default:** `false`. |
| `/manifest/resources`            | List    | Required | List of `resource` elements.                                                  |
| `/manifest/resources/resource`   | String  | Required | File path of XML Schema.                                                      |

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[mvn-plugin]: https://img.shields.io/badge/mvn-plugin-lightgrey.svg
[jjb]: https://github.com/SevaSafris/jjb