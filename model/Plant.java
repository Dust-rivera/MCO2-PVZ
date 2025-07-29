/** Represents a Plant abstract
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 2.0
 */

package model;

public abstract class Plant {
    protected int xPosition;
    protected int yPosition;
    protected int cost;
    protected int health;
    protected int range;
    protected int damage;
    protected int dirDamage;
    protected final int SPEED;
    protected int regenTime;

    /**
     * Creates Plant object given its cost, health, x position, y position, speed,
     * and range
     * 
     * @param cost      how much sun the plant costs
     * @param health    how much health the plant has
     * @param x         x position of plant
     * @param y         y position of plant
     * @param speed     how many ticks per update
     * @param range     how far plant reaches
     * @param damage    the amount of damage plant deals
     * @param dirDamage the amount of damage the plant deals close range
     */
    public Plant(int cost, int health, int x, int y, int speed, int range, int damage, int dirDamage, int regen) {
        xPosition = x;
        yPosition = y;
        this.cost = cost;
        this.health = health;
        SPEED = speed;
        this.range = range;
        this.damage = damage;
        this.dirDamage = dirDamage;
        this.regenTime = regen;
    }

    /**
     * This gets the zombie's regen time
     * @return an integer containing the zombie's regen time
     */
    public int getRegenTime() {
        return regenTime;
    }

    /**
     * This gets the cost of the plant
     * 
     * @return an integer containing the cost of the plant
     */
    public int getCost() {
        return cost;
    }

    /**
     * This gets the plant's health
     * 
     * @return an integer containing plant's health
     */
    public int getHealth() {
        return health;
    }

    /**
     * This gets the plant's x position relative to frame
     * 
     * @return an integer containing the plant's x position 
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * This gets the plant's y position relative to the board grid
     * 
     * @return an integer containing the plant's y position
     */
    public int getYPosition() {
        return yPosition;
    }

    /**
     * This gets the plant's base speed
     * 
     * @return an integer containing the plant's base speed
     */
    public int getSPEED() {
        return SPEED;
    }

    /**
     * This gets the Plant's range
     * 
     * @return and integer containing the Plant's range
     */
    public int getRange() {
        return range;
    }

    /**
     * This gets the plant's base damage
     * 
     * @return an integer containing the plant's base damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * This gets the plant's direct damage
     * 
     * @return an integer containing the plant's direct damage
     */
    public int getDirDamage() {
        return dirDamage;
    }

    /**
     * This decreases the plant's health based on the damage taken
     * 
     * @param damage the amount of damage the plant will take
     */
    public void decreaseHealth(int damage) {
        this.health -= damage;
    }

    /**
     * This check if the plant's health is below or equals to 0
     * 
     * @return a boolean if the plant's health is equal or below 0
     */
    public boolean isDead() {
        return health <= 0;
    }
}