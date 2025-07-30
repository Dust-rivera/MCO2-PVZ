/** This represents the User
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 2.0
 */
package model;

public class User {

    private int sunCount = 50;

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
<<<<<<< HEAD
        if (amount > 0) {
         sunCount -= amount;   
        } 
=======
        if(amount > 0)
            sunCount -= amount;
>>>>>>> 962b2c9b4dcd2b794f0b0c46de3d90e96176928e
    }

    /**
     * This collects the sun from the game
     */
    public void collectSun() {
        // board.setSun();
        sunCount += 25;
    }
}