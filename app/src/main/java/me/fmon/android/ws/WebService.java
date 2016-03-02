package me.fmon.android.ws;

import me.fmon.android.MyApplication;
import me.fmon.android.utils.DateTimeDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.joda.time.DateTime;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

@EBean(scope = EBean.Scope.Singleton)
public class WebService {

    public static final String TAG = WebService.class.getSimpleName();

    public static final String STAGING_URL = "";
    public static final String PROD_URL = "";
    public static final String BASE_URL = PROD_URL;

    ApiEndpoint api;

    @Bean
    AuthInterceptor interceptor;

    @App
    MyApplication myApp;


    Gson gson;

    @AfterInject
    void init() {

        gson = new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeDeserializer())
                .create();

        // Add the Auth interceptor to OkHttpClient
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        api = retrofit.create(ApiEndpoint.class);
    }
}
