package com.example.aes256demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener{
	
	private Button mEncryptButton;
	private Button mDecryptButton;
	private EditText mInputEdit;
	private TextView mShowText;
	
	private byte[] mEncryptText;
	private byte[] mKey;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
			
		mEncryptButton = (Button)findViewById(R.id.main_encrypt_button);
		mDecryptButton = (Button)findViewById(R.id.main_decrypt_button);
		mInputEdit = (EditText)findViewById(R.id.main_input_edittext);
		mShowText = (TextView)findViewById(R.id.main_show_encrypt_textview);
		
		mEncryptButton.setOnClickListener(this);
		mDecryptButton.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		int key = v.getId();
		switch (key) {
		case R.id.main_encrypt_button:
			String source = mInputEdit.getText().toString();
			
			AESEnc aesEncrypt = new AESEnc();

			try {
				mEncryptText = aesEncrypt.encrypt(source.getBytes());
				mKey = aesEncrypt.getKey();

			} catch (Exception e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			}
			
			java.math.BigInteger bi = new java.math.BigInteger(mEncryptText);
			mShowText.setText("encrypt:"+bi.abs().toString(16)+"\n");
			break;
			
		case R.id.main_decrypt_button:
			AESEnc aesDecrypt = new AESEnc(mKey);
			String text = null;
			try {
				text = new String(aesDecrypt.decrypt(mEncryptText));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			mShowText.setText("decrypt:"+text+"\n");
			break;

		default:
			break;
		}
	}
	
	
}