import rx.Observable;
import utilities.Helper;

/**
 * Created by fkruege on 3/26/16.
 */
public class RxJava4_Map {
    public static final String TAG = RxJava4_Map.class.getSimpleName();
    public static final String HELLO_WORLD = "Hello World";


    public static void run() {
        Helper.printHeader(TAG);

        Observable
                .just(HELLO_WORLD)
                .map(s -> s.hashCode())   // map transforms output from string to int
                .subscribe(i -> System.out.println(Integer.toString(i)));

        Observable
                .just(HELLO_WORLD)
                .map(s -> s.hashCode())            // map transforms output from string to int
                .map(i -> Integer.toString(i))     // map transforms back from int to string
                .subscribe(s -> System.out.println(s));


    }


}
