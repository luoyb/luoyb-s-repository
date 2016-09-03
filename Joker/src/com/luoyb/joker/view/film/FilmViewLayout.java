package com.luoyb.joker.view.film;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

	private static final String APP_ID = "100043510";
	private static final String SECRET_KEY = "297fd1105b04acedd1bd3a020dd11105";
	private static final String BANNER = "a93ed415e2edb8535628f19b5f200f4e";

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
					Ads.init(context, APP_ID, SECRET_KEY);
					return true;
				} catch (Exception e) {
					Log.e("ads-sample", "error", e);
					return false;
				}
			}

			@Override
			protected void onPostExecute(Boolean success) {
				if (success) {
					Ads.preLoad(BANNER, Ads.AdFormat.banner);
					View bannerView = Ads.createBannerView(context, BANNER);

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
