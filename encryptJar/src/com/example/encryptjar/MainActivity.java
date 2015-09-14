package com.example.encryptjar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.encryptjar.R;

public class MainActivity extends ActionBarActivity {
	private static final String TAG = "encryptJar";
	private static String seed = "guess"; // 种子

	private TextView text_state;
	private EditText edit_seed, edit_input, edit_output;
	private Button btn_Encryption, btn_D;

	private String inputFileName = null;
	private String outputFileName = null;
	private File sdCard = Environment.getExternalStorageDirectory();
	// 欲加密檔案的路径，在res\raw\outputjar.jar.jar找到文件，再放到外部存储的目录下。用於测试
	private File oldFile;

	private FileInputStream fis = null;
	private FileOutputStream fos = null;

	boolean isSuccess = false;

	String outputFilePath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/project/";

	class MyClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			String str = edit_seed.getText().toString();

			inputFileName = edit_input.getText().toString();
			oldFile = new File(sdCard + "/project/", inputFileName);
			outputFileName = edit_output.getText().toString();
			byte[] key;

			if (str == null || "".equals(str)) {
				text_state.setText("seed is null !!!");
				Log.e(TAG, "str is null" + str);
			} else {

				try {
					isSuccess = true;
					key = str.getBytes("UTF-8");
					new AESFiles().encrypt(oldFile, new File(outputFilePath
							+ outputFileName), key);
				} catch (UnsupportedEncodingException e) {
					isSuccess = false;
					e.printStackTrace();
					Log.e(TAG, e.getMessage());
				} catch (Exception e) {
					isSuccess = false;
					e.printStackTrace();
					Log.e(TAG, e.getMessage());
				}

				// MCrypt mcrypt = new MCrypt();
				// try {
				// /* 加密*/
				// String encrypted = MCrypt.bytesToHex(
				// mcrypt.encrypt("需加密的字符") );
				// } catch (Exception e1) {
				// // TODO 自動產生的 catch 區塊
				// e1.printStackTrace();
				// }

				// // 加密保存
				// seed = str;
				// isSuccess = true;
				//
				// try {
				// fis = new FileInputStream(oldFile);
				//
				// byte[] oldByte = new byte[(int) oldFile.length()];
				// fis.read(oldByte); // 读取
				//
				// AESUtils mAES = new AESUtils(seed);
				//
				// // 加密
				// byte[] newByte = AESUtils_old.encryptVoice(seed, oldByte);
				// // byte[] newByte = mAES.encryptCB(oldByte);
				// oldFile = new File(sdCard + "/project/", outputFileName);
				// fos = new FileOutputStream(oldFile);
				// fos.write(newByte);
				//
				// } catch (FileNotFoundException e) {
				// isSuccess = false;
				// e.printStackTrace();
				// Log.e(TAG, "FileNotFoundException= "+e.getMessage());
				// } catch (IOException e) {
				// isSuccess = false;
				// e.printStackTrace();
				// Log.e(TAG, "IOException= "+e.getMessage());
				// } catch (Exception e) {
				// isSuccess = false;
				// e.printStackTrace();
				// Log.e(TAG, "Exception= "+e.getMessage());
				// } finally {
				// try {
				// fis.close();
				// fos.close();
				// } catch (IOException e) {
				// e.printStackTrace();
				// Log.e(TAG, "IOException= "+e.getMessage());
				// }catch (NullPointerException e) {
				// e.printStackTrace();
				// Log.e(TAG, "NullPointerException= "+e.getMessage());
				// }catch (Exception e) {
				// e.printStackTrace();
				// Log.e(TAG, "Exception= "+e.getMessage());
				// }
				// }
				//
				if (isSuccess) {
					text_state.setText("加密成功");
					// Log.i(TAG, "seed= " + seed + ", 加密成功");
					// Log.i(TAG, "filepath= "
					// + oldFile.getAbsolutePath().toString());
				} else {
					text_state.setText("加密失敗");
				}
			}
		}

	}

	class MyClickListener2 implements OnClickListener {

		@Override
		public void onClick(View v) {

			String str = edit_seed.getText().toString();

			inputFileName = edit_input.getText().toString();
			oldFile = new File(sdCard + "/project/", inputFileName);
			Log.i(TAG, "oldFile= " + oldFile.getAbsolutePath().toString());
			outputFileName = edit_output.getText().toString();
			byte[] key;

			if (str == null || "".equals(str)) {
				text_state.setText("seed is null !!!");
				Log.e(TAG, "str is null" + str);
			} else {

				try {
					isSuccess = true;
					key = str.getBytes("UTF-8");
					new AESFiles().decrypt(oldFile, new File(outputFilePath
							+ outputFileName), key);
				} catch (UnsupportedEncodingException e) {
					isSuccess = false;
					e.printStackTrace();
					Log.e(TAG, e.getMessage());
				} catch (Exception e) {
					isSuccess = false;
					e.printStackTrace();
					Log.e(TAG, e.getMessage());
				}

				// MCrypt mcrypt = new MCrypt();
				// try {
				// /* 解密*/
				// String decrypted = new String( mcrypt.decryptCB( encrypted )
				// );
				// } catch (Exception e1) {
				// // TODO 自動產生的 catch 區塊
				// e1.printStackTrace();
				// }

				// // 解密保存
				// seed = str;
				// isSuccess = true;
				//
				// try {
				// fis = new FileInputStream(oldFile);
				//
				// byte[] oldByte = new byte[(int) oldFile.length()];
				// fis.read(oldByte); // 读取
				//
				// AESUtils mAES = new AESUtils(seed);
				//
				// // 解密
				// byte[] newByte = AESUtils_old.decryptVoice(seed, oldByte);
				// // byte[] newByte = mAES.decryptCB(oldByte);
				//
				// oldFile = new File(sdCard + "/project/", outputFileName);
				// fos = new FileOutputStream(oldFile);
				// fos.write(newByte);
				//
				// } catch (FileNotFoundException e) {
				// isSuccess = false;
				// e.printStackTrace();
				// Log.e(TAG, "FileNotFoundException= "+e.getMessage());
				// } catch (IOException e) {
				// isSuccess = false;
				// e.printStackTrace();
				// Log.e(TAG, "IOException= "+e.getMessage());
				// } catch (Exception e) {
				// isSuccess = false;
				// e.printStackTrace();
				// Log.e(TAG, "Exception= "+e.getMessage());
				// } finally {
				// try {
				// fis.close();
				// fos.close();
				// } catch (IOException e) {
				// e.printStackTrace();
				// Log.e(TAG, "IOException= "+e.getMessage());
				// }
				// }
				//
				if (isSuccess) {
					text_state.setText("D密成功");
					// Log.i(TAG, "seed= " + seed + ", D密成功");
					// Log.i(TAG, "filepath= "
					// + oldFile.getAbsolutePath().toString());
				} else {
					text_state.setText("D密失敗");
				}
			}
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		text_state = (TextView) findViewById(R.id.text_state);
		edit_seed = (EditText) findViewById(R.id.edit_seed);
		edit_input = (EditText) findViewById(R.id.edit_input);
		edit_output = (EditText) findViewById(R.id.edit_output);

		btn_Encryption = (Button) findViewById(R.id.btn_encryption);
		btn_Encryption.setOnClickListener(new MyClickListener());

		btn_D = (Button) findViewById(R.id.btn_D);
		btn_D.setOnClickListener(new MyClickListener2());
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
