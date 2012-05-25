package com.vaadin.graph.demo.neo4j;

import java.util.*;

import org.neo4j.graphdb.*;

final class Neo4JPropertyMap extends AbstractMap<String, Object> {
    private PropertyContainer container;

    Neo4JPropertyMap(PropertyContainer container) {
        this.container = container;
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return new AbstractSet<Map.Entry<String, Object>>() {

            @Override
            public Iterator<Map.Entry<String, Object>> iterator() {
                return new Iterator<Map.Entry<String, Object>>() {
                    final Iterator<String> keys = container.getPropertyKeys()
                            .iterator();

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
                                return container.getProperty(key, null);
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
                Iterator<String> keys = container.getPropertyKeys().iterator();
                while (keys.hasNext()) {
                    i++;
                    keys.next();
                }
                return i;
            }
        };
    }
}