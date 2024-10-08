package CommonUtils;

import CommonUtils.Interfaces.BetterHashTableInterface;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * Implements our {@link BetterHashTableInterface} and adds two constructors.
 * <p>
 * <b>251 students: You are explicitly forbidden from using java.util.Hashtable (including any subclass
 *   like HashMap) and any other java.util.* library EXCEPT java.util.Arrays and java.util.Objects.
 *   Write your own implementation of a hash table.</b>
 *
 * @implNote Implements a hash table using an array with initial capacity 8.
 *
 * @param <K> Type of key the hash table is holding
 * @param <V> Type of value the hash table is holding
 */
public class BetterHashTable<K, V> implements BetterHashTableInterface<K, V> {

    /* Table capacity and size. */
    private int capacity;
    private int size;

    /**
     * Initial size of hash table.
     */
    private final int INIT_CAPACITY = (1 << 4) + 3;//16+3
    /**
     * Determines the maximum ratio of number of elements to array size.
     * <p>
     * If the number of elements stored is > LOAD_FACTOR, it should increase
     *   the capacity of the array according to INCREASE_FACTOR.
     * <p>
     * Our hash table will not decrease its size for the duration of its lifetime.
     */
    private final double LOAD_FACTOR = 0.75;
    /**
     * Determines how much to increase the array's capacity.
     * <p>
     * If the array needs to increase in size (see load factor), it should prefer to
     *   increase by the old capacity * INCREASE_FACTOR.  If it cannot increase by
     *   that much (max int), it should increase by some small quantity (<100).
     */
    private final int INCREASE_FACTOR = 2;
    /**
     * "some small quantity (<100)"
     */
    private final int CAPACITY_INCREMENT = 1 << 5;//32

    /**
     * Simple storage unit for our hash table
     */
    private static class Node<K, V> {
        final K key;
        V value;

        Node(K key, V value){ this.key = key; this.value = value; }

        //default intellij-generated toString
        @Override
        public String toString() {
            return "Node{" + "key=" + key + ", value=" + value + '}';
        }

        //default intellij-generated equals
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;
            Node<?, ?> node = (Node<?, ?>) o;
            return Objects.equals(key, node.key) && Objects.equals(value, node.value);
        }
    }

    /**
     * Default entry to use when marking a slot as deleted.
     * <bold>251 students: When you remove an entry in the table, you cannot simply set it to null.
     *    Because we are using quadratic probing, there could be a "chain" of probing misses
     *    which would be disrupted if we just set the current node to null.  Having a "deleted"
     *    default value allows us to continue successfully searching for any object whose hash
     *    matches the object we are deleting.  It is up to you to figure out the best way to
     *    use this field, no further guidance will be given.</bold>
     */
    private final Node<K, V> DELETED = new Node<>(null, null);

    /**
     * Array to store elements  (according to the implementation
     *   note in the class header comment).
     */
    Node<K, V>[] table;

    /**
     * Constructs the hash table with a default size
     * <bold>251 Students: the syntax for this is a little weird, here is the easiest
     * way to create a list of Node<K, V>:
     *       (Node<K, V>[]) new Node[capacity_here_if_you_need_it]
     * </bold>
     */
    @SuppressWarnings("unchecked")
    public BetterHashTable() {
        table = (Node<K, V>[]) new Node[INIT_CAPACITY];
        this.capacity = INIT_CAPACITY;
        this.size = 0;
    }

    /**
     * Constructor that initializes the hash table with an initial capacity
     * @param initialCapacity initial table capacity
     * @throws IllegalArgumentException if the initial capacity is negative
     */
    @SuppressWarnings("unchecked")
    public BetterHashTable(int initialCapacity) throws IllegalArgumentException {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException();
        }
        table = (Node<K, V>[]) new Node[initialCapacity];
        this.capacity = initialCapacity;
        this.size = 0;

    }

    /**
     * Returns a positive hash of the given thing. Should be useful for a hash table.
     * @param thing thing to hash
     * @return positive hashed value
     */
    private int usefulHash(K thing){ return Math.abs(Objects.hash(thing)); }


    /**
     * Places the item in the hash table. Passing key=null will not change the state of the table.
     * Passing a key already in the table will modify the original entry in the table.
     *
     * @param key   key to associate with the value
     * @param value item to store in the hash table
     */
    @Override
    public void insert(K key, V value) {
        if (key == null) {
            return;
        }
        if (((double) this.size / (double) this.capacity) >= LOAD_FACTOR) {
            resize_rehash();
        }

        int index = myHash(key);
        int i = 0;
        int m = this.capacity;
        do {
            if ((table[index] == null) || (table[index] == DELETED)) {
                table[index] = new Node<>(key,value);
                this.size++;
                return;
            }
            if (table[index].key == key) {
                table[index].value = value;
                return;
            }
            index = quad_probe(key, i, 0, 1);
            i += 1;
        } while (i != m);

        // insert failed. rehash, resize, everything, again.
        resize_rehash();
        insert(key, value);
    }

    private int myHash(K key) {
        return usefulHash(key) % this.capacity;
    }

    /*
    Helper function for insert. adds size, resizes and rehashes if necessary.
     */

    /**
     * Removes the key, value pair associated with the given key
     *
     * @param key key/value to remove
     */
    @Override
    public void remove(K key) {
        int i = 0;
        int index;
        while (i < this.capacity) {
            index = quad_probe(key, i, 0, 1);
            if (index < 0) {
                return;
            }
            if ((table[index] != null) && (table[index] != DELETED) && (table[index].key.equals(key))) {
                table[index] = DELETED;
                this.size--;
                return;
            }
            i += 1;
        }
    }

    /**
     * Retrieves a value based on the given key
     *
     * @param key key to search by
     * @return value associated with the key, or <code>null</code> if it does not exist
     */
    @Override
    public V get(K key) {
        if ((size == 0) || (key == null)) {
            return null;
        }
        int i = 0;
        int index;
        while (i < this.capacity) {
            index = quad_probe(key, i, 0, 1);
            if (index < 0) {
                return null;
            }
            if ((table[index] == null) || (table[index] == DELETED)) {
                i++;
                continue;
            }
            if (table[index].key.equals(key)) {
                return table[index].value;
            }
            i++;
        }
        return null;
    }

    /**
     * Returns <code>true</code> if this hash table contains the given key.
     *
     * @param key key to check for
     * @return true iff the hash table contains a mapping for the specified key,
     *          as ultimately determined by the equals method
     */
    @Override
    public boolean containsKey(K key) {
        return (get(key) != null);
    }

    /**
     * Empties the hash table.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        this.size = 0;
        this.table = (Node<K, V>[]) new Node[this.capacity];
    }

    /**
     * Returns the number of items in the hash table
     *
     * @return integer representing the number of elements in the hash table
     */
    @Override
    public int size() {
        return this.size;
    }

    /* Quadratic Probing function. */

    public int quad_probe(K key, int i, int c_1, int c_2) {
        int res = (myHash(key) + c_1 * i + c_2 * i * i) % this.capacity;
        if (res < 0) {
            return -1;
        }
        return (res);
    }

    /* Resizing and rehashing function */

    public void resize_rehash() {
        int old_cap = this.capacity;
        int new_cap = old_cap * INCREASE_FACTOR;
        if (new_cap < old_cap) {
            new_cap = old_cap + CAPACITY_INCREMENT;
            if (new_cap < old_cap) {
                throw new OutOfMemoryError();
            }
        }
        Node<K, V>[] old_table  = this.table;
        this.capacity = new_cap;
        this.clear();
        this.size = 0;
        for (Node<K, V> node : old_table) {
            if ((node != null) && (node != DELETED)) {
                insert( node.key, node.value);
            }
        }
    }

    /**
     * DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
     *
     * @param g graphics object to draw on
     */
    @Override
    public void draw(Graphics g) {
        //DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
        if(g != null) g.getColor();
        //todo GRAPHICS DEVELOPER:: draw the hash table how we discussed
        //251 STUDENTS:: YOU ARE NOT THE GRAPHICS DEVELOPER!
    }

    /**
     * DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
     *
     * @param g graphics object to draw on
     */
    @Override
    public void visualize(Graphics g) {
        //DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
        if(g != null) g.getColor();
        //todo GRAPHICS DEVELOPER:: visualization is to be time-based -- how we discussed
        //251 STUDENTS:: YOU ARE NOT THE GRAPHICS DEVELOPER!
    }

    @Override
    public String toString() {
        String res = "BetterHashTable{" +
                "capacity=" + capacity +
                ", size=" + size +
                ", table=";
        for (String s : Arrays.toString(table).split(", Node")) {
            res += "Node" + (s) + '\n';
        }
        res += '}';
        return res;
    }
}
