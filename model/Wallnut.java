package model;
/**
 * Represents a Cherry Bomb plant that explodes and damages zombies in an area
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
public class Wallnut extends Plant {

    private static int COST = 50;

    public Wallnut(int x, int y) {
        super(50, 9999, x, y, 0, 1, 9999, 9999, 50000); // Massive damage, instant
    }

    public static int getCostStatic() {
        return COST;
    }

    /**
     * Triggers the explosion effect (to be implemented in Board/Controller)
     */
    // @Override
    // public void update(Board board) {
    //     // Explosion logic handled in Board/Controller
    // }

    /**
     * Gets the cooldown for Cherry Bomb
     * @return cooldown in ticks
     */
    // public static int getCherryBombCD() {
    //     return cherryBombCD;
    // }
} 