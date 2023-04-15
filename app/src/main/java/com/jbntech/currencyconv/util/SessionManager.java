package com.jbntech.currencyconv.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {


    private static String TAG = SessionManager.class.getSimpleName();

    //Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    //Shared pref mode
    int PRIVATE_MODE = 0;

    //share pref file name
    private static final String PREF_NAME = "CurrencyConverterPref";

    private static final String KEY_IS_REMOTE_QUERIED = "isRemoteQueried";
    private static final String KEY_REMOTE_QUERIED_DATE = "remoteQueiriedDate";

    public SessionManager(Context context){
        this._context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setRemoteQueried(boolean remoteQueried){
        editor.putBoolean(KEY_IS_REMOTE_QUERIED, remoteQueried);
        editor.commit();
    }

    public boolean isRemoteQueried(){
        return pref.getBoolean(KEY_IS_REMOTE_QUERIED, false);
    }

    public void setRemoteQueriedDate(String remoteQueriedDate){
        editor.putString(KEY_REMOTE_QUERIED_DATE, remoteQueriedDate);
        editor.commit();
    }

    public String getRemoteQueriedDate(){
        return pref.getString(KEY_REMOTE_QUERIED_DATE, "2020-01-01");
    }


}
