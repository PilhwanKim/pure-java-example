package me.leon.multithread.concurrency_solution;

import java.util.Random;

public class MetricsMain {
    public static void main(String[] args) {
        Metrics metrics = new Metrics();
        MinMaxMetrics minMaxMetrics = new MinMaxMetrics();

        BusinessLogic businessLogicThread1 = new BusinessLogic(metrics, minMaxMetrics);
        BusinessLogic businessLogicThread2 = new BusinessLogic(metrics, minMaxMetrics);

        MetricsPrinter metricsPrinter = new MetricsPrinter(metrics, minMaxMetrics);

        businessLogicThread1.start();
        businessLogicThread2.start();
        metricsPrinter.start();
    }

    public static class MetricsPrinter extends Thread {
        private Metrics metrics;
        private MinMaxMetrics minMaxMetrics;

        public MetricsPrinter(Metrics metrics, MinMaxMetrics minMaxMetrics) {
            this.metrics = metrics;
            this.minMaxMetrics = minMaxMetrics;
        }

        @Override
        public void run() {
            while(true) {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }

                double average = metrics.getAverage();
                long max = minMaxMetrics.getMax();
                long min = minMaxMetrics.getMin();

                System.out.println("Current Average is : " + average + " Min is : " + min + " Max is : " + max);
            }
        }
    }

    public static class BusinessLogic extends Thread {
        private Metrics metrics;
        private MinMaxMetrics minMaxMetrics;
        private Random random = new Random();

        public BusinessLogic(Metrics metrics, MinMaxMetrics minMaxMetrics) {
            this.metrics = metrics;
            this.minMaxMetrics = minMaxMetrics;
        }

        @Override
        public void run() {
            while(true) {
                long start = System.currentTimeMillis();

                try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {
                }

                long end = System.currentTimeMillis();

                long sample = end - start;
                metrics.addSample(sample);
                minMaxMetrics.addSample(sample);
            }
        }
    }

    public static class Metrics {
        private long count = 0;
        private volatile double average = 0.0;

        public synchronized void addSample(long sample) {
            double currentSum = average * count;
            count++;
            average = (currentSum + sample) / count;
        }

        public double getAverage() {
            return average;
        }
    }

    public static class MinMaxMetrics {
        private volatile long min;
        private volatile long max;

        /**
         * Initializes all member variables
         */
        public MinMaxMetrics() {
            min = Long.MAX_VALUE;
            max = Long.MIN_VALUE;
        }

        /**
         * Adds a new sample to our metrics.
         */
        public void addSample(long newSample) {
            synchronized (this) {
                min = Math.min(min, newSample);
                max = Math.max(max, newSample);
            }
        }

        /**
         * Returns the smallest sample we've seen so far.
         */
        public long getMin() {
            return min;
        }

        /**
         * Returns the biggest sample we've seen so far.
         */
        public long getMax() {
            return max;
        }
    }

}
