package deque;
import java.util.Comparator;
public class MaxArrayDeque<T> extends ArrayDeque<T>{
    private Comparator<T> cmp;
    public MaxArrayDeque(Comparator<T> cmp) {
        this.cmp = cmp;
    }

    public T max() {
        if (this.isEmpty()) {
            return null;
        }
        int i = (this.nextFirst + 1) % size;
        T ans = item[i];
        for (int k = 0; k < length; k += 1) {
            if (cmp.compare(ans, item[i]) < 0) {
                ans = item[i];
            }
            i = (i + 1) % size;
        }
        return ans;
    }

    public T max(Comparator<T> current) {
        if (this.isEmpty()) {
            return null;
        }
        int i = (this.nextFirst + 1) % size;
        T ans = item[i];
        for (int k = 0; k < length; k += 1) {
            if (current.compare(ans, item[i]) < 0) { //not negative
                ans = item[i];
            }
            i = (i + 1) % size;
        }
        return ans;
    }
}
