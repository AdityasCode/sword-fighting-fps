package CommonUtils;

import CommonUtilsOld.BetterStackInterface;

import java.util.EmptyStackException;

/**
 * @implNote Implement a stack using an array with initial capacity 8.
 *
 * Implement BetterStackInterface and add a constructor
 *
 * You are explicitly forbidden from using java.util.Stack and any
 * other java.util.* library EXCEPT java.util.EmptyStackException and java.util.Arrays.
 * Write your own implementation of a Stack.
 *
 *
 * @param <E> the type of object this stack will be holding
 */
public class BetterStack<E> implements BetterStackInterface<E> {

    /**
     * Initial size of stack.  Do not decrease capacity below this value.
     */
    private final int INIT_CAPACITY = 8;


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
     */
    private E[] stack;
    private int capacity = INIT_CAPACITY;
    private int size = 0;


    public E[] getStack() {
        return stack;
    }

    public void setStack(E[] stack) {
        this.stack = stack;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        E news[] = (E[]) new Object[capacity];
        int x = -1;
        for (int i = 0; (i < this.getCapacity()) && (i < capacity); i++) {
            E a = this.stack[i];
            news[i] = a;
            x = i;
        }
        this.stack = news;
        this.capacity = capacity;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Constructs an empty stack
     */
    @SuppressWarnings("unchecked")
    public BetterStack(){
        this.stack = (E[]) new Object[INIT_CAPACITY];
        this.size = 0;
        this.capacity = INIT_CAPACITY;
        //todo
    }


    /**
     * Push an item onto the top of the stack
     *
     * @param item item to push
     * @throws OutOfMemoryError if the underlying data structure cannot hold any more elements
     */
    @Override
    public void push(E item) throws OutOfMemoryError {
        while (this.size() >= this.getCapacity()) {
            if ((this.getCapacity() * INCREASE_FACTOR) <= Integer.MAX_VALUE) {
                this.setCapacity(this.getCapacity() * INCREASE_FACTOR);
            } else if ((this.getCapacity() + CONSTANT_INCREMENT) <= Integer.MAX_VALUE) {
                this.setCapacity(this.getCapacity() + CONSTANT_INCREMENT);
            }
            else {
                throw new OutOfMemoryError();
            }
        }
        this.stack[this.size()] = item;
        this.setSize(this.size() + 1);
        //todo
    }

    /**
     * Remove and return the top item on the stack
     *
     * @return the top of the stack
     * @throws EmptyStackException if stack is empty
     */
    /**
     * If the number of elements stored is < capacity * DECREASE_FACTOR, it should decrease
     * the capacity of the UDS to max(capacity * DECREASE_FACTOR, initial capacity).
     *
     */
    @Override
    public E pop() {
        //todo
        if (this.isEmpty()) {
            throw new EmptyStackException();
        }
        E re_val = this.stack[this.size() - 1];
        this.stack[this.size() - 1] = null;
        this.setSize(this.size() - 1);
        while ((this.size() < (this.getCapacity() * DECREASE_FACTOR)) && (this.getCapacity() > INIT_CAPACITY)) {
            this.setCapacity(((this.getCapacity() * DECREASE_FACTOR) > INIT_CAPACITY) ? (int) (this.getCapacity() * DECREASE_FACTOR) : INIT_CAPACITY);
        }
        return re_val;
    }

    /**
     * Returns the top of the stack (does not remove it).
     *
     * @return the top of the stack
     * @throws EmptyStackException if stack is empty
     */
    @Override
    public E peek() {
        if (this.isEmpty()) {
            throw new EmptyStackException();
        }
        //todo
        return this.stack[this.size() - 1];
    }

    /**
     * Returns whether the stack is empty
     *
     * @return true if the stack is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        //todo
        return this.size() == 0;
    }

    /**
     * Returns the number of elements in the stack
     *
     * @return integer representing the number of elements in the stack
     */
    @Override
    public int size() {
        //todo
        return this.size;
    }

    /**
     * DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
     *
     * @param g graphics object to draw on
     */
    @Override
    public void draw(java.awt.Graphics g) {
        //DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
        if(g != null) g.getColor();
        //todo GRAPHICS DEVELOPER:: draw the stack how we discussed
        //251 STUDENTS:: YOU ARE NOT THE GRAPHICS DEVELOPER!
    }
}
