package com.jbntech.currencyconv.repository.local.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Upsert;

import com.jbntech.currencyconv.dto.ConverterRateResponse;
import com.jbntech.currencyconv.repository.local.CurrencyRate;

import java.util.List;

@Dao
public interface CurrencyRatesDao {

    @Query("SELECT * from CurrencyRate WHERE uuid=1")
    CurrencyRate getAllCurrencyRate();

    @Query("SELECT * From CurrencyRate")
    List<CurrencyRate> getListOfCurrencyRates();

    @Upsert
    void insertOrUpdateCurrencyRate(CurrencyRate currencyRates);
}
