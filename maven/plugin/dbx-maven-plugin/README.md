<img src="https://www.cohesionfirst.org/logo.png" align="right"/>
## dbx-maven-plugin<br>![mvn-plugin][mvn-plugin] <a href="https://www.cohesionfirst.org/"><img src="https://img.shields.io/badge/CohesionFirst%E2%84%A2--blue.svg"></a>
> Maven Plugin for [DBx][dbx] framework

### Introduction

The `dbx-maven-plugin` plugin is used to execute database-related generators, which are currently the [DBx][dbx] framework.

### Goals Overview

* [`dbx:ddl`](#dbxddl) generates DDL SQL.
* [`dbx:dml`](#dbxdml) generates DML SQL.
* [`dbx:jsql`](#dbxjsql) generates jSQL Entities.

### Usage

#### `dbx:ddl`

The `dbx:ddl` goal is bound to the `generate-resources` phase, and is used to generate DDL schema files from XML files conforming to the [DDLx Schema][ddlx-schema].

##### Example 1

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>dbx-maven-plugin</artifactId>
  <version>0.9.1</version>
  <executions>
    <execution>
      <id>default-ddl</id>
      <goals>
        <goal>ddl</goal>
      </goals>
      <configuration>
        <vendor>PostgreSQL</vendor>
        <manifest xmlns="http://maven.safris.org/common/manifest.xsd">
          <destdir>${project.build.directory}/generated-resources/dbx</destdir>
          <schemas>
            <schema>${basedir}/src/main/resources/schema.ddlx</schema>
          </schemas>
        </manifest>
      </configuration>
    </execution>
  </executions>
</plugin>
```

##### Example 2

Alternatively, an external `schema.ddlx` (an XML file) can be specified:

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>dbx-maven-plugin</artifactId>
  <version>0.9.1</version>
  <executions>
    <execution>
      <id>default-ddl</id>
      <goals>
        <goal>ddl</goal>
      </goals>
      <configuration>
        <manifest xmlns="http://maven.safris.org/common/manifest.xsd" href="${basedir}/src/main/resources/schema.ddlx"/>
      </configuration>
    </execution>
  </executions>
</plugin>
```

The `manifest` element can therefore be externally defined in `src/main/resources/schema.ddlx`:

```xml
<manifest
  xmlns="http://maven.safris.org/common/manifest.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.safris.org/common/manifest.xsd http://maven.safris.org/common/manifest.xsd">
  <destdir explodeJars="true">${project.build.directory}/generated-resources/dbx</destdir>
  <schemas>
    <schema>${basedir}/src/main/resources/schema.ddlx</schema>
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

#### `dbx:dml`

The `dbx:dml` goal is bound to the `generate-resources` phase, and is used to generate an XML Schema to allow one to create a validating DMLx file for static data.

##### Example 1

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>dbx-maven-plugin</artifactId>
  <version>0.9.1</version>
  <executions>
    <execution>
      <id>default-dml</id>
      <goals>
        <goal>dml</goal>
      </goals>
      <configuration>
        <vendor>PostgreSQL</vendor>
        <manifest xmlns="http://maven.safris.org/common/manifest.xsd">
          <destdir>${project.build.directory}/generated-resources/dbx</destdir>
          <schemas>
            <schema>${basedir}/src/main/resources/schema.ddlx</schema>
          </schemas>
        </manifest>
      </configuration>
    </execution>
  </executions>
</plugin>
```

##### Example 2

Alternatively, an external `schema.ddlx` (an XML file) can be specified:

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>dbx-maven-plugin</artifactId>
  <version>0.9.1</version>
  <executions>
    <execution>
      <id>default-dml</id>
      <goals>
        <goal>dml</goal>
      </goals>
      <configuration>
        <manifest xmlns="http://maven.safris.org/common/manifest.xsd" href="${basedir}/src/main/resources/schema.ddlx"/>
      </configuration>
    </execution>
  </executions>
</plugin>
```

The `manifest` element can therefore be externally defined in `src/main/resources/schema.ddlx`:

```xml
<manifest
  xmlns="http://maven.safris.org/common/manifest.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.safris.org/common/manifest.xsd http://maven.safris.org/common/manifest.xsd">
  <destdir explodeJars="true">${project.build.directory}/generated-resources/dbx</destdir>
  <schemas>
    <schema>${basedir}/src/main/resources/schema.ddlx</schema>
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

#### `dbx:jsql`

The `dbx:jsql` goal is bound to the `generate-sources` phase, and is used to generate XDE Entities from XML files conforming to the [DDLx Schema][ddlx-schema].

##### Example 1

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>dbx-maven-plugin</artifactId>
  <version>0.9.1</version>
  <executions>
    <execution>
      <id>xde</id>
      <goals>
        <goal>xde</goal>
      </goals>
      <configuration>
        <vendor>PostgreSQL</vendor>
        <manifest xmlns="http://maven.safris.org/common/manifest.xsd">
          <destdir>${project.build.directory}/generated-sources/dbx</destdir>
          <schemas>
            <schema>${basedir}/src/main/resources/schema.ddlx</schema>
          </schemas>
        </manifest>
      </configuration>
    </execution>
  </executions>
</plugin>
```

##### Example 2

Alternatively, an external `schema.ddlx` (an XML file) can be specified:

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>dbx-maven-plugin</artifactId>
  <version>0.9.1</version>
  <executioins>
    <execution>
      <id>xde</id>
      <goals>
        <goal>xde</goal>
      </goals>
      <configuration>
        <manifest xmlns="http://maven.safris.org/common/manifest.xsd" href="${basedir}/src/main/resources/schema.ddlx"/>
      </configuration>
    </execution>
  </executioins>
</plugin>
```

The `manifest` element can therefore be externally defined in `src/main/resources/schema.ddlx`:

```xml
<manifest
  xmlns="http://maven.safris.org/common/manifest.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.safris.org/common/manifest.xsd http://maven.safris.org/common/manifest.xsd">
  <destdir explodeJars="true">${project.build.directory}/generated-sources/dbx</destdir>
  <schemas>
    <schema>${basedir}/src/main/resources/schema.ddlx</schema>
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
[dbx]: https://github.com/SevaSafris/dbx
[ddlx-schema]: https://github.com/SevaSafris/dbx/blob/master/ddlx/src/main/resources/ddlx.xsd