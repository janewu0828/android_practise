package com.androidhive.pushnotifications;

import static com.androidhive.pushnotifications.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.androidhive.pushnotifications.CommonUtilities.EXTRA_MESSAGE;
import static com.androidhive.pushnotifications.CommonUtilities.SENDER_ID;
import static com.androidhive.pushnotifications.CommonUtilities.SERVER_URL;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PolylinesPolygonsActivity extends FragmentActivity {
	/** myLoc **/
	// 宣告定位管理控制
	public static LocationManager mLocationManager;
	// String字串
	private String TEXT;
	// 設定距離範圍
	private int DISTANCE = 50000;
	CameraPosition cameraPosition;
	/** display myLoc **/
	// 經度// 緯度
	public static double myLon, myLat;

	final Context context = this;
	String regId = "";

	/** Map **/
	private GoogleMap map; // 儲存著地圖資訊
	/** Marker: myloc,圖書館,IB大樓,學生活動中心,學生宿舍1 **/
	Marker marker_myloc, marker_lib, marker_ib, marker_stu, marker_dormitory1;
	LatLng lat_myloc, lat_ntust;
	/** LatLng: 圖書館,IB大樓,學生活動中心,電資館EE,學舍1,第三教學大樓T3,游泳池,運動場 **/
	LatLng lat_lib, lat_ib, lat_stu, lat_ee, lat_dormitory1, lat_t3, lat_pool,
			lat_stadium;
	/** LatLng: lat_pol_ntust **/
	LatLng lat_pol1, lat_pol2, lat_pol3, lat_pol4, lat_pol5, lat_pol6;
	double locA, locB;

	/** timer **/
	TextView txtResult;
	public static TextView taskTimer, lblTimer;
	TimerManager timer = new TimerManager();
	 long time = 15 * 60 * 1000;
	 long timePlus = 5 * 60 * 1000;
	 long timeHunter = 2 * 60 * 1000;
//	long time = 1 * 60 * 1000;
//	long timePlus = 10 * 1000;
//	long timeHunter = 1 * 1000;
	/** if arrivednewMessage is true, completed task **/
	public static boolean arrivednewMessage;
	boolean isnewMessage;
	String txt_MsgTimer = "";
	/** if statusTask is false, recevied new task **/
	public static boolean statusTask;
	/** if statusGame is true, gameStart **/
	public static boolean statusGame;
	LayoutInflater inflater;
	public static View addViewPic, addViewPicError, addViewPicHunter;

	/** GSM **/
	// label to display gcm messages
	public static TextView lblMessage;
	// Asyntask
	AsyncTask<Void, Void, Void> mRegisterTask;
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	// Connection detector
	ConnectionDetector cd;
	public static String name;
	public static String email;
	public static String player;
	public static String newMessage;

	/** Log: pass newMessage to LocationActivity3 **/
	public static final String ACTIVITY_TAG_REGID = "tagRegId";
	public static final String ACTIVITY_TAG_MESSAGE = "tagMessage";
	/** Log: display map **/
	static final String ACTIVITY_TAG_MAP = "tagMap";
	static final int REQUEST_CODE = 20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_polyline_polygon);

		/** GCM-Start **/
		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(PolylinesPolygonsActivity.this,
					getString(R.string.msg_alertInternet),
					getString(R.string.msg_alertInternetConnection), false);
			// stop executing code by return
			return;
		}

		// Getting name, email from intent
		Intent i = getIntent();
		name = i.getStringExtra("name");
		email = i.getStringExtra("email");
		player = i.getStringExtra("player");

		if (name == null || email == null || player == null) {
			Log.i(ACTIVITY_TAG_REGID,
					"PolylinesPolygonsActivity: name == null||email==null||player==null");
			return;
		}

		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		GCMRegistrar.checkManifest(this);

		findViews();

		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));

		// Get GCM registration id
		regId = GCMRegistrar.getRegistrationId(this);
		Log.i(ACTIVITY_TAG_REGID, "PolylinesPolygonsActivity regId= " + regId);

		// Check if regid already presents
		if (regId.equals("")) {
			// Registration is not present, register now with GCM
			GCMRegistrar.register(this, SENDER_ID);
			Log.i(ACTIVITY_TAG_REGID,
					"PolylinesPolygonsActivity register now with GCM= " + regId);
		} else {
			// Device is already registered on GCM
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.
				Toast.makeText(getApplicationContext(),
						getString(R.string.msg_toastRegistered),
						Toast.LENGTH_LONG).show();
				Log.i(ACTIVITY_TAG_REGID,
						"PolylinesPolygonsActivity: Device is already registered on GCM, Skips registration.");
			} else {
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.

				// final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// Register on our server
						// On server creates a new user
						ServerUtilities.register(context, name, email, player,
								regId);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
			}
		}
		/** GCM-End **/

		/** Map-Start **/
		// 取得定位權限
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// 讀取我的位置，定位抓取方式為網路讀取(若欲以GPS為定位抓取方式則更改成LocationManager.GPS_PROVIDER)，最後則帶入定位更新Listener。
		mLocationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 10000.0f, LocationChange);

		initPoints();
		initMap();
		/** Map-End **/

		timer.gameTimer(PolylinesPolygonsActivity.this, time);
		timer.hunterTimer(PolylinesPolygonsActivity.this, timeHunter);
		// CCC
		arrivednewMessage = true;
		isnewMessage = false;
		statusTask = false;
		statusGame = true;
		/** onCreate()-End **/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, 0, 0, getString(R.string.txt_info_player));
		menu.add(0, 1, 0, getString(R.string.btn_instructions));
		// menu.add(0, 2, 0, getString(R.string.txt_logout));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case 0:
			String txt_player = "";
			if (player.equals("0"))
				txt_player = getString(R.string.rad_player0);
			else
				txt_player = getString(R.string.rad_player1);

			alert.showInfoDialog(PolylinesPolygonsActivity.this,
					getString(R.string.txt_info_player),
					getString(R.string.txt_name) + name + "\n"
							+ getString(R.string.txt_email) + email + "\n"
							+ getString(R.string.txt_role) + txt_player);
			break;
		 case 1:
			Intent i2 = new Intent(getApplicationContext(), InstructionsActivity.class);
			startActivityForResult(i2, REQUEST_CODE);
		 break;
		// case 2:
		// finish();
		// break;
		}
		return true;
	}

	// 更新定位Listener
	public LocationListener LocationChange = new LocationListener() {
		public void onLocationChanged(Location mLocation) {
			// 印出我的座標-經度緯度
			TEXT = getString(R.string.txt_myLon) + mLocation.getLongitude()
					+ getString(R.string.txt_myLat) + mLocation.getLatitude();

			/** display newMessage and result **/
			// 經度
			myLon = mLocation.getLongitude();
			// 緯度
			myLat = mLocation.getLatitude();

			// myloc
			lat_myloc = new LatLng(myLat, myLon);
			Log.i(ACTIVITY_TAG_MESSAGE,
					"PolylinesPolygonsActivity LocationChange: myLat =" + myLat
							+ ", myLon = " + myLon);
			isMyLoc();
		}

		public void onProviderDisabled(String provider) {

		}

		public void onProviderEnabled(String provider) {

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {

		}
	};

	private void findViews() {
		// TODO Auto-generated method stub
		lblMessage = (TextView) findViewById(R.id.lblMessage);
		txtResult = (TextView) findViewById(R.id.lblResult);
		lblTimer = (TextView) findViewById(R.id.lblTimer);
		taskTimer = (TextView) findViewById(R.id.lblMessageTimer);
		/** set PicDialog layout **/
		inflater = LayoutInflater.from(PolylinesPolygonsActivity.this);
		addViewPic = inflater.inflate(R.layout.dialog_pic, null);
		addViewPicError = inflater.inflate(R.layout.dialog_pic_error, null);
		addViewPicHunter = inflater.inflate(R.layout.dialog_pic_hunter, null);
	}

	/** GCM-Start **/
	/** Receiving push messages **/
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			newMessage = intent.getExtras().getString(EXTRA_MESSAGE);

			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());

			/**
			 * Take appropriate action on this message depending upon your app
			 * requirement For now i am just displaying it on the screen
			 * */

			/** if stausGame is true, gameStart, display newMessage **/
			if (statusGame) {
				// Showing received message
				/**
				 * if stausTask is false, completed old task, received
				 * newMessage
				 **/
				if (!statusTask) {
					lblMessage.setText(newMessage);
					/** Alert msg **/
					alert.showAlertDialog(PolylinesPolygonsActivity.this,
							getString(R.string.txt_newMessage), newMessage,
							false);
				}

				/** Toast msg **/
				Toast.makeText(getApplicationContext(),
						getString(R.string.txt_newMessage) + newMessage,
						Toast.LENGTH_LONG).show();

				Log.i(ACTIVITY_TAG_MESSAGE,
						"PolylinesPolygonsActivity newMessage= " + newMessage);

				/** repeat find isMyLoc() **/
				isMyLoc();
				Log.i(ACTIVITY_TAG_MESSAGE,
						"PolylinesPolygonsActivity isMyLoc() ");

			} else {
				/** if stausGame is false, alert newMessageError **/
				alert.showAlertDialog(PolylinesPolygonsActivity.this,
						getString(R.string.txt_newMessage),
						getString(R.string.txt_newMessageError), false);
			}

			// Releasing wake lock
			WakeLocker.release();
		}
	};

	private void isMyLoc() {
		// TODO Auto-generated method stub
		txtResult.setText("");
		locA = myLat;
		locB = myLon;

		/** setMyLoc AAA **/
//		 locA = 25.013889;
//		 locB = 121.541111;
		Log.i(ACTIVITY_TAG_MESSAGE,
				"PolylinesPolygonsActivity isMyLoc() locA= " + locA
						+ ", locB= " + locB);

		if (newMessage == null) {
			return;

		} else {
			if (newMessage.equals("圖書館")) {
				/**
				 * (25.013889, 121.541111) 左上: 25.013936, 121.540916 左下:
				 * 25.013591, 121.541281 * 右下: 25.013695, 121.541394 右上:
				 * 25.014035, 121.541029
				 * **/
				if (!statusTask) {
					isnewMessage = true;
					statusTask = true;
					arrivednewMessage = false;
					timer.taskTimer(PolylinesPolygonsActivity.this, timePlus);
					if (locA > 25.013591 && locA < 25.014035
							&& locB > 121.541029 && locB < 121.541394) {
						/** if arrivednewMessage is true, completed task **/
						arrivednewMessage = true;
						txtResult
								.setText(getString(R.string.txt_isMyLocCorrect));
						/** Pic msg **/
						AlertDialogManager.ShowPicDialog(
								PolylinesPolygonsActivity.this,
								getString(R.string.txt_isMyLocCorrect), "",
								addViewPic);
					} else {
						if (player.equals("0")) {
							/** Survivor is died **/
							txtResult
									.setText(getString(R.string.txt_isMyLocError)
											+ txt_MsgTimer);
						}
					}
				} else {
					/** Alert msg **/
					alert.showAlertDialog(PolylinesPolygonsActivity.this,
							getString(R.string.txt_newMessage),
							getString(R.string.txt_newTaskError) + newMessage,
							false);

					Toast.makeText(getApplicationContext(),
							getString(R.string.txt_newTaskError) + newMessage,
							Toast.LENGTH_LONG).show();
				}
			} else if (newMessage.equals("學生活動中心")) {
				/**
				 * (25.013611, 121.542500) 左上: 25.013953, 121.542413 左下:
				 * 25.013632, 121.542762 右下: 25.013853, 121.543016 右上:
				 * 25.014179, 121.542673
				 * **/
				if (!statusTask) {
					isnewMessage = true;
					statusTask = true;
					arrivednewMessage = false;
					timer.taskTimer(PolylinesPolygonsActivity.this, timePlus);
					if (locA > 25.013632 && locA < 25.014179
							&& locB > 121.542413 && locB < 121.543016) {
						arrivednewMessage = true;
						txtResult
								.setText(getString(R.string.txt_isMyLocCorrect));
					} else {
						if (player.equals("0")) {
							/** Survivor is died **/
							txtResult
									.setText(getString(R.string.txt_isMyLocError));
						}
					}
				} else {
					/** Alert msg: can not recevied new msg **/
					alert.showAlertDialog(PolylinesPolygonsActivity.this,
							getString(R.string.txt_newMessage),
							getString(R.string.txt_newTaskError) + newMessage,
							false);

					Toast.makeText(getApplicationContext(),
							getString(R.string.txt_newTaskError) + newMessage,
							Toast.LENGTH_LONG).show();
				}
			} else if (newMessage.equals("學生宿舍1")) {
				/**
				 * (25.012778, 121.542778) 左上: 25.012740, 121.542402 左下:
				 * 25.012601, 121.542517 右下: 25.012866, 121.543153 右上:
				 * 25.013194, 121.542818
				 * **/
				if (!statusTask) {
					isnewMessage = true;
					statusTask = true;
					arrivednewMessage = false;
					timer.taskTimer(PolylinesPolygonsActivity.this, timePlus);
					if (locA > 25.012601 && locA < 25.013194
							&& locB > 121.542402 && locB < 121.543153) {
						arrivednewMessage = true;
						txtResult
								.setText(getString(R.string.txt_isMyLocCorrect));
					} else {
						if (player.equals("0")) {
							/** Survivor is died **/
							txtResult
									.setText(getString(R.string.txt_isMyLocError));
						}
					}
				} else {
					/** Alert msg **/
					alert.showAlertDialog(PolylinesPolygonsActivity.this,
							getString(R.string.txt_newMessage),
							getString(R.string.txt_newTaskError) + newMessage,
							false);

					Toast.makeText(getApplicationContext(),
							getString(R.string.txt_newTaskError) + newMessage,
							Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
		mLocationManager.removeUpdates(LocationChange); // 程式結束時停止定位更新
	}

	/** GCM-End **/

	/** Map-Start **/
	// 初始化所有地點的緯經度
	private void initPoints() {
		// ntust
		lat_ntust = new LatLng(25.013333, 121.541389);
		lat_pol1 = new LatLng(25.013053, 121.539685);
		lat_pol2 = new LatLng(25.011410, 121.541053);
		lat_pol3 = new LatLng(25.012856, 121.543161);
		lat_pol4 = new LatLng(25.013374, 121.542925);
		lat_pol5 = new LatLng(25.013992, 121.543856);
		lat_pol6 = new LatLng(25.015554, 121.542619);

		// 圖書館
		lat_lib = new LatLng(25.013889, 121.541111);
		// IB大樓
		lat_ib = new LatLng(25.012778, 121.540000);
		// 學生活動中心
		lat_stu = new LatLng(25.013611, 121.542500);
		// 電資館EE
		lat_ee = new LatLng(25.011667, 121.541389);
		// 學舍1
		lat_dormitory1 = new LatLng(25.012778, 121.542778);
		// 第三教學大樓T3
		lat_t3 = new LatLng(25.013333, 121.542778);
		// 游泳池
		lat_pool = new LatLng(25.013889, 121.543333);
		// 運動場
		lat_stadium = new LatLng(25.014722, 121.542222);
	}

	// 初始化地圖
	private void initMap() {
		// 檢查GoogleMap物件是否存在
		if (map == null) {
			// 從SupportMapFragment取得GoogleMap物件
			map = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.fmMap)).getMap();
			if (map != null) {
				setUpMap();
			}
		}
	}

	// 完成地圖相關設定
	private void setUpMap() {
		// Enables the my-location layer.
		map.setMyLocationEnabled(true);

		// 鏡頭焦點
		cameraPosition = new CameraPosition.Builder().target(lat_ntust) // 鏡頭焦點在lat_ntust
				.zoom(16) // 地圖縮放層級定為16
				.build();

		// 改變鏡頭焦點到指定的新地點
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

		addMarkersToMap();

		// 如果不自訂InfoWindowAdapter會自動套用預設訊息視窗
		map.setInfoWindowAdapter(new MyInfoWindowAdapter());

		MyMarkerListener myMarkerListener = new MyMarkerListener();
		// 註冊OnMarkerClickListener，當標記被點擊時會自動呼叫該Listener的方法
		map.setOnMarkerClickListener(myMarkerListener);
		// 註冊OnInfoWindowClickListener，當標記訊息視窗被點擊時會自動呼叫該Listener的方法
		map.setOnInfoWindowClickListener(myMarkerListener);

		addPolylinesPolygonsToMap();
	}

	// 加入多個標記
	private void addMarkersToMap() {
		// 使用預設標記，但顏色改成綠色
		marker_lib = map.addMarker(new MarkerOptions()
				.position(lat_lib)
				.title(getString(R.string.marker_title_lib))
				.snippet(getString(R.string.marker_snippet_lib))
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

		marker_stu = map.addMarker(new MarkerOptions()
				.position(lat_stu)
				.title(getString(R.string.marker_title_stu))
				.snippet(getString(R.string.marker_snippet_stu))
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

		marker_dormitory1 = map.addMarker(new MarkerOptions()
				.position(lat_dormitory1)
				.title(getString(R.string.marker_title_dormitory1))
				.snippet(getString(R.string.marker_snippet_dormitory1))
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
	}

	private void addPolylinesPolygonsToMap() {
		// 連續線
		Polyline polyline = map.addPolyline(
		// 建立PolylineOptions物件
				new PolylineOptions()
				// 加入頂點，會依照加入順序繪製直線
						.add(lat_pol1, lat_pol2, lat_pol3, lat_pol4, lat_pol5,
								lat_pol6)
						// 設定線的粗細(像素)，預設為10像素
						.width(5)
						// 設定線的顏色(ARGB)，預設為黑色
						.color(Color.MAGENTA)
						// 與其他圖形在Z軸上的高低順序，預設為0
						// 數字大的圖形會蓋掉小的圖形
						.zIndex(1));

		// 可以利用Polyline物件改變原來屬性設定
		polyline.setWidth(6);

		// 多邊形
		map.addPolygon(
		// 建立PolygonOptions物件
		new PolygonOptions()
		// 加入頂點
				.add(lat_pol1, lat_pol2, lat_pol3, lat_pol4, lat_pol5, lat_pol6)
				// 設定外框線的粗細(像素)，預設為10像素
				.strokeWidth(5)
				// 設定外框線的顏色(ARGB)，預設為黑色
				.strokeColor(Color.BLUE)
				// 設定填充的顏色(ARGB)，預設為黑色
				.fillColor(Color.argb(200, 100, 150, 0)));
	}

	// 實作與標記相關的監聽器方法
	private class MyMarkerListener implements OnMarkerClickListener,
			OnInfoWindowClickListener {
		@Override
		// 點擊地圖上的標記
		public boolean onMarkerClick(final Marker marker) {
			return false;
		}

		@Override
		// 點擊標記的訊息視窗
		public void onInfoWindowClick(Marker marker) {
			// Toast標記的標題
			Toast.makeText(getBaseContext(), marker.getTitle(),
					Toast.LENGTH_SHORT).show();
		}

	}

	// 自訂InfoWindowAdapter，當點擊標記時會跳出自訂風格的訊息視窗
	private class MyInfoWindowAdapter implements InfoWindowAdapter {
		private final View infoWindow;

		MyInfoWindowAdapter() {
			// 取得指定layout檔，方便標記訊息視窗套用
			infoWindow = getLayoutInflater().inflate(
					R.layout.custom_info_window, null);
		}

		@Override
		// 回傳設計好的訊息視窗樣式
		// 回傳null會自動呼叫getInfoContents(Marker)
		public View getInfoWindow(Marker marker) {
			int logoId = 0;
			// 使用equals()方法檢查2個標記是否相同，千萬別用「==」檢查
			if (marker.equals(marker_lib)) {
				logoId = R.drawable.lib;
			} else if (marker.equals(marker_stu)) {
				logoId = R.drawable.stu;
			} else if (marker.equals(marker_dormitory1)) {
				logoId = R.drawable.dormitory1;
			} else {
				// 呼叫setImageResource(int)傳遞0則不會顯示任何圖形
				logoId = 0;
			}

			// 顯示圖示
			ImageView iv_logo = ((ImageView) infoWindow
					.findViewById(R.id.iv_logo));
			iv_logo.setImageResource(logoId);

			// 顯示標題
			String title = marker.getTitle();
			TextView tv_title = ((TextView) infoWindow
					.findViewById(R.id.tv_title));
			tv_title.setText(title);

			// 顯示描述
			String snippet = marker.getSnippet();
			TextView tv_snippet = ((TextView) infoWindow
					.findViewById(R.id.tv_snippet));
			tv_snippet.setText(snippet);

			return infoWindow;
		}

		@Override
		// 當getInfoWindow(Marker)回傳null時才會呼叫此方法
		// 此方法如果再回傳null，代表套用預設視窗樣式
		public View getInfoContents(Marker marker) {
			return null;
		}
	}

	// 執行與地圖有關的方法前應該先呼叫此方法以檢查GoogleMap物件是否存在
	private boolean isMapReady() {
		if (map == null) {
			Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;
	}

	// 按下「重置」按鈕重新打上標記
	public void onResetMap(View view) {
		if (!isMapReady()) {
			return;
		}
		// 先清除Map上的標記再重新標記以避免重複標記
		map.clear();
		addMarkersToMap();
		addPolylinesPolygonsToMap();
		setUpMap();
		txtResult.setText("");
	}

	@Override
	protected void onResume() {
		super.onResume();
		initMap();
	}

	public void finish() {
		super.finish();
		// /** clear old setup **/
		// txtResult.setText("");
		// ((CountDownTimer) TimerManager.taskTime).cancel();
		// Intent i2 = new Intent(getApplicationContext(), IndexActivity.class);
		// setResult(RESULT_OK, i2);
		// startActivityForResult(i2, REQUEST_CODE);
	}
}
