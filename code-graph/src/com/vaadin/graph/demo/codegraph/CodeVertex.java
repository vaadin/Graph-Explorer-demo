package com.vaadin.graph.demo.codegraph;

import java.util.HashSet;

import com.vaadin.graph.Vertex;

public class CodeVertex implements Vertex {

    private Class<?> cls;

    public CodeVertex(Class<?> cls) {
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
}
