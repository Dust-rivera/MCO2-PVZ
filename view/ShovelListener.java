package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import controller.GameController;

public class ShovelListener extends MouseAdapter {

    GameController controller;

    public ShovelListener(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        controller.shovelPressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        controller.shovelReleased(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        controller.imgDrag(e);
    }

}