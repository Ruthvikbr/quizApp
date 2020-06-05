package com.kotlinapps.quizapp.UI;

import android.os.Bundle;


import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.kotlinapps.quizapp.R;
import com.kotlinapps.quizapp.utils.NotificationWorker;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class SettingsClass extends PreferenceFragmentCompat {
    private String NOTIFICATION_WORK = "work";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences,rootKey);
        SwitchPreference notification_preference = findPreference("Notification_preference");
        notification_preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                long current = System.currentTimeMillis();

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,16);
                calendar.set(Calendar.MINUTE,30);

                if(calendar.getTimeInMillis() < current){
                    calendar.add(Calendar.DAY_OF_MONTH,1);
                }
                final WorkManager workManager = WorkManager.getInstance(requireActivity());
                final PeriodicWorkRequest.Builder builder  = new PeriodicWorkRequest.Builder(NotificationWorker.class,
                        1
                , TimeUnit.DAYS);
                builder.setInitialDelay(calendar.getTimeInMillis() - current,TimeUnit.MILLISECONDS);

                Boolean check = (Boolean) newValue;
                if(check){
                    workManager.enqueueUniquePeriodicWork(NOTIFICATION_WORK, ExistingPeriodicWorkPolicy.REPLACE,builder.build());
                }
                else{
                    workManager.cancelUniqueWork(NOTIFICATION_WORK);
                }

                return true;

            }
        });
    }
}
