package model;

/**
 * Represents the main board of the game
 * 
 * @author Deveza, Jerry King
 * @author Rivera, Dustine Gian
 * @version 5.1
 */
public class Board {

    private Tile[][] board;
    private boolean finalWaveFlag = false;
    private int numRows;
    private int numCols;
    private final int SUN_GENERATE_TIME = 10000;

    /**
     * This creates a board object given the player
     * 
     * @param row    the number of rows to be created
     * @param col    the number of columns to be created
     */
    public Board(int row, int col) {
        numRows = row;
        numCols = col;

        board = new Tile[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = new Tile();
            }
        }
    }

    /**
     * This creates a board object given the player
     * 
     * @return an integer containing the sun generation time
     */
    public int getSUN_GENERATE_TIME() {
        return SUN_GENERATE_TIME;
    }

    /**
     * This gets the state of the finaWaveFlag boolean
     * 
     * @return a boolean containing the state of the finalWaveFlag variable
     */
    public boolean getFinalWaveFlag() {
        return finalWaveFlag;
    }

    /**
     * This gets the number of rows of the board
     * 
     * @return an integer containing the number of rows board has
     */
    public int getRows() {
        return numRows;
    }

    /**
     * This gets the number of columns of the board
     * 
     * @return and integer containing amount of columns
     */
    public int getCol() {
        return numCols;
    }

    /**
     * This returns the specified tile given the row and column
     * 
     * @param row the row of the tile
     * @param col the column of the tile
     * @return a tile on the board
     */
    public Tile getTile(int row, int col) {
        return board[row][col];
    }

    /**
     * This gets the which row the tile given is on
     * 
     * @param target the tile to be row identified
     * @return an integer containing the row of targeted tile
     */
    public int getTileRow(Tile target) {
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                if (board[r][c] == target) {
                    return r;
                }
            }
        }
        return -1;
    }

    /**
     * This gets the which column the tile given is on
     * 
     * @param target the tile to be column identified
     * @return an integer containing the column of targeted tile
     */
    public int getTileCol(Tile target) {
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                if (board[r][c] == target) {
                    return c;
                }
            }
        }
        return -1;
    }

    /**
     * This gets the tile 2D array representing the board
     * @return a Tile 2D array representing the board
     */
    public Tile[][] getBoard() {
        return board;
    }

    /**
     * This sets the finalWaveFlag boolean to a boolean
     * @param finalWaveFlag a boolean to change the finalWaveFLag boolean
     */
    public void setFinalWaveFlag(boolean finalWaveFlag) {
        this.finalWaveFlag = finalWaveFlag;
    }

}