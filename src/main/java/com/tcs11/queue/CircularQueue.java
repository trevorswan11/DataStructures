package com.tcs11.queue;

/**
 * A circular queue implementation
 * 
 * @author Trevor Swan
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class CircularQueue<T> {
    /** The array used to implement the queue */
    private T[] arr;
    private int tail, head, size, capacity;
    
    /**
     * Initializes the queue with a given maximum capacity
     * 
     * @param capacity The maximum capacity of the queue
     */
    public CircularQueue(int capacity) {
        this.arr = (T[]) (new Object[capacity]);
        this.capacity = capacity;
        this.tail = -1;
        this.head = 0;
        this.size = 0;
    }

    /**
     * Checks if the queue is full
     * 
     * @return true if the queue is full, false otherwise
     */
    public boolean isFull() {
        return this.size == capacity;
    }

    /**
     * Checks if the queue is empty
     * 
     * @return true if the queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements in the queue
     * 
     * @return The total number of elements
     */
    public int size() {
        return size;
    }

    /**
     * Returns the maximum capacity of the queue
     * 
     * @return The maximum capacity
     */
    public int capacity() {
        return capacity;
    }
    
    /**
     * Adds an item to the tail of the queue
     * 
     * @param item The item to be added
     * @throws IllegalStateException if the queue is full
     */
    public void insert(T item) {
        enqueue(item);
    }

    /**
     * Adds an item to the tail of the queue
     * 
     * @param item The item to be added
     * @throws IllegalStateException if the queue is full
     */
    public void enqueue(T item) {
        if (isFull())
            throw new IllegalStateException("Queue is full.");
        tail = (tail + 1) % this.arr.length;                        // Move the tail pointer and add the item
        this.arr[tail] = item;
        size++;
    }

    /**
     * Removes and returns the item at the head of the queue
     * 
     * @return The item at the head
     * @throws IllegalStateException if the queue is empty
     */
    public void remove() {
        dequeue();
    }

    /**
     * Removes and returns the item at the head of the queue
     * 
     * @return The item at the head
     * @throws IllegalStateException if the queue is empty
     */
    public T dequeue() {
        if (isEmpty())
            throw new IllegalStateException("Queue is empty.");
        T value = this.arr[head];                                   // Get the item from the head, move the head pointer
        head = (head + 1) % this.arr.length;
        size--;
        return value;
    }

    /**
     * Returns the item at the head of the queue without removing it
     * 
     * @return The item at the head
     * @throws IllegalStateException if the queue is empty
     */
    public T peek() {
        if (isEmpty())
            throw new IllegalStateException("Queue is empty.");
        return this.arr[head];                                      // Simply return the head without changing pointer
    }

    /**
     * Returns the string representation of the Circular Queue
     * 
     * @return The formatteed string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            int index = (head + i) % arr.length;                    // Compute the correct index
            sb.append(arr[index]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Checks if the given object is equal to the current object
     * 
     * @param o The object to be compared
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CircularQueue<?> that = (CircularQueue<?>) o;
        if (this.size != that.size) return false;
        if (this.head != that.head) return false;
        if (this.tail != that.tail) return false;
        for (int i = 0; i < this.size; i++)
            if (this.arr[i] != that.arr[i]) return false;
        return true;
    }
}
