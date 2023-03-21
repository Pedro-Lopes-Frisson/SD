package datastructures;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.stream.Collectors;

public class MemFIFO<T> extends MemObject<T> {
    @Override
    public synchronized T read() {
        while (elemsCount == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        int pos = read_pos;
        read_pos = (read_pos + 1)% capacity;
        elemsCount--;
        notifyAll();
        return arr[pos];
    }

    @Override
    public synchronized void write(T elem) {
        while (elemsCount == capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        arr[write_pos] = elem;
        write_pos = (write_pos + 1) % capacity;
        elemsCount++;

        notifyAll();
    }

    public synchronized int size(){
        return elemsCount;
    }

    public synchronized boolean isFull(){
        return elemsCount == capacity ;
    }
    public synchronized boolean isEmpty(){
        return elemsCount == 0 ;
    }

    public synchronized boolean contains(T o){
        return Arrays.stream(arr).filter((T t) -> o.equals(t)).collect(Collectors.toList()).size() >= 1;
    }


    public MemFIFO(Class<T> tClass, int capacity) {
        super(tClass, capacity);
    }
}
