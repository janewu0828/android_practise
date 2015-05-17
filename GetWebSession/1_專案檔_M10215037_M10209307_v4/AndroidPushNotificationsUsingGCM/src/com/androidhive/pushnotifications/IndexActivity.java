package com.androidhive.pushnotifications;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class IndexActivity extends Activity {
	Button btnInst, btnReg;
	static final int REQUEST_CODE = 10;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		findViews();
    }

	private void findViews() {
		/**說明**/
		btnInst = (Button)findViewById(R.id.btnInst);
		btnInst.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i =new Intent(getApplicationContext(),InstructionsActivity.class);
				startActivityForResult(i, REQUEST_CODE);
			}
		});
		
		/**註冊**/
		btnReg = (Button)findViewById(R.id.btnReg);
		btnReg.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i =new Intent(getApplicationContext(),RegisterActivity.class);
				startActivity(i);
				finish();			
			}
		});
	}
}
