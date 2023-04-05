package com.jbntech.currencyconv.repository.remote.convert;

import android.webkit.GeolocationPermissions;

import androidx.annotation.AnyRes;

import com.google.gson.JsonObject;
import com.jbntech.currencyconv.dto.ConverterRateResponse;
import com.jbntech.currencyconv.dto.Currencies;

import java.util.HashMap;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiConvertorInterface {

    @GET("currencies.json")
    Call<JsonObject> getCurrencies(@Query("app_id") String app_id);

    @GET("latest.json")
    Call<ConverterRateResponse> getCurrentRates(@Query("app_id") String app_id);


}
