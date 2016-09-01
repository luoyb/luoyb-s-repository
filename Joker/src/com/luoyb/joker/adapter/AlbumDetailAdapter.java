package com.luoyb.joker.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import cc.cnfc.message.pub.Const;

import com.luoyb.joker.R;
import com.luoyb.joker.core.ImageRender;

public class AlbumDetailAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private int itemBackground;

	public AlbumDetailAdapter(Context ctx, List<Map<String, Object>> data) {
		this.context = ctx;
		this.data = data;
		TypedArray a = context.obtainStyledAttributes(R.styleable.AlbumGallery);
		itemBackground = a.getResourceId(
				R.styleable.AlbumGallery_android_galleryItemBackground, 0);
		a.recycle();
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(context);
		ImageRender.renderImage(
				Const.DOMAIN
						+ String.valueOf(data.get(position).get("imageUrl")),
				imageView);
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		imageView.setLayoutParams(new Gallery.LayoutParams(150, 120));
		imageView.setBackgroundResource(itemBackground);
		return imageView;
	}

}
