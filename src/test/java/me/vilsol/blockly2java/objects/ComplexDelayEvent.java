package me.vilsol.blockly2java.objects;

import me.vilsol.blockly2java.annotations.BBlock;
import me.vilsol.blockly2java.annotations.BValue;

@BBlock("event_complex_delay")
public class ComplexDelayEvent extends Event {

	@BValue("START")
	public ComplexTime start;

	@BValue("END")
	public ComplexTime end;

}
