package me.vilsol.blockly2java;

import me.vilsol.blockly2java.annotations.BBlock;
import me.vilsol.blockly2java.annotations.BField;
import me.vilsol.blockly2java.annotations.BStatement;
import me.vilsol.blockly2java.annotations.BValue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Blockly2Java {

    private static final Blockly2Java instance = new Blockly2Java();

    private final Map<String, BlocklyBlock> blocks = new HashMap<>();
    private final Pattern nodePattern = Pattern.compile("(<.*?>)([^<>]*)");
    private final Pattern attributePattern = Pattern.compile("([a-zA-Z0-9]+)=\"(.+?)\"");

    protected static Blockly2Java getInstance() {
        return instance;
    }

    /**
     * Register a class to be used as an object for converting blockly to java object
     *
     * @param block The class of the object
     */
    public static void registerClass(Class<?> block){
        BBlock b = block.getAnnotation(BBlock.class);
        if(b == null){
            throw new RuntimeException("Tried to register block (" + block.getName() + ") without @BBlock annotation");
        }

        Map<String, Field> blockFields = new HashMap<>();
        Map<String, Field> blockValues = new HashMap<>();
        Map<String, Field> blockStatements = new HashMap<>();

        Field[] fields = block.getDeclaredFields();
        for(Field field : fields){
            Annotation f;
            if((f = field.getAnnotation(BField.class)) != null){
                blockFields.put(((BField) f).value(), field);
            }else if((f = field.getAnnotation(BValue.class)) != null){
                if(field.getDeclaringClass().getAnnotation(BBlock.class) == null){
                    throw new RuntimeException("Class:" + block.getName() + " Should be a @BBlock class!");
                }
                blockValues.put(((BValue) f).value(), field);
            }else if((f = field.getAnnotation(BStatement.class)) != null){
                if(!field.getType().isAssignableFrom(List.class)){
                    throw new RuntimeException("Class:" + block.getName() + " Field:" + field.getName() + " Should be a list!");
                }
                blockStatements.put(((BStatement) f).value(), field);
            }

            if(f != null){
                field.setAccessible(true);
            }
        }

        getInstance().blocks.put(b.value(), new BlocklyBlock(block, b.value(), blockFields, blockValues, blockStatements));
    }

    /**
     * Parse blockly XML structure and convert into an object
     *
     * @param blockly Blockly XML structure
     * @return List of parent-most blocks
     */
    public static ArrayList<Object> parseBlockly(String blockly){
        Matcher m = getInstance().nodePattern.matcher(blockly);
        Stack<Node> nodes = new Stack<>();
        ArrayList<Object> blocks = new ArrayList<>();

        Node lastNode = null;
        int ignoreBlocks = 0;
        while(m.find()){
            if(!m.group(1).startsWith("</")) {
                Node node = getNode(m.group(1), m.group(2));
                if(node.getName().equals("mutation")){
                    continue;
                }

                if(node.getName().equals("next")){
                    nodes.pop();
                    lastNode = nodes.peek();
                    ignoreBlocks++;
                    continue;
                }

                if (lastNode != null) {
                    lastNode.addSubnode(node);
                }

                nodes.add(node);
                lastNode = node;
            }else{
                if(m.group(1).equals("</mutation>")){
                    continue;
                }

                if(nodes.size() > 0) {
                    if (nodes.peek().getName().equals("block") && ignoreBlocks > 0) {
                        ignoreBlocks--;
                    } else if (!m.group(1).contains("next")) {
                        nodes.pop();
                        if (nodes.size() > 0) {
                            lastNode = nodes.peek();
                        }
                    }
                }

                if(nodes.size() == 0){
                    if(m.group(1).equals("</block>")) {
                        blocks.add(parseBlock(lastNode));
                    }
                }
            }
        }

        return blocks;
    }

    private static Object parseBlock(Node node){
        BlocklyBlock baseBlock = getInstance().blocks.get(node.getAttributes().get("type"));
        if(baseBlock == null){
            throw new RuntimeException("No block with type '" + node.getAttributes().get("type") + "' registered!");
        }

        Object base = baseBlock.newInstance();
        fillBlock(baseBlock, base, node);
        return base;
    }

    private static void fillBlock(BlocklyBlock block, Object base, Node node){
        if(node.getSubnodes() == null || node.getSubnodes().size() == 0){
            return;
        }

        for(Node s : node.getSubnodes()){
            String fieldName = s.getAttributes().get("name");

            switch(s.getName()){
                case "field":
                    Field field = block.getFields().get(fieldName);

                    if(field == null){
                        throw new RuntimeException("Field '" + fieldName + "' not found in " + base.getClass().getName());
                    }

                    if(field.getType().equals(int.class) || field.getType().equals(Integer.class)){
                        setValue(base, fieldName, field, Integer.parseInt(s.getValue()));
                    }else if(field.getType().equals(double.class) || field.getType().equals(Double.class)) {
                        setValue(base, fieldName, field, Double.parseDouble(s.getValue()));
                    }else if(field.getType().equals(float.class) || field.getType().equals(Float.class)) {
                        setValue(base, fieldName, field, Float.parseFloat(s.getValue()));
                    }else if(field.getType().equals(long.class) || field.getType().equals(Long.class)) {
                        setValue(base, fieldName, field, Long.parseLong(s.getValue()));
                    }else if(field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {
                        setValue(base, fieldName, field, Boolean.parseBoolean(s.getValue()));
                    }else if(field.getType().isEnum()) {
                        setValue(base, fieldName, field, field.getType().getEnumConstants()[Integer.parseInt(s.getValue())]);
                    }else{
                        setValue(base, fieldName, field, s.getValue());
                    }
                    break;
                case "value":
                    setValue(base, fieldName, block.getValues().get(fieldName), parseBlock(s.getSubnodes().iterator().next()));
                    break;
                case "statement":
                    List<Object> objects = new ArrayList<>();
                    if(s.getSubnodes() != null && s.getSubnodes().size() > 0){
                        for(Node n : s.getSubnodes()){
                            objects.add(parseBlock(n));
                        }
                    }
                    setValue(base, fieldName, block.getStatements().get(fieldName), objects);
                    break;
            }
        }

        if(base instanceof BlocklyAfterParsed){
            ((BlocklyAfterParsed) base).onFinish();
        }
    }

    private static void setValue(Object object, String fieldName, Field field, Object value){
        try {
            field.set(object, value);
        } catch (Exception e) {
            if(field == null){
                throw new RuntimeException("Object '" + object.getClass().getName() + "' does not contain field '" + fieldName + "'");
            }else{
                throw new RuntimeException("Exception '" + e.getMessage() + "' whilst trying to set '" + fieldName + "' in object " + object.getClass().getName());
            }
        }
    }

    private static Node getNode(String node, String value){
        String name = node.split("\\s")[0].split(">")[0].substring(1);
        Matcher m = getInstance().attributePattern.matcher(node);
        Map<String, String> attributes = new HashMap<>();
        while(m.find()){
            attributes.put(m.group(1), m.group(2));
        }
        return new Node(name, attributes, value);
    }

}
