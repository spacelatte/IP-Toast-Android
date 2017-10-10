package me.n0pe.toastip;

import android.app.Application;
import android.content.Intent;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.util.Log;
import com.squareup.seismic.ShakeDetector;

public class App extends Application {
	
	String TAG = getClass().getSimpleName();
	
	public App() {
		super();
		return;
	}
	
	public static ShakeDetector detector = null;
	@Override
	public void onCreate() {
		super.onCreate();
		detector = new ShakeDetector(new ShakeDetector.Listener() {
			@Override
			public void hearShake() {
				Log.d(TAG, "hearShake: ");
				if(!PreferenceManager.getDefaultSharedPreferences(App.this)
					.getBoolean("shake_enabled", true)) {
					return;
				}
				sendBroadcast(new Intent(App.this, Receiver.class));
				return;
			}
		});
		
		detector.setSensitivity(Integer.parseInt(
			PreferenceManager.getDefaultSharedPreferences(this).getString("shake_sensitivity", "13")
		));
		detector.start((SensorManager) getSystemService(SENSOR_SERVICE));
		Log.d(TAG, "onCreate: " + getApplicationInfo());
		return;
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		return;
	}
}
