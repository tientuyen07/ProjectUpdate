package com.example.tient.spa;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    Context mContext;

    private static final String NAME_APP = "Spa";
    private static final String FIRST_USER = "IsFirstUser";
    private static final String LOGIN_SUCCESS = "LOGIN_SUCCESS";
    private static final String USERNAME = "username";

    SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        this.mContext = context;
        sp = mContext.getSharedPreferences(NAME_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    // Lưu cặp giá trị (key, value = String)
    public void saveSPString(String keySP, String value) {
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    // Lưu cặp giá trị (key, value  = int)
    public void saveSPInt(String keySP, int value) {
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    // Lưu cặp giá trị (key, value = boolean)
    public void saveSPBoolean(String keySP, boolean value) {
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public Boolean getSPFirstUser() {
        return sp.getBoolean(FIRST_USER, false);
    }

    public Boolean getSPLoginSuccess() {
        return sp.getBoolean(LOGIN_SUCCESS, false);
    }

    public String getSPUsername() {
        return sp.getString(USERNAME, "");
    }
}
