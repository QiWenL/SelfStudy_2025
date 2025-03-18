package deque;


import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Array;

/**
 * Created by hug.
 */
public class ArrayDequeTest {
    // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemoveForward() {
        ArrayDeque<Integer> Test = new ArrayDeque<>();
        /* first addition */
        Test.addLast(4);
        /* second addition */
        Test.addLast(5);
        /* third addition */
        Test.addLast(6);
        assertEquals(3, Test.size());
        assertEquals("6", Test.removeLast().toString());
        //should not be empty
        assertFalse("Test should be empty after removal", Test.isEmpty());
        assertEquals("5", Test.removeLast().toString());
        // should not be empty
        assertFalse("Test should be empty after removal", Test.isEmpty());
        assertEquals("4", Test.removeLast().toString());
        // should be empty
        assertTrue("Test should be empty after removal", Test.isEmpty());
    }
    @Test
    public void testThreeAddThreeRemoveBackward() {
        ArrayDeque<Integer> Test = new ArrayDeque<>();
        /* first addition */
        Test.addFirst(4);
        /* second addition */
        Test.addFirst(5);
        /* third addition */
        Test.addFirst(6);
        assertEquals(3, Test.size());
        assertEquals("6", Test.removeFirst().toString());
        //should not be empty
        assertFalse("Test should be empty after removal", Test.isEmpty());
        assertEquals("5", Test.removeFirst().toString());
        // should not be empty
        assertFalse("Test should be empty after removal", Test.isEmpty());
        assertEquals("4", Test.removeFirst().toString());
        // should be empty
        assertTrue("Test should be empty after removal", Test.isEmpty());
    }
    @Test
    public void randomizedTest() {
        ArrayDeque<Integer> Test = new ArrayDeque<>();
        int N = 1000;
        int correct_size = 0;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
                if (operationNumber == 0) {
                    // addLast
                    int randVal = StdRandom.uniform(0, 100);
                    Test.addLast(randVal);
                    correct_size= correct_size+1;
                    assertEquals(correct_size, Test.size());
                }else if (operationNumber == 1) {
                    // removeLast
                    if (correct_size>0) {
                        Test.removeLast();
                        correct_size = correct_size -1;
                        assertEquals(correct_size, Test.size());
                    } else continue;
                }
                else if (operationNumber == 2) {
                    // addLast
                    int randVal = StdRandom.uniform(0, 100);
                    Test.addFirst(randVal);
                    correct_size= correct_size+1;
                    assertEquals(correct_size, Test.size());
                }else if (operationNumber == 3) {
                    // removeLast
                    if (correct_size>0) {
                        Test.removeFirst();
                        correct_size = correct_size -1;
                        assertEquals(correct_size, Test.size());
                    } else continue;
            }
            }
        }
    }

