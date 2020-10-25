package me.leon.multithread.threadcreation;

public class ThreadCreation {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //Code that will run in a new thread
                System.out.println("We are now in thread" + Thread.currentThread());
                System.out.println("Current thread priority is " + Thread.currentThread().getPriority());
            }
        });

        thread.setName("New Worker Thread");

        thread.setPriority(Thread.MAX_PRIORITY);

        System.out.println("We are in thread:" + Thread.currentThread().getName() + "before starting a new thread");
        thread.start();
        System.out.println("We are in thread:" + Thread.currentThread().getName() + "after starting a new thread");

        Thread.sleep(10000);
    }
}
