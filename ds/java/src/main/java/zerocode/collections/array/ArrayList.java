package zerocode.collections.array;

import zerocode.collections.List;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayList<T> implements List<T> {
    private T [] underlying;
    private int size;

    public ArrayList() {
        this(10);
    }

    public ArrayList(int capacity) {
        underlying = (T[])new Object[capacity];
    }

    @Override
    public void add(T value) {
        checkSize(size+1);
        underlying[size++] = value;
    }

    @Override
    public T get(int value) {
        return underlying[value];
    }

    @Override
    public void insert(T value, int position) {
        if(position < 0) {
            throw new IllegalArgumentException("Index cannot be less than zero");
        }
        checkSize(position >= size ? position : size+1); //Ensure we can fit the value
        //Make a space
        shiftRight(position);
        underlying[position] = value;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void remove(T value) {
        for(int c=0;c<size;++c) {
            if(value.equals(underlying)) {
                remove(c);
                return;
            }
        }
    }

    @Override
    public void remove(int position) {
        shiftLeft(position);
        size--;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    //
    private void shiftRight(int from) {
        checkSize(size+1); // Grow if necessary
        for(int c=size;c>=from && c>=1;c--) {
            underlying[c] = underlying[c-1];
        }
    }

    private void shiftLeft(int from) {
        for(int c=from;c<size;++c) {
            underlying[c] = underlying[c+1];
        }
    }

    /*
     * Implement growing via a strategy.
     */
    private void checkSize(int needed) {
        if(underlying.length < needed) {
            underlying = Arrays.copyOf(underlying, needed); // Todo - growth factor?
        }
    }
}
