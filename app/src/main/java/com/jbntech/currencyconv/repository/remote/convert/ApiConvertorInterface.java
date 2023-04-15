package com.jbntech.currencyconv.repository.remote.convert;

import com.google.gson.JsonObject;
import com.jbntech.currencyconv.dto.ConverterRateResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiConvertorInterface {

    @GET("currencies.json")
    Call<JsonObject> getCurrencies(@Query("app_id") String app_id);

    @GET("latest.json")
    Call<ConverterRateResponse> getCurrentRates(@Query("app_id") String app_id);


}
