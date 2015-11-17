package me.vilsol.blockly2java;

import java.lang.reflect.Field;
import java.util.Map;

public class BlocklyBlock {

    private final Class<?> block;
    private final String name;
    private final Map<String, Field> fields;
    private final Map<String, Field> values;
    private final Map<String, Field> statements;

    protected BlocklyBlock(Class<?> block, String name, Map<String, Field> fields, Map<String, Field> values, Map<String, Field> statements){
        this.block = block;
        this.name = name;
        this.fields = fields;
        this.values = values;
        this.statements = statements;
    }

    protected Class<?> getBlock(){
        return block;
    }

    protected String getName(){
        return name;
    }

    protected Map<String, Field> getFields(){
        return fields;
    }

    protected Map<String, Field> getValues(){
        return values;
    }

    protected Map<String, Field> getStatements(){
        return statements;
    }

    protected Object newInstance(){
        try {
            return block.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
