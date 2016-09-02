package com.luoyb.joker.service.music;

import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import cc.cnfc.message.dto.MPageResult;
import cc.cnfc.message.service.joker.MMusic;

import com.luoyb.joker.adapter.MusicCommentAdapter;
import com.luoyb.joker.util.Const;

public class MusicCommentDataService extends
		AsyncTask<String[], Void, List<Map<String, Object>>> {

	private List<Map<String, Object>> data;
	private MusicCommentAdapter adapter;

	public MusicCommentDataService(MusicCommentAdapter adapter,
			List<Map<String, Object>> data) {
		this.adapter = adapter;
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<Map<String, Object>> doInBackground(String[]... params) {
		String[] strArr = params[0];
		MPageResult result = MMusic.getMMusic(Const.mSysId, Const.mToken)
				.findMusicComment(strArr[0], Integer.valueOf(strArr[1]));
		return (List<Map<String, Object>>) result.getPage().getQueryList();
	}

	@Override
	protected void onPostExecute(List<Map<String, Object>> list) {
		data.addAll(list);
		adapter.notifyDataSetChanged();
	}

}
