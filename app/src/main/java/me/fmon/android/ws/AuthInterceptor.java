package me.fmon.android.ws;


import me.fmon.android.MyApplication;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;

import java.io.IOException;


@EBean
public class AuthInterceptor implements Interceptor {

    public static final String TAG = AuthInterceptor.class.getSimpleName();

    @App
    MyApplication app;

    @Override
    public Response intercept(Chain chain) throws IOException {

    /*    String auth = app.getAuth();

        Request newRequest = chain.request().newBuilder().addHeader("Authorization", auth).build();
        return chain.proceed(newRequest); */
        return chain.proceed(chain.request());
    }
}
