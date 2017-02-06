package test.aron.com.retrofitdemo.api;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;
import test.aron.com.retrofitdemo.bean.deviceInfoBean;

/**
 * Created by Aron on 2016/12/9.
 */
public interface deviceInfoService {
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("device/getDeviceInfo")
    Call<deviceInfoBean> getDeviceInfo(@Body RequestBody json);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("device/getDeviceInfo")
    Observable<deviceInfoBean> getDeviceInfo_(@Body RequestBody json);

}
