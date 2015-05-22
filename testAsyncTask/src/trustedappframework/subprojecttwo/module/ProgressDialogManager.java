package trustedappframework.subprojecttwo.module;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogManager {
	private static ProgressDialog progressDialog;

	public ProgressDialogManager(Context context) {
		progressDialog = new ProgressDialog(context);
	}

	/**
	 * create dialog for progress bar
	 * @param drawable 
	 */
	@Deprecated
	public Dialog onCreateDialog(int drawable, String title, String message, boolean cancel) {
		progressDialog.setIcon(drawable);
		progressDialog.setTitle(title);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage(message);
		progressDialog.setCancelable(cancel);

		return progressDialog;
	}

	public void setText(String str) {
		progressDialog.setMessage(str);
	}

	public ProgressDialog getProgressDialog() {
		return progressDialog;
	}

}
