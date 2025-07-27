package model;
/**
 * Represents a Conehead Zombie (more health)
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
public class ConeheadZombie extends Zombie implements ZombieMechanics {
    public static final int CONEHEAD_SPEED = 4; 
    public static final int CONEHEAD_DAMAGE = 10;

    public ConeheadZombie(int yPosition) {
        super(140, 0, CONEHEAD_DAMAGE, CONEHEAD_SPEED);
    }

    public int getDamage() {
        return CONEHEAD_DAMAGE;
    }
    public int getSpeed() {
        return CONEHEAD_SPEED;
    }
} 