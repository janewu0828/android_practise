package ntust.edu.tw.calc;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.TabActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.TabHost.OnTabChangeListener;
import android.os.Build;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements OnTabChangeListener,
		OnTouchListener, OnGestureListener {

	// tabs
	private TabHost myTabhost;
	protected int myMenuSettingTag = 0;
	protected Menu myMenu;
	private int tabSize = 0;
	private int oldTabId = -1;

	// 翻页使用
	private ViewFlipper mFlipper;
	GestureDetector mGestureDetector;
	private static final int FLING_MIN_DISTANCE = 100;
	private static final int FLING_MIN_VELOCITY = 50;

	Context mContext;

	// 分 fen, 米數 mi shu
	Spinner spinnerFen, spinnerMiShu, spinnerFen2, spinnerMiShu2,
			spinnerMiShu3;
	ArrayAdapter<String> adapterFen, adapterMiShu, adapterFen2, adapterMiShu2,
			adapterMiShu3;
	String[] strTwo = { "2", "3", "4", "5", "6" };
	String[] strTwenty = { "20", "30" };
	float fen = 0.0f, thickness = 0.0f, miShu = 0.0f, weight = 0.0f,
			fen2 = 0.0f, thickness2 = 0.0f, miShu2 = 0.0f, weight2 = 0.0f,
			miShu3 = 0.0f, pakageCost3 = 0.0f, price3 = 0.0f;

	// 管徑 kuan ching, 重量 weight, 總重(PC) weight PC, 包皮總和 sum pao pi, 每箱成本 pakage
	// cost, 金額 price
	TextView txtKuanChing, txtWeight, txtKuanChing2, txtWeight2, txtWeightPC3,
			txtSumPaoPi3, txtPakageCost3, txtPrice3, txtPrice33;

	// 厚度 thickness, 價格(噸) price tun, 加工(噸) chia kung tun, 裝箱 chuang hsiang,
	// 價格(米) price mi, 其他費用(PC) other cost PC, 匯率 hui lu
	EditText editThickness, editThickness2, editPriceTun3, editChiaKungTun3,
			editChuangHsiang3, editPriceMi3, editOtherCostPC3, editHuiLu3,
			editHuiLu33;

	// 計算
	Button btnCalC_Blue, btnCalC_Green, btnCalC_Red, btnClear_Blue,
			btnClear_Green, btnClear_Red;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);

		// tabs
		getMyTabHost();

		// components
		findViews();
	}

	private void findViews() {
		mContext = this.getApplicationContext();

		spinnerFen = (Spinner) findViewById(R.id.spinnerFen11);		
		// adapterFen = new ArrayAdapter<String>(this,
		// android.R.layout.simple_spinner_item, strTwo);
		// 將可選内容與ArrayAdapter連接起來
		adapterFen = new ArrayAdapter<String>(this, R.layout.spinner_layout,
				strTwo);
		//設置下拉列表的風格
		adapterFen.setDropDownViewResource(R.layout.spinner_layout);
		// 設定fen spinner顯示的字串內容
		spinnerFen.setAdapter(adapterFen);
		// 設定當fen spinner item 選取後的動作
		spinnerFen.setOnItemSelectedListener(spinnerListenerFen);

		spinnerFen2 = (Spinner) findViewById(R.id.spinnerFen22);
		adapterFen2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, strTwo);
		spinnerFen2.setAdapter(adapterFen2);
		spinnerFen2.setOnItemSelectedListener(spinnerListenerFen2);

		spinnerMiShu = (Spinner) findViewById(R.id.spinnerMiShu11);
		adapterMiShu = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, strTwenty);
		spinnerMiShu.setAdapter(adapterMiShu);
		spinnerMiShu.setOnItemSelectedListener(spinnerListenerMiShu);

		spinnerMiShu2 = (Spinner) findViewById(R.id.spinnerMiShu22);
		adapterMiShu2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, strTwenty);
		spinnerMiShu2.setAdapter(adapterMiShu2);
		spinnerMiShu2.setOnItemSelectedListener(spinnerListenerMiShu2);

		spinnerMiShu3 = (Spinner) findViewById(R.id.spinnerMiShu33);
		adapterMiShu3 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, strTwenty);
		spinnerMiShu3.setAdapter(adapterMiShu3);
		spinnerMiShu3.setOnItemSelectedListener(spinnerListenerMiShu3);

		txtKuanChing = (TextView) findViewById(R.id.txtKuanChing11);
		txtWeight = (TextView) findViewById(R.id.txtWeight11);

		txtKuanChing2 = (TextView) findViewById(R.id.txtKuanChing22);
		txtWeight2 = (TextView) findViewById(R.id.txtWeight22);
		txtWeightPC3 = (TextView) findViewById(R.id.txtWeightPC33);

		txtSumPaoPi3 = (TextView) findViewById(R.id.txtSumPaoPi33);
		txtPakageCost3 = (TextView) findViewById(R.id.txtPakageCost33);
		txtPrice3 = (TextView) findViewById(R.id.txtPrice33);
		txtPrice33 = (TextView) findViewById(R.id.txtPrice3333);

		editThickness = (EditText) findViewById(R.id.editThickness11);

		editThickness2 = (EditText) findViewById(R.id.editThickness22);

		editPriceTun3 = (EditText) findViewById(R.id.editPriceTun33);
		editChiaKungTun3 = (EditText) findViewById(R.id.editChiaKungTun33);
		editChuangHsiang3 = (EditText) findViewById(R.id.editChuangHsiang33);
		editPriceMi3 = (EditText) findViewById(R.id.editPriceMi33);
		editOtherCostPC3 = (EditText) findViewById(R.id.editOtherCostPC33);
		editHuiLu3 = (EditText) findViewById(R.id.editHuiLu33);
		editHuiLu33 = (EditText) findViewById(R.id.editHuiLu3333);

		ButtonCalc btnCalc = new ButtonCalc();
		btnCalC_Blue = (Button) findViewById(R.id.btnCalC_blue);
		btnCalC_Blue.setOnClickListener(btnCalc);

		ButtonCalc_Green btnCalc2 = new ButtonCalc_Green();
		btnCalC_Green = (Button) findViewById(R.id.btnCalC_green);
		btnCalC_Green.setOnClickListener(btnCalc2);

		ButtonCalc_Red btnCalc3 = new ButtonCalc_Red();
		btnCalC_Red = (Button) findViewById(R.id.btnCalC_red);
		btnCalC_Red.setOnClickListener(btnCalc3);
	}

	// 內隱類別（inner class）：計算
	private class ButtonCalc implements OnClickListener {
		public void onClick(View view) {
			txtKuanChing.setText(""
					+ Float.parseFloat(String.format("%.4f", fen * 3.175)));

			// 若 editThickness 不為空
			if (!("".equals(editThickness.getText().toString().trim()))) {
				thickness = Float
						.parseFloat(editThickness.getText().toString());
				weight = (float) ((fen * 3.175 - thickness) * thickness * 0.028 * miShu);
				txtWeight.setText(""
						+ Float.parseFloat(String.format("%.4f", weight)));

				System.out.println("weight= " + weight);
			} else {
				Toast.makeText(mContext, "科目一：請輸入厚度", Toast.LENGTH_SHORT)
						.show();
			}
		}

	}

	// 內隱類別（inner class）：計算
	private class ButtonCalc_Green implements OnClickListener {

		public void onClick(View view) {
			txtKuanChing2.setText(""
					+ Float.parseFloat(String.format("%.4f", fen2 * 3.175)));

			// 若 editThickness2 不為空
			if (!("".equals(editThickness2.getText().toString().trim()))) {
				thickness2 = Float.parseFloat(editThickness2.getText()
						.toString());
				weight2 = (float) ((fen2 * 3.175 - thickness2) * thickness2
						* 0.028 * miShu2);
				txtWeight2.setText(""
						+ Float.parseFloat(String.format("%.4f", weight2)));

				System.out.println("weight2= " + weight2);
			} else {
				Toast.makeText(mContext, "科目二：請輸入厚度", Toast.LENGTH_SHORT)
						.show();
			}

			// 若 editThickness && editThickness2 不為空
			if (!("".equals(editThickness.getText().toString().trim()))
					&& !("".equals(editThickness2.getText().toString().trim())))
				txtWeightPC3.setText(""
						+ Float.parseFloat(String.format("%.4f", weight
								+ weight2)));
			else
				Toast.makeText(mContext, "科目一、二：請輸入厚度", Toast.LENGTH_SHORT)
						.show();
		}

	}

	// 內隱類別（inner class）：計算
	private class ButtonCalc_Red implements OnClickListener {

		public void onClick(View view) {
			// 若 editPriceMi3 不為空
			if (!("".equals(editPriceMi3.getText().toString().trim()))) {
				txtSumPaoPi3.setText(""
						+ Float.parseFloat(String.format(
								"%.4f",
								Float.parseFloat(editPriceMi3.getText()
										.toString()) * miShu3)));
			} else {
				Toast.makeText(mContext, "包皮成本：請輸入價格(米)", Toast.LENGTH_SHORT)
						.show();
			}

			// 若不為空
			if (!("".equals(editPriceTun3.getText().toString().trim()))
					&& !("".equals(editChiaKungTun3.getText().toString().trim()))
					&& !("".equals(editChuangHsiang3.getText().toString()
							.trim()))
					&& !("".equals(editOtherCostPC3.getText().toString().trim()))) {
				pakageCost3 = Float.parseFloat(txtWeightPC3.getText()
						.toString())
						* (Float.parseFloat(editPriceTun3.getText().toString()) + Float
								.parseFloat(editChiaKungTun3.getText()
										.toString()))
						/ 1000
						+ Float.parseFloat(editChuangHsiang3.getText()
								.toString())
						+ Float.parseFloat(txtSumPaoPi3.getText().toString())
						+ Float.parseFloat(editOtherCostPC3.getText()
								.toString());

				txtPakageCost3.setText(""
						+ Float.parseFloat(String.format("%.4f", pakageCost3)));

				System.out.println("包皮成本：總重*((價格+加工)/1000)+裝箱+包皮總和+其他費用 ");
			} else {
				Toast.makeText(mContext,
						"科目一、二：請輸入厚度；+\n+包皮成本：請輸入價格、加工、裝箱以及其他費用",
						Toast.LENGTH_SHORT).show();
			}

			// 若不為空
			if (!("".equals(editHuiLu3.getText().toString().trim()))) {
				price3 = pakageCost3
						* Float.parseFloat(editHuiLu3.getText().toString());
				txtPrice3.setText(""
						+ Float.parseFloat(String.format("%.4f", price3)));

				System.out.println("金額1：每箱成本 * 匯率1 ");
			} else {
				Toast.makeText(mContext, "包皮成本：請輸入匯率1", Toast.LENGTH_SHORT)
						.show();
			}

			// 若不為空
			if (!("".equals(editHuiLu33.getText().toString().trim()))) {
				txtPrice33.setText(""
						+ Float.parseFloat(String.format(
								"%.4f",
								price3
										* Float.parseFloat(editHuiLu33
												.getText().toString()))));

				System.out.println("金額2：金額1*匯率2 ");
			} else {
				Toast.makeText(mContext, "包皮成本：請輸入匯率2", Toast.LENGTH_SHORT)
						.show();
			}
		}

	}

	// 分 fen-start
	AdapterView.OnItemSelectedListener spinnerListenerFen = new AdapterView.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO 自動產生的方法 Stub
			Toast.makeText(mContext, "科目一：你選的是 " + strTwo[position] + " 分",
					Toast.LENGTH_SHORT).show();

			fen = position + 2;
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO 自動產生的方法 Stub

		}
	}; // 分 fen-end

	// 分 fen-start
	AdapterView.OnItemSelectedListener spinnerListenerFen2 = new AdapterView.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO 自動產生的方法 Stub
			Toast.makeText(mContext, "科目二：你選的是 " + strTwo[position] + " 分",
					Toast.LENGTH_SHORT).show();

			fen2 = position + 2;
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO 自動產生的方法 Stub

		}
	}; // 分 fen-end

	// 米數 mi shu-start
	AdapterView.OnItemSelectedListener spinnerListenerMiShu = new AdapterView.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO 自動產生的方法 Stub
			Toast.makeText(mContext, "科目一：你選的是 " + strTwenty[position] + " 米",
					Toast.LENGTH_SHORT).show();

			miShu = (position + 2) * 10;
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO 自動產生的方法 Stub

		}
	}; // 米數 mi shu-end

	// 米數 mi shu-start
	AdapterView.OnItemSelectedListener spinnerListenerMiShu2 = new AdapterView.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO 自動產生的方法 Stub
			Toast.makeText(mContext, "科目二：你選的是 " + strTwenty[position] + " 米",
					Toast.LENGTH_SHORT).show();

			miShu2 = (position + 2) * 10;
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO 自動產生的方法 Stub

		}
	}; // 米數 mi shu-end

	// 米數 mi shu-start
	AdapterView.OnItemSelectedListener spinnerListenerMiShu3 = new AdapterView.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO 自動產生的方法 Stub
			Toast.makeText(mContext, "包皮成本：你選的是 " + strTwenty[position] + " 米",
					Toast.LENGTH_SHORT).show();

			miShu3 = (position + 2) * 10;
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO 自動產生的方法 Stub

		}
	}; // 米數 mi shu-end

	// tabs-start
	private void getMyTabHost() {
		myTabhost = this.getTabHost();

		// get Tabhost
		LayoutInflater.from(this).inflate(R.layout.fragment_main,
				myTabhost.getTabContentView(), true);

		myTabhost.setBackgroundColor(Color.argb(150, 22, 70, 150));

		myTabhost.addTab(myTabhost
				.newTabSpec("One")
				.setIndicator("科目一",
						getResources().getDrawable(R.drawable.conf_artists))
				.setContent(R.id.widget_layout_blue));
		tabSize++;

		myTabhost.addTab(myTabhost
				.newTabSpec("Two")
				.setIndicator("科目二",
						getResources().getDrawable(R.drawable.conf_folder))
				.setContent(R.id.widget_layout_green));
		tabSize++;

		myTabhost.addTab(myTabhost
				.newTabSpec("Thr")
				.setIndicator("包皮成本",
						getResources().getDrawable(R.drawable.conf_person))
				.setContent(R.id.widget_layout_red));
		tabSize++;

		mFlipper = (ViewFlipper) findViewById(R.id.flipper);
		// 注册一个用于手势识别的类
		mGestureDetector = new GestureDetector(this);
		// 给mFlipper设置一个listener
		mFlipper.setOnTouchListener(this);
		// 允许长按住ViewFlipper,这样才能识别拖动等手势
		mFlipper.setLongClickable(true);
	}

	private void animateChangeTab(int index) {

		Animation slideLeftIn = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.slide_left_in);
		Animation slideLeftOut = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.slide_left_out);
		Animation slideRightIn = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.slide_right_in);
		Animation slideRightOut = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.slide_right_out);

		Log.v("newIndex", index + "");
		Log.v("oldTabId", oldTabId + "");
		// 定义退出效果
		if (index > oldTabId) {

			if (tabSize - 1 == index && oldTabId == 0) {

				myTabhost.getCurrentView().startAnimation(slideRightOut);

			} else {

				myTabhost.getCurrentView().startAnimation(slideLeftOut);

			}
		} else if (index < oldTabId) {

			if (0 == index && oldTabId == tabSize - 1) {

				myTabhost.getCurrentView().startAnimation(slideLeftOut);

			} else {

				myTabhost.getCurrentView().startAnimation(slideRightOut);

			}

		}

		myTabhost.setCurrentTab(index);
		// 定义进入效果
		int curTabId = myTabhost.getCurrentTab();

		if (curTabId > oldTabId) {

			if (tabSize - 1 == curTabId && oldTabId == 0) {

				myTabhost.getCurrentView().startAnimation(slideRightIn);

			} else {

				myTabhost.getCurrentView().startAnimation(slideLeftIn);

			}

		} else if (curTabId < oldTabId) {

			if (0 == curTabId && oldTabId == tabSize - 1) {

				myTabhost.getCurrentView().startAnimation(slideLeftIn);

			} else {

				myTabhost.getCurrentView().startAnimation(slideRightIn);

			}

		}

		Log.v("curTabId", curTabId + "");
		Log.v("oldTabId", oldTabId + "");

		this.oldTabId = myTabhost.getCurrentTab();
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO 自動產生的方法 Stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO 自動產生的方法 Stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO 自動產生的方法 Stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO 自動產生的方法 Stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO 自動產生的方法 Stub

	}

	/**
	 * 用户按下触摸屏、快速移动后松开即触发这个事件 e1：第1个ACTION_DOWN MotionEvent e2：最后一个ACTION_MOVE
	 * MotionEvent velocityX：X轴上的移动速度，像素/秒 velocityY：Y轴上的移动速度，像素/秒 触发条件 ：
	 * X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO 自動產生的方法 Stub
		if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
				&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
			myMenuSettingTag++;
			while (myMenuSettingTag > tabSize - 1) {
				myMenuSettingTag = myMenuSettingTag - tabSize;
			}

			animateChangeTab(myMenuSettingTag);

		} else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
				&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
			myMenuSettingTag--;
			while (myMenuSettingTag < 0) {
				myMenuSettingTag = myMenuSettingTag + tabSize;
			}

			animateChangeTab(myMenuSettingTag);

		}

		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO 自動產生的方法 Stub
		// return false;

		// 一定要将触屏事件交给手势识别类去处理（自己处理会很麻烦的）
		return mGestureDetector.onTouchEvent(event);
	}

	@Override
	public void onTabChanged(String tabId) {
		// TODO 自動產生的方法 Stub

	} // tabs-end

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
