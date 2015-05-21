package com.example.testasynctask;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.AsyncTask;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {
	private Context context;
//	private ProgressDialog pd;
	private Button b;

	@Override
	protected void onDestroy() {
//		if (pd != null) {
//			pd.dismiss();
//			b.setEnabled(true);
//		}
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = this;
		b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TestAsyncTask task = new TestAsyncTask(context);
				task.execute((Void[]) null);
						
//				v.setEnabled(false);
//				AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
//
//					@Override
//					protected void onPreExecute() {
//						pd = new ProgressDialog(context);
//						pd.setTitle("Processing...");
//						pd.setMessage("Please wait.");
//						pd.setCancelable(false);
//						pd.setIndeterminate(true);
//						pd.show();
//					}
//
//					@Override
//					protected Void doInBackground(Void... arg0) {
//						try {
//							// Do something...
//							Thread.sleep(5000);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//						return null;
//					}
//
//					@Override
//					protected void onPostExecute(Void result) {
//						if (pd != null) {
//							pd.dismiss();
//							b.setEnabled(true);
//						}
//					}
//
//				};
//				task.execute((Void[]) null);
			}
		});
		
//		new DownloadFilesTask().execute(url1, url2, url3);
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
