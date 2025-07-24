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

public class GameViewListener implements MouseListener{

    private JLabel sunflower, peashooter, cherry, sun;

    public GameViewListener(JLabel p1, JLabel p2, JLabel p3, JLabel sun){
        sunflower = p1;
        peashooter = p2;
        cherry = p3;
        this.sun = sun;
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

        Object source = e.getSource();

        if(source == sunflower){
            System.out.println("sunflower");
        }else if(source == peashooter){
            System.out.println("peashooter");
        }else if(source == cherry){
            System.out.println("cherry");
            sun.setText("25");
        }else if(source == sun){
            sun.setText("25");
        }

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