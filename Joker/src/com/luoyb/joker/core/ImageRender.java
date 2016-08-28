package com.luoyb.joker.core;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ImageRender {

	public static Map<String, Drawable> imageCache = Collections
			.synchronizedMap(new HashMap<String, Drawable>());

	public ImageRender() {
	}

	private static View view;

	/**
	 * 渲染封面
	 * 
	 * @param tv
	 */
	public static void renderImage(String url, View view) {
		ImageRender.view = view;
		Drawable drawable = imageCache.get(url);
		if (drawable != null) {
			renderDrawable2View(drawable);
		} else {
			new DownloadImageTask().execute(url);
		}
	}

	public static void renderDrawable2View(Drawable drawable) {
		if (view instanceof TextView) {
			// / 这一步必须要做,否则不会显示.
			drawable.setBounds(0, 0, drawable.getMinimumWidth() / 10,
					drawable.getMinimumHeight() / 10);
			((TextView) view).setCompoundDrawables(drawable, null, null, null);
		} else {
			Log.i("ImageRender",
					"not imageRender of this type view , please implement");
		}
	}
}

class DownloadImageTask extends AsyncTask<String, Void, Drawable> {

	private String imageUrl;

	protected Drawable doInBackground(String... urls) {
		imageUrl = urls[0];
		return loadImageFromNetwork();
	}

	protected void onPostExecute(Drawable drawable) {
		ImageRender.imageCache.put(imageUrl, drawable);
		ImageRender.renderDrawable2View(drawable);
	}

	private Drawable loadImageFromNetwork() {
		Drawable drawable = null;
		try {
			drawable = Drawable.createFromStream(
					new URL(imageUrl).openStream(), null);
		} catch (IOException e) {
			Log.d("", e.getMessage());
		}
		return drawable;
	}
}
