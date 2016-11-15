<img src="http://safris.org/logo.png" align="right" />
# xsb-maven-plugin [![CohesionFirst](http://safris.org/cf2.svg)](https://cohesionfirst.com/)
> Maven Plugin for [XSB framework](https://github.com/SevaSafris/java/tree/master/cf/xsb)

## Introduction

The `xsb-maven-plugin` plugin is used to generate XML bindings with the [XSB framework](https://github.com/SevaSafris/java/tree/master/cf/xsb).

## Goals Overview

* [`xsb:generate`](https://github.com/SevaSafris/java/new/master/maven/plugin/xsb-maven-plugin#xsbgenerate) generates XSB bindings.

## Usage

### `xsb:generate`

The `xsb:generate` goal is bound to the `generate-sources` phase, and is used to generate XSB bindings for XSD documents in the `manifest`. To configure the generation of XSB bindings for desired XML Schemas, add a `manifest` element to the plugin's configuration.

#### Example 1

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

#### Example 2

Alternatively, an external `manifest.xml` can be specified:

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>xsb-maven-plugin</artifactId>
  <version>2.1.2</version>
  <configuration>
    <manifest xmlns="http://maven.safris.org/common/manifest.xsd" href="${basedir}/src/main/resources/manifest.xml"/>
  </configuration>
</plugin>
```

The `manifest` element can therefore be externally defined in `src/main/resources/manifest.xml`:

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

| Name                                 | Type          | Use      | Description                                                |
|:-------------------------------------|:--------------|:---------|:-----------------------------------------------------------|
| /`manifest`                          | Object        | Required | Manifest descriptor.                                       |
| /`manifest`/`destdir`                | String        | Required | Destination path of generated XSB bindings.                |
| /`manifest`/`destdir`/`@explodeJars` | Boolean       | Optional | Explode generated jars in the source-path of `destdir`. **Default:** `false`. |
| /`manifest`/`schemas`                | List          | Required | List of `schema` elements.                                 |
| /`manifest`/`schemas`/`schema`       | String        | Required | File path of XML Schema.                                   |
