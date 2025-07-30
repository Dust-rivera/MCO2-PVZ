/** Represents a Sunflower that inherits the Plant class
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
package model;

public class Sunflower extends Plant {

    private static int COST = 50;

    /**
     * This creates a Sunflower object given its x and y position
     * 
     * @param x the x position of the sunflower
     * @param y the y position of the sunflower
     */
    public Sunflower(int x, int y) {
        super(50, 150, x, y, 0, 0, 0, 0, 7500); // 24 ticks = 24 seconds
    }

    /**
     * This gets peashooter's cost
     * @return an integer containing the cost of peashooter
     */
    public static int getCostStatic() {
        return COST;
    }

}
