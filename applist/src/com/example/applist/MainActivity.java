package com.example.applist;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

	// 系统中所有的app信息
	private List<ResolveInfo> apps;

	// 用九宫格视图呈现所有app
	private GridView apps_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		apps_list = (GridView) findViewById(R.id.apps_list);
		loadApps();
	}

	private void loadApps() {
		// 获取系统所有app信息
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		new ImageView(MainActivity.this);
		apps = getPackageManager().queryIntentActivities(mainIntent, 0);
		// 把信息设置到适配器中
		apps_list.setAdapter(new AppsAdapter());
	}

	public class AppsAdapter extends BaseAdapter {

		public AppsAdapter() {

		}

		@Override
		public int getCount() {
			return apps.size();
		}

		@Override
		public Object getItem(int i) {
			return apps.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup) {
			ImageView iv;
			if (view == null) {
				iv = new ImageView(MainActivity.this);
				iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
				iv.setLayoutParams(new GridView.LayoutParams(50, 50));
			} else {
				iv = (ImageView) view;
			}
			ResolveInfo info = apps.get(i);
			iv.setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));
			return iv;
		}
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
