package me.n0pe.toastip;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {
	String TAG = getClass().getSimpleName();
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive: " + intent.toUri(Intent.FLAG_DEBUG_LOG_RESOLUTION));
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		String action = intent.getAction();
		if(action == null) {
			action = "none";
		}
		if(!preferences.getBoolean("enabled", true)) {
			return;
		}
		if(action.contains("BOOT") && !preferences.getBoolean("receive_boot", true)) {
			return;
		}
		if(action.contains("WIFI") && !preferences.getBoolean("receive_wifi", true)) {
			return;
		}
		if(action.contains("USER") && !preferences.getBoolean("receive_unlock", true)) {
			return;
		}
		if(action.contains("UNLOCK") && !preferences.getBoolean("receive_unlock", true)) {
			return;
		}
		if(action.contains("SCREEN") && !preferences.getBoolean("receive_screen", true)) {
			return;
		}
		String message = null;
		try {
			int ip = ((WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE))
				.getConnectionInfo().getIpAddress();
			message = Formatter.formatIpAddress(ip);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		Toast.makeText(
			context.getApplicationContext(), message,
			PreferenceManager.getDefaultSharedPreferences(context).getBoolean("length", false) ? 1 : 0
		).show();
		return;
	}
}
