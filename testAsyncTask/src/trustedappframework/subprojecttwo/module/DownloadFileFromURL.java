package trustedappframework.subprojecttwo.module;

import static trustedappframework.subprojecttwo.module.ACAPD.outputFilePath;
import static trustedappframework.subprojecttwo.module.ACAPD.fileName;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;
import android.util.Log;

//AsynTask
public class DownloadFileFromURL extends AsyncTask<String, String, String> {
	private static final String TAG = "DownloadFileFromURL";

	/**
	 * Before starting background thread Show Progress Bar Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	/**
	 * Downloading file in background thread
	 * */
	@Override
	protected String doInBackground(String... f_url) {
		int count;
		try {
			URL url = new URL(f_url[0]);
			URLConnection conection = url.openConnection();
			conection.connect();

			// this will be useful so that you can show a tipical 0-100%
			// progress bar
			int lenghtOfFile = conection.getContentLength();

			// download the file
			InputStream input = new BufferedInputStream(url.openStream(), 8192);

			File outputFileDir = new File(outputFilePath);

			if (!outputFileDir.exists()) {
				outputFileDir.mkdir();
			}
			// Log.i(TAG, "outputFileDir= " + outputFileDir.toString());

			// Output stream
			OutputStream output = new FileOutputStream(outputFilePath
					+ fileName);

			byte data[] = new byte[2048];

			long total = 0;

			while ((count = input.read(data)) != -1) {
				total += count;
				// publishing the progress....
				// After this onProgressUpdate will be called
				publishProgress("" + (int) ((total * 100) / lenghtOfFile));

				// writing data to file
				output.write(data, 0, count);
			}

			// flushing output
			output.flush();

			// closing streams
			output.close();
			input.close();

		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getMessage());
		}

		return null;
	}

	/**
	 * Updating progress bar
	 * */
	protected void onProgressUpdate(String... progress) {
		// setting progress percentage
	}

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/
	@Override
	protected void onPostExecute(String paramString) {
		ACAPD.downloadStatus = true;
		// Log.i(TAG, "completed download, downloadStatus= "
		// + ACAPD.downloadStatus);

		ACAPD.loadACAPD2(fileName, ACAPD.loadPersonalKey);
		// Log.i(TAG, "fileName= " + fileName + ", key="
		// + ACAPD.loadPersonalKey);
	}
}
