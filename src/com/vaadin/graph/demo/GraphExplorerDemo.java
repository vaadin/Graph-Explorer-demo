/*
 * Copyright 2011 Vaadin Ltd.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/agpl-3.0.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.graph.demo;

import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import com.vaadin.Application;
import com.vaadin.graph.DefaultGraphLoader;
import com.vaadin.graph.Edge;
import com.vaadin.graph.EdgeDirection;
import com.vaadin.graph.GraphExplorer;
import com.vaadin.graph.GraphProvider;
import com.vaadin.graph.Vertex;
import com.vaadin.ui.UriFragmentUtility;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class GraphExplorerDemo extends Application {
    private Window window;
    private final GraphProvider graphProvider = new MyGraphProvider(
            "/Users/marlon/graphdb");

    @Override
    public void init() {
        window = new Window("Graph Explorer demo");
        setMainWindow(window);

        GraphExplorer graph = new GraphExplorer(new DefaultGraphLoader(
                graphProvider));
        window.addComponent(graph);

        final UriFragmentUtility urifu = new UriFragmentUtility();
        window.addComponent(urifu);

        window.getContent().setSizeFull();
        VerticalLayout content = (VerticalLayout) window.getContent();
        content.setExpandRatio(graph, 1);
        content.setExpandRatio(urifu, 0);
    }

    private static final class MyGraphProvider implements GraphProvider {
        private final EmbeddedGraphDatabase inner;

        public MyGraphProvider(String dbDir) {
            inner = new EmbeddedGraphDatabase(dbDir);
        }

        public MyVertex getVertexById(String id) {
            return new MyVertex(inner.getNodeById(Long.parseLong(id)));
        }

        public MyVertex getHomeVertex() {
            return new MyVertex(inner.getReferenceNode());
        }

        public Collection<String> getEdgeLabels() {
            Set<String> labels = new HashSet<String>();
            for (RelationshipType type : inner.getRelationshipTypes()) {
                labels.add(type.name());
            }
            return labels;
        }

        public void shutdown() {
            inner.shutdown();
        }

        public MyVertex getSource(Edge edge) {
            return new MyVertex(((MyEdge) edge).inner.getStartNode());
        }

        public MyVertex getDestination(Edge edge) {
            return new MyVertex(((MyEdge) edge).inner.getEndNode());
        }

        public MyVertex getOpposite(Vertex vertex, Edge edge) {
            return new MyVertex(
                    ((MyEdge) edge).inner
                            .getOtherNode(((MyVertex) vertex).inner));
        }

        public Collection<Edge> getEdges(Vertex node, final String label,
                EdgeDirection dir) {
            final Iterable<Relationship> rels = ((MyVertex) node).inner
                    .getRelationships(new RelationshipType() {

                        public String name() {
                            return label;
                        }
                    }, Direction.valueOf(dir.toString()));

            return new AbstractCollection<Edge>() {

                @Override
                public Iterator<Edge> iterator() {
                    return new Iterator<Edge>() {
                        Iterator<Relationship> iter = rels.iterator();

                        public boolean hasNext() {
                            return iter.hasNext();
                        }

                        public MyEdge next() {
                            return new MyEdge(iter.next());
                        }

                        public void remove() {
                            throw new UnsupportedOperationException();
                        }
                    };
                }

                @Override
                public int size() {
                    Iterator<Relationship> iter = rels.iterator();
                    int i = 0;
                    while (iter.hasNext()) {
                        i++;
                        iter.next();
                    }
                    return i;
                }
            };
        }
    }

    private static final class MyVertex implements Vertex {
        private final Node inner;

        public MyVertex(Node inner) {
            this.inner = inner;
        }

        public Iterable<String> getPropertyKeys() {
            return inner.getPropertyKeys();
        }

        public Object getProperty(String key, Object defaultValue) {
            return inner.getProperty(key, defaultValue);
        }

        public String getId() {
            return "" + inner.getId();
        }
    }

    private static final class MyEdge implements Edge {
        private final Relationship inner;

        public MyEdge(Relationship inner) {
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
    }
}
