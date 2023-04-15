package com.jbntech.currencyconv.dto;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

public class Currency {

    String currencyName;
    String currencyShort;


    public Currency(String currencyName, String currencyShort) {
        this.currencyName = currencyName;
        this.currencyShort = currencyShort;
    }


    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyShort() {
        return currencyShort;
    }

    public void setCurrencyShort(String currencyShort) {
        this.currencyShort = currencyShort;
    }
}
