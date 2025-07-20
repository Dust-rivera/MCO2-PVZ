package model;
/**
 * Represents a Flag Zombie (faster, signals final wave)
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
public class FlagZombie extends Zombie {
    public static final int FLAG_HEALTH = 70;
    public static final int FLAG_DAMAGE = 10;
    public static final int FLAG_SPEED = 12; // Faster than normal

    public FlagZombie(int yPosition) {
        super(yPosition);
        // Optionally override speed/logic in Board if needed
    }
} 