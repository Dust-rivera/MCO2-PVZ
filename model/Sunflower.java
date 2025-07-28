/** Represents a Sunflower that inherits the Plant class
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
package model;
public class Sunflower extends Plant{

    /**
     * This creates a Sunflower object given its x and y position
     * @param x the x position of the sunflower
     * @param y the y position of the sunflower
     */
    public Sunflower(int x, int y){
        super(50, 60, x, y, 24, 0, 0, 0, 7500); // 24 ticks = 24 seconds
    }

    /** This updates the Peashooter given the board
     * @param board the board to be updated
     */
    // @Override
    // public void update(Board board){
    //     this.increaseTick();;

    //     if(Plant.sunflowerCD != 0) 
    //         Plant.sunflowerCD--;
    //     if(this.getTick() % this.getSPEED() == 0){
    //         board.generateSun();
    //         this.setTick(0);
    //     }
    // }
    
    /**
     * This generates sun and adds to the sun count of the board
     * @param board the board to be updated
     */
    public void generateSun(Board board){
        board.generateSun();
    }

}
