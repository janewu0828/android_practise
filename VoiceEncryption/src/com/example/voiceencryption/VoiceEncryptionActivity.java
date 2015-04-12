package com.example.voiceencryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class VoiceEncryptionActivity extends Activity implements
		OnClickListener {
	private static final String TAG = "VoiceEncryptionActivity";
	private static final String seed = "guess"; // 种子
	private MediaPlayer mPlayer;
	private Button mPlayButton;
	private Button mEncryptionButton;
	private Button mDecryptionButton;
	private File sdCard = Environment.getExternalStorageDirectory();
	private File oldFile = new File(sdCard, "recording_old.3gpp");
	// 音频文件的路径，在res\raw\recording_old.3gpp中找到音频文件，再放到外部存储的根目录下。用于测试
	private FileInputStream fis = null;
	private FileOutputStream fos = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voice_encryption);
		mPlayButton = (Button) findViewById(R.id.playButton);
		mPlayButton.setOnClickListener(this);
		mEncryptionButton = (Button) findViewById(R.id.encryptionButton);
		mEncryptionButton.setOnClickListener(this);
		mDecryptionButton = (Button) findViewById(R.id.decryptionButton);
		mDecryptionButton.setOnClickListener(this);

	}

	@SuppressWarnings("static-access")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.playButton:
			if (mPlayer != null) {
				mPlayer.release();
				mPlayer = null;
			}
			// mPlayer = MediaPlayer.create(this, R.raw.recording_old);
			boolean isSuccess = true;
			try {
				fis = new FileInputStream(oldFile);
				mPlayer = new MediaPlayer();
				mPlayer.setDataSource(fis.getFD());
				mPlayer.prepare(); // 去掉会出错
				mPlayer.start();
			} catch (FileNotFoundException e) {
				isSuccess = false;
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				isSuccess = false;
				e.printStackTrace();
			} catch (IllegalStateException e) {
				isSuccess = false;
				e.printStackTrace();
			} catch (IOException e) {
				isSuccess = false;
				e.printStackTrace();
			} finally {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (!isSuccess)
				Toast.makeText(this, "播放失败", Toast.LENGTH_SHORT).show();
			break;

		case R.id.encryptionButton:
			// 加密保存
			isSuccess = true;
			try {
				fis = new FileInputStream(oldFile);
				byte[] oldByte = new byte[(int) oldFile.length()];
				fis.read(oldByte); // 读取
				byte[] newByte = AESUtils.encryptVoice(seed, oldByte);
				// 加密
				fos = new FileOutputStream(oldFile);
				fos.write(newByte);

			} catch (FileNotFoundException e) {
				isSuccess = false;
				e.printStackTrace();
			} catch (IOException e) {
				isSuccess = false;
				e.printStackTrace();
			} catch (Exception e) {
				isSuccess = false;
				e.printStackTrace();
			} finally {
				try {
					fis.close();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (isSuccess)
				Toast.makeText(this, "加密成功", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(this, "加密失败", Toast.LENGTH_SHORT).show();

			Log.i(TAG, "保存成功");
			break;

		case R.id.decryptionButton:
			// 解密保存
			isSuccess = true;
			byte[] oldByte = new byte[(int) oldFile.length()];
			try {
				fis = new FileInputStream(oldFile);
				fis.read(oldByte);
				byte[] newByte = AESUtils.decryptVoice(seed, oldByte);
				// 解密
				fos = new FileOutputStream(oldFile);
				fos.write(newByte);

			} catch (FileNotFoundException e) {
				isSuccess = false;
				e.printStackTrace();
			} catch (IOException e) {
				isSuccess = false;
				e.printStackTrace();
			} catch (Exception e) {
				isSuccess = false;
				e.printStackTrace();
			}
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (isSuccess)
				Toast.makeText(this, "解密成功", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(this, "解密失败", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}

	}
}
