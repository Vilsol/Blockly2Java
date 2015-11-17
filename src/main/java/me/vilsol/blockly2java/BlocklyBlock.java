package me.vilsol.blockly2java;

import java.lang.reflect.Field;
import java.util.HashMap;

public class BlocklyBlock {

    private Class<?> block;
    private String name;
    private HashMap<String, Field> fields = new HashMap<>();
    private HashMap<String, Field> values = new HashMap<>();
    private HashMap<String, Field> statements = new HashMap<>();

    public BlocklyBlock(Class<?> block, String name, HashMap<String, Field> fields, HashMap<String, Field> values, HashMap<String, Field> statements){
        this.block = block;
        this.name = name;
        this.fields = fields;
        this.values = values;
        this.statements = statements;
    }

    public Class<?> getBlock(){
        return block;
    }

    public String getName(){
        return name;
    }

    public HashMap<String, Field> getFields(){
        return fields;
    }

    public HashMap<String, Field> getValues(){
        return values;
    }

    public HashMap<String, Field> getStatements(){
        return statements;
    }

    public Object newInstance(){
        try {
            return block.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
