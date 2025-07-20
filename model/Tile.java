package model;
import java.util.ArrayList;
/** Represents a tile on the board
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
public class Tile {

    private Plant plant = null;
    private ArrayList<Zombie> zombies;

    /**
     * Creates a tile object
     */
    public Tile(){
        zombies = new ArrayList<>();
    }

    /**
     * This gets the plant occupying the tile
     * @return The plant occupying the tile
     */
    public Plant getPlant() {
        return plant;
    }

    /**
     * This gets the zombies occupying the tile
     * @return an ArrayList containing the zombies occupying the tile
     */
    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    /**
     * This gets a specific zombie given the index
     * @param index the index of zombie to be accessed
     * @return a Zombie containing specified zombie
     */
    public Zombie getZombie(int index){
        if(!zombies.isEmpty())
            return zombies.get(index);
        return null;
    }

    /**
     * This modifies the plant occupying the tile
     * @param plant the plant to be assigned
     */
    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    /**
     * This modifies the zombie occupying the tile
     * @param zombie The zombie to be assigned
     */
    public void addZombie(Zombie zombie) {
        zombies.add(zombie);
    }

    /**
     * This check if tile is occupied by a Plant Object
     * @return a booelan to check if tile is plant occupied
     */
    public boolean isPlantOccupied(){
        return plant != null;
    }

    /**
     * Removes the plant on the tile
     */
    public void removePlant(){
        this.plant = null;
    }

    /**
     * Removes the zombie on the tile
     * @param index the index of zombie to be removed
     */
    public void removeZombie(int index){
        zombies.remove(index);
    }

    /**
     * This removes a zombie from the zombies ArrayList 
     * given a Zombie Object
     * @param zombie the Zombie object to be removed from the ArrayList
     */
    public void removeZombie(Zombie zombie){
        zombies.remove(zombie);
    }
}
