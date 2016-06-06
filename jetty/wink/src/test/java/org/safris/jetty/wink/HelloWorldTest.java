package org.safris.jetty.wink;

import java.util.HashSet;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.safris.commons.net.Ports;
import org.safris.commons.servlet.xe.$se_realm;

import json.json.Basket;
import json.json.Fruit;

public class HelloWorldTest {
  public static void main(final String[] main) throws Exception {
    final HelloWorldServer server = new HelloWorldServer(Ports.findRandomOpenPort(), null, null, false, null);
    server.start();
    server.join();
  }

  public static class HelloWorldServer extends EmbeddedRESTServer {
    public HelloWorldServer(final int port, final String keyStorePath, final String keyStorePassword, final boolean externalResourcesAccess, final $se_realm realm) {
      super(port, keyStorePath, keyStorePassword, externalResourcesAccess, realm);
    }
  }

  @Path("/hello/world")
  @Produces({MediaType.APPLICATION_JSON, "application/v2+json"})
  @Consumes({MediaType.APPLICATION_JSON, "application/v2+json"})
  @PermitAll
  public static class HelloWorldProvider {
    @GET
    public Basket getV1() {
      final Basket basket = new Basket();
      basket.color.set("pink");
      basket.fruit.set(new HashSet<Fruit>());

      Fruit fruit = new Fruit();
      fruit.name.set("Apple");
      basket.fruit().add(fruit);

      fruit = new Fruit();
      fruit.name.set("Orange");
      basket.fruit().add(fruit);

      fruit = new Fruit();
      fruit.name.set("Banana");
      basket.fruit().add(fruit);

      return basket;
    }

    @GET
    public Basket getV2() {
      final Basket basket = new Basket();
      basket.color.set("pink");
      basket.fruit.set(new HashSet<Fruit>());

      Fruit fruit = new Fruit();
      fruit.name.set("Apple");
      basket.fruit().add(fruit);

      fruit = new Fruit();
      fruit.name.set("Orange");
      basket.fruit().add(fruit);

      fruit = new Fruit();
      fruit.name.set("Banana");
      basket.fruit().add(fruit);

      return basket;
    }

    @GET
    @Path("{id}")
    public Fruit get(@PathParam("id") final String id) {
      final Fruit fruit = new Fruit();
      fruit.name.set(id);
      return fruit;
    }

    @POST
    public Basket post(final Fruit fruit) {
      final Basket basket = new Basket();
      basket.color.set("pink");
      basket.fruit.set(new HashSet<Fruit>());
      basket.fruit().add(fruit);
      return basket;
    }
  }
}