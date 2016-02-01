package me.vilsol.blockly2java.objects;

import me.vilsol.blockly2java.annotations.BBlock;
import me.vilsol.blockly2java.annotations.BField;
import me.vilsol.blockly2java.annotations.BStatement;

import java.util.List;

@BBlock("dialogue_tree_option")
public class DialogueOption {

    @BField("MESSAGE")
    public String message;

    @BStatement("ACTIONS")
    public List<?> actions;

}
