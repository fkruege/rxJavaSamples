import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import utilities.Helper;
import utilities.URLSearch;

/**
 * Created by fkruege on 3/26/16.
 */
public class RxJava6_SubscriberOnError {
    public static final String TAG = RxJava6_SubscriberOnError.class.getSimpleName();


    public static void run() {
        Helper.printHeader(TAG);

        URLSearch urlSearch = new URLSearch();

        Helper.printHeader("Example 1");
        // Exception handling in subscriber onError
        urlSearch.rxQuery1("")
                .flatMapIterable(urls -> urls)
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        // introduce an error here
                        return Integer.parseInt(s);
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Subscription completed successfully");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Subscription terminated abnormally. Exception onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("Integer value is: " + integer.toString());
                    }
                });


        // added doOnError in the call chain
        Helper.printHeader("Example 2");
        urlSearch.rxQuery1("")
                .flatMapIterable(urls -> urls)
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        // introduce an error here
                        return Integer.parseInt(s);
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("Do On Error: " + throwable.getMessage());
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Subscription completed successfully");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Subscription terminated abnormally. Exception onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("Integer value is: " + integer.toString());
                    }
                });

        // added onErrorReturn in the call chain
        Helper.printHeader("Example 3");
        urlSearch.rxQuery1("")
                .flatMapIterable(urls -> urls)
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        // introduce an error here
                        return Integer.parseInt(s);
                    }
                })
                .onErrorReturn(new Func1<Throwable, Integer>() {
                    @Override
                    public Integer call(Throwable throwable) {
                        return Integer.MIN_VALUE;
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Subscription completed successfully");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Subscription terminated abnormally. Exception onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("Integer value is: " + integer.toString());
                    }
                });


        Helper.printHeader("Example 4");
        urlSearch.rxQuery1("")
                .flatMapIterable(urls -> urls)
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        // introduce an error here
                        return Integer.parseInt(s);
                    }
                })
                .onErrorResumeNext(new Func1<Throwable, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Throwable throwable) {
                        return Observable.just(Integer.MIN_VALUE);
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Subscription completed successfully");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Subscription terminated abnormally. Exception onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("Integer value is: " + integer.toString());
                    }
                });


        // this still terminates the stream
        Helper.printHeader("Example 5");
        urlSearch.rxQuery1("")
                .flatMapIterable(urls -> urls)
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        // introduce an error here
                        return Integer.parseInt(s);
                    }
                })
                .onExceptionResumeNext(Observable.just(Integer.MIN_VALUE))
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Subscription completed successfully");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Subscription terminated abnormally. Exception onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("Integer value is: " + integer.toString());
                    }
                });


    }


}
