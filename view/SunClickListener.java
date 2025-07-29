package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import controller.GameController;

public class SunClickListener implements MouseListener {

    JLabel sunLabel;
    GameController controller;

    public SunClickListener(JLabel sunLabel, GameController controller) {
        this.sunLabel = sunLabel;
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        controller.sunClick(sunLabel);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}