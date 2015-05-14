package com.example.applist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {
	ListView lv;
	MyAdapter adapter;
	ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lv = (ListView) findViewById(R.id.lv);
		// 得到PackageManager对象
		PackageManager pm = getPackageManager();
		// 得到系统安装的所有程序包的PackageInfo对象
		// List<ApplicationInfo> packs = pm.getInstalledApplications(0);
		List<PackageInfo> packs = pm.getInstalledPackages(0);

		for ( PackageInfo pi : packs) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			// 显示用户安装的应用程序，而不显示系统程序
			if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0
					&& (pi.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0) {
				// 这将会显示所有安装的应用程序，包括系统应用程序
				map.put("icon", pi.applicationInfo.loadIcon(pm));// 图标
				map.put("appName", pi.applicationInfo.loadLabel(pm));// 应用程序名称
				map.put("packageName", pi.applicationInfo.packageName);// 应用程序包名
				// 循环读取并存到HashMap中，再增加到ArrayList上，一个HashMap就是一项
				items.add(map);
			}
			// //这将会显示所有安装的应用程序，包括系统应用程序
			// map.put("icon", pi.applicationInfo.loadIcon(pm));//图标
			// map.put("appName", pi.applicationInfo.loadLabel(pm));//应用程序名称
			// map.put("packageName", pi.applicationInfo.packageName);//应用程序包名
			// 循环读取并存到HashMap中，再增加到ArrayList上，一个HashMap就是一项
			// items.add(map);
		}
		/**
		 * 参数：Context ArrayList(item的集合) item的layout 包含ArrayList中的HashMap的key的数组
		 * key所对应的值的相应的控件id
		 */
		adapter = new MyAdapter(this, items, R.layout.piitem, new String[] {
				"icon", "appName", "packageName" }, new int[] { R.id.icon,
				R.id.appName, R.id.packageName });
		lv.setAdapter(adapter);
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
