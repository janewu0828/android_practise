package trustedappframework.subprojecttwo.interfaces;

import static trustedappframework.subprojecttwo.module.ProjectConfig.mAppContext;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;

public class Load {
	private static final String TAG = "Load";

	@SuppressLint("NewApi")
	public void loadJar(String fileName, String folderPath) {
		Context context = mAppContext;
		try {
			String sourceFilePath = folderPath + fileName;

			// export jar path
			File sourceFile = new File(sourceFilePath);

			// export dex tmp path
			File file = context.getDir("osdk", 0);

			DexClassLoader classLoader = new DexClassLoader(
					sourceFile.getAbsolutePath(), file.getAbsolutePath(), null,
					context.getClassLoader());
			// Log.i(TAG, "sourceFile= " + sourceFile.getAbsolutePath());
			// Log.i(TAG, "file= " + file.getAbsolutePath());

			Class<?> libProviderClazz = classLoader
					.loadClass("trustedappframework.subprojecttwo.interfaces.InterfaceTest");

			// interface
			MainInterface mMainInterface = (MainInterface) libProviderClazz
					.newInstance();

			// // return jar result
			// String str = mMainInterface.sayHello();
			// Toast.makeText(context, "jar content= " + str,
			// Toast.LENGTH_SHORT)
			// .show();
			// Log.i(TAG, "jar content= " + str);

			// exe
			mMainInterface.loadMethod();
			// Log.i(TAG, "finished");

			File deleteFile = new File(sourceFile.getAbsolutePath());
			deleteFile.delete();
			// boolean deleted = deleteFile.delete();
			// Log.e(TAG, "deleted= " + String.valueOf(deleted) + ", file= "
			// + sourceFile.getAbsolutePath());

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "Error: " + e.getMessage());
		}
	}

}
