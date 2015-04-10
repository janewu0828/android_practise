package com.example.getapksign2;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Environment;

public class MainActivity extends ActionBarActivity {

	private static final String TAG = "getAPKSign";

	public void getSingInfo() {

		String PACKAGE_NAME = getApplicationContext().getPackageName();

		try {
			// PackageInfo packageInfo = getPackageManager().getPackageInfo(
			// "com.example.encryptjar", PackageManager.GET_SIGNATURES);
			PackageInfo packageInfo = getPackageManager().getPackageInfo(
					PACKAGE_NAME, PackageManager.GET_SIGNATURES);
			Signature[] signs = packageInfo.signatures;
			Signature sign = signs[0];
			parseSignature(sign.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parseSignature(byte[] signature) {
		try {
			CertificateFactory certFactory = CertificateFactory
					.getInstance("X.509");
			X509Certificate cert = (X509Certificate) certFactory
					.generateCertificate(new ByteArrayInputStream(signature));
			String pubKey = cert.getPublicKey().toString();
			String signNumber = cert.getSerialNumber().toString();
			System.out.println("signName:" + cert.getSigAlgName());
			System.out.println("pubKey:" + pubKey);
			System.out.println("signNumber:" + signNumber);
			System.out.println("subjectDN:" + cert.getSubjectDN().toString());

			Log.e(TAG, "signName:" + cert.getSigAlgName());
			Log.e(TAG, "pubKey:" + pubKey);
			Log.e(TAG, "signNumber:" + signNumber);
			Log.e(TAG, "subjectDN:" + cert.getSubjectDN().toString());

		} catch (CertificateException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getSingInfo();
	}

}
