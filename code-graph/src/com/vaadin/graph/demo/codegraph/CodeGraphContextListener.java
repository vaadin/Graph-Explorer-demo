package com.vaadin.graph.demo.codegraph;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CodeGraphContextListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent event) {
    }

    public void contextInitialized(ServletContextEvent event) {
        // TODO: initialize the data
        event.getServletContext().setAttribute(CodeGraphDemo.CODE_GRAPH_DATA,
                null);
    }
}
