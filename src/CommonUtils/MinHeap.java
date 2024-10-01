package CommonUtils;

import CommonUtils.Interfaces.MinHeapInterface;

import java.awt.*;
import java.util.ArrayList;

/**
 * Implements our MinHeapInterface and adds a constructor
 * <p>
 * <b>251 students: You are explicitly forbidden from using java.util.Queue (including any subclass
 *   like PriorityQueue) and any other java.util.* library EXCEPT java.util.Arrays and java.util.Vector.
 *   Write your own implementation of a MinHeap.</b>
 *
 * @param <E> the type of object this heap will be holding
 */
public class MinHeap<E extends Comparable<E>> implements MinHeapInterface<E> {
    /**
     * A recursive method to heapify (sort the root to where it should go) a
     *   subtree with the root at given index
     * Assumes the subtrees are already heapified.
     * (The purpose of this method is to balance tree starting at the root)
     * @param i root of the subtree to heapify
     */
    private void heapify(int i) {
        if (2 * i + 1 > this.size()) {
            return;
        }
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        int smallest;
        if ((l < this.size()) && (heap.get(l).compareTo(heap.get(i))) < 0) {
            smallest = l;
        } else {
            smallest = i;
        }
        if ((r < this.size()) && (heap.get(r).compareTo(heap.get(smallest)) < 0)) {
            smallest = r;
        }
        if (smallest != i) {
            swap(i, smallest);
            heapify(smallest);
        }
    }

    /*
    helps above, swaps elems
     */
    public void swap(int i, int j) {
        E temp = heap.get(i);
        this.heap.set(i, heap.get(j));
        this.heap.set(j, temp);
    }

    /**
     * Constructs an empty min heap
     */
    public MinHeap(){
        heap = new ArrayList<>();
    }
    private ArrayList<E> heap;

    /**
     * Adds the item to the min heap
     *
     * @param item item to add
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public void add(E item) {
        if (item == null) {
            throw new NullPointerException();
        }
        heap.add(item);
        swim_up(heap.size() - 1);
    }

    /**
     * Helps with adding. Swims up new element at index i.
     */

    public void swim_up(int i) {
        if (i == 0) {
            return;
        }
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (heap.get(i).compareTo(heap.get(parent)) >= 0) {
                break;
            }
            swap(i, parent);
            i = parent;
        }
    }

    /**
     * Empties the heap.
     */
    @Override
    public void clear() {
        this.heap.clear();
    }

    /**
     * Returns the minimum element without removing it, or returns <code>null</code> if heap is empty
     *
     * @return the minimum element in the heap, or <code>null</code> if heap is empty
     */
    @Override
    public E peekMin() {
        E item = null;
        if (heap.size() != 0) {
            item = heap.get(0);
        }
        return item;
    }

    /**
     * Remove and return the minimum element in the heap, or returns <code>null</code> if heap is empty
     *
     * @return the minimum element in the heap, or <code>null</code> if heap is empty
     */
    @Override
    public E removeMin() {
        E item = null;
        if (heap.size() == 1) {
            item = heap.remove(0);
            return item;
        }
        if (heap.size() != 0) {
            item = heap.get(0);
            E last = heap.remove(heap.size() - 1);
            this.heap.set(0, last);
            heapify(0);
        }
        return item;
    }

    /**
     * Returns the number of elements in the heap
     *
     * @return integer representing the number of elements in the heap
     */
    @Override
    public int size() {
        return this.heap.size();
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
        //todo GRAPHICS DEVELOPER:: draw the MinHeap how we discussed
        //251 STUDENTS:: YOU ARE NOT THE GRAPHICS DEVELOPER!
    }

    @Override
    public String toString() {
        String res =  "MinHeap{" +
                "heap=";
        for (E item : this.heap) {
            res += item.toString() + "; ";
        }
        res += "}";
        return res;
    }
}
