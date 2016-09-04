package com.luoyb.joker.view.joker;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.baidu.mobads.AdSettings;
import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.baidu.mobads.AppActivity;
import com.baidu.mobads.AppActivity.ActionBarColorTheme;
import com.luoyb.joker.R;

public class JokerViewLayout implements OnClickListener {

	private FrameLayout frameLayout;

	private Context context;

	private Button newBtn;
	private Button hotBtn;

	public JokerViewLayout(Context context) {
		this.context = context;
	}

	public RelativeLayout getView() {
		RelativeLayout layout = new RelativeLayout(context);
		// setContentView(layout);
		this.inflateByJokerListView(layout);
		this.inflateByJokerButton(layout); // 该方法有操作初始化listview的动作，所以需放在listview方法后面
		this.inflateByAdView(layout);
		return layout;
	}

	private void inflateByJokerButton(ViewGroup layout) {
		LayoutInflater fLayoutInflater = LayoutInflater.from(context);
		LinearLayout linearLayout = (LinearLayout) fLayoutInflater.inflate(
				R.layout.joker_button, null);

		newBtn = (Button) linearLayout.findViewById(R.id.new_joker_button);
		newBtn.setOnClickListener(this);

		hotBtn = (Button) linearLayout.findViewById(R.id.hot_joker_button);
		hotBtn.setOnClickListener(this);

		RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		rllp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		layout.addView(linearLayout, rllp);
		// 初始化joker视图
		this.onClick(newBtn);
	}

	/**
	 * 恢复按钮的样式
	 */
	private void resetBtn() {
		newBtn.setBackgroundColor(((Activity) context).getResources().getColor(
				R.color.white));
		hotBtn.setBackgroundColor(((Activity) context).getResources().getColor(
				R.color.white));
	}

	@Override
	public void onClick(View v) {
		this.resetBtn();
		Button btn = (Button) v;
		btn.setBackgroundColor(((Activity) context).getResources().getColor(
				R.color.blue));
		switch (v.getId()) {
		case R.id.new_joker_button:
			frameLayout.removeAllViews();
			frameLayout.addView(new NewJokerView(context).getNewJokerView());
			break;
		case R.id.hot_joker_button:
			frameLayout.removeAllViews();
			frameLayout.addView(new HotJokerView(context).getNewJokerView());
			break;
		default:
			break;
		}
	}

	/**
	 * 获取笑话列表
	 * 
	 * @param layout
	 */
	private void inflateByJokerListView(ViewGroup layout) {

		frameLayout = new FrameLayout(context);
		// LayoutInflater fLayoutInflater = LayoutInflater.from(context);
		// frameLayout = (FrameLayout) fLayoutInflater.inflate(
		// R.layout.joker_frame, null);

		// frameLayout.addView(new NewJokerView(context).getNewJokerView());

		RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		rllp.addRule(RelativeLayout.BELOW, R.id.joker_button_layout);
		layout.addView(frameLayout, rllp);
	}

	/**
	 * 获取百度广告view
	 * 
	 * @return
	 */
	private void inflateByAdView(ViewGroup layout) {
		AppActivity
				.setActionBarColorTheme(ActionBarColorTheme.ACTION_BAR_RED_THEME);
		AdView.setAppSid(context, "e8372c93");
		// 人群属性
		AdSettings.setKey(new String[] { "baidu", "中国" }); // 创建广告view
		String adPlaceID = "2793929";// 重要:请填上你的代码位ID,否则无法请求到广告 adView = new
		// AdView(this,adPlaceId);
		AdView adView = new AdView(context, adPlaceID);
		// 设置监听器
		adView.setListener(new AdViewListener() {
			public void onAdSwitch() {
				Log.w("AdView", "onAdSwitch");
			}

			public void onAdShow(JSONObject info) {
				// 广告已经渲染出来
				Log.w("AdView", "onAdShow " + info.toString());
			}

			public void onAdReady(AdView adView) {
				// 资源已经缓存完毕，还没有渲染出来
				Log.w("AdView", "onAdReady " + adView);
			}

			public void onAdFailed(String reason) {
				Log.w("AdView", "onAdFailed " + reason);
			}

			public void onAdClick(JSONObject info) {
				// Log.w("AdView", "onAdClick " + info.toString());

			}
		});
		// 将adView添加到父控件中(注：该父控件不一定为您的根控件，只要该控件能通过addView能添加广告视图即可)
		RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		rllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		layout.addView(adView, rllp);
	}

}
