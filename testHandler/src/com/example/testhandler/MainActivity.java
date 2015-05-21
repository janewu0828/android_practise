package com.example.testhandler;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	Thread countToTen;
	TextView txtCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txtCount = (TextView) findViewById(R.id.textView1);

		countToTen = new CountToTen();
		countToTen.start();
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			txtCount.setText(Integer.toString(msg.getData().getInt("count", 0)));
		}
	};

	class CountToTen extends Thread {

		@Override
		public void run() {
			super.run();
			try {
				for (int i = 0; i < 10; i++) {
					Thread.sleep(1000);
					Bundle countBundle = new Bundle();
					countBundle.putInt("count", i + 1);

					Message msg = new Message();
					msg.setData(countBundle);

					mHandler.sendMessage(msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
