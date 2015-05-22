package trustedappframework.subprojecttwo.module;

import com.example.testasynctask.R;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ProjectConfig {
	private static final String TAG = "ProjectConfig";

	// get global Application object of the current process
	public static Context mAppContext;

	// get context for AlertDialog
	public static Context mContext;

	// show AlertDialog
	private static AlertDialogManager alert = new AlertDialogManager();

	public static ProgressDialogManager pd;

	// dynamic loading class separation (JAR) -----
	public static String[] class_separation_segment = null;

	// tracing traitors -----
	public static String[] personal_key = null;

	public static void checkConnection() {
		// check network setting on device
		ConnectionDetector cd = new ConnectionDetector(mAppContext);
		if (!cd.isConnectingToInternet()) {
			alert.showAlertDialog(mContext,
					mContext.getString(R.string.alert_internet_error_title),
					mContext.getString(R.string.alert_internet_error_msg),
					false);
			Log.e(TAG, "checkConnectionError");
		}
	}

	public static void checkPersonalKey() {
		for (int i = 0; i < personal_key.length; i++) {
			ACAPD.personalKey[i] = PersonalKeyManager.read(personal_key[i]);

			if (ACAPD.personalKey[i].length() > 0
					&& !ACAPD.personalKey[i].trim().isEmpty()) {
				Log.i(TAG, "personalKey= " + ACAPD.personalKey[i]);

			} else {
				showPersonalKeyError();
				Log.e(TAG, "Error: " + "personalKey= null");

			}
		}
	}

	public static void ProgressDialog() {
		pd = new ProgressDialogManager(mContext);
		pd.onCreateDialog(R.drawable.ic_launcher,
				mContext.getString(R.string.progress_loading_title),
				mContext.getString(R.string.progress_loading_msg), false);
	}

	public static void updateProgressDialog(int status) {
		if (status == 1)
			pd.setText(mContext.getString(R.string.progress_checkJar));
		else if (status == 2)
			pd.setText(mContext.getString(R.string.progress_dynamicLoadingJar));
	}

	public static void showPersonalKeyError() {
		// show a Alert Dialog that Personal Key is failed
		alert.showAlertDialog(
				mContext,
				mContext.getResources().getString(
						R.string.alert_personal_key_error_title),
				mContext.getResources().getString(
						R.string.alert_personal_key_error_msg), false);
		Log.e(TAG, "showPersonalKeyError");
	}

	public static void showCheckUserCorrect() {
		Toast.makeText(mAppContext, "toast_checkuser_true", Toast.LENGTH_SHORT)
				.show();

		Log.e(TAG, "showCheckUserCorrect, " + "toast_checkuser_true");
	}

	public static void showCheckUserError() {
		// show a Alert Dialog that Authentication is failed
		alert.showAlertDialog(
				mContext,
				mContext.getResources().getString(
						R.string.alert_checkuser_error_title),
				mContext.getResources().getString(
						R.string.alert_checkuser_error_msg), false);
		Log.e(TAG, "showCheckUserError");
	}

	public static void showToast(String str) {
		Toast.makeText(mAppContext, str, Toast.LENGTH_SHORT).show();
		Log.e(TAG, "showToast, str= " + str);
	}

}
