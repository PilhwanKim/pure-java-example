package me.leon.multithread.concurrency_solution;

public class DataRaceMain {
    public static void main(String[] args) {
        SharedClass sharedClass = new SharedClass();
        Thread thread1 = new Thread(()-> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.increment();
            }
        });

        Thread thread2 = new Thread(()-> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.checkForDataRace();
            }
        });

        thread1.start();
        thread2.start();
    }

    public static class SharedClass {
        private volatile int x = 0;
        private volatile int y = 0;
        // 주석 내용과 차이점을 비교해보자.
//        private int x = 0;
//        private int y = 0;

        public void increment() {
            x++;
            y++;
        }

        public void checkForDataRace() {
            if(y > x) {
                System.out.println("Y > x - Data Race is detected");
            }
        }
    }
}
