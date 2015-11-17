package me.vilsol.blockly2java.objects;

import me.vilsol.blockly2java.annotations.BBlock;
import me.vilsol.blockly2java.annotations.BField;

@BBlock("cutscene_data")
public class Data {

    @BField("NAME")
    public String name;

    @BField("FADING")
    public boolean fading;

}
