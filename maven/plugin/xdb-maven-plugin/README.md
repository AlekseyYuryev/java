<img src="http://safris.org/logo.png" align="right"/>
## xdb-maven-plugin<br>[![JavaCommons](https://img.shields.io/badge/mvn-plugin-lightgrey.svg)](https://cohesionfirst.com/) [![CohesionFirst](https://img.shields.io/badge/CohesionFirst%E2%84%A2--blue.svg)](https://cohesionfirst.com/)
> Maven Plugin for [XDB](https://github.com/SevaSafris/xdb) framework

### Introduction

The `xdb-maven-plugin` plugin is used to execute database-related generators, which are currently the [XDB](https://github.com/SevaSafris/xdb) framework.

### Goals Overview

* [`xdb:schema`](#xdbschema) generates DDL SQL.
* [`xdb:entities`](#xdbentities) generates XDE Entities.

### Usage

#### `xdb:schema`

The `xdb:schema` goal is bound to the `generate-resources` phase, and is used to generate DDL schema files from XML files conforming to the [XDS schema][xds-schema].

##### Example 1

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>xdb-maven-plugin</artifactId>
  <version>2.1.2</version>
  <executions>
    <execution>
      <id>default-schema</id>
      <goals>
        <goal>schema</goal>
      </goals>
      <configuration>
        <vendor>PostgreSQL</vendor>
        <manifest xmlns="http://maven.safris.org/common/manifest.xsd">
          <destdir>${project.build.directory}/generated-resources/xdb</destdir>
          <schemas>
            <schema>${basedir}/src/main/resources/schema.xds</schema>
          </schemas>
        </manifest>
      </configuration>
    </execution>
  </executions>
</plugin>
```

##### Example 2

Alternatively, an external `xds.xml` can be specified:

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>xdb-maven-plugin</artifactId>
  <version>2.1.2</version>
  <executions>
    <execution>
      <id>default-schema</id>
      <goals>
        <goal>schema</goal>
      </goals>
      <configuration>
        <manifest xmlns="http://maven.safris.org/common/manifest.xsd" href="${basedir}/src/main/resources/schema.xds"/>
      </configuration>
    </execution>
  </executions>
</plugin>
```

The `manifest` element can therefore be externally defined in `src/main/resources/schema.xds`:

```xml
<manifest
  xmlns="http://maven.safris.org/common/manifest.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.safris.org/common/manifest.xsd http://maven.safris.org/common/manifest.xsd">
  <destdir explodeJars="true">${project.build.directory}/generated-resources/xdb</destdir>
  <schemas>
    <schema>${basedir}/src/main/resources/schema.xds</schema>
  </schemas>
</manifest>
```

#### Configuration Parameters

| Name                             | Type    | Use      | Description                                                                   |
|:---------------------------------|:--------|:---------|:------------------------------------------------------------------------------|
| `/vendor`                        | String  | Required | Target vendor of generated DDL.                                               |
| `/manifest`                      | Object  | Required | Manifest descriptor.                                                          |
| `/manifest/@href`                | String  | Optional | External manifest reference pointer.                                          |
| `/manifest/destdir`              | String  | Required | Destination path of generated bindings.                                       |
| `/manifest/destdir/@explodeJars` | Boolean | Optional | Explode generated jars in the source-path of `destdir`. **Default:** `false`. |
| `/manifest/schemas`              | List    | Required | List of `schema` elements.                                                    |
| `/manifest/schemas/schema`       | String  | Required | File path of XML Schema.                                                      |

#### `xdb:entities`

The `xdb:entities` goal is bound to the `generate-sources` phase, and is used to generate XDE Entities from XML files conforming to the [XDS schema][xds-schema].

##### Example 1

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>xdb-maven-plugin</artifactId>
  <version>2.1.2</version>
  <executions>
    <execution>
      <id>xde</id>
      <goals>
        <goal>xde</goal>
      </goals>
      <configuration>
        <vendor>PostgreSQL</vendor>
        <manifest xmlns="http://maven.safris.org/common/manifest.xsd">
          <destdir>${project.build.directory}/generated-sources/xde</destdir>
          <schemas>
            <schema>${basedir}/src/main/resources/schema.xds</schema>
          </schemas>
        </manifest>
      </configuration>
    </execution>
  </executions>
</plugin>
```

##### Example 2

Alternatively, an external `xds.xml` can be specified:

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>xdb-maven-plugin</artifactId>
  <version>2.1.2</version>
  <executioins>
    <execution>
      <id>xde</id>
      <goals>
        <goal>xde</goal>
      </goals>
      <configuration>
        <manifest xmlns="http://maven.safris.org/common/manifest.xsd" href="${basedir}/src/main/resources/schema.xds"/>
      </configuration>
    </execution>
  </executioins>
</plugin>
```

The `manifest` element can therefore be externally defined in `src/main/resources/schema.xds`:

```xml
<manifest
  xmlns="http://maven.safris.org/common/manifest.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.safris.org/common/manifest.xsd http://maven.safris.org/common/manifest.xsd">
  <destdir explodeJars="true">${project.build.directory}/generated-sources/xde</destdir>
  <schemas>
    <schema>${basedir}/src/main/resources/schema.xds</schema>
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

[xds-schema]: https://github.com/SevaSafris/xdb/blob/master/schema/src/main/resources/xds.xsd