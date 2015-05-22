package trustedappframework.subprojecttwo.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.util.Log;

public class Decrypt {
	private static final String TAG = "Decrypt";

	private String outputFileName = "decrypted.jar";
	private File decryptFile;

	private FileInputStream fis = null;
	private FileOutputStream fos = null;

	private boolean isSuccess = false;

	public String xor(String a, String b) {
		StringBuilder sb = new StringBuilder();
		for (int k = 0; k < a.length(); k++)
			sb.append((a.charAt(k) ^ b.charAt(k
					+ (Math.abs(a.length() - b.length())))));
		String result;
		result = sb.toString();
		return result;
	}

	public String decryptSessionKey(String[] code, String personalKey) {
		String str = null;
		AESUtils mAES = new AESUtils(personalKey);
		try {

			String decrypted = new String(mAES.decryptEB(code[0]));
			String decrypted2 = new String(mAES.decryptEB(code[1]));
			String decrypted3 = new String(mAES.decryptEB(code[2]));
			// System.out.println("decrypted= " + decrypted);
			// System.out.println("decrypted2= " + decrypted2);
			// System.out.println("decrypted3= " + decrypted3);

			// XOR (decimal)
			CharSequence c2 = decrypted2.subSequence(0, 15);
			CharSequence c3 = decrypted3.subSequence(0, 15);
			// System.out.println("c2= " + c2);
			// System.out.println("c3= " + c3);

			long session_key = 0;
			long[] secret_value = new long[code.length];
			secret_value[0] = Long.parseLong(decrypted);
			secret_value[1] = Long.parseLong(c2.toString());
			secret_value[2] = Long.parseLong(c3.toString());
			for (int i = 0; i < secret_value.length; i++) {
				// System.out.println("secret_value= " + i + ", "
				// + secret_value[i]);
				session_key = session_key ^ secret_value[i];
			}

			str = String.valueOf(session_key);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return str;
	}

	public void decryptJar(String fileName, String folderPath, String seed) {
		// 解密保存
		isSuccess = true;

		decryptFile = new File(folderPath, fileName);
		byte[] oldByte = new byte[(int) decryptFile.length()];

		try {
			fis = new FileInputStream(decryptFile);
			fis.read(oldByte);

			// 解密
			byte[] newByte = AESUtils.decryptFile(seed, oldByte);
			decryptFile = new File(folderPath, outputFileName);
			fos = new FileOutputStream(decryptFile);
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
			Log.e(TAG, "IOException Error: " + e.getMessage());
		} catch (NullPointerException e) {
			e.printStackTrace();
			Log.e(TAG, "NullPointerException Error: " + e.getMessage());
		}

		if (!isSuccess) {
			Log.e(TAG, "decrypted error");
			// Log.i(TAG, "decrypted path= "
			// + decryptFile.getAbsolutePath().toString());
		}
	}

	public String getOutputFileName() {
		return outputFileName;
	}

}
