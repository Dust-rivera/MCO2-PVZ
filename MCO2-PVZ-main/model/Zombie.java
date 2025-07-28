/** Represents a Zombie
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 2.0
 */
package model;
import java.util.Random;
public abstract class Zombie {

    protected int xPosition;
    protected int yPosition;
    protected int health ;
    protected int damage;
    protected int speed;
    

    /**
     * Creates Zombie object given its y position
     * @param yPosition the y position of the zombie
     */
    public Zombie(int health, int damage, int speed) { 

        Random random = new Random();
        this.yPosition = random.nextInt(5);
        this.xPosition = 800; 
        this.health = health;
        this.speed = speed;
    }

    
    /**
     * This gets the zombie's x position
     * @return an integer containing the zombie's x position
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * This gets the zombie's y position
     * @return an integer containing the zombie's y position
     */
    public int getYPosition() {
        return yPosition;
    }

    /**
     * This gets the zombie's health
     * @return an integer containing the zombie's health
     */
    public int getHealth() {
        return health;
    }

    /**
     * This modifies the zombie's y position
     * @param y the new y position of zombie
     */
    public void setYPosition(int y) {
        this.yPosition = y;
    }

    /**
     * This modifies the zombie's x position
     * @param x the new x position of zombie
     */
    public void setXPosition(int x) {
        this.xPosition = x;
    }

    /**
     * This decreases the zombie's health based on the damage taken
     * @param damage the amount of damage going to be inflicted
     */
    public void takeDamage(int damage){
        health -= damage;
    }


    /**
     * This moves the zombie's x position one unit to the left
     */
    public void move() {
        if (xPosition > 0) {
            xPosition -= 1;
        }
    }

    /**
     * This checks if the zombie's health is equal or below 0
     * @return a boolean if the zombie's health is equal or below 0
     */
    public boolean isDead() {
        return health <= 0;
    }

    public int getDamage(){
        return damage;
    }

    public int getSpeed(){
        return speed;
    }

    // /**
    //  * This gets the zombie's damage
    //  * @return an integer containing the zombie's damage
    //  */
    // public int getDamage() {
    //     return damage;
    // }
    // /**
    //  * This gets the zombie's speed
    //  * @return an integer containing the zombie's speed
    //  */
    // public int getSpeed() {
    //     return speed;
    // }

    
}