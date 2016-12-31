import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import utilities.Helper;
import utilities.URLSearch;

import java.util.List;

/**
 * Created by fkruege on 3/26/16.
 */
public class RxJava8_MoreErrors {

    public static final String TAG = RxJava8_MoreErrors.class.getSimpleName();

    public static void run() {
        Helper.printHeader(TAG);

        // Simple merge operator test
        // No errors are introduced
        Helper.printHeader("Example 1");
        Observable<Integer> observable1 = Observable.from(new Integer[]{1, 2, 3, 4, 5});
        Observable<Integer> observable2 = Observable.from(new Integer[]{6, 7, 8, 9, 10});

        Observable.merge(observable1, observable2)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Subscriber onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Subscriber onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("Subscriber onNext: " + integer);
                    }
                });


        // a runtime exception is thrown in one of the observables.
        // this causes the entire operation to abort out
        Helper.printHeader("Example 2");
        URLSearch urlSearch = new URLSearch();

        Observable<List<String>> listObservable1 = Observable.defer(() -> urlSearch.rxQuery1(""));
        Observable<List<String>> listObservable2 = Observable.defer(() -> urlSearch.rxQueryException1(""));
        Observable<List<String>> listObservable3 = Observable.defer(() -> urlSearch.rxQuery2(""));

        Observable
                .merge(listObservable2, listObservable1, listObservable3)
                .flatMapIterable(urls -> urls)
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("doOnError: " + throwable.getMessage());
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        System.out.println("doOnTerminate");
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Subscriber onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Subscriber onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(String value) {
                        System.out.println("Subscriber onNext: " + value);
                    }
                });


        // a runtime exception is thrown in one of the observables.
        // I have an onErrorReturn to handle the error
        // program handles the error gracefully
        Helper.printHeader("Example 3");

        Observable<List<String>> listObservable4 = Observable.defer(() -> urlSearch.rxQuery1(""));
        Observable<List<String>> listObservable5 = Observable.defer(() -> urlSearch.rxQueryException1(""));
        Observable<List<String>> listObservable6 = Observable.defer(() -> urlSearch.rxQuery2(""));

        Observable
                .merge(listObservable5, listObservable4, listObservable6)
                .flatMapIterable(urls -> urls)
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("doOnError: " + throwable.getMessage());
                    }
                })
                .onErrorReturn(new Func1<Throwable, String>() {
                    @Override
                    public String call(Throwable throwable) {
                        System.out.println("onErrorReturn: " + throwable.getMessage());
                        return "hello world";
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


        // a runtime exception is thrown in one of the observables.
        // testing if onError and onCompleted is called in the subscriber
        Helper.printHeader("Example 4");
        URLSearch urlSearch4 = new URLSearch();

        Observable<List<String>> listObservable4a = Observable.defer(() -> urlSearch4.rxQuery1(""));
        Observable<List<String>> listObservable4b = Observable.defer(() -> urlSearch4.rxQueryException2(""));
        Observable<List<String>> listObservable4c = Observable.defer(() -> urlSearch4.rxQuery2(""));

        Observable
                .merge(listObservable4a, listObservable4b, listObservable4c)
                .flatMapIterable(urls -> urls)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Subscriber onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Subscriber onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(String value) {
                        System.out.println("Subscriber onNext: " + value);
                    }
                });

        example5();


    }

    private static void example5() {


        // a runtime exception is thrown in the onNext
        // testing if onError and onCompleted is called in the subscriber
        Helper.printHeader("Example 5");
        URLSearch urlSearch4 = new URLSearch();

        Observable<List<String>> listObservable4a = Observable.defer(() -> urlSearch4.rxQuery1(""));
        Observable<List<String>> listObservable4b = Observable.defer(() -> urlSearch4.rxQueryException2(""));
        Observable<List<String>> listObservable4c = Observable.defer(() -> urlSearch4.rxQuery2(""));

        Observable
                .merge(listObservable4a, listObservable4b, listObservable4c)
                .flatMapIterable(urls -> urls)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Subscriber onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Subscriber onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(String value) {
                        System.out.println("Subscriber onNext: " + value);
                        System.out.println("Divide by 0: " + 9 / 0);
                    }
                });


    }

}
