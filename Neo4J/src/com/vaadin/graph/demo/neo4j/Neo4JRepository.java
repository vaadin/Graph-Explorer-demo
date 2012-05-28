package com.vaadin.graph.demo.neo4j;

import java.util.*;

import org.neo4j.graphdb.*;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import com.vaadin.graph.*;

final class Neo4JRepository implements GraphRepository<Neo4JNode, Neo4JArc> {
    private final EmbeddedGraphDatabase graphdb;

    public Neo4JRepository(EmbeddedGraphDatabase graphdb) {
        this.graphdb = graphdb;
    }

    public Neo4JNode getNodeById(String id) {
        return new Neo4JNode(graphdb.getNodeById(Long.parseLong(id)));
    }

    public Neo4JNode getHomeNode() {
        return new Neo4JNode(graphdb.getReferenceNode());
    }

    public Collection<String> getArcLabels() {
        Set<String> labels = new HashSet<String>();
        for (RelationshipType type : graphdb.getRelationshipTypes()) {
            labels.add(type.name());
        }
        return labels;
    }

    public Neo4JNode getSource(Neo4JArc arc) {
        return new Neo4JNode(arc.inner.getStartNode());
    }

    public Neo4JNode getDestination(Neo4JArc arc) {
        return new Neo4JNode(arc.inner.getEndNode());
    }

    public Neo4JNode getOpposite(Neo4JNode vertex, Neo4JArc arc) {
        return new Neo4JNode(arc.inner.getOtherNode(vertex.inner));
    }

    public Collection<Neo4JArc> getArcs(Neo4JNode node, final String label,
            Arc.Direction dir) {
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
                        return new Neo4JArc(iter.next());
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
