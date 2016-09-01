package com.luoyb.joker.view.film;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.luoyb.joker.adapter.FilmAdapter;
import com.luoyb.joker.core.BounceListView;
import com.luoyb.joker.core.InfiniteScrollListener;
import com.luoyb.joker.service.film.FilmDataService;

public class FilmView {

	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

	private FilmAdapter adapter;

	private Context context;

	public FilmView(Context context) {
		this.context = context;
	}

	public BounceListView getView() {
		BounceListView lsView = new BounceListView(context);
		adapter = new FilmAdapter(context, data);
		lsView.setAdapter(adapter);
		// Attach the listener to the AdapterView onCreate
		lsView.setOnScrollListener(new InfiniteScrollListener(5) {
			@Override
			public void loadMore(int page, int totalItemsCount) {
				new FilmDataService(adapter, data).execute(page);
			}
		});
		new FilmDataService(adapter, data).execute(1);
		return lsView;
	}
}
