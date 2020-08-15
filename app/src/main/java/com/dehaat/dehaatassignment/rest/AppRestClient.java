package com.dehaat.dehaatassignment.rest;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppRestClient {

    private static AppRestClient mInstance;
    int cacheSize = 10 * 1024 * 1024; // 10 MB
    private AppRestClientService appRestClientService;
    private Context context;


    private AppRestClient(Context context) {
        this.context = context;
        setRestClient();
    }

    public static AppRestClient getInstance(Context context) {
        if (mInstance == null) {
            synchronized (AppRestClient.class) {
                if (mInstance == null)
                    mInstance = new AppRestClient(context);
            }
        }
        return mInstance;
    }

    public static void setAppRestClientNull() {
        mInstance = null;
    }

    private void setRestClient() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Cache cache = new Cache(context.getCacheDir(), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d2a1b430-f3d9-493b-9dbc-ecbd75fe7c74.mock.pstmn.io") //ToDo: enter base url
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        appRestClientService = retrofit.create(AppRestClientService.class);
    }

    public AppRestClientService getAppRestClientService() {
        return appRestClientService;
    }
}
