import rx.Observable;
import utilities.Helper;

/**
 * Created by fkruege on 3/26/16.
 */
public class RxJava3_MethodReference {

    public static final String TAG = RxJava3_MethodReference.class.getSimpleName();

    public static void run() {
        Helper.printHeader(TAG);

        Observable.just("Hello World").subscribe(System.out::println);

    }}
