package com.david.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Recursive structure to store arboresente datas
 */
public class Node implements NodeElement {

    private final String name;
    private Object value;
    private final List<Node> children = new ArrayList<>();
    private NodeElement parentNode;

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Node> getChildren() {
        return children;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void addNode(Node node) {
        children.add(node);
    }

    public NodeElement getParentNode() {
        return parentNode;
    }

    public void setParentNode(NodeElement parentNode) {
        this.parentNode = parentNode;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public boolean hasParent() {
        return parentNode != null;
    }

    public List<String> getPath() {
        List<String> fields = new ArrayList<>();
        fields.add(name);
        NodeElement parent = getParentNode();
        while (parent != null && parent instanceof Node) {
            Node nodeParent = (Node) parent;
            fields.add(nodeParent.getName());
            parent = nodeParent.getParentNode();
        }
        Collections.reverse(fields);
        return fields;
    }



}

