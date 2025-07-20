package view;

/**
 * Listener interface for GameView to communicate with the controller
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
public interface GameViewListener {
    /**
     * Called when the start game button is clicked.
     */
    void onStartGame();

    /**
     * Called when the player selects a plant type (e.g., "Sunflower", "Peashooter").
     * @param plantType The type of plant selected.
     */
    void onPlantSelected(String plantType);

    /**
     * Called when a tile is clicked in the grid.
     * @param row The row of the clicked tile.
     * @param col The column of the clicked tile.
     */
    void onTileClicked(int row, int col);
}
