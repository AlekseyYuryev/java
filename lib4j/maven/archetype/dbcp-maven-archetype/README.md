<img src="https://www.cohesionfirst.org/logo.png" align="right">

## dbcp-maven-archetype<br>![mvn-archetype][mvn-archetype] <a href="https://www.cohesionfirst.org/"><img src="https://img.shields.io/badge/CohesionFirst%E2%84%A2--blue.svg"></a>
> Quick-start Maven Archetype for lib4j-dbcp

### Introduction

The `dbcp-maven-archetype` archetype is a quick-start example of how to use the [`lib4j-dbcp`][lib4j-dbcp] library.

### Usage Overview

To use the archetype, execute the following:

  ```tcsh
  mvn archetype:generate \
  -DgroupId=com.mycompany.app -DartifactId=my-app \
  -DarchetypeGroupId=org.lib4j.maven.archetype -DarchetypeArtifactId=dbcp-maven-archetype \
  -DarchetypeCatalog=http://mvn.repo.lib4j.org -DinteractiveMode=false
  ```

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[lib4j-dbcp]: https://github.com/lib4j/lib4j-dbcp
[mvn-archetype]: https://img.shields.io/badge/mvn-archetype-yellow.svg