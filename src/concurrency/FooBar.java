package concurrency;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * LeetCode 1115 - Print FooBar Alternately
 *
 * <p>Two threads share a single FooBarinstance. One calls foo() and the other
 * calls bar(). They must interleave their output so the combined result is
 * "foobar" repeated n times.
 *
 * <p>Approach: two semaphores acting as a hand-off signal.
 * <ul>
 *   <li>{@code fooSem} starts at 1  → foo thread goes first.</li>
 *   <li>{@code barSem} starts at 0  → bar thread blocks until foo releases it.</li>
 *   <li>After each print the thread releases the other's semaphore.</li>
 * </ul>
 *
 * Time  complexity : O(n)
 * Space complexity : O(1)
 */
public class FooBar {

    private final int n;
    private final Semaphore fooSem = new Semaphore(1); // foo goes first
    private final Semaphore barSem = new Semaphore(0); // bar waits initially

    public FooBar(int n) {
        this.n = n;
    }

    /**
     * Called by the "foo" thread {@code n} times.
     *
     * @param printFoo runnable that prints "foo"
     */
    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            fooSem.acquire();   // wait for our turn
            printFoo.run();     // print "foo"
            barSem.release();   // signal bar thread
        }
    }

    /**
     * Called by the "bar" thread {@code n} times.
     *
     * @param printBar consumer that prints "bar" (receives the iteration index)
     */
    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            barSem.acquire();   // wait for foo to finish its turn
            printBar.run();     // print "bar"
            fooSem.release();   // signal foo thread
        }
    }
}
