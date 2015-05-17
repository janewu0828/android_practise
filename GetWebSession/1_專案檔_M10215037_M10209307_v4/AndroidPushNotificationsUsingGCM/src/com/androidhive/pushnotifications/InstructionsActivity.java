package com.androidhive.pushnotifications;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class InstructionsActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instructions);
	}

	@Override
	public void finish() {
		Intent data = new Intent();
		setResult(RESULT_OK, data);
		super.finish();
	}
}