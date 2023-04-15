package com.jbntech.currencyconv.repository.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.JsonObject;

@Entity
public class Currencies{


    @PrimaryKey(autoGenerate = false)
    public int uid;

    @ColumnInfo(name = "currencyData")
    public String currencyData;

    public Currencies(int uid, String currencyData){
        this.uid = uid;
        this.currencyData = currencyData;
    }

}
