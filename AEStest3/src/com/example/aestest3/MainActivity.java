package com.example.aestest3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// needs <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
        String homeDirName = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/" + getPackageName();
        System.out.println("homeDirName= "+homeDirName.toString());
        
        File file = new File(homeDirName, "test.txt");
        byte[] keyBytes = null;
		try {
			keyBytes = getKey("password");
		} catch (UnsupportedEncodingException e1) {
			// TODO 自動產生的 catch 區塊
			e1.printStackTrace();
		}

        boolean encryptionIsOn = false;
		try {
            File dir = new File(homeDirName);
            if (!dir.exists())
                dir.mkdirs();
            if (!file.exists())
                file.createNewFile();

            OutputStreamWriter osw;

            if (encryptionIsOn) {
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
                IvParameterSpec ivParameterSpec = new IvParameterSpec(keyBytes);
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

                FileOutputStream fos = new FileOutputStream(file);
                CipherOutputStream cos = new CipherOutputStream(fos, cipher);
                osw = new OutputStreamWriter(cos, "UTF-8");
            }
            else    // not encryptionIsOn
                osw = new FileWriter(file);

            BufferedWriter out = new BufferedWriter(osw);
            out.write("This is a testn");
            out.close();
        }
        catch (Exception e) {
            System.out.println("Encryption Exception "+e);
        }

        ///////////////////////////////////
        try {
            InputStreamReader isr;

            if (encryptionIsOn) {
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
                IvParameterSpec ivParameterSpec = new IvParameterSpec(keyBytes);
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

                FileInputStream fis = new FileInputStream(file);
                CipherInputStream cis = new CipherInputStream(fis, cipher);
                isr = new InputStreamReader(cis, "UTF-8");
            }
            else
                isr = new FileReader(file);

            BufferedReader in = new BufferedReader(isr);
            String line = in.readLine();
            System.out.println("Text read: <"+line+">");
            in.close();
        }
        catch (Exception e) {
            System.out.println("Decryption Exception "+e);
        }
    }

	private byte[] getKey(String password) throws UnsupportedEncodingException {
        String key = "";
        while (key.length() < 16)
            key += password;
        return key.substring(0, 16).getBytes("UTF-8");
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
