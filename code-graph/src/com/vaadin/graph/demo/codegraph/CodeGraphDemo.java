package com.vaadin.graph.demo.codegraph;

import javax.servlet.ServletContext;

import com.vaadin.Application;
import com.vaadin.graph.DefaultGraphLoader;
import com.vaadin.graph.GraphExplorer;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class CodeGraphDemo extends Application {
    @Override
    public void init() {

        ServletContext servletContext = ((WebApplicationContext) getContext())
                .getHttpSession().getServletContext();
        servletContext.getAttribute("graph-data");

        VerticalLayout content = new VerticalLayout();
        setMainWindow(new Window("Graph Explorer Neo4J demo", content));

        GraphExplorer graph = new GraphExplorer(new DefaultGraphLoader(
                new CodeGraphProvider()));
        content.addComponent(graph);
        content.setExpandRatio(graph, 1);
        content.setSizeFull();
    }
}
