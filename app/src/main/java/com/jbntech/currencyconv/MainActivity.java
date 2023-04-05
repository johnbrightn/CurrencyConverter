package com.jbntech.currencyconv;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jbntech.currencyconv.adapter.CurrencyListAdapter;
import com.jbntech.currencyconv.dto.ConverterRateResponse;
import com.jbntech.currencyconv.dto.Currencies;
import com.jbntech.currencyconv.dto.Currency;
import com.jbntech.currencyconv.dto.CurrencyRate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static int SELECT_CURRENCY_BUTTON_ONE = 1;
    private static int SELECT_CURRENCY_BUTTON_TWO = 2;

    private String selectedFirstCurrency = "";
    private String selectedSecondCurrency = "";
    private String selectedFirstCurrencyCode = "";
    private String selectedSecondCurrencyCode = "";

    private double selectedFirstCurrencyRate = 0;
    private double selectedSecondCurrencyRate = 0;
    private List<Currency> currencyList = new ArrayList<>();
    private AlertDialog dialog;
    private Button buttonFirstCurrency;
    private Button buttonSecondCurrency;
    private EditText edtFirstCurrency;
    private EditText edtSecondCurrency;
    private MainActivityViewModel viewModel;

    private ConverterRateResponse _converterRateResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init view model
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        initGetCurrenciesLiveData();
        initGetCurrencyRatesLiveData();
        initExchangeRateCalculator();

        viewModel.getCurrencies();
        viewModel.getCurrentRates();

        //init buttons and edittexts
        buttonFirstCurrency = findViewById(R.id.buttonFirstCurrency);
        buttonSecondCurrency = findViewById(R.id.buttonSecondCurrency);
        edtFirstCurrency = findViewById(R.id.edtFirstCurrency);
        edtSecondCurrency = findViewById(R.id.edtSecondCurrency);

        buttonFirstCurrency.setOnClickListener(view -> {
            showSelectCurrencyModal(SELECT_CURRENCY_BUTTON_ONE);
        });

        buttonSecondCurrency.setOnClickListener(view -> {
                    showSelectCurrencyModal(SELECT_CURRENCY_BUTTON_TWO);
        });


        edtFirstCurrency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                double amount = Double.parseDouble(editable.toString().isEmpty() ? "0" : editable.toString());
                viewModel.calculateExchangeRateFirstInput(selectedFirstCurrencyRate, amount, selectedSecondCurrencyRate);

            }
        });

//       edtSecondCurrency.addTextChangedListener(new TextWatcher() {
//           @Override
//           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//           }
//
//           @Override
//           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//           }
//
//           @Override
//           public void afterTextChanged(Editable editable) {
//
//           }
//       });




    }


    private void initGetCurrenciesLiveData() {
        viewModel.currenciesLiveData.observe(this, currencies -> {
            if (currencies != null) {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                currencyList = currencies;
            } else {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initGetCurrencyRatesLiveData() {
        viewModel.converterRateResponseLiveData.observe(this, converterRateResponse -> {
            if (converterRateResponse != null) {
                _converterRateResponse = converterRateResponse;
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void showSelectCurrencyModal(int selectCurrencyButton) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.currency_list_view, null);
        CurrencyListAdapter adapter = new CurrencyListAdapter(this, currencyList);
        ListView listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            TextView txtCurrencyShort = view1.findViewById(R.id.txtCurrencyShort);
            TextView txtCurrencyName = view1.findViewById(R.id.txtCurrencyName);
            String currencyShort = txtCurrencyShort.getText().toString();
            String currencyName = txtCurrencyName.getText().toString();

            if (selectCurrencyButton == SELECT_CURRENCY_BUTTON_ONE) {
                selectedFirstCurrency = currencyName;
                selectedFirstCurrencyCode = currencyShort;
                buttonFirstCurrency.setText(selectedFirstCurrency + " " + selectedFirstCurrencyCode);
                selectedFirstCurrencyRate = _converterRateResponse.getRates().get(selectedFirstCurrencyCode).getAsDouble();
            }
            if (selectCurrencyButton == SELECT_CURRENCY_BUTTON_TWO) {
                selectedSecondCurrency = currencyName;
                selectedSecondCurrencyCode = currencyShort;
                buttonSecondCurrency.setText(selectedSecondCurrency + " " + selectedSecondCurrencyCode);
                selectedSecondCurrencyRate = _converterRateResponse.getRates().get(selectedSecondCurrencyCode).getAsDouble();
            }
            dialog.dismiss();
        });
        alertDialog.setView(view);

        dialog = alertDialog.create();
        dialog.show();

    }

    private void initExchangeRateCalculator() {
        viewModel.calculatedRateFirstInputLiveData.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                edtSecondCurrency.setText(String.format(Locale.ENGLISH, aDouble.toString()));
            }
        });

//        viewModel.calculatedRateSecondLiveData.observe(this, new Observer<Double>() {
//            @Override
//            public void onChanged(Double aDouble) {
//                edtFirstCurrency.setText(String.format(Locale.ENGLISH, aDouble.toString()));
//
//            }
//        });
    }


}