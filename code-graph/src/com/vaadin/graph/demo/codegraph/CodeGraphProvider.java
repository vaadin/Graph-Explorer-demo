package com.vaadin.graph.demo.codegraph;

import java.util.Collection;

import com.vaadin.graph.Edge;
import com.vaadin.graph.EdgeDirection;
import com.vaadin.graph.GraphProvider;
import com.vaadin.graph.Vertex;
import com.vaadin.ui.Component;

public class CodeGraphProvider implements GraphProvider {

    public Vertex getHomeVertex() {
        return new CodeVertex(Component.class);
    }

    public Vertex getDestination(Edge edge) {
        // TODO Auto-generated method stub
        return null;
    }

    public Iterable<String> getEdgeLabels() {
        // TODO Auto-generated method stub
        return null;
    }

    public Collection<Edge> getEdges(Vertex vertex, String label,
            EdgeDirection dir) {
        // TODO Auto-generated method stub
        return null;
    }

    public Vertex getOpposite(Vertex vertex, Edge edge) {
        // TODO Auto-generated method stub
        return null;
    }

    public Vertex getSource(Edge edge) {
        // TODO Auto-generated method stub
        return null;
    }

    public Vertex getVertexById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    public void shutdown() {
        // TODO Auto-generated method stub

    }

}
