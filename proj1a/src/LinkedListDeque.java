import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class LinkedListDeque<T> implements Deque<T>{
    private static class Node<T>{
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

        public Node(){
            this.item = null;
            this.prev = null;
            this.next = null;
        }
    }
    private Node<T> sentinel;

    private int size;

    public LinkedListDeque(){
        sentinel = new Node<>();
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }
    @Override
    public void addFirst(T x) {
        Node<T> preFirst = sentinel.next;
        sentinel.next = new Node<>(x, sentinel, preFirst);
        preFirst.prev = sentinel.next;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        Node<T> preLast = sentinel.prev;
        sentinel.prev = new Node<>(x, preLast, sentinel);
        preLast.next = sentinel.prev;
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node<T> node = sentinel.next;
        while (node != sentinel) {
            returnList.add(node.item);
            node = node.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next == sentinel && sentinel.prev == sentinel;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T item = sentinel.next.item;
        Node<T> curFirst = sentinel.next.next;
        sentinel.next = curFirst;
        curFirst.prev = sentinel;
        size -= 1;
        return item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T item = sentinel.prev.item;
        Node<T> curLast = sentinel.prev.prev;
        sentinel.prev= curLast;
        curLast.next = sentinel;
        size -= 1;
        return item;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null; /* index starts at 0 */
        }

        Node<T> node = sentinel.next;

        while (index > 0 && node != sentinel) {
            index -= 1;
            node = node.next;
        }

        return node.item;
    }

    @Override
    public T getRecursive(int index) {
        return getRecursiveHelper(sentinel.next, index);
    }

    private T getRecursiveHelper(Node<T> node, int index) {
        if (node == sentinel) {
            return null;
        }

        if (index == 0) {
            return node.item;
        }

        return getRecursiveHelper(node.next, index - 1);
    }
    public static void main(String[] args){
        Deque<String> lld1 = new LinkedListDeque<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]

        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]

        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
    }
}
