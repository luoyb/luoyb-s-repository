package com.luoyb.joker.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.luoyb.joker.adapter.JokerAdapter;
import com.luoyb.joker.core.BounceListView;
import com.luoyb.joker.core.InfiniteScrollListener;
import com.luoyb.joker.service.HotJokerDataService;

public class HotJokerView {

	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

	private JokerAdapter adapter;

	private Context context;

	public HotJokerView(Context context) {
		this.context = context;
	}

	public BounceListView getNewJokerView() {
		BounceListView lsView = new BounceListView(context);
		adapter = new JokerAdapter(context, data);
		lsView.setAdapter(adapter);
		// Attach the listener to the AdapterView onCreate
		lsView.setOnScrollListener(new InfiniteScrollListener(5) {
			@Override
			public void loadMore(int page, int totalItemsCount) {
				new HotJokerDataService(adapter, data).execute(page);
			}
		});
		new HotJokerDataService(adapter, data).execute(1);
		return lsView;
	}
}
