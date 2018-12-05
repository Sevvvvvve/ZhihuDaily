package com.vaynefond.zhihudaily.data.repository.remote.api;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

public abstract class RetrofitService<T> {
    protected T mService;

    protected RetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl())
                .client(createOkHttpClient())
                .addConverterFactory(createConverterFactory())
                .addCallAdapterFactory(createCallAdapterFactory())
                .build();
        mService = retrofit.create(getServiceClass());
    }

    private Class<T> getServiceClass() {
        Type sType = getClass().getGenericSuperclass();
        Type[] generics = ((ParameterizedType) Objects.requireNonNull(sType)).getActualTypeArguments();
        return (Class<T>)(generics[0]);
    }

    protected T getService() {
        return mService;
    }

    protected abstract String baseUrl();

    protected abstract OkHttpClient createOkHttpClient();

    protected abstract Converter.Factory createConverterFactory();

    protected abstract CallAdapter.Factory createCallAdapterFactory();
}
