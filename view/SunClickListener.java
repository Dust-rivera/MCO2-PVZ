package view;
/**
 * Listener class for sun dropped in the game
 * 
 * @author Deveza, Jerry King
 * @author Rivera, Dustine Gian
 * @version 2.0
 */
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import controller.GameController;

public class SunClickListener implements MouseListener {

    private JLabel sunLabel;
    private GameController controller;

    /**
     * Creates a SunClick Listener given the game controler and the sun's Jlabel
     * @param sunLabel the JLabel of the sun
     * @param controller the game controller
     */
    public SunClickListener(JLabel sunLabel, GameController controller) {
        this.sunLabel = sunLabel;
        this.controller = controller;
    }

    @Override
    /**
     * This executes if mouse button is clicked
     */
    public void mouseClicked(MouseEvent e) {
        controller.sunClick(sunLabel);
    }

    @Override
    /**
     * This executes if mouse button is pressed
     */
    public void mousePressed(MouseEvent e) {
    }

    @Override
    /**
     * This executes if mouse button is released from being pressed
     */
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    /**
     * This executes if mouse eneters within the JLabel
     */
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    /**
     * This executes if mouse eexits within the JLabel
     */
    public void mouseExited(MouseEvent e) {
    }

}