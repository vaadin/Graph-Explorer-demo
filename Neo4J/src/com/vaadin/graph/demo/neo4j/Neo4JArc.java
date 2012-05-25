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

    public Map<?, ?> getProperties() {
        return new AbstractMap<String, Object>() {

            @Override
            public Set<Map.Entry<String, Object>> entrySet() {
                return new AbstractSet<Map.Entry<String, Object>>() {

                    @Override
                    public Iterator<Map.Entry<String, Object>> iterator() {
                        return new Iterator<Map.Entry<String, Object>>() {
                            final Iterator<String> keys = inner
                                    .getPropertyKeys().iterator();

                            public boolean hasNext() {
                                return keys.hasNext();
                            }

                            public Map.Entry<String, Object> next() {
                                return new Map.Entry<String, Object>() {
                                    final String key = keys.next();

                                    public String getKey() {
                                        return key;
                                    }

                                    public Object getValue() {
                                        return inner.getProperty(key, null);
                                    }

                                    public Object setValue(Object value) {
                                        throw new UnsupportedOperationException();
                                    }
                                };
                            }

                            public void remove() {
                                throw new UnsupportedOperationException();
                            }
                        };
                    }

                    @Override
                    public int size() {
                        int i = 0;
                        Iterator<String> keys = inner.getPropertyKeys()
                                .iterator();
                        while (keys.hasNext()) {
                            i++;
                            keys.next();
                        }
                        return i;
                    }
                };
            }
        };
    }

    public Node getOtherEnd(Node v) {
        return parent.getOpposite((Neo4JNode) v, this);
    }
}
