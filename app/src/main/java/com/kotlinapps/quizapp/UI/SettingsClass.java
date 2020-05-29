package com.kotlinapps.quizapp.UI;

import android.os.Bundle;


import androidx.preference.PreferenceFragmentCompat;

import com.kotlinapps.quizapp.R;

public class SettingsClass extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences,rootKey);
    }
}
