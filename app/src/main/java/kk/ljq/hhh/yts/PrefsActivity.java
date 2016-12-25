package kk.ljq.hhh.yts;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Elhadedy on 11/12/2016.
 */

public class PrefsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
        final ListPreference pLocAuto = (ListPreference) findPreference("listpref");
        pLocAuto.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int index = pLocAuto.findIndexOfValue(newValue.toString());
                Log.e("Sass",""+index+"");
                if (index == 1)
                {
                    Toast.makeText(getBaseContext(), pLocAuto.getEntries()[index], Toast.LENGTH_LONG).show();
                    Intent refresh = new Intent(PrefsActivity.this, MainActivity.class);
                    startActivity(refresh);//Start the same Activity
                    finish(); //finish Activity.

                }
                else {
                    Toast.makeText(getBaseContext(), pLocAuto.getEntries()[index], Toast.LENGTH_LONG).show();
                    Intent refresh = new Intent(PrefsActivity.this, MainActivity.class);
                    startActivity(refresh);//Start the same Activity
                    finish(); //finish Activity.
                }
                return true;
            }
        });


    }

}
