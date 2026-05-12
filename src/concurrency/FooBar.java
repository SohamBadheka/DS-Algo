package concurrency;

import java.util.concurrent.Semaphore;

/**
 * LeetCode 1115 - Print FooBar Alternately
 *
 * Two semaphores act as turn signals between the two threads.
 * fooSem starts at 1 (foo prints first), barSem starts at 0 (bar waits).
 * After each print, the thread releases the other's semaphore.
 *
 * Time:  O(n)
 * Space: O(1)
 */
public class FooBar {

    private final int n;
    private final Semaphore fooSem = new Semaphore(1);
    private final Semaphore barSem = new Semaphore(0);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            fooSem.acquire();
            printFoo.run();
            barSem.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            barSem.acquire();
            printBar.run();
            fooSem.release();
        }
    }
}
