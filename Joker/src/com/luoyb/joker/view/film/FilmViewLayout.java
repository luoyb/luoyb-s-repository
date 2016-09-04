package com.luoyb.joker.view.film;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luoyb.joker.constant.WdjAdConst;
import com.wandoujia.ads.sdk.Ads;

public class FilmViewLayout implements OnClickListener {

	private Context context;

	private RelativeLayout layout;

	public FilmViewLayout(Context context) {
		this.context = context;
	}

	public RelativeLayout getView() {
		layout = new RelativeLayout(context);
		// setContentView(layout);
		this.inflateByFilmListView(layout);
		this.inflateByAdView();
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
	private void inflateByFilmListView(ViewGroup layout) {
		RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		rllp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		layout.addView(new FilmView(context).getView(), rllp);
	}

	/**
	 * 获取豌豆荚广告view
	 * 
	 * @return
	 */
	private void inflateByAdView() {
		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				try {
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
					Ads.preLoad(WdjAdConst.BANNER_AD_PLACE_ID,
							Ads.AdFormat.banner);
					View bannerView = Ads.createBannerView(context,
							WdjAdConst.BANNER_AD_PLACE_ID);

					RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(
							RelativeLayout.LayoutParams.FILL_PARENT,
							RelativeLayout.LayoutParams.WRAP_CONTENT);
					rllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
					layout.addView(bannerView, rllp);
				} else {
					TextView errorMsg = new TextView(context);
					errorMsg.setText("init failed");
					layout.addView(errorMsg);
				}
			}
		}.execute();
	}

}
