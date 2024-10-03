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
    private double cleanliness;
    private int index;


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

        /* if a sword is -1, don't send it anywhere. keep it in the array till
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
        if (this.getIndex() < o.getIndex()) {
            return -1;
        }
        if (this.getIndex() > o.getIndex()) {
            return 1;
        }
        if (this.getTimeToClean() < o.getTimeToClean()) {
            return -1;
        }
        if (this.getTimeToClean() > o.getTimeToClean()) {
            return 1;
        }
        if (this.getTotalHealth() < o.getTotalHealth()) {
            return -1;
        }
        if (this.getTotalHealth() > o.getTotalHealth()) {
            return 1;
        }
        if (this.getHealthLeft() < o.getHealthLeft()) {
            return -1;
        }
        if (this.getHealthLeft() > o.getHealthLeft()) {
            return 1;
        }
        if (this.getLength() < o.getLength()) {
            return -1;
        }
        if (this.getLength() > o.getLength()) {
            return 1;
        }
        if (this.getDPS() < o.getDPS()) {
            return -1;
        }
        if (this.getDPS() > o.getDPS()) {
            return 1;
        }
        int comp;
        if ((comp = Integer.compare(this.getAttackSpeed(), o.getAttackSpeed())) != 0) {
            return comp;
        }
        if ((comp = String.CASE_INSENSITIVE_ORDER.compare(this.getName(), o.getName())) != 0) {
            return comp;
        }
        if ((comp = String.CASE_INSENSITIVE_ORDER.compare(this.getDescription(), o.getDescription())) != 0) {
            return comp;
        }
        if ((comp = String.CASE_INSENSITIVE_ORDER.compare(this.getComments(), o.getComments())) != 0) {
            return comp;
        }
        return (String.CASE_INSENSITIVE_ORDER.compare(this.getStyle(), o.getStyle()));
    }

    public double getCleanliness() {
        return cleanliness;
    }

    public void setCleanliness(double cleanliness) {
        this.cleanliness = cleanliness;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "SwordType{" +
//                "cleanliness=" + cleanliness + ", " +
                "totalHealth=" + totalHealth +
                ", healthLeft=" + healthLeft +
                ", timeToClean=" + timeToClean +
                ", length=" + length +
                ", DPS=" + DPS +
                ", attackSpeed=" + attackSpeed +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", comments='" + comments + '\'' +
                ", style='" + style + '\'' +
                '}';
    }
}
