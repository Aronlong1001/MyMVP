package test.aron.com.mvpdemo.common;

import android.support.annotation.NonNull;

import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import test.aron.com.mvpdemo.BaseApplication;
import test.aron.com.mvpdemo.api.ApiService;
import test.aron.com.mvpdemo.entity.NewsSummary;
import test.aron.com.mvpdemo.utils.NetUtil;

/**
 * Created by Aron on 2016/12/21.
 */
public class RetrofitManager {
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    private static final String CACHE_CONTROL_AGE = "max-age=0";
    private ApiService mService;
    private static volatile OkHttpClient sOkHttpClient;
    private static RetrofitManager mRetrofitManager;

    public RetrofitManager() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Common.NETEAST_HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient()).build();
        mService = retrofit.create(ApiService.class);
    }

    private OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null){
            synchronized (RetrofitManager.class){
                Cache cache = new Cache(new File(BaseApplication.getAppContext().getCacheDir(), "HttpCache"),
                        1024 * 1024 * 100);
                if (sOkHttpClient == null){
                    sOkHttpClient = new OkHttpClient.Builder().cache(cache)
                            .connectTimeout(6, TimeUnit.SECONDS)
                            .readTimeout(6, TimeUnit.SECONDS)
                            .writeTimeout(6, TimeUnit.SECONDS)
                            .addInterceptor(mLoggingInterceptor)
                            .build();
                }
            }
        }
        return sOkHttpClient;
    }

    private final Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            KLog.i(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            KLog.i(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    };

    public static RetrofitManager getInstance(){
        if (mRetrofitManager == null){
            mRetrofitManager = new RetrofitManager();
        }
        return mRetrofitManager;
    }

    @NonNull
    private String getCacheControl() {
        return NetUtil.isNetworkAvailable() ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }

    public Observable<Map<String, List<NewsSummary>>> getNewsListObservable(
            String newsType, String newsId, int startPage) {
        return mService.getNewsList(getCacheControl(), newsType, newsId, startPage);
    }
}
