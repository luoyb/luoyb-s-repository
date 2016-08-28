package com.luoyb.joker.view.album;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.luoyb.joker.adapter.AlbumAdapter;
import com.luoyb.joker.core.BounceListView;
import com.luoyb.joker.core.InfiniteScrollListener;
import com.luoyb.joker.service.album.AlbumDataService;

public class AlbumView {

	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

	private AlbumAdapter adapter;

	private Context context;

	public AlbumView(Context context) {
		this.context = context;
	}

	public BounceListView getView() {
		BounceListView lsView = new BounceListView(context);
		adapter = new AlbumAdapter(context, data);
		lsView.setAdapter(adapter);
		// Attach the listener to the AdapterView onCreate
		lsView.setOnScrollListener(new InfiniteScrollListener(5) {
			@Override
			public void loadMore(int page, int totalItemsCount) {
				new AlbumDataService(adapter, data).execute(page);
			}
		});
		new AlbumDataService(adapter, data).execute(1);
		return lsView;
	}
}
