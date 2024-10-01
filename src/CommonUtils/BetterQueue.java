package CommonUtils;

import CommonUtilsOld.BetterQueueInterface;

import java.awt.*;

/**
 * @implNote implement a queue using a circular array with initial capacity 8.
 *
 * Implement BetterQueueInterface and add a constructor
 *
 * You are explicitly forbidden from using java.util.Queue and any subclass
 * (including LinkedList, for example) and any other java.util.* library EXCEPT java.util.Objects.
 * Write your own implementation of a Queue.
 *
 * Another great example of why we are implementing our own queue here is that
 * our queue is actually FASTER than Java's LinkedList (our solution is 2x faster!). This is due
 * to a few reasons, the biggest of which are 1. the overhead associated with standard library
 * classes, 2. the fact that Java's LinkedList doesn't store elements next to each other, which
 * increases memory overhead for the system, and 3. LinkedList stores 2 pointers with each element,
 * which matters when you store classes that aren't massive (because it increases the size of each
 * element, making more work for the system).
 *
 * @param <E> the type of object this queue will be holding
 */
public class BetterQueue<E> implements BetterQueueInterface<E> {

    public E[] getQueue() {
        return queue;
    }

    public void setQueue(E[] queue) {
        this.queue = queue;
    }

    public long getCapacity() {
        return this.capacity;
    }

    public void setCapacity(long newcap) {
        long oldcap = this.getCapacity();
        E newq[] = (E[]) new Object[(int) newcap];
        int oldfront = this.getFront();
        E q[] = this.getQueue();
        for (int i = 0; i < this.getSize(); i++) {
            E olde = q[(int) ((oldfront + i) % oldcap)];
            newq[i] = olde;
        }
        this.setFront(0);
        this.setBack(this.getSize());
        this.setQueue(newq);
        this.capacity = newcap;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        //System.out.printf("size changed from %d to %d\n", this.size, size);
        this.size = size;
    }

    public int getFront() {
        return front;
    }

    public void setFront(int front) {
        this.front = front;
    }

    public int getBack() {
        return back;
    }

    public void setBack(int back) {
        this.back = back;
    }

    /**
     * Initial size of queue.  Do not decrease capacity below this value.
     */
    private final int INIT_CAPACITY = 8;
    private long capacity = INIT_CAPACITY;
    private int size = 0;
    private int front = 0;
    private int back = 0;

    /**
     * If the array needs to increase in size, it should be increased to
     * old capacity * INCREASE_FACTOR.
     *
     * If it cannot increase by that much (old capacity * INCREASE_FACTOR > max int),
     * it should increase by CONSTANT_INCREMENT.
     *
     * If that can't be done either throw OutOfMemoryError()
     *
     */
    private final int INCREASE_FACTOR = 2;
    private final int CONSTANT_INCREMENT = 1 << 5; // 32



    /**
     * If the number of elements stored is < capacity * DECREASE_FACTOR, it should decrease
     * the capacity of the UDS to max(capacity * DECREASE_FACTOR, initial capacity).
     *
     */
    private final double DECREASE_FACTOR = 0.5;


    /**
     * Array to store elements in (according to the implementation
     * note in the class header comment).
     *
     * Circular arrays work as follows:
     *
     *   1. Removing an element increments the "first" index
     *   2. Adding an element inserts it into the next available slot. Incrementing
     *      the "last" index WRAPS to the front of the array, if there are spots available
     *      there (if we have removed some elements, for example).
     *   3. The only way to know if the array is full is if the "last" index
     *      is right in front of the "first" index.
     *   4. If you need to increase the size of the array, put the elements back into
     *      the array starting with "first" (i.e. "first" is at index 0 in the new array).
     *   5. No other implementation details will be given, but a good piece of advice
     *      is to draw out what should be happening in each operation before you code it.
     *
     *   hint: modulus might be helpful
     */
    private E[] queue;


    /**
     * Constructs an empty queue
     */
    @SuppressWarnings("unchecked")
    public BetterQueue(){
        this.queue = (E[]) new Object[INIT_CAPACITY];
        //todo
    }

    /**
     * Add an item to the back of the queue
     *
     * @param item item to push
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public void add(E item) {
        //System.out.println("element adding: " + item);
        //todo
        if (item == null) {
            throw new NullPointerException();
        }
        int s = this.getSize();
        long c = this.getCapacity();
        if (c <= s) {
            if (c * INCREASE_FACTOR <= (long) Integer.MAX_VALUE) {
                this.setCapacity(c * INCREASE_FACTOR);
            }
            else if (c + CONSTANT_INCREMENT <= Integer.MAX_VALUE) {
                this.setCapacity(c + CONSTANT_INCREMENT);
            }
            else {
                throw new OutOfMemoryError();
            }
        }
        this.queue[this.back] = item;
        this.setBack(this.getBack() + 1);
        this.setSize(this.getSize() + 1);
        if ((this.getSize() < this.getCapacity()) && (this.getBack() == this.getCapacity())) {
            this.setBack(0);
        }

    }

    /**
     * Returns the front of the queue (does not remove it) or <code>null</code> if the queue is empty
     *
     * @return front of the queue or <code>null</code> if the queue is empty
     */
    @Override
    public E peek() {
        //todo
        if (this.isEmpty()) { return null;}
        return this.queue[this.getFront()];
    }

    /**
     * Returns and removes the front of the queue
     *
     * @return the head of the queue, or <code>null</code> if this queue is empty
     */
    @Override
    public E remove() {
        //todo
        if (this.isEmpty()) {
            return null;
        }
        E return_val = this.queue[front];
        this.queue[front] = null;
        this.setFront((int) ((this.getFront() + 1) % this.getCapacity()));
        this.setSize(this.getSize() - 1);
        while ((this.getSize() < (this.getCapacity() * DECREASE_FACTOR)) && (this.getCapacity() != INIT_CAPACITY)) {
            this.setCapacity(((this.getCapacity() * DECREASE_FACTOR) > INIT_CAPACITY) ? (int) (this.getCapacity() * DECREASE_FACTOR) : INIT_CAPACITY);
        }
        return return_val;
    }

    /**
     * Returns the number of elements in the queue
     *
     * @return integer representing the number of elements in the queue
     */
    @Override
    public int size() {
        //todo
        return this.getSize();
    }

    /**
     * Returns whether the queue is empty
     *
     * @return true if the queue is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        //todo
        return (this.getSize() == 0);
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
        //todo GRAPHICS DEVELOPER:: draw the queue how we discussed
        //251 STUDENTS:: YOU ARE NOT THE GRAPHICS DEVELOPER!
    }
}
