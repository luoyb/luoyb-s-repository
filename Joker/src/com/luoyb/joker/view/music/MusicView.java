package com.luoyb.joker.view.music;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.luoyb.joker.adapter.MusicAdapter;
import com.luoyb.joker.core.BounceListView;
import com.luoyb.joker.core.InfiniteScrollListener;
import com.luoyb.joker.service.music.MusicDataService;

public class MusicView {

	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

	private MusicAdapter adapter;

	private Context context;

	public MusicView(Context context) {
		this.context = context;
	}

	public BounceListView getView() {
		BounceListView lsView = new BounceListView(context);
		adapter = new MusicAdapter(context, data);
		lsView.setAdapter(adapter);
		// Attach the listener to the AdapterView onCreate
		lsView.setOnScrollListener(new InfiniteScrollListener(5) {
			@Override
			public void loadMore(int page, int totalItemsCount) {
				new MusicDataService(adapter, data).execute(page);
			}
		});
		new MusicDataService(adapter, data).execute(1);
		return lsView;
	}
}
