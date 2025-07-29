/** Represents a Peashooter that inherits the Plant class
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 3.0
 */
package model;

public class Peashooter extends Plant {

    private static int COST = 100;

    /**
     * This creates a Peashooter object given its x and y position
     * 
     * @param x the x position of the peashooter
     * @param y the y position of the peashooter
     */
    public Peashooter(int x, int y) {
        super(100, 60, x, y, 6, 9, 20, 30, 7500);
    }

    /**
     * This gets peashooter's cost
     * @return an integer containing the cost of peashooter
     */
    public static int getCostStatic() {

        return COST;
    }

}
