package com.login.main;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GetUserInfoActivity extends Activity {

	private HashMap<String, String> session;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_info);

		// 获取从登录成功后界面的再次传递的参数
		session = (HashMap<String, String>) this.getIntent()
				.getBundleExtra("session").getSerializable("sessionid");
		// 读取session的基本信息，并显示相应的控件
		String session_info = session.get("info_userinfo");
		String session_level = session.get("info_level");
		String session_id = session.get("info_sessionid");
		// 显示相应的内容到控件

		System.out.println("--------session_info--------" + session_info);

		TextView get_info = (TextView) findViewById(R.id.get_info);
		get_info.setText(session_info);

		TextView get_level = (TextView) findViewById(R.id.get_level);
		get_level.setText(session_level);

		TextView get_sessionid = (TextView) findViewById(R.id.get_sessionid);
		get_sessionid.setText(session_id);
	}

}
