package view;

import java.awt.event.MouseEvent;

import java.awt.event.MouseAdapter;

import controller.GameController;

public class ShopListener extends MouseAdapter {

    GameController controller;

    public ShopListener(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        controller.shopPressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        controller.shopReleased(e);
    }

    

    @Override // this is used when the user is dragging the plant, magpapakita ung image na dinadrag mo siya
    public void mouseDragged(MouseEvent e) {
        controller.imgDrag(e);
    }

}