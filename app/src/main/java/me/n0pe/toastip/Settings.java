package me.n0pe.toastip;


import android.app.ActionBar;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
public class Settings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
	SharedPreferences preferences = null;
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
		Log.d(getClass().getSimpleName(), "onSharedPreferenceChanged: "
			+ sharedPreferences + ": " + s);
		if(s.equals("shake_sensitivity")) {
			App.detector.setSensitivity(Integer.parseInt(sharedPreferences.getString(s, "13")));
		}
		return;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		if (actionBar != null) {
			// Show the Up button in the action bar.
			actionBar.setDisplayHomeAsUpEnabled(false);
		}
		addPreferencesFromResource(R.xml.settings);
		PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
		/*
		Map<String, ?> prefs = PreferenceManager.getDefaultSharedPreferences(this).getAll();
		for(String k : prefs.keySet()) {
			Log.d(getClass().getSimpleName(), "onCreate: " + k + ": " + prefs.get(k));
		}
		*/
		return;
	}
	
	@Override
	public boolean onIsMultiPane() {
		return (getResources().getConfiguration().screenLayout
			& Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
	}
	
	protected boolean isValidFragment(String fragmentName) {
		return false; // PreferenceFragment.class.getName().equals(fragmentName)
	}
}
