<img src="http://safris.org/logo.png" align="right" />
## cli-maven-archetype<br>![mvn-archetype][mvn-archetype] [![CohesionFirst™][cohesionfirst_badge]][cohesionfirst]
> Quick-start Maven Archetype for commons-cli

### Introduction

The `cli-maven-archetype` archetype is a quick-start example of how to use the [`commons-cli`][commons-cli] library.

### Usage Overview

To use the archetype, execute the following:

  ```tcsh
  mvn archetype:generate \
  -DgroupId=com.mycompany.app -DartifactId=my-app \
  -DarchetypeGroupId=org.safris.maven.archetype -DarchetypeArtifactId=cli-maven-archetype \
  -DarchetypeCatalog=http://mvn.repo.safris.org -DinteractiveMode=false
  ```

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[cohesionfirst]: https://www.cohesionfirst.com/
[cohesionfirst_badge]: https://img.shields.io/badge/CohesionFirst%E2%84%A2--blue.svg
[commons-cli]: https://github.com/SevaSafris/commons-cli
[mvn-archetype]: https://img.shields.io/badge/mvn-archetype-yellow.svg