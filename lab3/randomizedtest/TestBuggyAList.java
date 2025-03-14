package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> buggy = new BuggyAList<>();
        /* first addition */
        correct.addLast(4);
        buggy.addLast(4);
        /* second addition */
        correct.addLast(5);
        buggy.addLast(5);
        /* third addition */
        correct.addLast(6);
        buggy.addLast(6);
        assertEquals(correct.size(), buggy.size());
        assertEquals(correct.removeLast(), buggy.removeLast());
        assertEquals(correct.removeLast(), buggy.removeLast());
        assertEquals(correct.removeLast(), buggy.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> buggy = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
                if (operationNumber == 0) {
                    // addLast
                    int randVal = StdRandom.uniform(0, 100);
                    correct.addLast(randVal);
                    buggy.addLast(randVal);
                    assertEquals(correct.size(), buggy.size());
                }else if (operationNumber == 1) {
                    // removeLast
                    if (buggy.size() > 0 && correct.size() > 0) {
                        assertEquals(buggy.removeLast(), correct.removeLast());
                    } else continue;
                }
                else if (operationNumber == 2) {
                    // get last
                    if (buggy.size()>0 && correct.size()>0){
                        assertEquals(buggy.getLast(), correct.getLast());
                    }
                    else continue;
             } else if (operationNumber == 3) {
                    // size
                    assertEquals(buggy.size(), correct.size());
                }
            }
        }
    }

