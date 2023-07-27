package Input;

import Data.Click;

import java.awt.*;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener {
    // Fields
    private boolean isReady;
    private Click lastClick;

    // Constructor
    public Mouse() {
        this.isReady = false;
        this.lastClick = null;
    }

    public static Point getMouseCoords() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // We are using this
        lastClick = new Click(arg0.getX(), arg0.getY(), arg0.getButton());
        isReady = true;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public Click pollClick() {
        if(!isReady) {
            return null;
        }
        isReady = false;
        return lastClick;
    }

    public boolean isReady() {
        return isReady;
    }
}
