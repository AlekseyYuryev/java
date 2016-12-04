<img src="https://www.cohesionfirst.org/logo.png" align="right" />
## dbcp-maven-archetype<br>![mvn-archetype][mvn-archetype] [![CohesionFirst™][cohesionfirst_badge]][cohesionfirst]
> Quick-start Maven Archetype for commons-dbcp

### Introduction

The `dbcp-maven-archetype` archetype is a quick-start example of how to use the [`commons-dbcp`][commons-dbcp] library.

### Usage Overview

To use the archetype, execute the following:

  ```tcsh
  mvn archetype:generate \
  -DgroupId=com.mycompany.app -DartifactId=my-app \
  -DarchetypeGroupId=org.safris.maven.archetype -DarchetypeArtifactId=dbcp-maven-archetype \
  -DarchetypeCatalog=http://mvn.repo.safris.org -DinteractiveMode=false
  ```

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[cohesionfirst]: https://www.cohesionfirst.com/
[cohesionfirst_badge]: https://img.shields.io/badge/CohesionFirst%E2%84%A2--blue.svg
[commons-dbcp]: https://github.com/SevaSafris/commons-dbcp
[mvn-archetype]: https://img.shields.io/badge/mvn-archetype-yellow.svg