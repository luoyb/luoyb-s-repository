package com.luoyb.joker.service.album;

import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import cc.cnfc.message.dto.MPageResult;
import cc.cnfc.message.service.joker.MAlbum;

import com.luoyb.joker.adapter.AlbumDetailAdapter;
import com.luoyb.joker.util.Const;

public class AlbumDetailDataService extends
		AsyncTask<String, Void, List<Map<String, Object>>> {

	private List<Map<String, Object>> data;
	private AlbumDetailAdapter adapter;

	public AlbumDetailDataService(AlbumDetailAdapter adapter,
			List<Map<String, Object>> data) {
		this.adapter = adapter;
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<Map<String, Object>> doInBackground(String... params) {
		String albumId = params[0];
		MPageResult result = MAlbum.getMAlbum(Const.mSysId, Const.mToken)
				.AlbumDetail(albumId);
		return (List<Map<String, Object>>) result.getPage().getQueryList();
	}

	@Override
	protected void onPostExecute(List<Map<String, Object>> list) {
		data.addAll(list);
		adapter.notifyDataSetChanged();
	}

}
