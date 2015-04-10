package com.example.getapksign;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class MainActivity extends ActionBarActivity {
	private static final String TAG = "getapksign";

	private String appid = null;
	private File sdCard = Environment.getExternalStorageDirectory();

	/**
	 * 获取文件的md5值
	 * 
	 * @param path
	 *            文件的全路径名称
	 * @return
	 */
	private String getFileMd5(String path) {
		try {
			// 获取一个文件的特征信息，签名信息。
			// File file = new File(path);
			File file = new File(sdCard, path);
			// md5
			MessageDigest digest = MessageDigest.getInstance("md5");
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = fis.read(buffer)) != -1) {
				digest.update(buffer, 0, len);
			}
			byte[] result = digest.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : result) {
				// 与运算
				int number = b & 0xff;// 加盐
				String str = Integer.toHexString(number);
				// System.out.println(str);
				if (str.length() == 1) {
					sb.append("0");
				}
				sb.append(str);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getArchivePackageName(Context ctx,
			String archiveFilePath) {
		System.out.println("PakageUtil:" + archiveFilePath);
		Log.e(TAG, "PakageUtil= " + archiveFilePath);

		PackageInfo pInfo = ctx.getPackageManager().getPackageArchiveInfo(
				archiveFilePath, PackageManager.GET_ACTIVITIES);
		if (pInfo == null) {
			return null;
		}
		return pInfo.packageName;
	}

	private String getAppId() {
		String appId = "";

		// ---get hash code of apk---
		String PACKAGE_NAME = getApplicationContext().getPackageName();
		String apkName = "";
		String apkPath = Environment.getDataDirectory() + "/app/" + apkName;
		File apkFile = null;
		int i = 0;
		int isNext = 0;

		// ---get apk file name---

		// ---get hash code of apk---
		try {
			appId = Hash.sha256(apkFile);
			// System.out.println("appId= " + appId);
			// System.out.println("appId length= " + appId.length());

		} catch (Exception e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
			Log.e(TAG, "Error: " + e.getMessage());

		}

		return appId;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String md5 = getFileMd5("encryptJar.apk");
		System.out.println(md5);
		Log.e(TAG, md5);

		String PACKAGE_NAME = getApplicationContext().getPackageName();
		Log.e(TAG, "pkg= " + PACKAGE_NAME);

		String path = Environment.getDataDirectory().toString();
		System.out.println(path);
		String result = getArchivePackageName(getApplicationContext(), path);
		Log.e(TAG, "pkg2= " + result);
	}
}