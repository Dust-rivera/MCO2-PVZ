package model;
/**
 * Represents a Normal Zombie
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
public class NormalZombie extends Zombie {
    public static final int NORMAL_HEALTH = 70;
    public static final int NORMAL_DAMAGE = 10;

    public NormalZombie(int yPosition) {
        super(yPosition);
        // Optionally set health/damage if needed
    }
} 