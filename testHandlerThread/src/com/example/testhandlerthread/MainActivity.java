package com.example.testhandlerthread;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Handler;
import android.os.HandlerThread;

public class MainActivity extends ActionBarActivity {

	// 多執行緒的部分，有幾個名詞：
	// 。Runnable 工作包 (要做的事情)
	// 。Thread 執行緒
	//
	// 。Handler 服務的窗口(給他事情做的地方):
	// // // 1. 直接給他一個Runnable，讓他去執行: mUI_Handler.post(r2);
	// // // 2. 傳一個Message給他: mUI_Handler.sendEmptyMessage(MSG_UPLOAD_OK);
	//
	// 。Message 用一個值 (一句話)代表一堆事情(Runnable)

	// 用現實的例子說明好了
	// Thread ==> 做工的工人
	// Runnable ==> 要做的工作事項 (工作說明書)
	// HandlerThread ==> 特約工人
	// Handler ==> 特約工人的經紀人

	// 找到工人的經紀人，這樣才能派遣工作(找到顯示畫面的UI Thread上的Handler)
	private Handler mUI_Handler = new Handler();

	// 宣告特約工人的經紀人
	private Handler mThreadHandler;

	// 宣告特約工人
	private HandlerThread mThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 聘請一個特約工人，有其經紀人派遣其工人做事 (另起
		// 一個有Handler的Thread)
		mThread = new HandlerThread("name");

		// 讓Worker待命，等待其工作 (開啟Thread)
		mThread.start();

		// 找到特約工人的經紀人，這樣才能派遣工作 (找
		// 到Thread上的Handler)
		mThreadHandler = new Handler(mThread.getLooper());

		// 請經紀人指派工作名稱 r，給工人做
		mThreadHandler.post(r1);

	}

	// 工作名稱 r1 的工作內容
	private Runnable r1 = new Runnable() {

		public void run() {
			// 做了很多事

			// 請經紀人指派工作名稱 r，給工人做
			mUI_Handler.post(r2);
		}
	};

	// 工作名稱 r2 的工作內容
	private Runnable r2 = new Runnable() {

		public void run() {
			// 做了很多事
			// 顯示畫面的動作

		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// 移除工人上的工作
		if (mThreadHandler != null) {
			mThreadHandler.removeCallbacks(r1);
		}

		// 解聘工人 (關閉Thread)
		if (mThread != null) {
			mThread.quit();
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
