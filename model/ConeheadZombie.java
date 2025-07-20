package model;
/**
 * Represents a Conehead Zombie (more health)
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
public class ConeheadZombie extends Zombie {
    public static final int CONEHEAD_HEALTH = 140;
    public static final int CONEHEAD_DAMAGE = 10;

    public ConeheadZombie(int yPosition) {
        super(yPosition);
        // Optionally set health/damage if needed
    }
} 