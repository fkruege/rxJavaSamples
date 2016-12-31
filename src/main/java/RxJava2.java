import rx.Observable;
import rx.functions.Action1;
import utilities.Helper;

/**
 * Created by fkruege on 3/26/16.
 */
public class RxJava2 {

    public static final String TAG = RxJava2.class.getSimpleName();

    public static void run() {
        Helper.printHeader(TAG);


        Observable<String> just = Observable.just("Hello World");

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        };

        just.subscribe(onNextAction);

    }
}
