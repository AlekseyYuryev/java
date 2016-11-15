<img src="http://safris.org/logo.png" align="right" />
# xdb-maven-plugin [![CohesionFirst](http://safris.org/cf2.svg)](https://cohesionfirst.com/)
> Maven Plugin for [XDL](https://github.com/SevaSafris/java/tree/master/cf/xdl) and [XDE](https://github.com/SevaSafris/java/tree/master/cf/xde) frameworks

## Introduction

The `xdb-maven-plugin` plugin is used to execute database-related generators, which are currently the [XDL](https://github.com/SevaSafris/java/tree/master/cf/xdl) and [XDE](https://github.com/SevaSafris/java/tree/master/cf/xde) frameworks.

## Goals Overview

* [`xdb:xdl`](https://github.com/SevaSafris/java/tree/master/maven/plugin/xdb-maven-plugin#xdbxdl) generates DDL SQL.
* [`xdb:xde`](https://github.com/SevaSafris/java/tree/master/maven/plugin/xdb-maven-plugin#xdbxde) generates XDE Entities.

## Usage

### `xdb:xdl`

The `xdb:xdl` goal is bound to the `generate-resources` phase, and is used to generate DDL schema files from XML files conforming to the [XDL schema](http://cf.safris.org/xdl.xsd).

#### Example 1

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>xdb-maven-plugin</artifactId>
  <executions>
    <execution>
      <id>xdl</id>
      <configuration>
        <vendor>PostgreSQL</vendor>
        <manifest xmlns="http://maven.safris.org/common/manifest.xsd">
          <destdir>${project.build.directory}/generated-resources/xdl</destdir>
          <schemas>
            <schema>${basedir}/src/main/resources/schema.xdl</schema>
          </schemas>
        </manifest>
      </configuration>
    </execution>
  </executions>
</plugin>
```

#### Example 2

Alternatively, an external `xdl.xml` can be specified:

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>xdb-maven-plugin</artifactId>
  <version>2.1.2</version>
  <configuration>
    <manifest xmlns="http://maven.safris.org/common/manifest.xsd" href="${basedir}/src/main/resources/schema.xdl"/>
  </configuration>
</plugin>
```

The `manifest` element can therefore be externally defined in `src/main/resources/schema.xdl`:

```xml
<manifest
  xmlns="http://maven.safris.org/common/manifest.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.safris.org/common/manifest.xsd http://maven.safris.org/common/manifest.xsd">
  <destdir explodeJars="true">${project.build.directory}/generated-resources/xdl</destdir>
  <schemas>
    <schema>${basedir}/src/main/resources/schema.xdl</schema>
  </schemas>
</manifest>
```

#### Configuration Parameters

| Name                                 | Type          | Use      | Description                                                                   |
|:-------------------------------------|:--------------|:---------|:------------------------------------------------------------------------------|
| /`vendor  `                          | String        | Required | Target vendor of generated DDL.                                               |
| /`manifest`                          | Object        | Required | Manifest descriptor.                                                          |
| /`manifest`/`destdir`                | String        | Required | Destination path of generated bindings.                                       |
| /`manifest`/`destdir`/`@explodeJars` | Boolean       | Optional | Explode generated jars in the source-path of `destdir`. **Default:** `false`. |
| /`manifest`/`schemas`                | List          | Required | List of `schema` elements.                                                    |
| /`manifest`/`schemas`/`schema`       | String        | Required | File path of XML Schema.                                                      |

### `xdb:xde`

The `xdb:xde` goal is bound to the `generate-sources` phase, and is used to generate XDE Entities from XML files conforming to the [XDL schema](http://cf.safris.org/xdl.xsd).

#### Example 1

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>xdb-maven-plugin</artifactId>
  <executions>
    <execution>
      <id>xde</id>
      <configuration>
        <vendor>PostgreSQL</vendor>
        <manifest xmlns="http://maven.safris.org/common/manifest.xsd">
          <destdir>${project.build.directory}/generated-sources/xde</destdir>
          <schemas>
            <schema>${basedir}/src/main/resources/schema.xdl</schema>
          </schemas>
        </manifest>
      </configuration>
    </execution>
  </executions>
</plugin>
```

#### Example 2

Alternatively, an external `xdl.xml` can be specified:

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>xdb-maven-plugin</artifactId>
  <version>2.1.2</version>
  <configuration>
    <manifest xmlns="http://maven.safris.org/common/manifest.xsd" href="${basedir}/src/main/resources/schema.xdl"/>
  </configuration>
</plugin>
```

The `manifest` element can therefore be externally defined in `src/main/resources/schema.xdl`:

```xml
<manifest
  xmlns="http://maven.safris.org/common/manifest.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.safris.org/common/manifest.xsd http://maven.safris.org/common/manifest.xsd">
  <destdir explodeJars="true">${project.build.directory}/generated-sources/xde</destdir>
  <schemas>
    <schema>${basedir}/src/main/resources/schema.xdl</schema>
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
