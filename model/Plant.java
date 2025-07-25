/** Represents a Plant
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
    protected int tick = 0;
    protected int range;
    protected int damage;
    protected int dirDamage;
    protected final int SPEED;


    /**
     * Creates Plant object given its cost, health, x position, y position, speed, and range
     * @param cost how much sun the plant costs
     * @param health how much health the plant has
     * @param x x position of plant
     * @param y y position of plant
     * @param speed how many ticks per update
     * @param range how far plant reaches
     * @param damage the amount of damage plant deals
     * @param dirDamage the amount of damage the plant deals close range
     */
    public Plant(int cost, int health, int x, int y, int speed, int range, int damage, int dirDamage) {
        xPosition = x;
        yPosition = y;
        this.cost = cost;
        this.health = health;
        SPEED = speed;
        this.range = range;
        this.damage = damage;
        this.dirDamage = dirDamage;
    }

    // /**
    //  * This updates the plant
    //  * @param board the board object to be updated
    //  */
    // public abstract void update(Board board){    
    // }
    public abstract void plantTurn(Board board);

    /**
     * This gets the cost of the plant
     * @return an integer containing the cost of the plant
     */
    public int getCost() {
        return cost;
    }

    /**
     * This gets the plant's health
     * @return an integer containing plant's health
     */
    public int getHealth() {
        return health;
    }
    
    /**
     * This gets the plant's x position
     * @return an integer containing the plant's x position
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * This gets the plant's y position
     * @return an integer containing the plant's y position
     */
    public int getYPosition() {
        return yPosition;
    }

    /**
     * This gets the plant's base speed
     * @return an integer containing the plant's base speed
     */
    public int getSPEED(){
        return SPEED;
    }

    /**
     * This gets the plant's current tick
     * @return an integer containing the plant's current tick
     */
    public int getTick(){
        return tick;
    }

    /**
     * This gets the Plant's range
     * @return and integer containing the Plant's range
     */
    public int getRange(){
        return range;
    }

    /**
     * This gets the plant's base damage
     * @return an integer containing the plant's base damage
     */
    public int getDamage(){
        return damage;
    }

    /**
     * This gets the plant's direct damage
     * @return an integer containing the plant's direct damage
     */
    public int getDirDamage(){
        return dirDamage;
    }

    /**
     * This modifies the plant's tick
     * @param set the new value of tick
     */
    public void setTick(int set){
        tick = set;
    }

    /**
     * This increments the plant's tick
     */
    public void increaseTick(){
        tick++;
    }

    /**
     * This decreases the plant's health based on the damage taken
     * @param damage the amount of damage the plant will take
     */
    public void decreaseHealth(int damage){
        this.health -= damage;
    }

    /**
     * This check if the plant's health is below or equals to 0
     * @return a boolean if the plant's health is equal or below 0
     */
    public boolean isDead(){
        return health <= 0;
    }
    /**
     * This gets the plant's cooldown time
     * @return an integer containing the plant's cooldown time
     */
    // public int getcoolDown() {
    //     return cooldown;
    // }

    // public void setCooldown(int cooldown) {
    //     this.cooldown = cooldown;
    // }
    /**
     * This gets the plant's regeneration rate
     * @return an integer containing the plant's regeneration rate
     */
    // public int getRegen() {
    //     return REGEN;
    // }

}