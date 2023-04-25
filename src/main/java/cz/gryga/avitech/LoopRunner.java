package cz.gryga.avitech;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Concurrently run {@link Runnable} tasks in a loop.
 */
public class LoopRunner {

    private final ExecutorService pool;

    private final Set<LoopedTask> tasks = new HashSet<>();

    /**
     * Create a LoopRunner with default cached thread pool.
     */
    public LoopRunner() {
        this.pool = Executors.newCachedThreadPool();
    }

    /**
     * Run task in the loop indefinitely.
     * <p>
     * See {@link #stop()} how to stop the tasks.
     *
     */
    public void loopTask(Runnable task) {
        LoopedTask loopedTask = new LoopedTask(task);
        tasks.add(loopedTask);
        pool.submit(loopedTask);
    }

    /**
     * Stop all running tasks
     */
    public void stop(){
        tasks.forEach(LoopedTask::stop);
        pool.shutdownNow();
    }

    private static class LoopedTask implements Runnable {

        private final Runnable task;

        private boolean running = true;

        public LoopedTask(Runnable task) {
            this.task = task;
        }

        public void stop() {
            running = false;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    task.run();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
