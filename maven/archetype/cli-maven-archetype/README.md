<img src="https://www.cohesionfirst.org/logo.png" align="right">

## cli-maven-archetype<br>![mvn-archetype][mvn-archetype] <a href="https://www.cohesionfirst.org/"><img src="https://img.shields.io/badge/CohesionFirst%E2%84%A2--blue.svg"></a>
> Quick-start Maven Archetype for lib4j-cli

### Introduction

The `cli-maven-archetype` archetype is a quick-start example of how to use the [`lib4jx-cli`][lib4jx-cli] library.

### Usage Overview

To use the archetype, execute the following:

  ```tcsh
  mvn archetype:generate \
  -DgroupId=com.mycompany.app -DartifactId=my-app \
  -DarchetypeGroupId=org.lib4jx.maven.archetype -DarchetypeArtifactId=cli-maven-archetype \
  -DarchetypeCatalog=http://mvn.repo.lib4j.org -DinteractiveMode=false
  ```

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[lib4jx-cli]: https://github.com/lib4jx/lib4jx-cli
[mvn-archetype]: https://img.shields.io/badge/mvn-archetype-yellow.svg