package com.example.androideditdialog;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	Button btnEdit;
	TextView textOut;

	private Button.OnClickListener btnEditOnClickListener = new Button.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO 自動產生的方法 Stub
			AlertDialog.Builder editDialog = new AlertDialog.Builder(
					MainActivity.this);
			editDialog.setTitle("--- Edit ---");

			final EditText editText = new EditText(MainActivity.this);
			editText.setText(textOut.getText());
			editDialog.setView(editText);

			editDialog.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						// do something when the button is clicked
						public void onClick(DialogInterface arg0, int arg1) {
							textOut.setText(editText.getText().toString());
						}
					});
			editDialog.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						// do something when the button is clicked
						public void onClick(DialogInterface arg0, int arg1) {
							// ...
						}
					});
			editDialog.show();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnEdit = (Button) findViewById(R.id.edit);
		textOut = (TextView) findViewById(R.id.textout);

		btnEdit.setOnClickListener(btnEditOnClickListener);
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
