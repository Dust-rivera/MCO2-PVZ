package model;
/**
 * Represents a Flag Zombie (faster, signals final wave)
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
public class FlagZombie extends Zombie implements ZombieMechanics {

    // public static final int FLAG_DAMAGE = 10;
    // public static final int FLAG_SPEED = 6;

    public FlagZombie() {
        super(70, 10, 8); 
    }
} 