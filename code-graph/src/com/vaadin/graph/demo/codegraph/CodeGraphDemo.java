package com.vaadin.graph.demo.codegraph;

import com.vaadin.Application;
import com.vaadin.graph.DefaultGraphLoader;
import com.vaadin.graph.GraphExplorer;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class CodeGraphDemo extends Application {
    @Override
    public void init() {
        setMainWindow(new Window("Graph Explorer Neo4J demo"));

        GraphExplorer graph = new GraphExplorer(new DefaultGraphLoader(
                new CodeGraphProvider()));
        new Window("Graph Explorer Neo4J demo").addComponent(graph);

        VerticalLayout content = (VerticalLayout) new Window(
                "Graph Explorer Neo4J demo").getContent();
        content.setSizeFull();
        content.setExpandRatio(graph, 1);
    }
}
