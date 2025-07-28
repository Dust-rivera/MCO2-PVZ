package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.GameController;

public class MenuListener implements ActionListener {

    GameController controller;

    public MenuListener(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.lvlSelect(e);
    }

}