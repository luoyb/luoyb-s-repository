package com.luoyb.joker.view.music;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.baidu.mobads.AdSettings;
import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.baidu.mobads.AppActivity;
import com.baidu.mobads.AppActivity.ActionBarColorTheme;

public class MusicViewLayout implements OnClickListener {

	private Context context;

	public MusicViewLayout(Context context) {
		this.context = context;
	}

	public RelativeLayout getView() {
		RelativeLayout layout = new RelativeLayout(context);
		// setContentView(layout);
		this.inflateByMusicListView(layout);
		// TODO 段子页有一个了，再显示一个会报错
		// this.inflateByAdView(layout);
		return layout;
	}

	@Override
	public void onClick(View v) {

	}

	/**
	 * 获取相册列表
	 * 
	 * @param layout
	 */
	private void inflateByMusicListView(ViewGroup layout) {
		RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		rllp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		layout.addView(new MusicView(context).getView(), rllp);
	}

	/**
	 * 获取百度广告view
	 * 
	 * @return
	 */
	private void inflateByAdView(ViewGroup layout) {
		AppActivity
				.setActionBarColorTheme(ActionBarColorTheme.ACTION_BAR_RED_THEME);
		// 人群属性
		AdSettings.setKey(new String[] { "baidu", "中国" }); // 创建广告view
		String adPlaceID = "2015351";// 重要:请填上你的代码位ID,否则无法请求到广告 adView = new
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
