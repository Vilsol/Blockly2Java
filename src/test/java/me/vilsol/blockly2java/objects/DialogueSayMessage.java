package me.vilsol.blockly2java.objects;

import me.vilsol.blockly2java.annotations.BBlock;
import me.vilsol.blockly2java.annotations.BField;

@BBlock("dialogue_tree_action_say")
public class DialogueSayMessage {

    @BField("MESSAGE")
    public String message;

}
