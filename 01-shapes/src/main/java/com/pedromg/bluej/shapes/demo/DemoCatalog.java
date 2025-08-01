package com.pedromg.bluej.shapes.demo;

import com.pedromg.bluej.shapes.preconditions.PreConditions;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DemoCatalog {

  private final Map<String, Demo> catalog;

  public DemoCatalog() {
    catalog = new LinkedHashMap<>();
  }

  public DemoCatalog register(String name, Demo demo) {
    PreConditions.requireNotBlank(name, "name must not be blank")
        .andNotNull(demo, "demo must not be null");

    catalog.put(name, demo);
    return this;
  }

  public Demo find(String demoName) {
    PreConditions.requireNotBlank(demoName, "demoName must not be blank")
        .and(catalog.containsKey(demoName), "demoName must be registered first");

    return catalog.get(demoName);
  }

  public Set<String> availableDemos() {
    return Collections.unmodifiableSet(catalog.keySet());
  }
}
