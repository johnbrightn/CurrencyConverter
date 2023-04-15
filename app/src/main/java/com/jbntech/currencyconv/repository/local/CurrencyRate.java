package com.jbntech.currencyconv.repository.local;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.JsonObject;

@Entity
public class CurrencyRate{

    @PrimaryKey(autoGenerate = false)
    public int uuid;

    @ColumnInfo(name = "disclaimer")
    public String disclaimer;
    @ColumnInfo(name = "license")
    public String license;
    @ColumnInfo(name = "timestamp")
    public Long timestamp;
    @ColumnInfo(name = "base")
    public String base;
    @ColumnInfo(name= "rates")
    public String rates;

    public CurrencyRate(int uuid, String disclaimer, String license, Long timestamp, String base, String rates){
        this.uuid = uuid;
        this.disclaimer = disclaimer;
        this.license = license;
        this.timestamp = timestamp;
        this.base = base;
        this.rates = rates;
    }
}
