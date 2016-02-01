package me.vilsol.blockly2java.objects;

import me.vilsol.blockly2java.annotations.BBlock;
import me.vilsol.blockly2java.annotations.BField;
import me.vilsol.blockly2java.annotations.BStatement;

import java.util.List;

@BBlock("dialogue_tree_base")
public class Dialogue {

    @BField("TREE_NAME")
    public String name;

    @BStatement("OPTIONS")
    public List<DialogueOption> options;

}
