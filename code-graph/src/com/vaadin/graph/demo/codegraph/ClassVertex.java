package com.vaadin.graph.demo.codegraph;

import java.util.HashSet;

import com.vaadin.graph.Vertex;

public class ClassVertex implements Vertex {

    private Class<?> cls;

    public ClassVertex(Class<?> cls) {
        this.cls = cls;
    }

    public String getId() {
        return cls.toString();
    }

    public Object getProperty(String key, Object defaultValue) {
        return null;
    }

    public Iterable<String> getPropertyKeys() {
        return new HashSet<String>();
    }

    public String getLabel() {
        return cls.toString();
    }
}
