package model;

/**
 * Represents a Cherry Bomb plant that explodes and damages zombies in an area
 * inherits the Plant class
 * @author Deveza, Jerry King
 * @author Rivera, Dustine Gian
 * @version 2.0
 */
public class CherryBomb extends Plant {

    private static int COST = 150;

    /**
     * This creates a cherrybomb plant given its x and y position
     * @param x the x coordinate relative to the grid board
     * @param y the y coordinate relative to the grid board
     */
    public CherryBomb(int x, int y) {
        super(150, 9999, x, y, 0, 1, 9999, 9999, 50000); // Massive damage, instant
    }

    /**
     * This gets cherrybomb's cost
     * @return an integer containing the cost of cherrybomb
     */
    public static int getCostStatic() {
        return COST;
    }
}