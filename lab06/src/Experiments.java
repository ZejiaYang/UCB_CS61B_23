import edu.princeton.cs.algs4.Stopwatch;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Experiments {

    private static void printTimingTable(TimingData data) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < data.getNs().size(); i += 1) {
            int N = data.getNs().get(i);
            double time = data.getTimes().get(i);
            int opCount = data.getOpCounts().get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static TimingData timeFindPC() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        for (int N = 1000; N <= 12800000; N *= 2) {
            Ns.add(N);
            opCounts.add(N);
            UnionFind uf = new UnionFind(N);
            for (int j = 1; j < N; j += 1) {
                uf.union(j - 1, j);
            }
            Stopwatch sw = new Stopwatch();
            for (int i = N - 1; i >= 0; i -= 1) {
                int t = uf.find(i);
            }
            times.add(sw.elapsedTime());
        }

        return new TimingData(Ns, times, opCounts);
    }

    public static TimingData timeFindSingle() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();
        UnionFind uf = new UnionFind(128000);

        for (int j = 1; j < 128000; j += 1) {
            uf.union(j - 1, j);
        }

        for (int N = 128000 - 1; N >= 0; N -= 100) {
            Ns.add(N);
            opCounts.add(1);
            Stopwatch sw = new Stopwatch();
            int t = uf.find(N);
            times.add(sw.elapsedTime());
        }

        return new TimingData(Ns, times, opCounts);
    }

    public static TimingData timeUnionSingle() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();
        UnionFind uf = new UnionFind(64000);
        for (int N = 1; N < 32000; N += 2) {
            uf.union(N + 1, N);
        }
        for (int N = 32000 + 1; N < 64000 - 1; N += 2) {
            uf.union(N + 1, N);
        }

        for (int N = 2; N < 64000; N += 2) {
            uf.union(N - 2, N);
        }

        for (int N = 1; N < 64000; N += 2) {
            Ns.add(N);
            opCounts.add(1);
            Stopwatch sw = new Stopwatch();
            uf.find(N);
            times.add(sw.elapsedTime());
        }
        System.out.println(uf.parent(64000 - 999));
        System.out.println(uf.parent(63002));
        return new TimingData(Ns, times, opCounts);
    }
    public static void main(String[] args) {
        // TODO: Modify the following line to change the experiment you're running
        TimingData td = timeUnionSingle();
        // Modify this line to make the chart title make sense
        String title = "Union Single find()";

        // Convert "times" (in seconds) and "opCounts" to nanoseconds / op
        List<Double> timesUsPerOp = new ArrayList<>();
        for (int i = 0; i < td.getTimes().size(); i++) {
            timesUsPerOp.add(td.getTimes().get(i) / td.getOpCounts().get(i) * 1e6);
        }

        // printTimingTable(td);

        XYChart chart = QuickChart.getChart(title, "N", "time (us per op)", "Time", td.getNs(), timesUsPerOp);
        new SwingWrapper(chart).displayChart();
    }
}
