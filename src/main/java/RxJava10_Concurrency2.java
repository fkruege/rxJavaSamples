import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import utilities.Helper;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.ObjDoubleConsumer;

/**
 * Created by fkruege on 3/26/16.
 */

// http://tomstechnicalblog.blogspot.com/2015/11/rxjava-achieving-parallelization.html
public class RxJava10_Concurrency2 {
    public static final String TAG = RxJava10_Concurrency2.class.getSimpleName();

    private final Object lock = new Object();

    private boolean mWakeup = false;

    public RxJava10_Concurrency2() {

    }

    // test concurrency
    public synchronized void run() {
        Helper.printHeader(TAG);

        Helper.printHeader("Example 3: trying concurrency but this does not work.");
        Observable<Integer> vals = Observable.range(1, 10);
        Subscription subscription = vals.subscribeOn(Schedulers.computation())
                .map(i -> intenseCalculation(i))
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                        synchronized (lock) {
                            mWakeup = true;
                            lock.notifyAll();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("Subscriber received "
                                + integer + " on "
                                + Thread.currentThread().getName());
                    }
                });

        synchronized (lock) {
            while (!mWakeup) {
                try {
                    System.out.println("Waiting");
                    lock.wait();
                    System.out.println("Woke up");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static int intenseCalculation(int i) {
        try {
            System.out.println("Calculating " + i +
                    " on " + Thread.currentThread().getName());
            Thread.sleep(ThreadLocalRandom.current().nextInt(250, 500));
            return i;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
