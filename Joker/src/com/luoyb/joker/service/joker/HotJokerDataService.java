package com.luoyb.joker.service.joker;

import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import cc.cnfc.message.dto.MPageResult;
import cc.cnfc.message.service.joker.MJoker;

import com.luoyb.joker.adapter.JokerAdapter;
import com.luoyb.joker.util.Const;

public class HotJokerDataService extends
		AsyncTask<Integer, Void, List<Map<String, Object>>> {

	private List<Map<String, Object>> data;
	private JokerAdapter adapter;

	public HotJokerDataService(JokerAdapter adapter,
			List<Map<String, Object>> data) {
		this.adapter = adapter;
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<Map<String, Object>> doInBackground(Integer... params) {
		int page = params[0];
		MPageResult result = MJoker.getMJoker(Const.mSysId, Const.mToken)
				.findHotJokers(page);
		return (List<Map<String, Object>>) result.getPage().getQueryList();
	}

	@Override
	protected void onPostExecute(List<Map<String, Object>> list) {
		data.addAll(list);
		adapter.notifyDataSetChanged();
	}

}
