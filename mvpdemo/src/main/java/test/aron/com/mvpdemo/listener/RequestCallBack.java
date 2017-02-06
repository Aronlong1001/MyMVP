package test.aron.com.mvpdemo.listener;

/**
 * Created by Aron on 2016/12/18.
 */
public interface RequestCallBack<T> {
    void beforeRequest();
    void success(T data);
    void onError(String errorMsg);
}
