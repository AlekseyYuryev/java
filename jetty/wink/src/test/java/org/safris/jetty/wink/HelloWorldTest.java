/* Copyright (c) 2016 Seva Safris
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

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
      basket.color("pink");
      basket.fruit(new HashSet<Fruit>());

      Fruit fruit = new Fruit();
      fruit.name("Apple");
      basket.fruit().add(fruit);

      fruit = new Fruit();
      fruit.name("Orange");
      basket.fruit().add(fruit);

      fruit = new Fruit();
      fruit.name("Banana");
      basket.fruit().add(fruit);

      return basket;
    }

    @GET
    public Basket getV2() {
      final Basket basket = new Basket();
      basket.color("pink");
      basket.fruit(new HashSet<Fruit>());

      Fruit fruit = new Fruit();
      fruit.name("Apple");
      basket.fruit().add(fruit);

      fruit = new Fruit();
      fruit.name("Orange");
      basket.fruit().add(fruit);

      fruit = new Fruit();
      fruit.name("Banana");
      basket.fruit().add(fruit);

      return basket;
    }

    @GET
    @Path("{id}")
    public Fruit get(@PathParam("id") final String id) {
      final Fruit fruit = new Fruit();
      fruit.name(id);
      return fruit;
    }

    @POST
    public Basket post(final Fruit fruit) {
      final Basket basket = new Basket();
      basket.color("pink");
      basket.fruit(new HashSet<Fruit>());
      basket.fruit().add(fruit);
      return basket;
    }
  }
}