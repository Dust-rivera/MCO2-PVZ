package view;

import java.awt.event.MouseEvent;

// /**
//  * Listener interface for GameView to communicate with the controller
//  * @author Deveza, Jerry King 
//  * @author Rivera, Dustine Gian
//  * @version 2.0
//  */
// public interface GameViewListener {
//     /**
//      * Called when the start game button is clicked.
//      */
//     void onStartGame();

//     /**
//      * Called when the player selects a plant type (e.g., "Sunflower", "Peashooter").
//      * @param plantType The type of plant selected.
//      */
//     void onPlantSelected(String plantType);

//     /**
//      * Called when a tile is clicked in the grid.
//      * @param row The row of the clicked tile.
//      * @param col The column of the clicked tile.
//      */
//     void onTileClicked(int row, int col);

//     /**
//      * Called when the claim sun button is clicked.
//      */
//     void onClaimSun();
// }

import java.awt.event.MouseListener;


import javax.swing.JLabel;

import controller.GameController;

public class SunClickListener implements MouseListener{

    JLabel sunLabel;
    GameController controller;

    public SunClickListener(JLabel sunLabel, GameController controller){
        this.sunLabel = sunLabel;
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        controller.sunClick(sunLabel);
        // throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }

    

}