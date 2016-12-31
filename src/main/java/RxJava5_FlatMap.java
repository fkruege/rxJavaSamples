import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import utilities.Helper;
import utilities.URLSearch;

import java.util.List;

/**
 * Created by fkruege on 3/26/16.
 */
public class RxJava5_FlatMap {
    public static final String TAG = RxJava5_FlatMap.class.getSimpleName();

    public static void run() {
        Helper.printHeader(TAG);

        URLSearch urlSearch = new URLSearch();


        System.out.println("Example 1");
        // approach 1 to process a list of items
        urlSearch.rxQuery1("")
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> urls) {
                        for (String url : urls) {
                            System.out.println(url);
                        }
                    }
                });


        Helper.printHeader("Example 2");
        // Using both the flatMap operator to transform our List<String> to just String
        // now using "from" operator that accepts a collection of items and emits them one at a time
        urlSearch.rxQuery1("")
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> urls) {
                        return Observable.from(urls);
                    }
                })
                .subscribe(url -> System.out.println(url));


        Helper.printHeader("Example 3");
        // Now using flatMapIterable to iterate over the items???
        urlSearch.rxQuery1("")
                .flatMapIterable(new Func1<List<String>, Iterable<String>>() {
                    @Override
                    public Iterable<String> call(List<String> urls) {
                        return urls;
                    }
                })
                .subscribe(url -> System.out.println(url));


        // fetching a list of urls then getting the titles for each url using flatmap
        Helper.printHeader("Example 4");
        urlSearch.rxQuery1("")
                .flatMapIterable(urls -> urls)
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String url) {
                        return urlSearch.rxGetTitleForUrl(url);
                    }
                })
                .subscribe(System.out::println);


        // now using map to do the same thing as Example 4 but the map operator
        // does not return an Observable but just a string
        Helper.printHeader("Example 5");
        urlSearch.rxQuery1("")
                .flatMapIterable(urls -> urls)
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        Observable<String> stringObservable = urlSearch.rxGetTitleForUrl(s);
                        return stringObservable.toBlocking().single();
                    }
                })
                .subscribe(System.out::println);

    }


}
