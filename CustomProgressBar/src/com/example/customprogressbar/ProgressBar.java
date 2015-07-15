package com.example.customprogressbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ProgressBar extends Activity {

	CharSequence[] values = { "Android", "Apple", "Java" };
	boolean[] itemChecked = new boolean[values.length];
	private ProgressDialog _progressDialog;
	private int _progress = 0;
	private Handler _progressHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress_bar);

		/**
		 * Set click on button
		 */
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(1);
				_progress = 0;
				_progressDialog.setProgress(0);
				_progressHandler.sendEmptyMessage(0);

			}
		});

		/**
		 * start progress thread
		 */
		_progressHandler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (_progress >= 100) {
					_progressDialog.dismiss();
				} else {
					_progress++;
					_progressDialog.incrementProgressBy(1);
					_progressHandler.sendEmptyMessageDelayed(0, 100);
				}
			}
		};

	}

	/**
	 * create dialog for hide and cancel
	 */
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0:
			return new AlertDialog.Builder(this)
					.setIcon(R.drawable.ic_launcher)
					.setTitle("Open Dialog with text...")

					/**
					 * set positive button
					 */
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									Toast.makeText(getBaseContext(),
											"OK button clicked!",
											Toast.LENGTH_SHORT).show();
								}
							})

					/**
					 * set negative button
					 */
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									Toast.makeText(getBaseContext(),
											"Cancel button clicked!",
											Toast.LENGTH_SHORT).show();
								}
							})

					.setMultiChoiceItems(values, itemChecked,
							new DialogInterface.OnMultiChoiceClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which, boolean isChecked) {

									Toast.makeText(
											getBaseContext(),
											values[which]
													+ (isChecked ? " checked!"
															: " unchecked!"),
											Toast.LENGTH_SHORT).show();
								}
							}).create();
		case 1:
			_progressDialog = new ProgressDialog(this);
			_progressDialog.setIcon(R.drawable.ic_launcher);
			_progressDialog.setTitle("Uploading files...");
			_progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			// _progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			// _progressDialog.setMessage("message...");
			// _progressDialog.setCancelable(false);
			_progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Hide",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							Toast.makeText(getBaseContext(), "Hide clicked!",
									Toast.LENGTH_SHORT).show();
						}
					});

			_progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
					"Cancel", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							Toast.makeText(getBaseContext(), "Cancel clicked!",
									Toast.LENGTH_SHORT).show();
						}
					});
			return _progressDialog;
		}
		return null;
	}

}
