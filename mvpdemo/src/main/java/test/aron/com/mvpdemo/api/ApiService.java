package test.aron.com.mvpdemo.api;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;
import test.aron.com.mvpdemo.entity.DeviceInfoBean;
import test.aron.com.mvpdemo.entity.NewsSummary;

/**
 * Created by Aron on 2016/12/9.
 */
public interface ApiService {
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("device/getDeviceInfo")
    Observable<DeviceInfoBean> getDeviceInfo_(@Body RequestBody json);

    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Map<String, List<NewsSummary>>> getNewsList(
            @Header("Cache-Control") String cacheControl,
            @Path("type") String type, @Path("id") String id,
            @Path("startPage") int startPage);
}
