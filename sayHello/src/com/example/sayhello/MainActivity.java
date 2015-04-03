package com.example.sayhello;

import java.io.File;

import com.example.interfaces.MainInterface;

import dalvik.system.DexClassLoader;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private void test(Context context) {
		try {
			/** here **/
			File sourceFile = new File(
					Environment.getExternalStorageDirectory() + File.separator
							+ "outputhello.jar");// 导出的jar的存储位置

			File file = context.getDir("osdk", 0);// dex临时存储路径

			Log.e("path", sourceFile.getAbsolutePath());

			DexClassLoader classLoader = new DexClassLoader(
					sourceFile.getAbsolutePath(), file.getAbsolutePath(), null,
					context.getClassLoader());

			Class<?> libProviderClazz = classLoader
					.loadClass("com.example.interfaces.InterfaceTest");

			MainInterface mMainInterface = (MainInterface) libProviderClazz
					.newInstance();// 接口
			String str = mMainInterface.sayHello();// 获取jar包提供的数据

			Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
			Log.e("state", "str= " + str);

			File deleteFile = new File(sourceFile.getAbsolutePath());
			boolean deleted = deleteFile.delete();
			
			Log.e("state", String.valueOf(deleted));

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("error", e.getMessage());
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		test(getApplicationContext());
	}

}
