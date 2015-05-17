package com.androidhive.pushnotifications;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class GameOverFaillActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gameover_fail);
	}

	@Override
	public void finish() {
		super.finish();
		Intent data = new Intent();
		setResult(RESULT_OK, data);
	}
}