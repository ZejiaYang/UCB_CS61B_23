import deque.MaxArrayDeque;
import org.junit.jupiter.api.*;
import deque.Deque;
import deque.ArrayDeque;
import deque.LinkedListDeque;

import java.net.CookieManager;
import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
public class MaxArrayDequeTest {
    @Test
    public void addLastTestBasicWithoutToList() {
        Deque<String> lld1 = new LinkedListDeque<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        assertThat(lld1).containsExactly("front", "middle", "back");
    }

    @Test
    public void addFirstTestBasicWithoutToList() {
        Deque<String> lld1 = new ArrayDeque<>();

        lld1.addFirst("back");
        lld1.addFirst("middle");
        lld1.addFirst("front");
        assertThat(lld1).containsExactly("front", "middle","back");

        for (String s : lld1) {
            System.out.println(s);
        }
    }

    @Test
    public void testEqualDeques() {
        Deque<String> lld1 = new LinkedListDeque<>();
        Deque<String> lld2 = new LinkedListDeque<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1.equals(lld2)).isTrue();

        lld2.addLast("wula");
        assertThat(lld1.equals(lld2)).isFalse();
    }
    @Test
    public void testArrayEqualDeques() {
        Deque<String> lld1 = new ArrayDeque<>();
        Deque<String> lld2 = new ArrayDeque<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1.equals(lld2)).isTrue();

        lld2.addLast("wula");
        assertThat(lld1.equals(lld2)).isFalse();
    }

    @Test
    public void testToString() {
        Deque<String> lld1 = new ArrayDeque<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        System.out.println(lld1);
    }

    @Test
    public void testMax() {
        MaxArrayDeque<String> lld = new MaxArrayDeque<String>(new Comparator<String>() {
            public int compare(String a1, String a2) {
                return a1.compareTo(a2);
            }
        });
        lld.addLast("front");
        lld.addLast("middle");
        lld.addLast("back");
        assertThat(lld.max().equals("middle")).isTrue();

        MaxArrayDeque<String> lld1 = new MaxArrayDeque<String>(String::compareTo);
        lld1.addLast("a");
        lld1.addLast("m");
        lld1.addLast("k");
        lld1.addFirst("x");
        assertThat(lld1.max().equals("x")).isTrue();
    }

    @Test
    public void testArbitraryCmp() {
        MaxArrayDeque<String> lld = new MaxArrayDeque<String>(new Comparator<String>() {
            public int compare(String a1, String a2) {
                return a1.compareTo(a2);
            }
        });
        lld.addLast("frontier");
        lld.addLast("middle");
        lld.addLast("back");
        assertThat(lld.max((String a1, String a2) -> a1.length() - a2.length()).equals("frontier")).isTrue();
    }
}
