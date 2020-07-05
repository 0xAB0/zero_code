package zerocode.collections;

/*
 * An simple array list interface
 */
public interface List<T> extends Iterable<T> {

    void add(T value);
    T get(int value);
    void insert(T value, int position);
    int size();
    void remove(T value);
    void remove(int position);

}
