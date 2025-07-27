package model;
/**
 * Represents a Conehead Zombie (more health)
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
public class ConeheadZombie extends Zombie implements ZombieMechanics {
    // public static final int CONEHEAD_SPEED = 4; 
    // public static final int CONEHEAD_DAMAGE = 10;

    public ConeheadZombie() {
        super(140, 10, 4);
    }
} 