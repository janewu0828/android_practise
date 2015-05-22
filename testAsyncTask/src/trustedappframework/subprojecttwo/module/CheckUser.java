package trustedappframework.subprojecttwo.module;

import static trustedappframework.subprojecttwo.module.ACAPD.appSecurityEnhancer_url;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckUser {
	public static final int progress_bar_type = 0;

	private String uri = appSecurityEnhancer_url + "php/app.php";
	private String appId = null;
	private String appId2 = null;
	private String UUID = null;
	// private String IMEI = null;

	// 主要是記錄用戶會話過程中的一些用戶的基本訊息
	private HashMap<String, String> session = new HashMap<String, String>();

	/**
	 * @param appId
	 * @param appId2
	 * @param UUID
	 * @param IMEI
	 */
	// public CheckUser(String appId, String appId2,String UUID, String IMEI) {
	// super();
	// this.appId = appId;
	// this.appId2 = appId2;
	// this.UUID = UUID;
	// this.IMEI = IMEI;
	// }

	public CheckUser(String appId, String appId2, String UUID) {
		super();
		this.appId = appId;
		this.appId2 = appId2;
		this.UUID = UUID;
	}

	public boolean checkUser() {

		DefaultHttpClient mHttpClient = new DefaultHttpClient();
		HttpPost mPost = new HttpPost(uri);

		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		pairs.add(new BasicNameValuePair("appId", appId));
		pairs.add(new BasicNameValuePair("appId2", appId2));
		pairs.add(new BasicNameValuePair("UUID", UUID));
		// pairs.add(new BasicNameValuePair("IMEI", IMEI));

		try {
			mPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		try {
			HttpResponse response = mHttpClient.execute(mPost);
			int res = response.getStatusLine().getStatusCode();

			if (res == 200) {
				HttpEntity entity = response.getEntity();

				if (entity != null) {
					String info = EntityUtils.toString(entity);
					System.out.println("-----------info-----------" + info);
					// 以下主要是對伺服器端傳回的資料進行解析
					JSONObject jsonObject = null;
					// flag為身份鑑別成功與否的標記，是從伺服器端傳回的資料
					String flag = "";
					String sessionid = "";
					try {
						jsonObject = new JSONObject(info);
						flag = jsonObject.getString("flag");
						ACAPD.enable_block[0] = jsonObject
								.getString("enable_block");
						ACAPD.enable_block[1] = jsonObject
								.getString("enable_block2");
						ACAPD.enable_block[2] = jsonObject
								.getString("enable_block3");
						sessionid = jsonObject.getString("sessionid");

					} catch (JSONException e) {
						e.printStackTrace();
					}
					// 根據伺服器端返回的標記，判斷伺服器端鑑別是否成功
					if (flag.equals("success")) {
						// 為session傳遞的值，用於在session過程中記錄相關用戶訊息
						session.put("s_enable_block",
								ACAPD.enable_block[0]);
						session.put("s_enable_block2",
								ACAPD.enable_block[1]);
						session.put("s_enable_block3",
								ACAPD.enable_block[2]);
						session.put("s_sessionid", sessionid);

						return true;
					} else {

						return false;
					}
				} else {

					return false;
				}

			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public HashMap<String, String> getSession() {
		return session;
	}

}