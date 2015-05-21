package com.example.testasynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class TestAsyncTask extends AsyncTask<Void, Void, Void> {
	private Context context;
	private ProgressDialog pd;

	public TestAsyncTask(Context context) {
		super();
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		pd = new ProgressDialog(context);
		pd.setTitle("Processing...");
		pd.setMessage("Please wait.");
		pd.setCancelable(false);
		pd.setIndeterminate(true);
		pd.show();
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		try {
			// Do something...
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		if (pd != null) {
			pd.dismiss();
			
		}
	}

}
