package com.jbntech.currencyconv.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.jbntech.currencyconv.R;
import com.jbntech.currencyconv.dto.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrencyListAdapter extends BaseAdapter {
    private List<Currency> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Activity activity;

    public CurrencyListAdapter(Activity activity, List<Currency> data){
        this.data = data;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

            View v = view;
            if(view == null){
                v = inflater.inflate(R.layout.adapter_currency_list, null);
            }

        TextView txtCurrencyName = v.findViewById(R.id.txtCurrencyName);
        TextView txtCurrencyShort =v.findViewById(R.id.txtCurrencyShort);

        Currency currency = data.get(i);
        txtCurrencyName.setText(currency.getCurrencyName());
        txtCurrencyShort.setText(currency.getCurrencyShort());

        return v;
    }
}
