package com.luoyb.ad;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wandoujia.ads.sdk.Ads;

/**
 * 豌豆荚广告
 * 
 * @author luoyb
 * 
 */
public class WdjAdView {

	private Context context;
	private ViewGroup layout;
	private String adPlaceId;

	public WdjAdView(Context ctx, ViewGroup layout, String adPlaceId) {
		this.context = ctx;
		this.layout = layout;
		this.adPlaceId = adPlaceId;
	}

	/**
	 * 获取豌豆荚横幅广告view
	 * 
	 * @return
	 */
	public void inflateByBannerAdView() {
		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					Log.i("test", "ads init ...");
					ThreadUtils.randomSleep();
					Ads.init(context, WdjAdConst.APP_ID, WdjAdConst.SECRET_KEY);
					return true;
				} catch (Exception e) {
					Log.e("ads-sample", "error", e);
					return false;
				}
			}

			@Override
			protected void onPostExecute(Boolean success) {
				if (success) {
					Log.i("test", "ads pre load ...");
					ThreadUtils.randomSleep();
					Ads.preLoad(adPlaceId, Ads.AdFormat.banner);
					View bannerView = Ads.createBannerView(context, adPlaceId);

					// RelativeLayout.LayoutParams rllp = new
					// RelativeLayout.LayoutParams(
					// RelativeLayout.LayoutParams.FILL_PARENT,
					// RelativeLayout.LayoutParams.WRAP_CONTENT);
					// rllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
					layout.addView(bannerView);
				} else {
					TextView errorMsg = new TextView(context);
					errorMsg.setText("init failed");
					layout.addView(errorMsg);
				}
			}
		}.execute();
	}

}
