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

    

    @Override // this is used when the user is dragging the plant, magpapakita ung image na dinadrag mo siya
    public void mouseDragged(MouseEvent e) {
        controller.imgDrag(e);
    }

}