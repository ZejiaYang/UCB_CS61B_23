package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ArrayDeque<T> implements Deque<T> {

    @Override
    public Iterator<T> iterator() {
        return getIterator();
    }

    private class ArrayDequeIterator<T> implements Iterator<T> {
        private int curr;
        public ArrayDequeIterator(int begins) {
            curr = begins;
        }

        @Override
        public boolean hasNext() {
            curr = (curr + 1) % size;
            return (curr != nextLast) && (item[curr] != null);
        }

        @Override
        public T next() {
            if (curr == nextFirst || item[curr] == null) {
                throw new NoSuchElementException();
            }
            return (T) item[curr];
        }
    }

    public ArrayDequeIterator<T> getIterator() {
        return new ArrayDequeIterator(nextFirst);
    }

    protected T[] item;
    protected int size;
    protected int nextFirst;
    protected int nextLast;
    protected int length;

    public ArrayDeque() {
        item = (T[]) new Object[8];
        size = 8;
        nextFirst = 3;
        nextLast = 4;
        length = 0;
    }

    private T[] resize(int size) {
        T[] renew = (T[]) new Object[size];
        int i = (nextFirst + 1) % this.size;
        for (int k = size / 4; k < size / 4 * 3; k += 1) {
            renew[k] = item[i];
            i = (i + 1) % this.size;
        }
        this.size = size;
        nextFirst = size / 4 - 1;
        nextLast = size / 4 * 3;
        return renew;
    }
    @Override
    public void addFirst(T x) {
        if (length == size) {
            item = resize(size * 2);
        }
        length += 1;
        item[nextFirst] = x;
        nextFirst = (nextFirst - 1 + size) % size;
    }

    @Override
    public void addLast(T x) {
        if (length == size) {
            item = resize(size * 2);
        }
        length += 1;
        item[nextLast] = x;
        nextLast = (nextLast + 1) % size;
    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        int i = (nextFirst + 1) % size;
        for (int k = 0; k < length; k += 1) {
            list.add(item[i]);
            i = (i + 1) % size;
        }
        return list;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public int size() {
        return length;
    }

    public T[] downsize() {
        T[] renew = (T[]) new Object[size / 2];
        int i = (nextFirst + 1) % size;
        for (int k = size / 8; k < size / 8 + length; k += 1) {
            renew[k] = item[i];
            i = (i + 1) % this.size;
        }
        size = size / 2;
        nextFirst = size / 4 - 1;
        nextLast = size / 4 + length;
        return renew;
    }

    @Override
    public T removeFirst() {
        if (length == 0) {
            return null;
        }
        nextFirst = (nextFirst + 1) % size;
        T first = item[nextFirst];
        item[nextFirst] = null;
        length -= 1;
        if (length * 4 < size && size > 8) {
            item = downsize();
        }
        return first;
    }

    @Override
    public T removeLast() {
        if (length == 0) {
            return null;
        }
        nextLast = (nextLast - 1 + size) % size;
        T last = item[nextLast];
        item[nextLast] = null;
        length -= 1;
        if (length * 4 < size && size > 8) {
            item = downsize();
        }
        return last;
    }

    @Override
    public T get(int index) {
        if (index >= length) {
            return null;
        }
        return item[(nextFirst + index + 1) % size];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof ArrayDeque list) {
            List<T> obList =  list.toList();
            List<T> thisList = this.toList();
            return obList.equals(thisList);
        }
        return false;
    }

    @Override
    public String toString() {
        return this.toList().toString();
    }
}
