import jh61b.utils.Reflection;
import org.apache.commons.collections.list.AbstractLinkedList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {

//     @Test
//     @DisplayName("ArrayDeque has no fields besides backing array and primitives")
//     void noNonTrivialFields() {
//         List<Field> badFields = Reflection.getFields(ArrayDeque.class)
//                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
//                 .toList();
//
//         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
//     }
    @Test
    /** In this test, we have three different assert statements that verify that addFirst works correctly. */
    public void addFirstTestBasic() {
        Deque<String> lld1 = new ArrayDeque<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(lld1.toList()).containsExactly("back").inOrder();

        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

             /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
                to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
                but not ["front", "middle", "back"].
              */
    }

    @Test
    /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
     *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
    public void addLastTestBasic() {
        Deque<String> lld1 = new ArrayDeque<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    /** This test performs interspersed addFirst and addLast calls. */
    public void addFirstAndAddLastTest() {
        Deque<Integer> lld1 = new ArrayDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
    }

    @Test
    /** This test performs interspersed addFirst & addLast  and requires resize. */
    public void addFirstAndResizeTest() {
        Deque<Integer> lld1 = new ArrayDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]
        lld1.addFirst(-8); // [-8, -2, -1, 0, 1, 2]
        lld1.addLast(10);  // [-8, -2, -1, 0, 1, 2, 10]
        lld1.addLast(1);   // [-8, -2, -1, 0, 1, 2, 10, 1]
        lld1.addLast(70);  // [-8, -2, -1, 0, 1, 2, 10, 1, 70]
        lld1.addFirst(4);  // [4, -8, -2, -1, 0, 1, 2, 10, 1, 70]

        assertThat(lld1.toList()).containsExactly(4, -8, -2, -1, 0, 1, 2, 10, 1, 70).inOrder();
    }
    @Test
    public void addComplexResizeTest() {
        Deque<Integer> lld1 = new ArrayDeque<>();
        List<Integer> truth = new ArrayList<>(15);
        for (int i = 0; i < 15; i += 1) {
            lld1.addLast(0);
            truth.add(0);
        }

        assertThat(lld1.toList()).containsExactlyElementsIn(truth).inOrder();
    }
    @Test
    public void testValidget() {
        Deque<Integer> lld1 = new ArrayDeque<>();

        /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]
        assertThat(lld1.get(0) == -2).isTrue();
        assertThat(lld1.get(3) == 1).isTrue();

        lld1.addFirst(-8); // [-8, -2, -1, 0, 1, 2]
        lld1.addLast(10);  // [-8, -2, -1, 0, 1, 2, 10]
        lld1.addLast(1);   // [-8, -2, -1, 0, 1, 2, 10, 1]
        lld1.addLast(70);  // [-8, -2, -1, 0, 1, 2, 10, 1, 70]
        lld1.addFirst(4);  // [4, -8, -2, -1, 0, 1, 2, 10, 1, 70]
        assertThat(lld1.get(9) == 70).isTrue();

        lld1.addLast(99);
        lld1.addLast(98);
        lld1.addLast(97);
        lld1.addLast(96);
        assertThat(lld1.get(13) == 96).isTrue();
    }

    @Test
    public void testNullget() {
        Deque<Integer> lld1 = new ArrayDeque<>();
        assertThat(lld1.get(0)).isNull();

        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]
        assertThat(lld1.get(10)).isNull();
    }

    @Test
    public void testIsEmpty() {
        Deque<Integer> lld1 = new ArrayDeque<>();
        assertThat(lld1.isEmpty()).isTrue();
    }

    @Test
    public void testNonEmpty() {
        Deque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(1);
        lld1.addLast(2);
        assertThat(lld1.isEmpty()).isFalse();
    }

    @Test
    public void testVaryEmpty() {
        Deque<Integer> lld1 = new ArrayDeque<>();
        assertThat(lld1.isEmpty()).isTrue();

        lld1.addFirst(2);
        lld1.addLast(3);
        assertThat(lld1.isEmpty()).isFalse();

        lld1.removeFirst();
        lld1.removeLast();
        assertThat(lld1.isEmpty()).isTrue();
    }

    @Test
    public void testEmptySize() {
        Deque<Integer> lld1 = new ArrayDeque<>();
        assertThat(lld1.size() == 0).isTrue();
    }
    @Test
    public void testAddSize() {
        Deque<Integer> lld1 = new ArrayDeque<>();

        lld1.addFirst(1);
        lld1.addLast(2);
        assertThat(lld1.size() == 2).isTrue();
    }
    @Test
    public void testVarySize() {
        Deque<Integer> lld1 = new ArrayDeque<>();

        lld1.addFirst(1);
        lld1.addLast(2);
        lld1.removeLast();
        assertThat(lld1.size() == 1).isTrue();
    }
    @Test
    public void testVaryNonSize() {
        Deque<Integer> lld1 = new ArrayDeque<>();

        lld1.addFirst(1);
        lld1.addLast(2);
        lld1.removeLast();
        lld1.removeLast();

        assertThat(lld1.size() == 0).isTrue();
    }

    @Test
    public void removeFirstTestBasic() {
        Deque<String> lld1 = new ArrayDeque<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(lld1.toList()).containsExactly("back").inOrder();

        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly("back").inOrder();

        lld1.removeFirst();
        assertThat(lld1.removeFirst()).isNull();
    }

    @Test
    public void removeLastTestBasic() {
        Deque<String> lld1 = new ArrayDeque<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(lld1.toList()).containsExactly("back").inOrder();

        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly("front", "middle").inOrder();

        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly("front").inOrder();

        lld1.removeLast();
        assertThat(lld1.removeLast()).isNull();
    }

    @Test
    public void removeFirstAndRemoveLastTest() {
        Deque<Integer> lld1 = new ArrayDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        lld1.removeLast();
        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly(-1, 0, 1).inOrder();
    }

    @Test
    public void removeFirstInvolveResize() {
        Deque<Integer> lld1 = new ArrayDeque<>();

        for (int i = 0; i < 10; i += 1) {
            lld1.addLast(i);
        }

        for (int j = 0; j < 7; j += 1) {
            lld1.removeFirst();
        }

        assertThat(lld1.toList()).containsExactly(7, 8, 9).inOrder();
    }

    @Test
    public void removeLastInvolveResize() {
        Deque<Integer> lld1 = new ArrayDeque<>();

        for (int i = 0; i < 14; i += 1) {
            lld1.addLast(i);
        }

        for (int j = 0; j < 11; j += 1) {
            lld1.removeLast();
        }

        assertThat(lld1.toList()).containsExactly(0, 1, 2).inOrder();
    }
    @Test
    public void removeLastWithoutDownsize() {
        Deque<Integer> lld1 = new ArrayDeque<>();

        for (int i = 0; i < 14; i += 1) {
            lld1.addLast(i);
        }

        for (int j = 0; j < 7; j += 1) {
            lld1.removeLast();
        }
        assertThat(lld1.toList()).containsExactly(0, 1, 2, 3, 4, 5, 6).inOrder();
    }

    @Test
    public void removeFirstWithoutDownsize() {
        Deque<Integer> lld1 = new ArrayDeque<>();

        for (int i = 0; i < 14; i += 1) {
            lld1.addLast(i);
        }

        for (int j = 0; j < 7; j += 1) {
            lld1.removeFirst();
        }
        assertThat(lld1.toList()).containsExactly(7, 8, 9, 10, 11, 12, 13).inOrder();
    }

    @Test
    public void removeFull() {
        Deque<Integer> lld1 = new ArrayDeque<>();

        for (int i = 0; i < 8; i += 1) {
            lld1.addLast(i);
        }
        lld1.removeLast();
        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly(1, 2, 3, 4, 5, 6).inOrder();
    }

}
