package me.n0pe.toastip;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

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
		Toast.makeText(
			context.getApplicationContext(), getDeviceIPAddress(),
			PreferenceManager.getDefaultSharedPreferences(context).getBoolean("length", false) ? 1 : 0
		).show();
		return;
	}
	
	/// from: https://stackoverflow.com/a/30182851/5601157
	public static String getDeviceIPAddress() {
		StringBuilder builder = new StringBuilder();
		try {
			List<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface networkInterface : networkInterfaces) {
				List<InetAddress> inetAddresses = Collections.list(networkInterface.getInetAddresses());
				for (InetAddress inetAddress : inetAddresses) {
					if (!inetAddress.isLoopbackAddress()) {
						String addr = inetAddress.getHostAddress();
						builder.append(addr);
						builder.append('\n');
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return builder.toString();
	}
}
