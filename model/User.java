/** This represents the User
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 2.0
 */
package model;

public class User {

    private int sunCount;

    /**
     * This creates a User object
     */
    public User() {
        sunCount = 500;
    }

    /**
     * This gets the user's sun count
     * 
     * @return an integer containing the user's sun count
     */
    public int getSunCount() {
        return sunCount;
    }

    /**
     * This deduct the user's sun count by the amount/cost of plant
     * 
     * @param amount the cost of the plant to be bought
     */
    public void buyPlant(int amount) {
        sunCount -= amount;
    }

    /**
     * This collects the sun from the board
     * 
     * @param board  the board to be updated
     */
    public void collectSun(Board board) {
        // board.setSun();
        sunCount += 25;
    }
}