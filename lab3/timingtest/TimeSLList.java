package timingtest;
import edu.princeton.cs.algs4.Stopwatch;


/**
 * Created by hug.
 */
public class TimeSLList {

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
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        AList <Integer> Ns = new AList<Integer>();
        AList <Integer> OpCounts = new AList<Integer>();
        AList <Double> times = new AList<Double>();
        int M = 10000;
        for (int i = 1000; i <= 128000; i *= 2) {
            /* add the relative Ns */
            Ns.addLast(i);
            OpCounts.addLast(M);
            /* add the relative OpCount */
            /* Add Last Operation */
            SLList <Integer> TestList = new SLList<Integer>( );
            for (int j = 0; j < i; j += 1) {
                TestList.addLast(j);
            }
            /* start the stopwatch */
            Stopwatch sw = new Stopwatch();
            for (int k = 0; k < M; k ++) {
                TestList.getLast();
            }
            double timeInSeconds = sw.elapsedTime();
            times.addLast(timeInSeconds);
        }
        printTimingTable(Ns, times, OpCounts);
    }
}

