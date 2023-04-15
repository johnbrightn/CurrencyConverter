package com.jbntech.currencyconv.repository.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jbntech.currencyconv.dto.Currency;
import com.jbntech.currencyconv.repository.local.dao.CurrenciesDao;
import com.jbntech.currencyconv.repository.local.dao.CurrencyRatesDao;

@Database(entities = {Currencies.class, CurrencyRate.class}, version = 1)
public abstract class LocalDb extends RoomDatabase {

    public abstract CurrenciesDao currencyDao();
    public abstract CurrencyRatesDao currencyRateDao();
    public static LocalDb INSTANCE;

    public static LocalDb getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LocalDb.class, "localDb")
                    .allowMainThreadQueries().build();
        }

        return INSTANCE;
    }
}
