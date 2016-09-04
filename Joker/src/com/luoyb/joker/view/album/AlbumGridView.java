package com.luoyb.joker.view.album;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.GridView;

import com.luoyb.joker.R;
import com.luoyb.joker.adapter.AlbumGridAdapter;
import com.luoyb.joker.core.InfiniteScrollListener;
import com.luoyb.joker.service.album.AlbumGridDataService;

public class AlbumGridView {

	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

	private AlbumGridAdapter adapter;

	private Context context;

	public AlbumGridView(Context context) {
		this.context = context;
	}

	public GridView getView() {

		// GridView gridView = new GridView(context);

		LayoutInflater fLayoutInflater = LayoutInflater.from(context);
		GridView gridView = (GridView) fLayoutInflater.inflate(
				R.layout.album_grid_view, null);

		adapter = new AlbumGridAdapter(context, data);
		gridView.setAdapter(adapter);
		// Attach the listener to the AdapterView onCreate
		gridView.setOnScrollListener(new InfiniteScrollListener(5) {
			@Override
			public void loadMore(int page, int totalItemsCount) {
				new AlbumGridDataService(adapter, data).execute(page);
			}
		});
		new AlbumGridDataService(adapter, data).execute(1);
		return gridView;
	}
}
