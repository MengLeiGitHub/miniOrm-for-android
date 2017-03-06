package com.test;

import android.support.multidex.MultiDexApplication;

import com.async.http.AsyncHttp;
import com.async.http.Interceptor2.RequestInterceptorActionInterface;
import com.async.http.request2.BaseHttpRequest;
import com.async.http.request2.BaseRequest;
import com.async.http.request2.RequestConfig;
import com.async.http.request2.entity.Header;
import com.async.http.utils.LogUtils;
import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;

/**
 * Created by admin on 2016/9/28.
 */
public class MyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        RequestConfig requestConfig = new RequestConfig();
        requestConfig.setConnectTimeout(10000);
        requestConfig.setSocketTimeout(30000);
        //  requestConfig.setBaseUrl("http://120.26.106.136:8080/");
        ArrayList<Header> headerlist = new ArrayList<Header>();
        headerlist.add(new Header("connection", "Keep-Alive"));
        headerlist.add(new Header("user-agent", "AsyHttp/1.0 ml"));
        headerlist.add(new Header("Accept-Charset", "ISO-8859-1"));
        requestConfig.setHeadList(headerlist);
        requestConfig.setBaseUrl("http://120.26.106.136:8080/");
        AsyncHttp.instance().setConfig(requestConfig);

        AsyncHttp.instance().addRequestInterceptor(new RequestInterceptorActionInterface() {
            @Override
            public <T> BaseRequest<T> interceptorAction(BaseRequest<T> baserequest) throws Exception {
                baserequest.addHead(new Header("version", "1.0"));
                baserequest.addHead(new Header("tokenId", "8FA24C888B39405FB46499C62E48A504"));
                baserequest.addHead(new Header("token", "D38F719AEDCF4E6E8DADB7B773665E11"));
                //35C43D51E8C844B69B4AF149A82B40E7
                baserequest.addHead(new Header("appType", "2"));
                baserequest.addHead(new Header("ostype", "1"));
                baserequest.addHead(new Header("deviceId", "1231232342342341"));
                return baserequest;
            }
        });
        LogUtils.setDebug(true);

        LeakCanary.install(this);


    }

}
