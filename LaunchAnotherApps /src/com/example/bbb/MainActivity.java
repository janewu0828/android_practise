package com.example.bbb;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	static final int REQUEST_ACTION_PICK = 1;
	public static final String PACKAGE_NAME = "irdc.ex04_16";
	public static final String CLASS_NAME = "irdc.ex04_16.EX04_16";
	private List<ApplicationInfo> m_appList;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 拍照後顯示圖片
		ImageView iv = (ImageView) findViewById(R.id.imagecaptured);
		if (resultCode == RESULT_OK) {
			// 取出拍照後回傳資料
			Bundle extras = data.getExtras();
			// 將資料轉換為圖像格式
			Bitmap bmp = (Bitmap) extras.get("data");
			// 載入ImageView
			iv.setImageBitmap(bmp);
		}

		// 覆蓋原來的Activity
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// galleryHandler();
		// sendTextHandler();

		// startNewActivity(this, PACKAGE_NAME) ;

		// 調用其他服務拍照按鈕
		Button buttonCamera = (Button) findViewById(R.id.captureimage);

		buttonCamera.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// 使用Intent調用其他服務幫忙拍照
				Intent intent_camera = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent_camera, 0);
			}

		});
	}

	public void startNewActivity(Context context, String packageName) {
		Intent intent = context.getPackageManager().getLaunchIntentForPackage(
				packageName);
		if (intent == null) {
			// Bring user to the market or let them choose an app?
			intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("market://details?id=" + packageName));
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	public void galleryHandler() {
		if (checkLineInstalled()) {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			startActivityForResult(Intent.createChooser(intent, "select"),
					REQUEST_ACTION_PICK);
		} else {
			Toast toast = Toast.makeText(this, "LINEがインストールされていません",
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	public void sendTextHandler() {
		String sendText = ((TextView) findViewById(R.id.hello)).getText()
				.toString();
		if (checkLineInstalled()) {
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setClassName(PACKAGE_NAME, CLASS_NAME);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_TEXT, sendText);
			startActivity(intent);
		} else {
			Toast toast = Toast.makeText(this, "LINEがインストールされていません",
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	private boolean checkLineInstalled() {
		PackageManager pm = getPackageManager();
		m_appList = pm.getInstalledApplications(0);
		boolean lineInstallFlag = false;
		for (ApplicationInfo ai : m_appList) {
			if (ai.packageName.equals(PACKAGE_NAME)) {
				lineInstallFlag = true;
				break;
			}
		}
		return lineInstallFlag;
	}

	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// String fname = "send_image.jpg";
	// String fileFullPath = Environment.getExternalStorageDirectory()
	// .getAbsolutePath() + File.separator + fname;
	// FileOutputStream out = null;
	//
	// if (resultCode == RESULT_OK) {
	// if (requestCode == REQUEST_ACTION_PICK) {
	// try {
	// InputStream iStream = getContentResolver().openInputStream(
	// data.getData());
	// ByteArrayOutputStream os = new ByteArrayOutputStream();
	// Bitmap bm = BitmapFactory.decodeStream(iStream);
	// bm.compress(Bitmap.CompressFormat.JPEG, 100, os);
	// os.flush();
	// byte[] w = os.toByteArray();
	// os.close();
	// iStream.close();
	// out = new FileOutputStream(fileFullPath);
	// out.write(w, 0, w.length);
	// out.flush();
	//
	// Uri uri = Uri.fromFile(new File(fileFullPath));
	//
	// Intent intent = new Intent(Intent.ACTION_SEND);
	//
	// intent.setClassName(PACKAGE_NAME, CLASS_NAME);
	// intent.setType("image/jpeg");
	// intent.putExtra(Intent.EXTRA_STREAM, uri);
	// // Bitmapで普通に利用ができます。
	// // ((ImageView)findViewById(R.id.imageView1)).setImageBitmap(bm);
	// startActivity(intent);
	// } catch (IOException e) {
	// Log.d("test_error", e.getMessage());
	// }
	// }
	// }
	// super.onActivityResult(requestCode, resultCode, data);
	// }
}