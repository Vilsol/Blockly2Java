package me.vilsol.blockly2java;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Node {

    private final String name;
    private final Map<String, String> attributes;
    private final String value;
    private final Set<Node> subnodes = new LinkedHashSet<>();

    protected Node(String name, Map<String, String> attributes, String value) {
        this.name = name;
        this.attributes = attributes;
        this.value = value;
    }

    protected String getName() {
        return name;
    }

    protected Map<String, String> getAttributes() {
        return attributes;
    }

    protected String getValue() {
        return value;
    }

    protected Set<Node> getSubnodes() {
        return subnodes;
    }

    protected void addSubnode(Node node){
        subnodes.add(node);
    }

}
