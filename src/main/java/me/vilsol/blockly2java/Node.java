package me.vilsol.blockly2java;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class Node {

    private final String name;
    private final HashMap<String, String> attributes;
    private final String value;
    private LinkedHashSet<Node> subnodes = new LinkedHashSet<>();

    public Node(String name, HashMap<String, String> attributes, String value) {
        this.name = name;
        this.attributes = attributes;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public String getValue() {
        return value;
    }

    public LinkedHashSet<Node> getSubnodes() {
        return subnodes;
    }

    public void addSubnode(Node node){
        subnodes.add(node);
    }

}
