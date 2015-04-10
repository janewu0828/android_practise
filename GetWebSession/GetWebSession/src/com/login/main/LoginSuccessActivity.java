package com.login.main;

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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginSuccessActivity extends Activity {

	private HashMap<String, String> session;

	private String url = GetWebSession.server + "getinfo.php";

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login_success);

		// 获取从登录成功后界面的传递的参数
		session = (HashMap<String, String>) this.getIntent()
				.getBundleExtra("session").getSerializable("sessionid");
		// 读取session的基本信息，并显示相应的控件
		String userid_info = session.get("s_userid");
		String username_info = session.get("s_username");
		String session_id = session.get("s_sessionid");

		// 显示相应的内容到控件
		TextView userid_show = (TextView) findViewById(R.id.userid_show);
		userid_show.setText(userid_info);
		TextView username_show = (TextView) findViewById(R.id.username_show);
		username_show.setText(username_info);
		TextView sessionid_show = (TextView) findViewById(R.id.sessionid_show);
		sessionid_show.setText(session_id);

		// 根据本次session再次获取用户信息
		Button getInfo = (Button) findViewById(R.id.getinfo);

		getInfo.setOnClickListener(getInfoClick);

	}

	OnClickListener getInfoClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (getUserInfo()) {
				Context context = v.getContext();
				Intent intent = new Intent(context, GetUserInfoActivity.class);
				// 传递session参数,在用户登录成功后为session初始化赋值,即传递HashMap的值
				Bundle map = new Bundle();
				map.putSerializable("sessionid", session);
				intent.putExtra("session", map);
				context.startActivity(intent); // 跳转到成功页面

			} else {
				Toast.makeText(v.getContext(), "数据为空！", Toast.LENGTH_SHORT)
						.show();
			}

		}
	};

	private boolean getUserInfo() {

		String sess_username = session.get("s_username");
		String sess_userid = session.get("s_userid");
		String sess_id = session.get("s_sessionid");

		DefaultHttpClient mHttpClient = new DefaultHttpClient();
		HttpPost mPost = new HttpPost(url);

		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		pairs.add(new BasicNameValuePair("sess_userid", sess_userid));
		pairs.add(new BasicNameValuePair("sess_username", sess_username));
		pairs.add(new BasicNameValuePair("sess_sessionid", sess_id));

		try {
			mPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			HttpResponse response = mHttpClient.execute(mPost);
			int res = response.getStatusLine().getStatusCode();

			if (res == 200) {

				HttpEntity entity = response.getEntity();

				if (entity != null) {
					String info = EntityUtils.toString(entity);
					System.out.println("info-----------" + info);
					// 以下主要是对服务器端返回的数据进行解析

					JSONObject jsonObject = null;
					// flag为登录成功与否的标记,从服务器端返回的数据
					String flag = "";
					String userinfo = "";
					String level = "";
					String sessionid = "";
					try {
						jsonObject = new JSONObject(info);
						flag = jsonObject.getString("flag");
						userinfo = jsonObject.getString("info");
						level = jsonObject.getString("level");
						sessionid = jsonObject.getString("sessionid");

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// 根据服务器端返回的标记,判断服务端端验证是否成功

					if (flag.equals("notempty")) {
						// 为session传递相应的值,用于在session过程中记录相关用户信息
						session.put("info_userinfo", userinfo);
						session.put("info_level", level);
						session.put("info_sessionid", sessionid);
						return true;
					} else {
						return false;
					}
				} else {

					return false;
				}

			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
