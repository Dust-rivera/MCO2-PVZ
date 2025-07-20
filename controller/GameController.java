package controller;

/**
 * Controller class that manages the interaction between the model and view
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
import model.*;
import view.*;


public class GameController implements GameViewListener {
    private final Board board;
    private final GameView view;
    private final User player;
    private String selectedPlant = "";

    public GameController() {
        this.player = new User();
        this.board = new Board(player, 5, 9);
        this.view = new GameView(5, 9, this);
        updateSunDisplay();
    }

    @Override
    public void onTileClicked(int row, int col) {
        if (selectedPlant.equals("Sunflower")) {
            if (player.getSunCount() >= 50 && !board.getTile(row, col).isPlantOccupied()) {
                board.placePlant(row, col, new Sunflower(row, col));
                view.updateTile(row, col, "S");
                updateSunDisplay();
                view.setMessage("Sunflower Planted at (" + (row + 1) + ", " + (col + 1) + ")");
            } else {
                view.setMessage("Can't place Sunflower here or not enough sun.");
            }
        } else if (selectedPlant.equals("Peashooter")) {
            if (player.getSunCount() >= 100 && !board.getTile(row, col).isPlantOccupied()) {
                board.placePlant(row, col, new Peashooter(row, col));
                view.updateTile(row, col, "P");
                updateSunDisplay();
                view.setMessage("Peashooter Planted at (" + (row + 1) + ", " + (col + 1) + ")");
            } else {
                view.setMessage("Can't place Peashooter here or not enough sun.");
            }
        } else if (selectedPlant.equals("CherryBomb")) {
            if (player.getSunCount() >= 150 && !board.getTile(row, col).isPlantOccupied()) {
                //CherryBomb bomb = new CherryBomb(row, col);
                //bomb.explode(board);
                player.buyPlant(150);
                updateSunDisplay();
                view.setMessage("CherryBomb exploded at (" + (row + 1) + ", " + (col + 1) + ")");
            } else {
                view.setMessage("Can't place CherryBomb or not enough sun.");
            }
        }
        selectedPlant = "";
    }

    @Override
    public void onPlantSelected(String plantType) {
        if (plantType.equals("_generateSun")) {
            board.generateSun();
            updateSunDisplay();
        } else {
            this.selectedPlant = plantType;
        }
    }

    @Override
    public void onStartGame() {
        board.update();
        player.collectSun(board.getSunCount(), board); // Collect all sun from board
        // Show feedback if a zombie was hit or killed
        String msg = board.getMessage();
        if (msg != null && (msg.contains("damaged") || msg.contains("dead"))) {
            view.setMessage(msg);
            view.clearMessageAfterDelay(1000);
        }
        updateSunDisplay();
        updateZombies();
        updatePlants();
        // Game end logic
        if (!board.getRunning()) {
            view.setMessage("GAME OVER! Zombies reached your house.");
            view.stopAllTimers();
        } else if (view.getTimeLeft() <= 0) {
            view.setMessage("GAME WON!!!!!");
            view.stopAllTimers();
        }
    }

    private void updateSunDisplay() {
        view.setSun(player.getSunCount());
    }

    private void updateZombies() {
        view.clearZombies();
        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getCol(); c++) {
                Tile tile = board.getTile(r, c);
                int zombieCount = tile.getZombies().size();
                if (zombieCount > 0) {
                    view.updateTile(r, c, "Z" + zombieCount);
                }
            }
        }
    }

    private void updatePlants() {
        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getCol(); c++) {
                Plant plant = board.getTile(r, c).getPlant();
                if (plant instanceof Sunflower) {
                    view.updateTile(r, c, "S");
                } else if (plant instanceof Peashooter) {
                    view.updateTile(r, c, "P");
                }
            }
        }
    }
}
