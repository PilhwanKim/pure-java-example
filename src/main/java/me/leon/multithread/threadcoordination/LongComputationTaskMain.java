package me.leon.multithread.threadcoordination;

import java.math.BigInteger;

public class LongComputationTaskMain {
    public static void main(String[] args) {
        Thread thread = new Thread(new LongComputationTask(new BigInteger("200000"), new BigInteger("1000000000")));

        thread.start();

        thread.interrupt();
    }

    private static class LongComputationTask implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + "=" + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO ; i.compareTo(power) != 0 ; i = i.add(BigInteger.ONE)) {
                // if 문을 주석처리하면 꺼지지 않음
                if(Thread.currentThread().isInterrupted()) {
                    System.out.println("Prematurely interrupted computation");
                    return BigInteger.ONE;
                }
                result = result.multiply(base);
            }

            return result;
        }
    }
}
