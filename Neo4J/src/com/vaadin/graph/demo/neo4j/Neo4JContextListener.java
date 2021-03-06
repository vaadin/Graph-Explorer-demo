package com.vaadin.graph.demo.neo4j;

import java.io.*;

import javax.servlet.*;

import org.neo4j.kernel.EmbeddedGraphDatabase;

public class Neo4JContextListener implements ServletContextListener {
    private final String path;
    private EmbeddedGraphDatabase graphdb;

    public Neo4JContextListener() throws IOException {
        path = new File(System.getProperty("user.home"), "graphdb").getCanonicalPath();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        graphdb.shutdown();
        graphdb = null;
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        graphdb = new EmbeddedGraphDatabase(path);
        event.getServletContext().setAttribute(Neo4JDemo.GRAPHDB, graphdb);
    }
}
