import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DelayTaskScheduler {

    private static final int CAPACITY = 50;

    ExecutorService executor = Executors.newFixedThreadPool(CAPACITY);
    DelayBlockingQueue blockingQueue = new DelayBlockingQueue();

    private volatile boolean running = true;

    static class DelayBlockingQueue {
        PriorityQueue<TimedTask> pq= new PriorityQueue<>(Comparator.comparing(TimedTask::getScheduledTime));

        Object lock = new Object();

        void add(TimedTask task) {
            synchronized (lock) {
                pq.add(task);
                if (pq.peek() == task) {
                    lock.notify();
                }
            }
        }

        TimedTask take() throws InterruptedException {
            synchronized (lock) {
                while (true) {
                    if (pq.isEmpty()) {
                        lock.wait();
                    } else {
                        TimedTask task = pq.peek();
                        if (task.shouldRunNow()) {
                            return pq.poll();
                        } else {
                            lock.wait(task.runFromNow());
                        }
                    }
                }
            }
        }
    }

    public void start() throws InterruptedException {
        while (running) {
            try {
                TimedTask timedTask = blockingQueue.take();
                executor.submit(timedTask.task);
            } catch (InterruptedException ex) {
                break;
            }
        }
    }

    public void schedule(Runnable task) {
        scheduleWithDelay(task, 0);
    }

    public void scheduleWithDelay(Runnable task, long delayMs) {
        blockingQueue.add(TimedTask.fromTask(task, delayMs));
    }

    public void stop() {
        this.running = false;
        executor.shutdown();
        //System.exit(0);
    }

    private static class TimedTask {

        private Runnable task;
        private long scheduledTime;

        public TimedTask(Runnable task, long scheduledTime) {
            this.task = task;
            this.scheduledTime = scheduledTime;
        }

        public static TimedTask fromTask(Runnable task, long delayMs) {
            long now = System.currentTimeMillis();
            return new TimedTask(task, now + delayMs);
        }

        public long getScheduledTime() {
            return scheduledTime;
        }

        public long runFromNow() {
            return scheduledTime - System.currentTimeMillis();
        }

        public boolean shouldRunNow() {
            return runFromNow() <= 0;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        class MyTask implements Runnable {
            private String name;

            public MyTask(String name) {
                this.name = name;
            }

            @Override
            public void run() {
                System.out.println(name + ":" + System.currentTimeMillis());
            }
        }

        final DelayTaskScheduler scheduler =  new DelayTaskScheduler();
        scheduler.scheduleWithDelay(new MyTask("No. 1"), 5000);
        scheduler.scheduleWithDelay(new MyTask("No. 2"), 10000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    scheduler.start();
                } catch (InterruptedException e) {

                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                scheduler.scheduleWithDelay(new MyTask("No. 3"), 200);
                scheduler.scheduleWithDelay(new MyTask("No. 4"), 1000);
                scheduler.scheduleWithDelay(new MyTask("No. 5"), 300);
                scheduler.scheduleWithDelay(new MyTask("No. 6"), 700);
                scheduler.scheduleWithDelay(new MyTask("No. 7"), 100);
                scheduler.schedule(new MyTask("No. 8"));

            }
        }).start();

        Thread.sleep(11000);
        scheduler.stop();
    }
}