package model;

/**
 * Represents a Wallnut plant that tanks damage, inherits the Plant class
 * 
 * @author Deveza, Jerry King
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
public class Wallnut extends Plant {

    private static int COST = 50;

    /**
     * This creates a Wallnut object given its x and y position
     * 
     * @param x the x position of the sunflower
     * @param y the y position of the sunflower
     */
    public Wallnut(int x, int y) {
        super(50, 9999, x, y, 0, 1, 9999, 9999, 50000); // Massive damage, instant
    }

    /**
     * This gets peashooter's cost
     * @return an integer containing the cost of peashooter
     */
    public static int getCostStatic() {
        return COST;
    }

}