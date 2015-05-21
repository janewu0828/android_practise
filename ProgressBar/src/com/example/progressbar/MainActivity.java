package com.example.progressbar;

import trustedappframework.subprojecttwo.module.ProgressDialogManager;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	private static final String TAG = "MainActivity";
	private final int step1 = 1, step2 = 2, step3 = 3, finish = 4;
	private Thread thread = null;

	private ProgressDialog progressDialog;
	private ProgressDialogManager pd;

	// 宣告Handler並同時建構隱含類別實體
	Handler uiMessageHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			// 讀出ui.xml中的進度光棒
			final ProgressBar pBar = (ProgressBar) findViewById(R.id.ProgressDialogSample_ProgressBar_pBar);
			pBar.setVisibility(View.VISIBLE); // 開始後設為可見

			// 讀出ui.xml中用以表示未開始的圖案
			final ImageView pImg = (ImageView) findViewById(R.id.ProgressDialogSample_ImageView_pImg);
			pImg.setVisibility(View.INVISIBLE); // 開始後設為不可見
			// 讀出ui.xml中的描述用TextView
			TextView tv = (TextView) findViewById(R.id.ProgressDialogSample_TextView_desc);

			switch (msg.what) {
			case step1:
				tv.setText("processing_step1");
				pd.setText("processing_step1");
				Log.i(TAG, "processing_step1");
				break;
			case step2:
				tv.setText("processing_step2");
				pd.setText("processing_step2");
				Log.i(TAG, "processing_step2");
				break;
			case step3:
				tv.setText("processing_step3");
				pd.setText("processing_step3");
				Log.i(TAG, "processing_step3");
				break;
			case finish:
				tv.setText("finish");
				pBar.setVisibility(View.INVISIBLE);
				pImg.setVisibility(View.VISIBLE);
				tv.setText("完成。");
				pd.setText("完成。");
				Log.i(TAG, "完成。");
				thread.interrupt();

				progressDialog.dismiss();

				break;
			}

			super.handleMessage(msg);
		}
	};

	private void doStep1() throws InterruptedException {
		Thread.sleep(3000);

		Message msg = new Message();
		msg.what = step1;
		uiMessageHandler.sendMessage(msg);
	}

	private void doStep2() throws InterruptedException {
		Thread.sleep(3000);
		Message msg = new Message();
		msg.what = step2;
		uiMessageHandler.sendMessage(msg);
	}

	private void doStep3() throws InterruptedException {
		Thread.sleep(3000);
		Message msg = new Message();
		msg.what = step3;
		uiMessageHandler.sendMessage(msg);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui);

		pd = new ProgressDialogManager(MainActivity.this);
		progressDialog = pd.getProgressDialog();
		pd.onCreateDialog("ACAPD", "驗證ing", false);

		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				pd.setText("start");
				progressDialog.show();

				// 建構執行緒
				thread = new Thread() {
					@Override
					public void run() {
						try {
							doStep1();
							doStep2();
							doStep3();
							Thread.sleep(3000);
							Message msg = new Message();
							msg.what = finish;
							uiMessageHandler.sendMessage(msg);
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
						}
					}
				};

				// 開始執行執行緒
				thread.start();
			}
		});

	}

	@Override
	protected void onPause() {
		super.onPause();

		if (thread != null) {
			if (!thread.isInterrupted()) {
				thread.interrupt();
				Log.e(TAG, "onPause: thread.interrupt");
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		thread.interrupt();
		Log.e(TAG, "onDestroy: thread.interrupt");
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
