package com.luoyb.joker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;
import cc.cnfc.message.pub.Const;

import com.luoyb.joker.adapter.AlbumDetailAdapter;
import com.luoyb.joker.core.ImageRender;
import com.luoyb.joker.service.album.AlbumDetailDataService;

public class AlbumDetailActivity extends Activity implements ViewFactory {

	// private TextView albumDetailName;
	private ImageSwitcher imageSwitcher;
	private AlbumDetailAdapter adapter;

	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.album_detail);
		Intent intent = this.getIntent();

		this.setTitle(this.getString(R.string.album_detail_activity_name) + "｜"
				+ intent.getStringExtra("albumName"));
		// albumDetailName = (TextView)
		// this.findViewById(R.id.album_detail_name);
		// albumDetailName.setText(intent.getStringExtra("albumName"));

		imageSwitcher = (ImageSwitcher) this.findViewById(R.id.album_switcher);
		imageSwitcher.setFactory(this);
		imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_in));
		imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out));

		Gallery gallery = (Gallery) this.findViewById(R.id.album_gallery);
		adapter = new AlbumDetailAdapter(this, data);
		gallery.setAdapter(adapter);
		gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				ImageRender.renderImage(
						Const.DOMAIN
								+ String.valueOf(data.get(position).get(
										"imageUrl")), imageSwitcher);
			}
		});

		new AlbumDetailDataService(adapter, data).execute(intent
				.getStringExtra("albumId"));
	}

	@Override
	public View makeView() {
		ImageView imageView = new ImageView(this);
		imageView.setBackgroundColor(0xFF000000);
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return imageView;
	}

}
