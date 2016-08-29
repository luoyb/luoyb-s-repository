package com.luoyb.joker.core;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class ImageRender {

	public static Map<String, Bitmap> imageCache = Collections
			.synchronizedMap(new HashMap<String, Bitmap>());

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
		Bitmap bitmap = imageCache.get(url);
		if (bitmap != null) {
			Log.i("", "hit cache : " + url);
			renderDrawable2View(bitmap);
		} else {
			Log.i("", "not hit cache : " + url);
			new DownloadImageTask().execute(url);
		}
	}

	public static void renderDrawable2View(Bitmap bitmap) {
		if (view instanceof ImageView) {
			((ImageView) view).setImageBitmap(bitmap);
			return;
		}
		Log.i("ImageRender",
				"not imageRender of this type view , please implement");
	}
}

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

	private String imageUrl;

	protected Bitmap doInBackground(String... urls) {
		imageUrl = urls[0];
		return loadImageFromNetwork();
	}

	protected void onPostExecute(Bitmap bitmap) {
		ImageRender.imageCache.put(imageUrl, bitmap);
		ImageRender.renderDrawable2View(bitmap);
	}

	private Bitmap loadImageFromNetwork() {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = 4;
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(new URL(imageUrl).openStream(),
					null, opts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}
