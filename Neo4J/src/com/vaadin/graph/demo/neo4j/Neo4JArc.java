package com.vaadin.graph.demo.neo4j;

import java.util.*;

import com.vaadin.graph.*;

final class Neo4JArc implements Arc {
    final org.neo4j.graphdb.Relationship inner;

    public Neo4JArc(org.neo4j.graphdb.Relationship inner) {
        this.inner = inner;
    }

    public String getId() {
        return "" + inner.getId();
    }

    public String getLabel() {
        return inner.getType().name();
    }

    public Map<String, Object> getProperties() {
        return new Neo4JPropertyMap(inner);
    }
}
