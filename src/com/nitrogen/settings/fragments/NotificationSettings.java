package com.nitrogen.settings.fragments;

import com.android.internal.logging.nano.MetricsProto;

import android.os.Bundle;
import com.android.settings.R;
import android.provider.Settings;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.SettingsPreferenceFragment;

import com.nitrogen.settings.preferences.Utils;
import com.nitrogen.settings.preferences.SystemSettingSwitchPreference;

public class NotificationSettings extends SettingsPreferenceFragment {

    private Preference mChargingLeds;
    private static final String INCALL_VIB_OPTIONS = "incall_vib_options";
    private static final String PULSE_AMBIANT_LIGHT_PREF = "pulse_ambient_light";

    private SystemSettingSwitchPreference mPulseEdgeLights;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.nitrogen_settings_notifications);

        PreferenceScreen prefScreen = getPreferenceScreen();

        mPulseEdgeLights = (SystemSettingSwitchPreference) findPreference(PULSE_AMBIANT_LIGHT_PREF);
        boolean mPulseNotificationEnabled = Settings.Secure.getInt(getContentResolver(),
                Settings.Secure.DOZE_ENABLED, 0) != 0;
        mPulseEdgeLights.setEnabled(mPulseNotificationEnabled);

        mChargingLeds = (Preference) findPreference("charging_light");
        if (mChargingLeds != null
                && !getResources().getBoolean(
                        com.android.internal.R.bool.config_intrusiveBatteryLed)) {
            prefScreen.removePreference(mChargingLeds);
        }

        PreferenceCategory incallVibCategory = (PreferenceCategory) findPreference(INCALL_VIB_OPTIONS);
        if (!Utils.isVoiceCapable(getActivity())) {
            prefScreen.removePreference(incallVibCategory);
        }
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.NITROGEN_SETTINGS;
    }
}
