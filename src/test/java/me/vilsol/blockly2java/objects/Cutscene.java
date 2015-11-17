package me.vilsol.blockly2java.objects;

import me.vilsol.blockly2java.annotations.BBlock;
import me.vilsol.blockly2java.annotations.BStatement;
import me.vilsol.blockly2java.annotations.BValue;

import java.util.List;

@BBlock("cutscene_base")
public class Cutscene {

    @BValue("DATA")
    public Data data;

    @BStatement("EVENTS")
    public List<Event> events;

}
