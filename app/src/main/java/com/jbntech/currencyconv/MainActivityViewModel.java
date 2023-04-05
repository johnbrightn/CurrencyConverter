package com.jbntech.currencyconv;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jbntech.currencyconv.adapter.CurrencyListAdapter;
import com.jbntech.currencyconv.dto.ConverterRateResponse;
import com.jbntech.currencyconv.dto.Currencies;
import com.jbntech.currencyconv.dto.Currency;
import com.jbntech.currencyconv.repository.remote.RetroInstance;
import com.jbntech.currencyconv.repository.remote.convert.ApiConvertorInterface;
import com.jbntech.currencyconv.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<Currency>> _currencies = new MutableLiveData<>();
    public LiveData<List<Currency>> currenciesLiveData = _currencies;

    private MutableLiveData<ConverterRateResponse> _converterRate = new MutableLiveData<>();
    public LiveData<ConverterRateResponse> converterRateResponseLiveData = _converterRate;

    private MutableLiveData<Double> _calculatedRateFirstInput = new MutableLiveData<>();
    public LiveData<Double> calculatedRateFirstInputLiveData = _calculatedRateFirstInput;

    private MutableLiveData<Double> _calculatedRateSecondInput = new MutableLiveData<>();
    public LiveData<Double> calculatedRateSecondLiveData = _calculatedRateSecondInput;

    //this method call returns all list of currencies
    public void getCurrencies() {
        ApiConvertorInterface convertorInterface = RetroInstance.getRetrofitInstance().create(ApiConvertorInterface.class);
        Call<JsonObject> response = convertorInterface.getCurrencies(Constants.APP_ID);
        response.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    Log.d("JSON RESPONSE", response.body().toString());
                    List<Currency> currency = new ArrayList<>();
                    JsonObject jsonObject = response.body();
                    Set<String> it =  jsonObject.keySet();
                    Iterator<String> its = it.iterator();
                    while (its.hasNext()){
                        String key = its.next();
                        currency.add(new Currency(jsonObject.get(key).getAsString(), key));
                    }
                    _currencies.postValue(currency);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }


    public void getCurrentRates() {
        ApiConvertorInterface convertorInterface = RetroInstance.getRetrofitInstance().create(ApiConvertorInterface.class);
        Call<ConverterRateResponse> response = convertorInterface.getCurrentRates(Constants.APP_ID);
        response.enqueue(new Callback<ConverterRateResponse>() {
            @Override
            public void onResponse(Call<ConverterRateResponse> call, retrofit2.Response<ConverterRateResponse> response) {
               if(response.isSuccessful()){
                   _converterRate.postValue(response.body());
               }
            }

            @Override
            public void onFailure(Call<ConverterRateResponse> call, Throwable t) {
                _converterRate.postValue(null);
            }
        });
    }



    public void calculateExchangeRateFirstInput(double firstCurrencyRate, double firstCurrencyAmount, double secondCurrencyRate){
        double firstExchange = (firstCurrencyAmount * secondCurrencyRate) / firstCurrencyRate;
        _calculatedRateFirstInput.postValue(firstExchange);
    }


    public void calculateExchangeRateSecondInput(double firstCurrencyRate, double secondCurrencyRate, double secondCurrencyAmount){
        double secondExchange = (secondCurrencyAmount * firstCurrencyRate) /secondCurrencyRate;
        _calculatedRateSecondInput.postValue(secondExchange);

    }

}
