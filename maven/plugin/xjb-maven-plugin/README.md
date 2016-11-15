<img src="http://safris.org/logo.png" align="right" />
# xjb-maven-plugin [![CohesionFirst](http://safris.org/cf2.svg)](https://cohesionfirst.com/)
> Maven Plugin for [XJB framework](https://github.com/SevaSafris/java/tree/master/cf/xjb)

## Introduction

The `xjb-maven-plugin` plugin is used to generate JSON bindings with the [XJB framework](https://github.com/SevaSafris/java/tree/master/cf/xjb).

## Goals Overview

* [`xjb:generate`](https://github.com/SevaSafris/java/new/master/maven/plugin/xjb-maven-plugin#xjbgenerate) generates XJB bindings.

## Usage

### `xjb:generate`

The `xjb:generate` goal is bound to the `generate-sources` phase, and is used to generate XJB bindings for XJB documents in the `manifest`. To configure the generation of XJB bindings for desired XJB schemas, add a `manifest` element to the plugin's configuration.

#### Example 1

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>xjb-maven-plugin</artifactId>
  <version>1.1.3</version>
  <configuration>
    <manifest xmlns="http://maven.safris.org/common/manifest.xsd">
      <destdir explodeJars="true">${project.build.directory}/generated-sources/xjb</destdir>
      <schemas>
        <schema>${basedir}/src/main/resources/json.xjb</schema>
      </schemas>
    </manifest>
  </configuration>
</plugin>
```

#### Example 2

Alternatively, an external `xjb.xml` can be specified:

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>xjb-maven-plugin</artifactId>
  <version>2.1.2</version>
  <configuration>
    <manifest xmlns="http://maven.safris.org/common/manifest.xsd" href="${basedir}/src/main/resources/xjb.xml"/>
  </configuration>
</plugin>
```

The `manifest` element can therefore be externally defined in `src/main/resources/xjb.xml`:

```xml
<manifest
  xmlns="http://maven.safris.org/common/manifest.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.safris.org/common/manifest.xsd http://maven.safris.org/common/manifest.xsd">
  <destdir explodeJars="true">${project.build.directory}/generated-sources/xjb</destdir>
  <schemas>
    <schema>${basedir}/src/main/resources/json.xjb</schema>
  </schemas>
</manifest>
```

#### Configuration Parameters

| Name                                 | Type          | Use      | Description                                                                   |
|:-------------------------------------|:--------------|:---------|:------------------------------------------------------------------------------|
| /`manifest`                          | Object        | Required | Manifest descriptor.                                                          |
| /`manifest`/`destdir`                | String        | Required | Destination path of generated bindings.                                       |
| /`manifest`/`destdir`/`@explodeJars` | Boolean       | Optional | Explode generated jars in the source-path of `destdir`. **Default:** `false`. |
| /`manifest`/`schemas`                | List          | Required | List of `schema` elements.                                                    |
| /`manifest`/`schemas`/`schema`       | String        | Required | File path of XML Schema.                                                      |
