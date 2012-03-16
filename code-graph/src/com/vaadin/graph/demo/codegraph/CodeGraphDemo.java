package com.vaadin.graph.demo.codegraph;

import com.vaadin.Application;
import com.vaadin.graph.DefaultGraphLoader;
import com.vaadin.graph.GraphExplorer;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class CodeGraphDemo extends Application {
    @Override
    public void init() {
        VerticalLayout content = new VerticalLayout();
        setMainWindow(new Window("Graph Explorer Neo4J demo", content));

        GraphExplorer graph = new GraphExplorer(new DefaultGraphLoader(
                new CodeGraphProvider()));
        content.addComponent(graph);
        content.setExpandRatio(graph, 1);
        content.setSizeFull();
    }
}
