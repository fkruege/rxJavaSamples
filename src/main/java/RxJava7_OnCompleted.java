import rx.Subscriber;
import rx.functions.Action0;
import utilities.Helper;
import utilities.URLSearch;

/**
 * Created by fkruege on 3/26/16.
 */
public class RxJava7_OnCompleted {
    public static final String TAG = RxJava7_OnCompleted.class.getSimpleName();


    public static void run() {
        Helper.printHeader(TAG);



        URLSearch urlSearch = new URLSearch();

        urlSearch
                .rxQuery1("")
                .flatMapIterable(urls -> urls)
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        System.out.println("doOnTerminate");
                    }
                })
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        System.out.println("doAfterTerminate");
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        System.out.println("doOnCompleted");
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
                    public void onNext(String url) {
                        System.out.println("Subscriber onNext: " + url);
                    }
                });

    }



}
