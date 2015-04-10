package com.example.getapksign3;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import java.security.cert.CertificateException;

import android.support.v7.app.ActionBarActivity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	private static final String TAG = "getApkSign";

	TextView text = null;
	StringBuffer sb = new StringBuffer();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		text = (TextView) findViewById(R.id.text);
		try {
			PackageInfo pi = getPackageManager().getPackageInfo(
					getPackageName(), PackageManager.GET_SIGNATURES);
			Signature signatures = pi.signatures[0];
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(signatures.toByteArray());
			byte[] digest = md.digest();
			String res = toHexString(digest);
			Log.e(TAG, "apk md5 = " + res);
			sb.append("apk md5 = " + res);
			MessageDigest md2 = MessageDigest.getInstance("SHA1");
			md2.update(signatures.toByteArray());
			byte[] digest2 = md.digest();
			String res2 = toHexString(digest2);
			Log.e(TAG, "apk SHA1 = " + res2);
			sb.append("\napk SHA1 = " + res2);
			ByteArrayInputStream bais = new ByteArrayInputStream(
					signatures.toByteArray());
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) cf
					.generateCertificate(bais);
			String sigAlgName = cert.getSigAlgName();
			String subjectDN = cert.getSubjectDN().toString();
			Log.e(TAG, "sigAlgName = " + sigAlgName);
			Log.e(TAG, "subjectDN = " + subjectDN);
			sb.append("\n sigAlgName = " + sigAlgName);
			sb.append("\n subjectDN = " + subjectDN);
			bais.close();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		text.setText(sb.toString());
	}

	private void byte2hex(byte b, StringBuffer buf) {
		char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		int high = ((b & 0xf0) >> 4);
		int low = (b & 0x0f);
		buf.append(hexChars[high]);
		buf.append(hexChars[low]);
	}

	/*
	 * Converts a byte array to hex string
	 */
	private String toHexString(byte[] block) {
		StringBuffer buf = new StringBuffer();
		int len = block.length;
		for (int i = 0; i < len; i++) {
			byte2hex(block[i], buf);
			if (i < len - 1) {
				buf.append(":");
			}
		}
		return buf.toString();
	}
}