package me.vilsol.blockly2java.objects;

import me.vilsol.blockly2java.annotations.BBlock;
import me.vilsol.blockly2java.annotations.BField;

@BBlock("complex_time")
public class ComplexTime {

	@BField("MINUTES")
	public int minutes;

	@BField("SECONDS")
	public int seconds;

}
