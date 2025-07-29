package view;

/**
 * Listener class for all the buttons in the main menu
 * 
 * @author Deveza, Jerry King
 * @author Rivera, Dustine Gian
 * @version 5.0
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.GameController;

public class MenuListener implements ActionListener {

    private GameController controller;

    /**
     * Creates a Menu Listener given the game controler
     * @param controller the game controller
     */
    public MenuListener(GameController controller) {
        this.controller = controller;
    }

    @Override
    /**
     * This executes if button is pressed
     */
    public void actionPerformed(ActionEvent e) {
        controller.lvlSelect(e);
    }

}