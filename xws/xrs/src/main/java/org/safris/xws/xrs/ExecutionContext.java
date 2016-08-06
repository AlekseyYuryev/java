package org.safris.xws.xrs;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;

public class ExecutionContext {
  private final MultivaluedMap<String,ServiceManifest> registry;
  private final ContainerFilters containerFilters;
  private final EntityProviders entityProviders;

  public ExecutionContext(final MultivaluedMap<String,ServiceManifest> registry, final ContainerFilters containerFilters, final EntityProviders entityProviders) {
    this.registry = registry;
    this.containerFilters = containerFilters;
    this.entityProviders = entityProviders;
  }

  public ContainerFilters getContainerFilters() {
    return containerFilters;
  }

  public EntityProviders getEntityProviders() {
    return entityProviders;
  }

  public ServiceManifest filterAndMatch(final ContainerRequestContext requestContext) {
    final List<ServiceManifest> manifests = registry.get(requestContext.getMethod().toUpperCase());
    if (manifests == null)
      return null;

    for (final ServiceManifest manifest : manifests)
      if (manifest.matches(requestContext))
        return manifest;

    return null;
  }
}