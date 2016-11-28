<img src="http://safris.org/logo.png" align="right" />
# dbcp-maven-archetype<br>[![JavaCommons](https://img.shields.io/badge/mvn-archetype-yellow.svg)](https://cohesionfirst.com/) [![CohesionFirst](https://img.shields.io/badge/CohesionFirst%E2%84%A2--blue.svg)](https://cohesionfirst.com/)
> Quick-start Maven Archetype for commons-dbcp

## Introduction

The `dbcp-maven-archetype` archetype is a quick-start example of how to use the [`commons-dbcp`](https://github.com/SevaSafris/commons-dbcp) library.

## Usage Overview

To use the archetype, execute the following:

  ```tcsh
  mvn archetype:generate \
  -DgroupId=com.mycompany.app -DartifactId=my-app \
  -DarchetypeGroupId=org.safris.maven.archetype -DarchetypeArtifactId=dbcp-maven-archetype \
  -DarchetypeCatalog=mvn.repo.safris.org -DinteractiveMode=false
  ```

## License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.