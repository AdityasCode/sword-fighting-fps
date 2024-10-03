package Items;

import Items.Types.SwordType;

import java.util.Objects;

/**
 * Implements our SwordType class
 */
public class Sword extends SwordType implements Comparable<Sword> {
    /**
     * Constructs the Sword object by referencing the parent class.  Fields are as named.
     */
    public Sword(int timeToClean, int totalHealth, int healthLeft, int length, int DPS, int attackSpeed,
                 String name, String description, String comments, String style) {
        super(totalHealth, healthLeft, timeToClean, length, DPS, attackSpeed, name, description, comments, style);
    }
    private long cleanliness;


    //hint: it might be helpful to implement a static function that returns a unique value based only on certain fields



    /**
     * Returns the hash code of this object, for internal use only
     *
     * <bold>251 Students: This function will not be tested directly, it is for your convenience only.</bold>
     * @return a hash code of the object
     */
    @Override
    public int hashCode() {
       return Objects.hash(this.totalHealth, this.DPS, this.attackSpeed, this.style);
    }

    @Override
    public int compareTo(Sword o) {
//        if (this.timeToClean == -1 && o.timeToClean != -1) {
//            return
//        }
        /* if a sword is -1, dont send it anywhere. keep it in the array till
        * it's returned, then add it to the minheap. if a sword is not -1, add it
        * to the minheap immediately. when it is taken off, you may remove it
        * and that's it. when it is returned, add the time to clean to its
        * time to clean and add it to the minheap.
        */
        if (this.cleanliness < o.cleanliness) {
            return -1;
        }
        if (this.cleanliness > o.cleanliness) {
            return 1;
        }
        return 0;
    }

    public long getCleanliness() {
        return cleanliness;
    }

    public void setCleanliness(long cleanliness) {
        this.cleanliness = cleanliness;
    }
}
