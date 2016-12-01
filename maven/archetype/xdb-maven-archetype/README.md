<img src="http://safris.org/logo.png" align="right" />
## xdb-maven-archetype<br>![mvn-archetype][mvn-archetype] [![CohesionFirst™][cohesionfirst_badge]][cohesionfirst]
> Quick-start Maven Archetype for XDB framework

### Introduction

The `xdb-maven-archetype` archetype is a quick-start example of how to use the [`xdb`][xdb] framework.

### Usage Overview

To use the archetype, execute the following:

  ```tcsh
  mvn archetype:generate \
  -DgroupId=com.mycompany.app -DartifactId=my-app \
  -DarchetypeGroupId=org.safris.maven.archetype -DarchetypeArtifactId=xdb-maven-archetype \
  -DarchetypeCatalog=http://mvn.repo.safris.org -DinteractiveMode=false
  ```

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[cohesionfirst]: https://www.cohesionfirst.com/
[cohesionfirst_badge]: https://img.shields.io/badge/CohesionFirst%E2%84%A2--blue.svg
[mvn-archetype]: https://img.shields.io/badge/mvn-archetype-yellow.svg
[xdb]: https://github.com/SevaSafris/xdb