package com.vostdev.deviceid;

import java.util.UUID;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;
import android.provider.Settings.Secure;

public class MainActivity extends ActionBarActivity {
	private String uuid = new String();
	private TextView txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// if (savedInstanceState == null) {
		// getSupportFragmentManager().beginTransaction()
		// .add(R.id.container, new PlaceholderFragment())
		// .commit();
		// }

		uuid = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
		System.out.println("uuid= " + uuid);

		String id = id(this);
		System.out.println("uuid2= " + id);

		String id3 = getUUID(this);
		System.out.println("uuid3= " + id3);

		String IMEI = getIMEI(this);
		System.out.println("imei= " + IMEI);

		txt = (TextView) findViewById(R.id.txt);
		txt.setText("uuid= " + uuid + "\n" + "uuid2= " + id + "\n" + "uuid3= "
				+ id3 + "\n" + "imei= " + IMEI);

		System.out
				.println("now= "
						+ uuid
						+ ", original: "
						+ "uuid= 5fb777fb456634de, uuid2= b2f57fe4-4bcb-4b0d-95dd-c19634230530");
	}

	private static String uniqueID2 = null;
	private static final String PREF_UNIQUE_ID2 = "PREF_UNIQUE_ID";

	public synchronized static String getUUID(Context context) {
		if (uniqueID2 == null) {
			SharedPreferences sharedPrefs = context.getSharedPreferences(
					PREF_UNIQUE_ID2, Context.MODE_PRIVATE);
			uniqueID2 = Secure.getString(context.getContentResolver(),
					Secure.ANDROID_ID);
			if (uniqueID2 == null) {
				uniqueID2 = Secure.getString(context.getContentResolver(),
						Secure.ANDROID_ID);
				Editor editor = sharedPrefs.edit();
				editor.putString(PREF_UNIQUE_ID2, uniqueID2);
				editor.commit();
			}
		}

		return uniqueID2;
	}

	private static String uniqueID = null;
	private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

	public synchronized static String id(Context context) {
		if (uniqueID == null) {
			SharedPreferences sharedPrefs = context.getSharedPreferences(
					PREF_UNIQUE_ID, Context.MODE_PRIVATE);
			uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
			if (uniqueID == null) {
				uniqueID = UUID.randomUUID().toString();
				Editor editor = sharedPrefs.edit();
				editor.putString(PREF_UNIQUE_ID, uniqueID);
				editor.commit();
			}
		}

		return uniqueID;
	}

	// see http://androidsnippets.com/generate-random-uuid-and-store-it

	private String getIMEI(Context context) {
		TelephonyManager tM = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String str = tM.getDeviceId();

		return str;
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	//
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle action bar item clicks here. The action bar will
	// // automatically handle clicks on the Home/Up button, so long
	// // as you specify a parent activity in AndroidManifest.xml.
	// int id = item.getItemId();
	// if (id == R.id.action_settings) {
	// return true;
	// }
	// return super.onOptionsItemSelected(item);
	// }
	//
	// /**
	// * A placeholder fragment containing a simple view.
	// */
	// public static class PlaceholderFragment extends Fragment {
	//
	// public PlaceholderFragment() {
	// }
	//
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// View rootView = inflater.inflate(R.layout.fragment_main, container,
	// false);
	// return rootView;
	// }
	// }

}
