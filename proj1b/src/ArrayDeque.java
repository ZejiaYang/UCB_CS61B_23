import java.util.ArrayList;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {

    private T[] item;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int length;
    private int factor;
    public ArrayDeque() {
        item = (T[]) new Object[8];
        size = 8;
        nextFirst = 3;
        nextLast = 4;
        length = 0;
        factor = 2;
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
            item = resize(size * factor);
        }
        length += 1;
        item[nextFirst] = x;
        nextFirst = (nextFirst - 1 + size) % size;
    }

    @Override
    public void addLast(T x) {
        if (length == size) {
            item = resize(size * factor);
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
        return null;
    }
}
