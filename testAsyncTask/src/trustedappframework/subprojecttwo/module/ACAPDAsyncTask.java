package trustedappframework.subprojecttwo.module;

import static trustedappframework.subprojecttwo.module.ProjectConfig.pd;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class ACAPDAsyncTask extends AsyncTask<Void, Void, Void> {
	private static final String TAG = "ACAPDAsyncTask";

	private ProgressDialog progressDialog;

	private SendPostRunnable sr = null;
	private ACAPD myACAPD = null;
	private String fileName = null;
	private String key = null;

	public ACAPDAsyncTask() {
		super();

		myACAPD = new ACAPD();
		sr = myACAPD.sr;
	}

	@Override
	protected void onPreExecute() {
		progressDialog = pd.getProgressDialog();
		progressDialog.show();

	}

	@Override
	protected Void doInBackground(Void... arg0) {
		// ---check user ---
		sr.setPostStatus(0);

		// start a Thread, the data to be transferred into the Runnable
		Thread t = new Thread(sr);
		t.start();
		Log.i(TAG, "checkUser start");

		try {
			// wait Thread t
			t.join();
			Log.i(TAG, "checkUser join");
		} catch (InterruptedException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// App Clone Attack Prevention and Detection (ACAPD)
		myACAPD.loadACAPD(fileName, key);

		if (progressDialog != null) {
			progressDialog.dismiss();

		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
