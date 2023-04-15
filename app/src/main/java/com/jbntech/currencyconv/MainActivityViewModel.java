package com.jbntech.currencyconv;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jbntech.currencyconv.dto.ConverterRateResponse;
import com.jbntech.currencyconv.dto.Currency;
import com.jbntech.currencyconv.repository.local.Currencies;
import com.jbntech.currencyconv.repository.local.CurrencyRate;
import com.jbntech.currencyconv.repository.local.LocalDb;
import com.jbntech.currencyconv.repository.remote.RetroInstance;
import com.jbntech.currencyconv.repository.remote.convert.ApiConvertorInterface;
import com.jbntech.currencyconv.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {

    private LocalDb localDb;

    private MutableLiveData<List<Currency>> _currencies = new MutableLiveData<>();
    public LiveData<List<Currency>> currenciesLiveData = _currencies;

    private MutableLiveData<ConverterRateResponse> _converterRate = new MutableLiveData<>();
    public LiveData<ConverterRateResponse> converterRateResponseLiveData = _converterRate;

    private MutableLiveData<Double> _calculatedRateFirstInput = new MutableLiveData<>();
    public LiveData<Double> calculatedRateFirstInputLiveData = _calculatedRateFirstInput;

    private MutableLiveData<Double> _calculatedRateSecondInput = new MutableLiveData<>();
    public LiveData<Double> calculatedRateSecondLiveData = _calculatedRateSecondInput;

    //this method call returns all list of currencies
    public void getRemoteCurrencies(Context context) {
        localDb = LocalDb.getInstance(context);
        ApiConvertorInterface convertorInterface = RetroInstance.getRetrofitInstance().create(ApiConvertorInterface.class);
        Call<JsonObject> response = convertorInterface.getCurrencies(Constants.APP_ID);
        response.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

//                    _currencies.postValue(currency);
                    assert response.body() != null;
                    Currencies currencies = new Currencies(1, response.body().toString());
                    Log.d("JSON RESPONSE", response.body().toString());

                    localDb.currencyDao().insertOrUpdateCurrencies(currencies);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }

    public void getRemoteCurrencyRates(Context context) {
        localDb = LocalDb.getInstance(context);
        ApiConvertorInterface convertorInterface = RetroInstance.getRetrofitInstance().create(ApiConvertorInterface.class);
        Call<ConverterRateResponse> response = convertorInterface.getCurrentRates(Constants.APP_ID);
        response.enqueue(new Callback<ConverterRateResponse>() {
            @Override
            public void onResponse(Call<ConverterRateResponse> call, retrofit2.Response<ConverterRateResponse> response) {
                if (response.isSuccessful()) {
//                    _converterRate.postValue(response.body());
                    ConverterRateResponse cvr = response.body();
                    CurrencyRate currencyRate = new CurrencyRate(
                            1,
                            cvr.getDisclaimer(),
                            cvr.getLicense(),
                            cvr.getTimestamp(),
                            cvr.getBase(),
                            cvr.getRates().toString()
                    );
                    localDb.currencyRateDao().insertOrUpdateCurrencyRate(currencyRate);
                }
            }

            @Override
            public void onFailure(Call<ConverterRateResponse> call, Throwable t) {
//                _converterRate.postValue(null);
            }
        });
    }

    public void getCurrencies(Context context) throws JSONException {
        localDb = LocalDb.getInstance(context);
        Currencies currencies = localDb.currencyDao().getAllCurrencies();
        if (currencies == null) {
            return;
        }
        List<Currency> currency = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(currencies.currencyData);

        Iterator<String> its = jsonObject.keys();
//        Iterator<String> its = it.iterator();
        while (its.hasNext()) {
            String key = its.next();
            currency.add(new Currency(jsonObject.get(key).toString(), key));
        }

        _currencies.postValue(currency);

    }


    public void getCurrentRates(Context context) {

            localDb = LocalDb.getInstance(context);
//            List<CurrencyRate> listOf = localDb.currencyRateDao().getListOfCurrencyRates();
//            ListIterator<CurrencyRate> listIterator = listOf.listIterator();
//            while (listIterator.hasNext()){
//                System.out.println("UR_UUID: "+listIterator.next().uuid);
//
//            }
//            Log.d("LIST_OF_CU", listOf.);
            CurrencyRate currencyRate = localDb.currencyRateDao().getAllCurrencyRate();
            if (currencyRate == null) {
                return;
            }
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JsonObject jsonObject = gson.fromJson(currencyRate.rates, JsonObject.class);

            ConverterRateResponse converterRateResponse = new ConverterRateResponse(
                    currencyRate.disclaimer,
                    currencyRate.license,
                    currencyRate.timestamp,
                    currencyRate.base,
                    jsonObject
            );
            Log.d("REMOTE_CR", converterRateResponse.getRates().get("USD").toString());

            _converterRate.postValue(converterRateResponse);

    }


    public void calculateExchangeRateFirstInput(double firstCurrencyRate, double firstCurrencyAmount, double secondCurrencyRate) {
        double firstExchange = (firstCurrencyAmount * secondCurrencyRate) / firstCurrencyRate;
        _calculatedRateFirstInput.postValue(firstExchange);
    }


    public void calculateExchangeRateSecondInput(double firstCurrencyRate, double secondCurrencyRate, double secondCurrencyAmount) {
        double secondExchange = (secondCurrencyAmount * firstCurrencyRate) / secondCurrencyRate;
        _calculatedRateSecondInput.postValue(secondExchange);

    }

}
