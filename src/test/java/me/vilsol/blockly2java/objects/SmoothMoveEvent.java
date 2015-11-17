package me.vilsol.blockly2java.objects;

import me.vilsol.blockly2java.annotations.BBlock;
import me.vilsol.blockly2java.annotations.BField;

@BBlock("event_smooth_move")
public class SmoothMoveEvent extends Event {

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
