package view;
/**
 * Listener class for the shovel in the main game
 * 
 * @author Deveza, Jerry King
 * @author Rivera, Dustine Gian
 * @version 2.0
 */
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import controller.GameController;

public class ShovelListener extends MouseAdapter {

    private GameController controller;

    /**
     * Creates a Shovel Listener given the game controler
     * @param controller the game controller
     */
    public ShovelListener(GameController controller) {
        this.controller = controller;
    }

    @Override
    /**
     * This executes if mouse button is pressed
     */
    public void mousePressed(MouseEvent e) {
        controller.shovelPressed(e);
    }

    @Override
    /**
     * This executes if mouse button is released from being pressed
     */
    public void mouseReleased(MouseEvent e) {
        controller.shovelReleased(e);
    }

    @Override
    /**
     * This executes if mouse is being dragged while being pressed
     */
    public void mouseDragged(MouseEvent e) {
        controller.imgDrag(e);
    }

}