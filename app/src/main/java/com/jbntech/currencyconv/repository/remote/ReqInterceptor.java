package com.jbntech.currencyconv.repository.remote;


import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ReqInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
//                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
//                .addHeader()
                .build();

        return chain.proceed(request);
    }
}
