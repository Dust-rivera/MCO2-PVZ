/** This represents the User
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
package model;
public class User {

    private int sunCount;
    //private String input;

    /** 
     * This creates a User object
     */
    public User(){
        sunCount = 50;
    }
    
    /**
     * This gets the user's sun count
     * @return an integer containing the user's sun count
     */
    public int getSunCount() {
        return sunCount;
    }

    /**
     * This gets the user's keyboard input
     * @return a String containing the user's keyboard input
     */
    // public String getInput(){
    //     return input;
    // }

    /**
     * This sets the input by the user's keyboard input
     * @param input the user's keyboard input
     */
    // public void setInput(String input){
    //     this.input = input;
    // }

    /**
     * This deduct the user's sun count by the amount/cost of plant
     * @param amount the cost of the plant to be bought
     */
    public void buyPlant(int amount){
        sunCount -= amount;
    }

    /**
     * This collects the sun from the board
     * @param amount the amount of sun board has
     * @param board the board to be updated
     */
    public void collectSun(Board board){
        //board.setSun();
        sunCount += 25;

    }
}