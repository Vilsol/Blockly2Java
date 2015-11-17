package me.vilsol.blockly2java.objects;

import me.vilsol.blockly2java.annotations.BBlock;
import me.vilsol.blockly2java.annotations.BField;

@BBlock("event_caption")
public class CaptionEvent extends Event {

    @BField("TITLE")
    public String title;

    @BField("SUBTITLE")
    public String subtitle;

}
