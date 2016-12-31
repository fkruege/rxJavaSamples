import rx.Observable;
import rx.Subscriber;
import utilities.Helper;

/**
 * Created by fkruege on 3/26/16.
 */
public class RxJava1 {
    public static final String TAG = RxJava1.class.getSimpleName();

    public static void run() {
        Helper.printHeader(TAG);

        Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello World");
                subscriber.onCompleted();
            }
        });


        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

                System.out.println(s);

            }
        };

        myObservable.subscribe(mySubscriber);
    }
}
