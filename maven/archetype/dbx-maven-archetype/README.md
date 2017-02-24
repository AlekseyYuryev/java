<img src="https://www.cohesionfirst.org/logo.png" align="right" />
## dbx-maven-archetype<br>![mvn-archetype][mvn-archetype] <a href="https://www.cohesionfirst.org/"><img src="https://img.shields.io/badge/CohesionFirst%E2%84%A2--blue.svg"></a>
> Quick-start Maven Archetype for DBx framework

### Introduction

The `dbx-maven-archetype` archetype is a quick-start example of how to use the [`dbx`][dbx] framework.

### Usage Overview

To use the archetype, execute the following:

  ```tcsh
  mvn archetype:generate \
  -DgroupId=com.mycompany.app -DartifactId=my-app \
  -DarchetypeGroupId=org.safris.maven.archetype -DarchetypeArtifactId=dbx-maven-archetype \
  -DarchetypeCatalog=http://mvn.repo.safris.org -DinteractiveMode=false
  ```

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[mvn-archetype]: https://img.shields.io/badge/mvn-archetype-yellow.svg
[dbx]: https://github.com/SevaSafris/dbx