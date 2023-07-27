package Command;

import Data.RECT;
import Data.Frame;
import logic.Control;
import Main.Main;
import java.awt.Color;
import java.util.ArrayList;
public class Interpreter {

    public static ArrayList<RECT> rects;

    public static void runScript(ArrayList<Command> commands, Control ctrl) {
        rects = new ArrayList<>();
        for(Command c: commands) {
            if(c.isCommand("show_sprite") && c.getNumParms() == 3) {
                int x = Integer.parseInt(c.getParmByIndex(0));
                int y = Integer.parseInt(c.getParmByIndex(1));
                String tag = c.getParmByIndex(2);
                ctrl.addSpriteToFrontBuffer(x, y, tag);
            } else if(c.isCommand("show_background") && c.getNumParms() == 1) {
                ctrl.addSpriteToFrontBuffer(0, 0, c.getParmByIndex(0));
            } else if(c.isCommand("text") && c.getNumParms() == 1) {
                String display = c.getParmByIndex(0);
                ctrl.drawString(0,250, display, Color.WHITE);
            } else if(c.isCommand("RECT") && c.getNumParms() == 6) {
                rects.add(new RECT(Integer.parseInt(c.getParmByIndex(0)), Integer.parseInt(c.getParmByIndex(1)),
                        Integer.parseInt(c.getParmByIndex(2)), Integer.parseInt(c.getParmByIndex(3)),
                        c.getParmByIndex(4), c.getParmByIndex(5)));
            } else if(c.isCommand("anim") && c.getNumParms() == 1) {
                Frame curFrame = Main.anim.getCurrentFrame();
                if(curFrame != null) {
                    ctrl.addSpriteToFrontBuffer(curFrame.getX(), curFrame.getY(), curFrame.getSpriteTag());
                }
            }
        }
    }
}
