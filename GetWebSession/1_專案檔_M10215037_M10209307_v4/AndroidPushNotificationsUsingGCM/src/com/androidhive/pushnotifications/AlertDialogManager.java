package com.androidhive.pushnotifications;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

public class AlertDialogManager {
	/**
	 * Function to display simple Alert Dialog
	 * @param context - application context
	 * @param title - alert dialog title
	 * @param message - alert message
	 * @param status - success/failure (used to set icon)
	 * 				 - pass null if you don't want icon
	 * */
	public void showAlertDialog(Context context, String title, String message,
			Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		if (status != null)
			// Setting alert dialog icon
			alertDialog
					.setIcon((status) ? R.drawable.success : R.drawable.fail);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}
	
	@SuppressWarnings("deprecation")
	public static void ShowPicDialog(Context context, String title, String msg, View view) {
		AlertDialog PicDialog = new AlertDialog.Builder(context).create();
		PicDialog.setTitle(title);
		PicDialog.setMessage(msg);
		PicDialog.setView(view);
		DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
			}
		};
		PicDialog.setButton("Ok", OkClick);
		PicDialog.show();
	}
	
	public void showInfoDialog(Context context, String title, String message) {
		Builder InfoDialog = new AlertDialog.Builder(context);
		InfoDialog.setTitle(title);
		InfoDialog.setMessage(message);
		InfoDialog.show();
	}
}
