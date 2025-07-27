/** Represents a Zombie
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 2.0
 */
package model;
import java.util.Random;

import javax.swing.JLabel;
public abstract class Zombie {

    protected int xPosition;
    protected int yPosition;
    protected int health ;
    protected int attackTick; 
    protected int damage;
    protected int speed;
    protected JLabel label;
    protected int row; // Added row field
    

    /**
     * Creates Zombie object given its y position
     * @param yPosition the y position of the zombie
     */
    public Zombie(int health, int attackTick, int damage, int speed) { 

        Random random = new Random();
        this.yPosition = random.nextInt(5);
        this.xPosition = 8; 
        this.health = health;
        this.attackTick = attackTick;
    }
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
    
    public abstract int getDamage();
    public abstract int getSpeed();
    /**
     * This gets the attack tick of the zombie
     * @return an integer containing the attack tick
     */
    public int getAttackTick() {
        return attackTick;
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
        if (isDead() && label != null) {
            label.getParent().remove(label);
            label = null;
        }
    }

    public JLabel getLabel() {
        return label;
    }

    /**
     * This increases attack tick
     */
    public void incrementAttackTick() {
        attackTick++;
    }

    /**
     * This resets attack tick
     */
    public void resetAttackTick() {
        attackTick = 0;
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
    /**
     * This sets the JLabel linked to the zombie
     * @param label the JLabel displaying the zombie
     */
    public void setLabel(JLabel label) {
        this.label = label;
    }

}