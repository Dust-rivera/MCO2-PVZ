package model;
/**
 * Represents a Normal Zombie
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
public class NormalZombie extends Zombie implements ZombieMechanics {
    public static final int NORMAL_DAMAGE = 10;
    public static final int NORMAL_SPEED = 4;

    public NormalZombie() {
        super(70, 0, NORMAL_DAMAGE, NORMAL_SPEED); 
    }

    public static  int getDamage() {
        return NORMAL_DAMAGE;
    }

    public static int getSpeed() {
        return NORMAL_SPEED;
    }

} 