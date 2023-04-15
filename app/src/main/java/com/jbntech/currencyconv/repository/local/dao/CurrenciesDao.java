package com.jbntech.currencyconv.repository.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Upsert;


import com.jbntech.currencyconv.repository.local.Currencies;

import java.util.List;

@Dao
public interface CurrenciesDao {

    @Query("SELECT * from Currencies WHERE uid=1")
    Currencies getAllCurrencies();

    @Upsert
    void insertOrUpdateCurrencies(Currencies...currencies);
}
