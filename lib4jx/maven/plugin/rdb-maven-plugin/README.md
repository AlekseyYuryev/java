<img src="https://www.cohesionfirst.org/logo.png" align="right">

## rdb-maven-plugin<br>![mvn-plugin][mvn-plugin] <a href="https://www.cohesionfirst.org/"><img src="https://img.shields.io/badge/CohesionFirst%E2%84%A2--blue.svg"></a>
> Maven Plugin for [RDB][rdb] framework

### Introduction

The `rdb-maven-plugin` plugin is used to execute database-related generators, which are currently the [RDB][rdb] framework.

### Goals Overview

* [`rdb:ddl`](#rdbddl) generates DDL SQL.
* [`rdb:dml`](#rdbdml) generates DML SQL.
* [`rdb:jsql`](#rdbjsql) generates jSQL Entities.

### Usage

#### `rdb:ddl`

The `rdb:ddl` goal is bound to the `generate-resources` phase, and is used to generate DDL schema files from XML files conforming to the [DDLx Schema][ddlx-schema].

##### Example

```xml
<plugin>
  <groupId>org.lib4jx.maven.plugin</groupId>
  <artifactId>rdb-maven-plugin</artifactId>
  <version>0.9.7</version>
  <executions>
    <execution>
      <id>default-ddl</id>
      <goals>
        <goal>ddl</goal>
      </goals>
      <configuration>
        <vendor>PostgreSQL</vendor>
        <manifest xmlns="http://maven.lib4j.org/common/manifest.xsd">
          <destdir>generated-resources/rdb</destdir>
          <resources>
            <resource>src/main/resources/resource.ddlx</resource>
          </resources>
        </manifest>
      </configuration>
    </execution>
  </executions>
</plugin>
```

#### Configuration Parameters

| Name                             | Type    | Use      | Description                                                                   |
|:---------------------------------|:--------|:---------|:------------------------------------------------------------------------------|
| `/vendor`                        | String  | Required | Target vendor of generated DDL.                                               |
| `/manifest`                      | Object  | Required | Manifest descriptor.                                                          |
| `/manifest/destdir`              | String  | Required | Destination path of generated bindings.                                       |
| `/manifest/destdir/@explodeJars` | Boolean | Optional | Explode generated jars in the source-path of `destdir`. **Default:** `false`. |
| `/manifest/resources`            | List    | Required | List of `resource` elements.                                                  |
| `/manifest/resources/resource`   | String  | Required | File path of XML Schema.                                                      |

#### `rdb:dml`

The `rdb:dml` goal is bound to the `generate-resources` phase, and is used to generate an XML Schema to allow one to create a validating DMLx file for static data.

##### Example

```xml
<plugin>
  <groupId>org.lib4jx.maven.plugin</groupId>
  <artifactId>rdb-maven-plugin</artifactId>
  <version>0.9.7</version>
  <executions>
    <execution>
      <id>default-dml</id>
      <goals>
        <goal>dml</goal>
      </goals>
      <configuration>
        <vendor>PostgreSQL</vendor>
        <manifest xmlns="http://maven.lib4j.org/common/manifest.xsd">
          <destdir>generated-resources/rdb</destdir>
          <resources>
            <resource>src/main/resources/schema.ddlx</resource>
          </resources>
        </manifest>
      </configuration>
    </execution>
  </executions>
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

#### `rdb:jsql`

The `rdb:jsql` goal is bound to the `generate-sources` phase, and is used to generate XDE Entities from XML files conforming to the [DDLx Schema][ddlx-schema].

##### Example

```xml
<plugin>
  <groupId>org.lib4jx.maven.plugin</groupId>
  <artifactId>rdb-maven-plugin</artifactId>
  <version>0.9.7</version>
  <executions>
    <execution>
      <id>xde</id>
      <goals>
        <goal>xde</goal>
      </goals>
      <configuration>
        <vendor>PostgreSQL</vendor>
        <manifest xmlns="http://maven.lib4j.org/common/manifest.xsd">
          <destdir>generated-sources/rdb</destdir>
          <resources>
            <resource>src/main/resources/schema.ddlx</resource>
          </resources>
        </manifest>
      </configuration>
    </execution>
  </executions>
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
[rdb]: https://github.com/lib4jx/rdb
[ddlx-schema]: https://github.com/lib4jx/rdb/blob/master/ddlx/src/main/resources/ddlx.xsd