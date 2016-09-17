package com.luoyb.joker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.baidu.mobads.AdSettings;
import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.baidu.mobads.AppActivity;
import com.baidu.mobads.AppActivity.ActionBarColorTheme;
import com.luoyb.joker.adapter.MusicCommentAdapter;
import com.luoyb.joker.constant.WdjAdConst;
import com.luoyb.joker.core.BounceListView;
import com.luoyb.joker.core.InfiniteScrollListener;
import com.luoyb.joker.service.music.MusicCommentDataService;
import com.luoyb.joker.view.ad.WdjAdView;

public class MusicCommentActivity extends Activity {

	private MusicCommentAdapter adapter;

	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

	private String musicId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		musicId = intent.getStringExtra("musicId");

		this.setTitle(this.getString(R.string.music_comment_activity_name)
				+ "｜" + intent.getStringExtra("musicName"));

		RelativeLayout layout = new RelativeLayout(this);
		setContentView(layout);

		this.inflateByFilmCommentListView(layout);
		new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_05)
				.inflateByBannerAdView();
	}

	private void inflateByFilmCommentListView(ViewGroup layout) {
		BounceListView lsView = new BounceListView(this);
		adapter = new MusicCommentAdapter(this, data);
		lsView.setAdapter(adapter);
		// Attach the listener to the AdapterView onCreate
		lsView.setOnScrollListener(new InfiniteScrollListener(5) {
			@Override
			public void loadMore(int page, int totalItemsCount) {
				new MusicCommentDataService(adapter, data)
						.execute(new String[] { musicId, String.valueOf(page) });
			}
		});

		RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		rllp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		layout.addView(lsView, rllp);

		new MusicCommentDataService(adapter, data).execute(new String[] {
				musicId, "1" });
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
		AdView adView = new AdView(this, adPlaceID);
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
