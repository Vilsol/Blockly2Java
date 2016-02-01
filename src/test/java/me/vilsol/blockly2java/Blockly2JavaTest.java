package me.vilsol.blockly2java;

import me.vilsol.blockly2java.objects.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class Blockly2JavaTest {

    private static String cutsceneInput = "<block type=\"cutscene_base\" id=\"1\" deletable=\"false\" movable=\"false\" x=\"0\" y=\"0\"><value name=\"DATA\"><block type=\"cutscene_data\" id=\"2\" inline=\"false\"><field name=\"NAME\">TestScene</field><field name=\"FADING\">TRUE</field></block></value><statement name=\"EVENTS\"><block type=\"event_delay\" id=\"6\"><field name=\"TIME\">20</field><next><block type=\"event_teleport\" id=\"11\"><field name=\"X\">1.1</field><field name=\"Y\">2.2</field><field name=\"Z\">3.3</field><field name=\"PITCH\">4.4</field><field name=\"YAW\">5.5</field><next><block type=\"event_smooth_move\" id=\"16\"><field name=\"X\">6.6</field><field name=\"Y\">7.7</field><field name=\"Z\">8.8</field><field name=\"PITCH\">9.9</field><field name=\"YAW\">10</field><next><block type=\"event_caption\" id=\"21\"><field name=\"TITLE\">Test Title</field><field name=\"SUBTITLE\">Test Subtitle</field></block></next></block></next></block></next></block></statement></block>";
    private static String dialogueInput = "<block type=\"dialogue_tree_base\" id=\"1\" x=\"154\" y=\"124\"><field name=\"TREE_NAME\">base</field><statement name=\"OPTIONS\"><block type=\"dialogue_tree_option\" id=\"2\"><field name=\"MESSAGE\">Hello</field><statement name=\"ACTIONS\"><block type=\"dialogue_tree_action_say\" id=\"3\"><field name=\"MESSAGE\">Hello</field><next><block type=\"dialogue_tree_action_open\" id=\"4\"><field name=\"TREE\">base</field></block></next></block></statement><next><block type=\"dialogue_tree_option\" id=\"5\"><field name=\"MESSAGE\">Who am I?</field><statement name=\"ACTIONS\"><block type=\"dialogue_tree_action_say\" id=\"6\"><field name=\"MESSAGE\">Yer' a wizard, Harry!</field><next><block type=\"dialogue_tree_action_open\" id=\"7\"><field name=\"TREE\">whoami</field></block></next></block></statement><next><block type=\"dialogue_tree_option\" id=\"8\"><field name=\"MESSAGE\">Bye</field><statement name=\"ACTIONS\"><block type=\"dialogue_tree_action_close\" id=\"9\"></block></statement></block></next></block></next></block></statement></block><block type=\"dialogue_tree_base\" id=\"21\" x=\"873\" y=\"174\"><field name=\"TREE_NAME\">testy</field><statement name=\"OPTIONS\"><block type=\"dialogue_tree_option\" id=\"27\"><field name=\"MESSAGE\">Hello</field><statement name=\"ACTIONS\"><block type=\"dialogue_tree_action_say\" id=\"39\"><field name=\"MESSAGE\">What</field></block></statement></block></statement></block><block type=\"dialogue_tree_base\" id=\"10\" x=\"520\" y=\"223\"><field name=\"TREE_NAME\">whoami</field><statement name=\"OPTIONS\"><block type=\"dialogue_tree_option\" id=\"11\"><field name=\"MESSAGE\">A wizard?!?</field><statement name=\"ACTIONS\"><block type=\"dialogue_tree_action_say\" id=\"12\"><field name=\"MESSAGE\">Yes! A wizard!</field><next><block type=\"dialogue_tree_action_open\" id=\"13\"><field name=\"TREE\">base</field></block></next></block></statement><next><block type=\"dialogue_tree_option\" id=\"14\"><field name=\"MESSAGE\">Oh, ok...</field><statement name=\"ACTIONS\"><block type=\"dialogue_tree_action_open\" id=\"15\"><field name=\"TREE\">base</field></block></statement></block></next></block></statement></block>";

    @Test
    public void testClass() throws Exception {
        Blockly2Java.registerClass(Cutscene.class);
        Blockly2Java.registerClass(Data.class);
        Blockly2Java.registerClass(CaptionEvent.class);
        Blockly2Java.registerClass(DelayEvent.class);
        Blockly2Java.registerClass(SmoothMoveEvent.class);
        Blockly2Java.registerClass(TeleportEvent.class);

        Cutscene cutscene = (Cutscene) Blockly2Java.parseBlockly(cutsceneInput).get(0);

        Assert.assertEquals(true, cutscene.data.fading);
        Assert.assertEquals("TestScene", cutscene.data.name);

        Assert.assertEquals(20, ((DelayEvent) cutscene.events.get(0)).time);

        Assert.assertEquals(1.1, ((TeleportEvent) cutscene.events.get(1)).x, 0);
        Assert.assertEquals(2.2, ((TeleportEvent) cutscene.events.get(1)).y, 0);
        Assert.assertEquals(3.3, ((TeleportEvent) cutscene.events.get(1)).z, 0);
        Assert.assertEquals(4.4, ((TeleportEvent) cutscene.events.get(1)).pitch, 0.01);
        Assert.assertEquals(5.5, ((TeleportEvent) cutscene.events.get(1)).yaw, 0.01);

        Assert.assertEquals(6.6, ((SmoothMoveEvent) cutscene.events.get(2)).x, 0);
        Assert.assertEquals(7.7, ((SmoothMoveEvent) cutscene.events.get(2)).y, 0);
        Assert.assertEquals(8.8, ((SmoothMoveEvent) cutscene.events.get(2)).z, 0);
        Assert.assertEquals(9.9, ((SmoothMoveEvent) cutscene.events.get(2)).pitch, 0.01);
        Assert.assertEquals(10 , ((SmoothMoveEvent) cutscene.events.get(2)).yaw, 0.01);

        Assert.assertEquals("Test Title", ((CaptionEvent) cutscene.events.get(3)).title);
        Assert.assertEquals("Test Subtitle", ((CaptionEvent) cutscene.events.get(3)).subtitle);

        Blockly2Java.registerClass(Dialogue.class);
        Blockly2Java.registerClass(DialogueClose.class);
        Blockly2Java.registerClass(DialogueOpenDialogue.class);
        Blockly2Java.registerClass(DialogueOption.class);
        Blockly2Java.registerClass(DialogueSayMessage.class);

        ArrayList<Object> dialogues = Blockly2Java.parseBlockly(dialogueInput);
        Assert.assertEquals("base", ((Dialogue) dialogues.get(0)).name);
        Assert.assertEquals("testy", ((Dialogue) dialogues.get(1)).name);
        Assert.assertEquals("whoami", ((Dialogue) dialogues.get(2)).name);
    }

}