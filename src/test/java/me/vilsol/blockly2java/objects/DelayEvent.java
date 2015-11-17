package me.vilsol.blockly2java.objects;

import me.vilsol.blockly2java.annotations.BBlock;
import me.vilsol.blockly2java.annotations.BField;

@BBlock("event_delay")
public class DelayEvent extends Event {

    @BField("TIME")
    public int time;

}
