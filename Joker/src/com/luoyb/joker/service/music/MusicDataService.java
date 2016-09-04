package com.luoyb.joker.service.music;

import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import cc.cnfc.message.dto.MPageResult;
import cc.cnfc.message.service.joker.MMusic;

import com.luoyb.joker.adapter.MusicAdapter;
import com.luoyb.joker.constant.Const;

public class MusicDataService extends
		AsyncTask<Integer, Void, List<Map<String, Object>>> {

	private List<Map<String, Object>> data;
	private MusicAdapter adapter;

	public MusicDataService(MusicAdapter adapter, List<Map<String, Object>> data) {
		this.adapter = adapter;
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<Map<String, Object>> doInBackground(Integer... params) {
		int page = params[0];
		MPageResult result = MMusic.getMMusic(Const.mSysId, Const.mToken)
				.findNewMusics(page);
		return (List<Map<String, Object>>) result.getPage().getQueryList();
	}

	@Override
	protected void onPostExecute(List<Map<String, Object>> list) {
		data.addAll(list);
		adapter.notifyDataSetChanged();
	}

}
