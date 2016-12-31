package utilities;

import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fkruege on 3/26/16.
 */
public class URLSearch {

    public Observable<List<String>> rxQuery1(String text) {
        List<String> query = query(text);

        return Observable.just(query);
    }

    public Observable<List<String>> rxQuery2(String text) {
        List<String> query = new ArrayList<>();
        query.add(Helper.WEB6);
        query.add(Helper.WEB7);

        return Observable.just(query);
    }

    public Observable<List<String>> rxQueryException1(String text) {
        List<String> query = query(text);

        Observable<List<String>> observable = Observable.just(query)
                .map(new Func1<List<String>, List<String>>() {
                    @Override
                    public List<String> call(List<String> strings) {
                        throw new RuntimeException("Artificial error");
                    }
                })
                .onErrorReturn(new Func1<Throwable, List<String>>() {
                    @Override
                    public List<String> call(Throwable throwable) {
                        System.out.println("rxQueryException1 onErrorReturn. return empty arraylist  ");
                        return new ArrayList<>();
                    }

                });

        return observable;
    }


    public Observable<List<String>> rxQueryException2(String text) {
        List<String> query = query(text);

        Observable<List<String>> observable = Observable.just(query)
                .map(new Func1<List<String>, List<String>>() {
                    @Override
                    public List<String> call(List<String> strings) {
                        throw new RuntimeException("Artificial error");
                    }
                });

        return observable;
    }


    public List<String> query(String text) {
        List<String> results = new ArrayList<>(10);
        results.add(Helper.WEB1);
        results.add(Helper.WEB2);
        results.add(Helper.WEB3);
        results.add(Helper.WEB4);
        results.add(Helper.WEB5);

        return results;
    }

    public Observable<String> rxGetTitleForUrl(String url) {
        if (url.equals(Helper.WEB1)) {
            return Observable.just(Helper.TITLE1);
        } else if (url.equals(Helper.WEB2)) {
            return Observable.just(Helper.TITLE2);
        } else if (url.equals(Helper.WEB3)) {
            return Observable.just(Helper.TITLE3);
        } else if (url.equals(Helper.WEB4)) {
            return Observable.just(Helper.TITLE4);
        } else if (url.equals(Helper.WEB5)) {
            return Observable.just(Helper.TITLE5);
        }

        return Observable.just("");
    }
}
