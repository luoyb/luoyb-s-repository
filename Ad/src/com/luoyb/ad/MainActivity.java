package com.luoyb.ad;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		LayoutInflater fLayoutInflater = LayoutInflater.from(this);
		LinearLayout layout = (LinearLayout) fLayoutInflater.inflate(
				R.layout.activity_main, null);
		LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		this.addContentView(layout, layoutParam);

		this.inflateByColumnAds(layout);
		this.inflateByColumnAds(layout);
		this.inflateByColumnAds(layout);
		this.inflateByColumnAds(layout);
		this.inflateByColumnAds(layout);

	}

	public void inflateByColumnAds(ViewGroup layout) {
		LayoutParams subLayoutParam = new LinearLayout.LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 1);
		LinearLayout subLayout = new LinearLayout(this);
		subLayout.setOrientation(LinearLayout.VERTICAL);
		this.columnAds(subLayout);
		layout.addView(subLayout, subLayoutParam);
	}

	private void columnAds(ViewGroup layout) {
		// new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_01,
		// WdjAdConst.APP_ID, WdjAdConst.SECRET_KEY)
		// .inflateByBannerAdView();
		// new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_02,
		// WdjAdConst.APP_ID, WdjAdConst.SECRET_KEY)
		// .inflateByBannerAdView();
		// new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_03,
		// WdjAdConst.APP_ID, WdjAdConst.SECRET_KEY)
		// .inflateByBannerAdView();
		// new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_04,
		// WdjAdConst.APP_ID, WdjAdConst.SECRET_KEY)
		// .inflateByBannerAdView();
		// new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_05,
		// WdjAdConst.APP_ID, WdjAdConst.SECRET_KEY)
		// .inflateByBannerAdView();
		// new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_06,
		// WdjAdConst.APP_ID, WdjAdConst.SECRET_KEY)
		// .inflateByBannerAdView();
		// new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_07,
		// WdjAdConst.APP_ID, WdjAdConst.SECRET_KEY)
		// .inflateByBannerAdView();
		// new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_08,
		// WdjAdConst.APP_ID, WdjAdConst.SECRET_KEY)
		// .inflateByBannerAdView();
		// new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_09,
		// WdjAdConst.APP_ID, WdjAdConst.SECRET_KEY)
		// .inflateByBannerAdView();
		// new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_10,
		// WdjAdConst.APP_ID, WdjAdConst.SECRET_KEY)
		// .inflateByBannerAdView();

		new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_4_D_01,
				WdjAdConst.APP_ID_4_D, WdjAdConst.SECRET_KEY_4_D)
				.inflateByBannerAdView();
		new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_4_D_02,
				WdjAdConst.APP_ID_4_D, WdjAdConst.SECRET_KEY_4_D)
				.inflateByBannerAdView();
		new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_4_D_03,
				WdjAdConst.APP_ID_4_D, WdjAdConst.SECRET_KEY_4_D)
				.inflateByBannerAdView();
		new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_4_D_04,
				WdjAdConst.APP_ID_4_D, WdjAdConst.SECRET_KEY_4_D)
				.inflateByBannerAdView();
		new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_4_D_05,
				WdjAdConst.APP_ID_4_D, WdjAdConst.SECRET_KEY_4_D)
				.inflateByBannerAdView();
		new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_4_D_06,
				WdjAdConst.APP_ID_4_D, WdjAdConst.SECRET_KEY_4_D)
				.inflateByBannerAdView();
		new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_4_D_07,
				WdjAdConst.APP_ID_4_D, WdjAdConst.SECRET_KEY_4_D)
				.inflateByBannerAdView();
		new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_4_D_08,
				WdjAdConst.APP_ID_4_D, WdjAdConst.SECRET_KEY_4_D)
				.inflateByBannerAdView();
		new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_4_D_09,
				WdjAdConst.APP_ID_4_D, WdjAdConst.SECRET_KEY_4_D)
				.inflateByBannerAdView();
		new WdjAdView(this, layout, WdjAdConst.BANNER_AD_PLACE_ID_4_D_10,
				WdjAdConst.APP_ID_4_D, WdjAdConst.SECRET_KEY_4_D)
				.inflateByBannerAdView();

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
