package trustedappframework.subprojecttwo.module;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogManager {
	private static ProgressDialog progressDialog;

	// private Context context;

	public ProgressDialogManager(Context context) {
		// public ProgressDialogManager(Context context, String str, boolean
		// cancel) {
		// this.context = context;
		progressDialog = new ProgressDialog(context);
		// progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// progressDialog.setMessage(str);
		// progressDialog.setCancelable(cancel);
	}

	public ProgressDialog getProgressDialog() {
		return progressDialog;
	}

	/**
	 * create dialog for progress bar
	 */
	@Deprecated
	public Dialog onCreateDialog(String title, String message, boolean cancel) {
//		 progressDialog.setIcon(R.drawable.ic_launcher);
		progressDialog.setTitle(title);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage(message);
		progressDialog.setCancelable(cancel);

		return progressDialog;
	}

	public void setText(String str) {
		progressDialog.setMessage(str);
	}

}
