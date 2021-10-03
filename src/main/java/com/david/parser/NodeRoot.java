package com.david.parser;

import java.util.ArrayList;
import java.util.List;

public class NodeRoot implements NodeElement{

    private final List<Node> nodes = new ArrayList<>();

    public List<Node> getNodes() {
        return nodes;
    }

    public void addNode (Node node){
        nodes.add(node);
    }
}
