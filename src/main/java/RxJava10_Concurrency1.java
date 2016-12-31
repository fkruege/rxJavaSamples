import rx.Observable;
import rx.schedulers.Schedulers;
import utilities.Helper;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by fkruege on 3/26/16.
 */

// http://tomstechnicalblog.blogspot.com/2015/11/rxjava-achieving-parallelization.html
public class RxJava10_Concurrency1 {
    public static final String TAG = RxJava10_Concurrency1.class.getSimpleName();

    // test concurrency
    public static void run() {
        Helper.printHeader(TAG);

        Helper.printHeader("Example 1: Simple range operator");
        Observable
                .range(1, 10)
                .subscribe(integer -> {
                    System.out.println(integer + " - Thread: " + Thread.currentThread().getName());
                });


        Helper.printHeader("Example 2: Simple range operator with random sleeping");
        Observable.range(1, 10)
                .map(value -> intenseCalculation(value))
                .subscribe(integer -> {
                    System.out.println(integer + " - Thread: " + Thread.currentThread().getName());
                });

    }

    public static int intenseCalculation(int i) {
        try {
            System.out.println("Calculating " + i +
                    " on " + Thread.currentThread().getName());
            Thread.sleep(ThreadLocalRandom.current().nextInt(250, 1000));
            return i;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
