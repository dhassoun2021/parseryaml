package com.david.yamlparser;

import java.util.ArrayList;
import java.util.List;

/**
 * Root element of structure tree
 */
public class EntityRoot implements NodeElement{

    private final List<Node> nodes = new ArrayList<>();

    public List<Node> getNodes() {
        return nodes;
    }

    public void addNode (Node node){
        nodes.add(node);
    }

    @Override
    public boolean isRoot() {
        return true;
    }
}
