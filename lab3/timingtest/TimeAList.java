package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
        AList <Integer> Ns = new AList<Integer>();
        AList <Integer> OpCounts = new AList<Integer>();
        AList <Double> times = new AList<Double>();
        for (int i = 1000; i <= 128000; i *= 2) {
            /* add the relative Ns */
            Ns.addLast(i);
            /* add the relative OpCount */
            OpCounts.addLast(i);
            /* Add Last Operation */
            /* start the stopwatch */
            Stopwatch sw = new Stopwatch();
            AList<Integer> TestList = new AList<Integer>();
            for (int j = 0; j < i; j ++) {
                TestList.addLast(j);
            }
            double timeInSeconds = sw.elapsedTime();
            times.addLast(timeInSeconds);
        }
        printTimingTable(Ns, times, OpCounts);
    }
}
