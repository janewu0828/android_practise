package com.example.encryptjar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Log;

public class AESFiles {
	final String TAG = "AESFiles";
	byte[] iv;
	byte[] ivBytes;
	IvParameterSpec ivParameterSpec;
	Cipher cipher;

	public AESFiles() {
		try {
//			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher = Cipher.getInstance("AES/CBC/NoPadding");
			iv = "fedcba9876543210".getBytes("UTF-8");
			ivBytes = getKeyBytes(iv);
			ivParameterSpec = new IvParameterSpec(ivBytes);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}

	}

	private byte[] getKeyBytes(final byte[] key) throws Exception {
		byte[] keyBytes = new byte[16];
		System.arraycopy(key, 0, keyBytes, 0,
				Math.min(key.length, keyBytes.length));
		return keyBytes;
	}

	public Cipher getCipherEncrypt(final byte[] key) throws Exception {
		byte[] keyBytes = getKeyBytes(key);
		// Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		// Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
		// IvParameterSpec ivParameterSpec = new IvParameterSpec(keyBytes);
		// byte[] iv = "fedcba9876543210".getBytes("UTF-8");
		// byte[] ivBytes = getKeyBytes(iv);
		// IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		return cipher;
	}

	public Cipher getCipherDecrypt(byte[] key) throws Exception {
		byte[] keyBytes = getKeyBytes(key);
		// Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		// Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
		// IvParameterSpec ivParameterSpec = new IvParameterSpec(keyBytes);
		// byte[] iv = "fedcba9876543210".getBytes("UTF-8");
		// byte[] ivBytes = getKeyBytes(iv);
		// IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
		return cipher;
	}

	public void encrypt(File inputFile, File outputFile, byte[] key)
			throws Exception {
		Cipher cipher = getCipherEncrypt(key);
		FileOutputStream fos = null;
		CipherOutputStream cos = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(inputFile);
			fos = new FileOutputStream(outputFile);
			cos = new CipherOutputStream(fos, cipher);

			// once cos wraps the fos, you should set it to null
			fos = null;

			byte[] data = new byte[1024];
			int read = fis.read(data);
			while (read != -1) {
				cos.write(data, 0, read);
				read = fis.read(data);
				// System.out.println(new String(data, "UTF-8").trim());
				Log.i(TAG, "encrypt, data= " + new String(data, "UTF-8").trim());
			}
			cos.flush();
		} finally {
			if (cos != null) {
				cos.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
	}

	public void decrypt(File inputFile, File outputFile, byte[] key)
			throws Exception {
		Cipher cipher = getCipherDecrypt(key);
		FileOutputStream fos = null;
		CipherInputStream cis = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(inputFile);
			cis = new CipherInputStream(fis, cipher);
			fos = new FileOutputStream(outputFile);
			byte[] data = new byte[1024];
			int read = cis.read(data);
			while (read != -1) {
				fos.write(data, 0, read);
				read = cis.read(data);
				// System.out.println(new String(data, "UTF-8").trim());
				Log.i(TAG, "decrypt, data= "+new String(data, "UTF-8").trim());
			}
		} finally {
			fos.close();
			cis.close();
			fis.close();
		}
	}
}