package com.luoyb.joker.service;

import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import cc.cnfc.message.dto.JokerResult;
import cc.cnfc.message.service.MJoker;

import com.luoyb.joker.adapter.JokerAdapter;

public class JokerDataService extends
		AsyncTask<Integer, Void, List<Map<String, Object>>> {

	private List<Map<String, Object>> data;
	private JokerAdapter adapter;

	public JokerDataService(JokerAdapter adapter, List<Map<String, Object>> data) {
		this.adapter = adapter;
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<Map<String, Object>> doInBackground(Integer... params) {
		int page = params[0];
		JokerResult result = MJoker.getMJoker("ltuoyb", "Vtll34vHseD^ss")
				.findJokers(page);
		return (List<Map<String, Object>>) result.getPage().getQueryList();
	}

	@Override
	protected void onPostExecute(List<Map<String, Object>> list) {
		data.addAll(list);
		adapter.notifyDataSetChanged();
	}

}
