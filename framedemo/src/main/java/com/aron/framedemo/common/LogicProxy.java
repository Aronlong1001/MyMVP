package com.aron.framedemo.common;

import com.aron.framedemo.annotation.Implement;
import com.aron.framedemo.presenter.base.BasePresenterImpl;
import com.aron.framedemo.presenter.base.BaseView;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aron on 2016/12/19.
 */
public class LogicProxy {
    private static final LogicProxy m_instance = new LogicProxy();

    public static LogicProxy getInstance() {
        return m_instance;
    }

    private LogicProxy() {
        m_objects = new HashMap<>();
    }
    private Map<Class, Object> m_objects;

    public void init(Class... clss) {
        //        List<Class> list = new LinkedList<Class>();
        for (Class cls : clss) {
            if (cls.isAnnotationPresent(Implement.class)) {
                //                list.add(cls);
                for (Annotation ann : cls.getDeclaredAnnotations()) {
                    if (ann instanceof Implement) {
                        try {
                            m_objects.put(cls, ((Implement) ann).value().newInstance());
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    // 初始化presenter add map
    public <T> T bind(Class clzz, BaseView var1) {
        if (!m_objects.containsKey(clzz)) {
            init(clzz);
        }
        BasePresenterImpl presenter = ((BasePresenterImpl) m_objects.get(clzz));
        if (var1 != presenter.getView()) {
            if (presenter.getView() != null) {
                presenter.detachView();
            }
            presenter.attachView(var1);
        }
        return (T) presenter;
    }

    // 解除绑定 移除map
    public void unbind(Class clzz, BaseView var1) {
        if (m_objects.containsKey(clzz)) {
            BasePresenterImpl presenter = ((BasePresenterImpl) m_objects.get(clzz));
            if (var1 != presenter.getView()) {
                if (presenter.getView() != null)
                    presenter.detachView();
                m_objects.remove(clzz);
            }

        }
    }
}
