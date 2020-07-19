package com.iukhan.ecp.Home.ui.Settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.iukhan.ecp.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}