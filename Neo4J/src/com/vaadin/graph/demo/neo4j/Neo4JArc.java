package com.vaadin.graph.demo.neo4j;

import java.util.*;

import com.vaadin.graph.*;

final class Neo4JArc implements Arc {
    final org.neo4j.graphdb.Relationship inner;
    private Neo4JRepository parent;

    public Neo4JArc(Neo4JRepository parent, org.neo4j.graphdb.Relationship inner) {
        this.parent = parent;
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

    public Node getOtherEnd(Node v) {
        return parent.getOpposite((Neo4JNode) v, this);
    }
}
