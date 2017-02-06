package test.aron.com.retrofitdemo.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aron on 2016/12/13.
 */
public class RestApi {

    private static RestApi mInstance;

    public static synchronized RestApi getInstance(){
        if (mInstance == null){
            mInstance = new RestApi();
        }
        return mInstance;
    }

    public Retrofit createApiClient(String baseUrl){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
