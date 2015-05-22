package trustedappframework.subprojecttwo.module;

import com.example.testasynctask.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.TextView;

public class AlertDialogManager {
	private float myTextSize = 28.0f;

	/**
	 * Function to display simple AlertDialog
	 * 
	 * @param context
	 *            - application context
	 * @param title
	 *            - alert dialog title
	 * @param message
	 *            - alert message
	 * @param status
	 *            - success/failure (used to set icon) - pass null if you don't
	 *            want icon
	 * */
	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message,
			Boolean status) {

		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Changing title font size of AlertDialog
		// TextView textViewTitle = new TextView(context);
		// textViewTitle.setText(title);
		// textViewTitle.setTextSize(myTextSize);
		// alertDialog.setCustomTitle(textViewTitle);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		if (status != null) {
			// Setting alert dialog icon
			alertDialog
					.setIcon((status) ? R.drawable.success : R.drawable.fail);

			// Setting OK Button
			if (status) {
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
			} else {
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								System.exit(0);
							}
						});
			}

			// Showing Alert Message
			alertDialog.show();

			// Changing message font size of AlertDialog
			alertDialog.getWindow().getAttributes();

			TextView textViewMessage = (TextView) alertDialog
					.findViewById(android.R.id.message);
			textViewMessage.setTextSize(myTextSize);

			// Changing button font size of AlertDialog
			Button btn = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
			btn.setTextSize(myTextSize);
		}
	}

}
