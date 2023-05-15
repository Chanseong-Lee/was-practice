package org.example.counter;

/**
 * - Servlet 객체를 싱글톤으로 관리(인스턴스 하나만 생성하여 공유하는 방식)
 * ㄴ Tread safety 하지 않기 때문에 상태를 유지(Stateful)하게 설계하면 안됨
 */
public class Counter implements Runnable {
    private int count = 0;

    public void increment() {
        count++;
    }

    public void decrement() {
        count--;
    }

    public int getValue() {
        return count;
    }

    @Override
    public void run() {
        synchronized (this) {
            this.increment();
            System.out.println("Value for Thread After increment " + Thread.currentThread().getName() + " " + this.getValue());
            this.decrement();
            System.out.println("Value for Thread at last " + Thread.currentThread().getName() + " " + this.getValue());
        }
    }
}
