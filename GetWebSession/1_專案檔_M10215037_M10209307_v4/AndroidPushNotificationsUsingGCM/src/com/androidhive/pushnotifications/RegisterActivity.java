package com.androidhive.pushnotifications;

import static com.androidhive.pushnotifications.CommonUtilities.SENDER_ID;
import static com.androidhive.pushnotifications.CommonUtilities.SERVER_URL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	// alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();

	// Internet detector
	ConnectionDetector cd;

	// UI elements
	EditText txtName;
	EditText txtEmail;

	// Register button
	Button btnRegister;

	/** RadioPlayer **/
	RadioGroup radiogroup;
	RadioButton radioPlayer, radioHunter;
	// player: Player=0, Hunter=1
	int player = 0;
	public static final String ACTIVITY_TAG_PLAYER = "tagPlayer";
	static final String ACTIVITY_TAG_MESSAGE = "tagMessage";
	/** back to MainActivity from LocationActivity3 **/
	static final int REQUEST_CODE = 10;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(RegisterActivity.this,
					getString(R.string.msg_alertInternet),
					getString(R.string.msg_alertInternetConnection), false);
			// stop executing code by return
			return;
		}

		// Check if GCM configuration is set
		if (SERVER_URL == null || SENDER_ID == null || SERVER_URL.length() == 0
				|| SENDER_ID.length() == 0) {
			// GCM sernder id / server url is missing
			alert.showAlertDialog(RegisterActivity.this,
					getString(R.string.msg_alertConfiguration),
					getString(R.string.msg_alertConfigurationCorrect), false);
			// stop executing code by return
			return;
		}

		txtName = (EditText) findViewById(R.id.txtName);
		txtEmail = (EditText) findViewById(R.id.txtEmail);

		radiogroup = (RadioGroup) findViewById(R.id.RadioPlayer);
		radioPlayer = (RadioButton) findViewById(R.id.player0);
		radioHunter = (RadioButton) findViewById(R.id.player1);
		radiogroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						// player: Player=0, Hunter=1
						if (checkedId == radioHunter.getId()) {
							player = 1;
						} else {
							player = 0;
						}
						Log.i(ACTIVITY_TAG_PLAYER,
								"RegisterActivity: switch player");
					}
				});

		btnRegister = (Button) findViewById(R.id.btnRegister);
		/*
		 * Click event on Register button
		 */
		btnRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Read EditText dat
				String name = txtName.getText().toString();
				String email = txtEmail.getText().toString();
				String txtPlayer = Integer.toString(player);

				// Check if user filled the form
				if (name.trim().length() > 0 && email.trim().length() > 0) {
					// Launch Main Activity
					Intent i = new Intent(getApplicationContext(),
							PolylinesPolygonsActivity.class);

					// Registering user on our server
					// Sending registraiton details to MainActivity
					i.putExtra("name", name);
					i.putExtra("email", email);
					i.putExtra("player", txtPlayer);
					Log.i(ACTIVITY_TAG_PLAYER, "RegisterActivity player= "
							+ player);

					startActivity(i);		
					finish();
				} else {
					// user doen't filled that data
					// ask him to fill the form
					alert.showAlertDialog(RegisterActivity.this,
							getString(R.string.msg_alertRegError),
							getString(R.string.msg_alertRegErrorCorrect), false);
				}
			}
		});
	}
}
