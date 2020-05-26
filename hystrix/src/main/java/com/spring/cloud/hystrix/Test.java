package com.spring.cloud.hystrix;


import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void case1() {
        // 创建被观察者，用于产生事件
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> observableEmitter) throws Exception {
                observableEmitter.onNext("1");
                observableEmitter.onNext("2");
                observableEmitter.onComplete();
            }
        })
        // 被观察者与观察者通过 subscribe 建立连接
        .subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                logger.info("建立连接。。。");
            }
            @Override
            public void onNext(Object o) {
                logger.info("onNext, value: {}", o);
                logger.info("Current Thread Name: {}", Thread.currentThread().getName());
            }
            @Override
            public void onError(Throwable throwable) {
                logger.info("onError, e: {}", throwable);
            }
            @Override
            public void onComplete() {
                logger.info("onComplete...");
            }
        });
    }

    public static void case2() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                logger.info("上游发送 onNext事件 1");
                emitter.onNext(1);
                logger.info("上游发送 onNext事件 2");
                emitter.onNext(2);
                logger.info("上游发送 onNext事件 3");
                emitter.onNext(3);
                logger.info("上游发送结束事件 onComplete");
                emitter.onComplete();
                logger.info("上游发送 onNext事件 4");
                emitter.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable mDisposable;
            private int mCount = 0;
            @Override
            public void onSubscribe(Disposable d) {
                logger.info("建立连接。。。");
                mDisposable = d;
            }
            @Override
            public void onNext(Integer value) {
                logger.info("onNext: value = " + value);
                mCount++;
                if (mCount == 2) {
                    logger.info("进行 Dispose。。。");
                    mDisposable.dispose();
                    logger.info("isDisposed : " + mDisposable.isDisposed());
                }
            }
            @Override
            public void onError(Throwable e) {
                logger.info("onError, e: {}", e);
            }
            @Override
            public void onComplete() {
                logger.info("onComplete...");
            }
        });
    }

    public static void case3() {
        Observable.create((ObservableEmitter<Integer> emitter) -> {
            logger.info("subscribe: thread = " + Thread.currentThread().getName());
            emitter.onNext(1);
            emitter.onComplete();
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                logger.info("accept: thread = " + Thread.currentThread().getName());
            }
        });
    }

    public static void case4() {
        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1), r -> new Thread(r,"supplier refresh"));
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                logger.info("subscribe: thread = " + Thread.currentThread().getName());
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onComplete();
            }
        })
        .subscribeOn(Schedulers.newThread())
        .observeOn(Schedulers.from(executorService))
        .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                 logger.info("accept: thread = " + Thread.currentThread().getName());
            }
        });
        logger.info("end...当前线程为: {}", Thread.currentThread().getName());
    }

    public static void case5() {
        Observable
                // 利用 just函数创建一个事件流
                .just(1, 2, 3, 4, 5)
                // 利用 map转换事件流对象
                .map(num -> "this is " + num)
                // 观测消费事件流对象
                .subscribe(System.out::println)
        ;
    }

    public static void case6() {
        String[][] items = new String[3][];
        items[0] = new String[] {"0", "1"};
        items[1] = new String[] {"2", "3"};
        items[2] = new String[] {"4", "5"};
        Observable
                .fromArray(items)
                .flatMap(Observable::fromArray)
                .subscribe(System.out::println);
    }

    public static void case7() {
        Observable
                .just(1, 2, 3, 4, 5)
                .scan((sum, curr) -> sum + curr)
                .subscribe(System.out::println);
    }

    public static void case8() {
        Observable
                .just(1, 3, 5, 6, 8)
                .skip(2)
                .skipLast(1)
                // 5 6
                .elementAt(0)
                // 下标从 0开始，结果为 5
                .subscribe(System.out::println);
    }

    public static void case13() {
        String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
        Observable<String> observableA = Observable
                // 每隔 1s发射一个 Long型数据源，数值为当前发射的次数，从 0开始
                .interval(1000, TimeUnit.MILLISECONDS)
                // 转为 String类型
                .map(pos -> letters[pos.intValue()]
                )
                .take(letters.length);
        Integer[] numbers = new Integer[] {1, 2, 3, 4};
        Observable<Integer> observableB = Observable
                .interval(1500, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .take(numbers.length);
        Observable
                // 2个数据源进行合并，无序
                .concat(observableA, observableB)
                .subscribe(System.out::println);
        // 使用 interval，一定得保证当前线程不被销毁
        try {
            Thread.sleep(1000 * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void case14() {
        Observable
                .just(1, 2, 3)
                // 被观察者泛型实例
                .startWith(0)
                // 实现了 Iterable接口的集合
                .startWith(Arrays.asList(4, 5))
                // 被观察者实例
                .startWith(Observable.just(6, 7))
                .subscribe(System.out::println);
    }

    public static void case16() {
        String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
        Observable<String> observableA = Observable
                // 每隔 1s发射一个 Long型数据源，数值为当前发射的次数，从 0开始
                .interval(1000, TimeUnit.MILLISECONDS)
                // 转为 String类型
                .map(pos -> letters[pos.intValue()]
                )
                .take(letters.length);
        Integer[] numbers = new Integer[] {1, 2, 3, 4};
        Observable<Integer> observableB = Observable
                .interval(1500, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .take(numbers.length);
        Observable
                // 2个数据源进行合并，按照后面的 func进行合并
                .zip(observableA, observableB, (str, num) -> str + "&" + num)
                .subscribe(System.out::println);
        // 使用 interval，一定得保证当前线程不被销毁
        try {
            Thread.sleep(1000 * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void case17() {
        String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
        Observable<String> observableA = Observable
                // 每隔 1s发射一个 Long型数据源，数值为当前发射的次数，从 0开始
                .interval(1000, TimeUnit.MILLISECONDS)
                // 转为 String类型
                .map(pos -> letters[pos.intValue()]
                )
                .take(letters.length);
        Integer[] numbers = new Integer[] {1, 2, 3, 4};
        Observable<Integer> observableB = Observable
                .interval(1500, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .take(numbers.length);
        Observable
                // 2个数据源进行合并，两个 Observable最近发射的数据按照后面的 func进行合并
                .combineLatest(observableA, observableB, (str, num) -> str + "&" + num)
                .subscribe(System.out::println);
        // 使用 interval，一定得保证当前线程不被销毁
        try {
            Thread.sleep(1000 * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void case18() {
        String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
        Observable<String> observableA = Observable
                // 每隔 1s发射一个 Long型数据源，数值为当前发射的次数，从 0开始
                .interval(1000, TimeUnit.MILLISECONDS)
                // 转为 String类型
                .map(pos -> letters[pos.intValue()]
                )
                .take(letters.length);
        Integer[] numbers = new Integer[] {1, 2, 3, 4};
        Observable<Integer> observableB = Observable
                .interval(2000, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .take(numbers.length);
        observableA.join(observableB,
                new Function<String, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(String s) throws Exception {
                        return Observable.timer(2, TimeUnit.SECONDS);
                    }
                },
                new Function<Integer, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(Integer integer) throws Exception {
                        return Observable.timer(1, TimeUnit.SECONDS);
                    }
                },
                new BiFunction<String, Integer, String>() {
                    @Override
                    public String apply(String s, Integer number) throws Exception {
                        return (s + " --> " + number);
                    }
                }
        ).subscribe(System.out::println);
        // 使用 interval，一定得保证当前线程不被销毁
        try {
            Thread.sleep(1000 * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        case18();
//        case17();
//        case16();
//        case14();
//        case13();
//        case1();
//        case2();
//        case3();
//        case4();
//        case5();
//        case6();
//        case7();
//        case8();
        ArrayList<Integer> arr1 = new ArrayList<Integer>();
        arr1.add(1);
        arr1.add(3);
        arr1.add(5);

        ArrayList<Integer> arr2 = new ArrayList<Integer>();
        arr2.add(2);
        arr2.add(4);
        arr2.add(6);

        ArrayList<Integer> result = mergeArray(arr1, arr2);
        System.out.println(result);
    }

    public static ArrayList<Integer> mergeArray(ArrayList<Integer> arr1, ArrayList<Integer> arr2) {
        // 在这里写代码
        ArrayList<Integer> result = new ArrayList<>();
        int len1 = arr1.size();
        int len2 = arr2.size();
        int len = len1 + len2;
        int one = 0, two = 0;
        for (int i = 0; i < len; i++) {
            if (one == len1) {
                result.add(arr2.get(two));
                two++;
                continue;
            }
            if (two == len2) {
                result.add(arr2.get(two));
                two++;
                continue;
            }
            if (arr1.get(one) <= arr2.get(two)) {
                result.add(arr1.get(one));
                one++;
            } else {
                result.add(arr2.get(two));
                two++;
            }
        }
        return result;
    }

}
