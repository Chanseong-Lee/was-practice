package org.example.counter;

public class RaceConditionDemo {
    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread t1 = new Thread(counter, "Thread-1");
        Thread t2 = new Thread(counter, "Thread-2");
        Thread t3 = new Thread(counter, "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        /**
         * 1. synchronized (동기화) 를 사용하지 않은 결과
         * Value for Thread After increment Thread-2 2
         * Value for Thread After increment Thread-1 1
         * Value for Thread After increment Thread-3 3
         * Value for Thread at last Thread-1 1
         * Value for Thread at last Thread-2 2
         * Value for Thread at last Thread-3 0
         *
         * -> 싱글톤 객체에서는 stateful 하게 관리하면 예상치 못한 결과가 나옴.
         * -> race condition : 여러 프로세스 혹은 쓰레드가 동시에 하나의 자원에 접근하려고 하는 상태
         *
         * 1. synchronized (동기화) 를 사용한 결과
         * Value for Thread After increment Thread-1 1
         * Value for Thread at last Thread-1 0
         * Value for Thread After increment Thread-3 1
         * Value for Thread at last Thread-3 0
         * Value for Thread After increment Thread-2 1
         * Value for Thread at last Thread-2 0
         *
         * -> 원하는 결과 출력 완료
         */
    }
}
