<img src="http://safris.org/logo.png" align="right" />
# XRS [![CohesionFirst](http://safris.org/cf2.svg)](https://cohesionfirst.com/)
> jaX REST Server

## Getting Started

XRS is an implementation of the [JAX-RS v2.0 Specification](http://download.oracle.com/otn-pub/jcp/jaxrs-2_0_rev_A-mrel-eval-spec/jsr339-jaxrs-2.0-final-spec.pdf) that runs in a Servlet Container (such as [Jetty](http://www.eclipse.org/jetty/)). This project was inspired to create a better, simpler, easier to use, reliable, and debugable JAX-RS implementation using the CohesionFirst™ approach. Existing solutions such as [Jersey](https://jersey.java.net/), [JBoss RESTEasy](http://resteasy.jboss.org/), [Restlet](https://restlet.com/), [Apache CXF](http://cxf.apache.org/), [Apache Wink](https://wink.apache.org/) and others are challenging to work with, because they are bloated, buggy, and are not pure to the JAX-RS specification.

Many people experience unnecessary pains using existing JAX-RS implementations, specifically related to things such as debugging. Debugging of JAX-RS servers is difficult because of the high internal complexity of the implementation. A common pattern that is used in many OpenSource projects is dynamic method invocation. Dynamic method invocation is powerful, but it comes at a cost -- the cost is debugability. Dynamic method invocation results in stack traces that lack information of the specific execution path that led to the exception. Instead of clear mention of methods and line numbers, a dynamically invoked method call looks like "Method.invoke()" in the stack trace. After much pain and suffering, the idea of a pure JAX-RS server emerged, one that minimizes dynamic invocation, providing clear execution paths, and conforming to the JAX-RS specification. More often than not, JAX-RS implementation implement their own proprietary APIs, which thus couples you to the implementation and makes it unnecessarily difficult to migrate in the future. XRS is designed to be clear, cohesive, and 100% conforming to the JAX-RS v2.0 specification.

### Prerequisites

* [Maven](https://maven.apache.org/) - The dependency management system used to install XSR.
* [Servlet Container](https://en.wikipedia.org/wiki/Web_container) - A Servlet Container is needed to provide the HTTP service functionality. We recommend [Jetty](http://www.eclipse.org/jetty/) as the ideal starting point for any project.

### Installing

Currently, Maven is the only method by which one can install XRS. To install XRS:

Add the mvn.repo.safris.org Maven Repositories to the POM.

```
<repositories>
  <repository>
    <id>mvn.repo.safris.org</id>
    <url>http://mvn.repo.safris.org/m2</url>
  </repository>
</repositories>
<pluginRepositories>
  <pluginRepository>
    <id>mvn.repo.safris.org</id>
    <url>http://mvn.repo.safris.org/m2</url>
  </pluginRepository>
</pluginRepositories>
```

Add the org.safris.xws:xrs dependency to the POM.

```
<dependency>
  <groupId>org.safris.xws</groupId>
  <artifactId>xrs</artifactId>
  <version>2.0.1</version>
</dependency>
```

### Getting Started

Create a javax.ws.rs.core.Application.

```
@javax.ws.rs.ApplicationPath("/*")
public class Application extends javax.ws.rs.core.Application {
  @Override
  public java.util.Set<Object> getSingletons() {
    final java.util.Set<Object> singletons = new java.util.HashSet<Object>();
    singletons.add(new org.safris.xws.xjb.rs.JSObjectBodyReader()); // Optional BodyReader to parse JSON messages to beans.
    singletons.add(new org.safris.xws.xjb.rs.JSObjectBodyWriter()); // Optional BodyReader to marshal beans to JSON messages.
    return singletons;
  }
}
```

Extend org.safris.xws.xrs.DefaultRESTServlet, pointing to Application.

```
@WebServlet(initParams={@WebInitParam(name="javax.ws.rs.Application", value="Application")})
public class RESTServlet extends org.safris.xws.xrs.DefaultRESTServlet {
  private static final long serialVersionUID = -826395783690371140L;
}
```

Deploy the servlet to a Servlet Container, such as [Jetty](http://www.eclipse.org/jetty/). For a quick and easy solution see here.

## License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.
