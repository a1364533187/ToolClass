package cn.dyz.tools.file.lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * 自己实现lock
 */
public class ZhiwuLock implements Lock {

    private AtomicReference<Thread> lockHolder = new AtomicReference<>(); //默认值是null
    private LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();

    @Override
    public void lock() {
        //抢锁时，我期望当前的lockHolder的线程是null, 这样我可以当前lockHolder设置为当前的线程。
        while (!lockHolder.compareAndSet(null, Thread.currentThread())) {
            waiters.add(Thread.currentThread());
            LockSupport.park();
            waiters.remove(Thread.currentThread());
        }
        //当前的线程抢锁成功
    }

    @Override
    public void unlock() {
        //释放锁时， 我期望当前的lockHolder的线程时当前的线程， 这样我可以释放掉锁， 将lockHolder的线程置为null
        while (lockHolder.compareAndSet(Thread.currentThread(), null)) {
            for (Object waiter : waiters.toArray()) {
                LockSupport.unpark((Thread) waiter);
            }
        }
        //不是当前lockHolder的线程，不用处理
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
