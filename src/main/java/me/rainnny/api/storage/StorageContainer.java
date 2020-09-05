package me.rainnny.api.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Braydon
 */
public class StorageContainer<E> {
    public static final int DEFAULT_CAPACITY = 10;

    protected StorageElement[] elements;
    protected int size;

    public StorageContainer() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Create a new {@code StorageContainer}
     * @param capacity - The initial capacity of the container
     */
    public StorageContainer(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Cannot initialize with a capacity of " + capacity + ", it must be greater than 0");
        elements = new StorageElement[capacity];
        size = 0;
    }

    /**
     * Add an element to the container
     * @param key - The key of the element you would like to add
     * @param element - The value of the element you would like to add
     */
    public void add(String key, E element) {
        if (isFull())
            copy(IncreaseAction.DOUBLE, 0);
        elements[size] = new StorageElement(key, element);
        size++;
    }

    /**
     * Add an element to the container at the provided index
     * @param index - The index of where you would like to add your element
     * @param key - The key of the element you would like to add
     * @param element - The value of the element you would like to add
     */
    public void add(int index, String key, E element) {
        if (isFull())
            copy(IncreaseAction.DOUBLE, 0);
        if (index < 0 || index >= elements.length)
            throw new ArrayIndexOutOfBoundsException("Cannot add a new element at index " + index);
        StorageElement temp = elements[index];
        elements[index] = new StorageElement(key, element);

        StorageElement object;

        for (int i = index; i < elements.length - 1; i++) {
            object = elements[i + 1];
            elements[i + 1] = temp;
            temp = object;
        }

        copy(IncreaseAction.INCREMENT, 0);
        size++;
    }

    /**
     * Get an element at the provided index
     * @param index - The index of the element you would like to get
     * @return the element at the provided index
     */
    public E get(int index) {
        if (index < 0 || index >= elements.length)
            throw new ArrayIndexOutOfBoundsException("Cannot get the element at index " + index);
        return (E) elements[index].getValue();
    }

    /**
     * Get an element with the provided key
     * @param key - The key of the element you would like to get
     * @return the element at the provided key
     */
    public E get(String key) {
        int index = indexOf(key);
        if (index == -1)
            throw new ArrayIndexOutOfBoundsException("Cannot get the element at index " + index + " with key '" + key + "'");
        return (E) elements[index].getValue();
    }

    /**
     * Get a list of elements
     * @return the list of elements
     */
    public List<StorageElement> getElements() {
        List<StorageElement> elements = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            StorageElement element = this.elements[i];
            if (element == null)
                continue;
            elements.add(i, element);
        }
        return elements;
    }

    /**
     * Get a list of keys
     * @return the list of keys
     */
    public List<String> getKeys() {
        List<String> keys = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            StorageElement element = elements[i];
            if (element == null)
                continue;
            keys.add(i, element.getKey());
        }
        return keys;
    }

    /**
     * Get a list of values
     * @return the list of values
     */
    public List<E> getValues() {
        List<E> values = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            StorageElement element = this.elements[i];
            if (element == null)
                continue;
            values.add(i, (E) element.getValue());
        }
        return values;
    }

    /**
     * Remove the element matching the provided key
     * @param key - The key of the element you would like to remove
     */
    public void remove(String key) {
        remove(indexOf(key));
    }

    /**
     * Remove the provided element
     * @param element - The element you would like to remove
     */
    public void remove(E element) {
        remove(indexOf(element));
    }

    /**
     * Remove the element at the provided index
     * @param index - The index of the element you would like to remove
     */
    public void remove(int index) {
        if (index == -1)
            throw new IllegalArgumentException("You cannot remove an element that is not in the container");
        elements[index] = null;
        size--;
        copy(IncreaseAction.INCREMENT, 0);
    }

    /**
     * Returns whether or not the container contains an element
     * matching the provided key
     * @param key - The key of the element you would like to check
     * @return whether or not the container contains an element
     *         matching the provided key
     */
    public boolean contains(String key) {
        return indexOf(key) >= 0;
    }

    /**
     * Returns whether or not the container contains the provided element
     * @param element - The element you would like to check
     * @return whether or not the container contains the provided element
     */
    public boolean contains(E element) {
        return indexOf(element) >= 0;
    }

    /**
     * Get the index of the element matching the provided key
     * @param key - The key of the element you would like to get the index for
     * @return the index of the element matching the provided key, -1 if none
     */
    public int indexOf(String key) {
        for (int index = 0; index < elements.length; index++) {
            if (elements[index].getKey().equals(key)) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Get the index of the provided element
     * @param element - The element you would like to get the index for
     * @return the index of the provided element, -1 if none
     */
    public int indexOf(E element) {
        for (int index = 0; index < elements.length; index++) {
            if (elements[index].getValue().equals(element)) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Returns whether or not the container is full
     * @return whether or not the container is full
     */
    public boolean isFull() {
        return size >= elements.length;
    }

    /**
     * Returns whether or not the container is empty
     * @return whether or not the container is empty
     */
    public boolean isEmpty() {
        return elements.length <= 0;
    }

    /**
     * Returns the amount of elements in the container
     * @return the amount of elements in the container
     */
    public int size() {
        return size;
    }

    /**
     * Cleans up the container and removes all of it's elements
     */
    public void cleanup() {
        Arrays.fill(elements, null);
        size = 0;
    }

    private void copy(IncreaseAction action, int amount) {
        amount = increaseSize(action, amount);
        StorageElement[] temp = new StorageElement[amount];
        int element = 0;
        for (int i = 0; i < elements.length; i++, element++) {
            if (elements[i] == null) {
                element--;
                continue;
            }
            temp[element] = elements[i];
        }
        elements = null;
        elements = new StorageElement[temp.length];
        elements = temp;
    }

    private int increaseSize(IncreaseAction action, int amount) {
        if (action == IncreaseAction.DOUBLE)
            amount = elements.length * 2;
        else amount = elements.length + amount;
        return amount;
    }

    @Override
    public String toString() {
        return "StorageContainer{" +
                "elements=" + Arrays.toString(elements) +
                ", size=" + size +
                '}';
    }

    private enum IncreaseAction {
        DOUBLE, INCREMENT
    }
}