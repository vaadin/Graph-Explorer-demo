package com.vaadin.graph.demo.neo4j;

import java.util.*;

import org.neo4j.graphdb.*;
import org.neo4j.kernel.*;

import com.vaadin.graph.*;

final class Neo4JRepository implements GraphRepository<Neo4JNode, Neo4JArc> {
    private final EmbeddedGraphDatabase inner;

    public Neo4JRepository(EmbeddedGraphDatabase graphdb) {
        inner = graphdb;
    }

    public Neo4JNode getVertexById(String id) {
        return new Neo4JNode(inner.getNodeById(Long.parseLong(id)));
    }

    public Neo4JNode getHomeVertex() {
        return new Neo4JNode(inner.getReferenceNode());
    }

    public Collection<String> getEdgeLabels() {
        Set<String> labels = new HashSet<String>();
        for (RelationshipType type : inner.getRelationshipTypes()) {
            labels.add(type.name());
        }
        return labels;
    }

    public Neo4JNode getSource(Neo4JArc edge) {
        return new Neo4JNode(edge.inner.getStartNode());
    }

    public Neo4JNode getDestination(Neo4JArc edge) {
        return new Neo4JNode(edge.inner.getEndNode());
    }

    public Neo4JNode getOpposite(Neo4JNode vertex, Neo4JArc edge) {
        return new Neo4JNode(edge.inner.getOtherNode(vertex.inner));
    }

    public Collection<Neo4JArc> getEdges(Neo4JNode node, final String label,
            ArcDirection dir) {
        final Iterable<Relationship> rels = node.inner.getRelationships(
                new RelationshipType() {

                    public String name() {
                        return label;
                    }
                }, Direction.valueOf(dir.toString()));

        return new AbstractCollection<Neo4JArc>() {

            @Override
            public Iterator<Neo4JArc> iterator() {
                return new Iterator<Neo4JArc>() {
                    Iterator<Relationship> iter = rels.iterator();

                    public boolean hasNext() {
                        return iter.hasNext();
                    }

                    public Neo4JArc next() {
                        return new Neo4JArc(Neo4JRepository.this, iter.next());
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
