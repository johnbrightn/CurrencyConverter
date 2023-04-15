package com.jbntech.currencyconv.dto;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConverterRateResponse {
    private String disclaimer;
    private String license;
    private Long timestamp;
    private String base;
    private JsonObject rates;

    public ConverterRateResponse(String disclaimer, String license, Long timestamp, String base, JsonObject rates) {
        this.disclaimer = disclaimer;
        this.license = license;
        this.timestamp = timestamp;
        this.base = base;
        this.rates = rates;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public JsonObject getRates() {
        return rates;
    }

    public void setRates(JsonObject rates) {
        this.rates = rates;
    }
}
