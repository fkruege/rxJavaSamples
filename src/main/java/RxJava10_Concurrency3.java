import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import utilities.Helper;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by fkruege on 3/26/16.
 */

// http://tomstechnicalblog.blogspot.com/2015/11/rxjava-achieving-parallelization.html
public class RxJava10_Concurrency3 {
    public static final String TAG = RxJava10_Concurrency3.class.getSimpleName();

    private final Object lock = new Object();

    private boolean mWakeup = false;

    public RxJava10_Concurrency3() {

    }

    // test concurrency
    public synchronized void run() {
        Helper.printHeader(TAG);

        Helper.printHeader("Example 3: trying concurrency but with flatMap");
        Observable<Integer> vals =
                Observable
                        .range(1, 10)
                        .flatMap(value
                                ->
                                Observable
                                        .just(value)
                                        .subscribeOn(Schedulers.computation())
                                        .map(RxJava10_Concurrency3::intenseCalculation)
                        );


        vals
                .doOnSubscribe(() -> System.out.println("doOnSubscribe in " + Thread.currentThread().getName()))
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        synchronized (lock) {
                            mWakeup = true;
                            System.out.println("Wake everyone up: " + Thread.currentThread().getName());
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
                    System.out.println("Waiting in " + Thread.currentThread().getName());
                    lock.wait();
                    System.out.println("Woke up in " + Thread.currentThread().getName());
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
