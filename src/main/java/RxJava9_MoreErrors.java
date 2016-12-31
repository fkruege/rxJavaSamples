import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import utilities.Helper;
import utilities.URLSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fkruege on 3/26/16.
 */
public class RxJava9_MoreErrors {

    public static final String TAG = RxJava9_MoreErrors.class.getSimpleName();

    public static void run() {
        Helper.printHeader(TAG);

        URLSearch urlSearch = new URLSearch();


        // a runtime exception is thrown in one of the observables.
        Helper.printHeader("Example 1");

        Observable<List<String>> listObservable4 = Observable.defer(() -> urlSearch.rxQuery1(""));
        Observable<List<String>> listObservable5 = Observable.defer(() -> urlSearch.rxQueryException2(""));
        Observable<List<String>> listObservable6 = Observable.defer(() -> urlSearch.rxQuery2(""));

        // I try to add a onErrorReturn but it still terminates abnormally
        listObservable5.onErrorReturn(throwable -> {
            System.out.println("rxQueryException1 onErrorReturn. return empty arraylist  ");
            return new ArrayList<>();
        });

        Observable
                .merge(listObservable5, listObservable4, listObservable6)
                .flatMapIterable(urls -> urls)
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("doOnError: " + throwable.getMessage());
                    }
                })
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("doOnNext: " + s);
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        System.out.println("doOnTerminate");
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("Subscriber onNext: " + s);
                    }
                });


    }

}
