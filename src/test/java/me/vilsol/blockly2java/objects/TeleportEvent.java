package me.vilsol.blockly2java.objects;

import me.vilsol.blockly2java.annotations.BBlock;
import me.vilsol.blockly2java.annotations.BField;

@BBlock("event_teleport")
public class TeleportEvent extends Event {

    @BField("X")
    public double x;

    @BField("Y")
    public double y;

    @BField("Z")
    public double z;

    @BField("PITCH")
    public float pitch;

    @BField("YAW")
    public float yaw;

}
