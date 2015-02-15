package com.example.aestest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {
	String username = "bob@google.org";
	String password = "Password1";
	String secretID = "BlahBlahBlah";
	String SALT2 = "deliciously salty";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get the Key
		byte[] key;
		try {
			key = (SALT2 + username + password).getBytes("UTF-8");
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16); // use only first 128 bit
			System.out.println((SALT2 + username + password).getBytes().length);

			// Need to pad key for AES
			// TODO: Best way?

			// Generate the secret key specs.
			SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

			// Instantiate the cipher
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

			// byte[] encrypted = cipher.doFinal((secrectID).getBytes());
			// System.out.println("encrypted string: " + asHex(encrypted));
			//
			// cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			// byte[] original = cipher.doFinal(encrypted);
			// String originalString = new String(original);
			// System.out.println("Original string: " + originalString +
			// "\nOriginal string (Hex): " + asHex(original));
		} catch (UnsupportedEncodingException e1) {
			// TODO 自動產生的 catch 區塊
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}

	}
	
	static void encrypt() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
	    // Here you read the cleartext.
	    FileInputStream fis = new FileInputStream("data/cleartext");
	    // This stream write the encrypted text. This stream will be wrapped by another stream.
	    FileOutputStream fos = new FileOutputStream("data/encrypted");

	    // Length is 16 byte
	    // Careful when taking user input!!! http://stackoverflow.com/a/3452620/1188357
	    SecretKeySpec sks = new SecretKeySpec("MyDifficultPassw".getBytes(), "AES");
	    // Create cipher
	    Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.ENCRYPT_MODE, sks);
	    // Wrap the output stream
	    CipherOutputStream cos = new CipherOutputStream(fos, cipher);
	    // Write bytes
	    int b;
	    byte[] d = new byte[8];
	    while((b = fis.read(d)) != -1) {
	        cos.write(d, 0, b);
	    }
	    // Flush and close streams.
	    cos.flush();
	    cos.close();
	    fis.close();
	}
	
	static void decrypt() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
	    FileInputStream fis = new FileInputStream("data/encrypted");

	    FileOutputStream fos = new FileOutputStream("data/decrypted");
	    SecretKeySpec sks = new SecretKeySpec("MyDifficultPassw".getBytes(), "AES");
	    Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.DECRYPT_MODE, sks);
	    CipherInputStream cis = new CipherInputStream(fis, cipher);
	    int b;
	    byte[] d = new byte[8];
	    while((b = cis.read(d)) != -1) {
	        fos.write(d, 0, b);
	    }
	    fos.flush();
	    fos.close();
	    cis.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
