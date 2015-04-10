package com.example.getdeviceinfo;

import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String imei = getIMEI();
		String uuid = getDeviceId(MainActivity.this);

		TextView txt = (TextView) findViewById(R.id.txt);
		txt.setText("IMEI: " + imei + "\n" + "UUID: " + uuid);

		System.out.println("IMEI:" + imei + "\n UUID(now): " + uuid
				+ ", UUID(org): 890f9b50-749d-31aa-a06f-6caf63379ed2");
		// 01-22 12:07:25.250: I/System.out(27072): uuid(now):
		// 890f9b50-749d-31aa-a06f-6caf63379ed2, uuid(org):

		System.out.println("IMEI length: " + imei.length());
		System.out.println("UUID length: " + uuid.length());

	}

	private String getIMEI() {
		// TODO Auto-generated method stub
		TelephonyManager tM = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		String IMEI = tM.getDeviceId();

		return IMEI;
	}

	private String getDeviceId(Context context) {
		// TODO Auto-generated method stub
		DeviceUuidFactory DFactory = new DeviceUuidFactory(context);
		String deviceId = DFactory.getDeviceUuid().toString();

		return deviceId;
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
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
}
