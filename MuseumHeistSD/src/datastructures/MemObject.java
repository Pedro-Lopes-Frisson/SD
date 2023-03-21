package datastructures;

import java.lang.reflect.Array;
import java.util.concurrent.locks.ReentrantLock;

public abstract class MemObject<T> {
    T[] arr;

    final int capacity;

    int write_pos;

    int read_pos;

    int elemsCount;

    public abstract T read();

    public abstract void write(T elem);
    public MemObject(Class<T> tClass, int capacity){
        this.arr = (T[]) Array.newInstance(tClass, capacity);
        this.capacity = capacity;
        this.write_pos = 0;
        this.read_pos = 0;
        elemsCount = 0;
    }
}
