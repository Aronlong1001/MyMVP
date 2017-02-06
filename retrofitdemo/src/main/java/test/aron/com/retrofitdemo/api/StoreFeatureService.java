package test.aron.com.retrofitdemo.api;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;
import test.aron.com.retrofitdemo.bean.StoreFeatureBean;

/**
 * Created by Aron on 2016/12/13.
 */
public interface StoreFeatureService {
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("device/android/queryStoreFeature")
    Observable<StoreFeatureBean> getStoreFeature(@Body RequestBody json);
}
