package com.luoyb.joker.service.album;

import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import cc.cnfc.message.dto.MPageResult;
import cc.cnfc.message.service.joker.MAlbum;

import com.luoyb.joker.adapter.AlbumGridAdapter;
import com.luoyb.joker.constant.Const;

public class AlbumGridDataService extends
		AsyncTask<Integer, Void, List<Map<String, Object>>> {

	private List<Map<String, Object>> data;
	private AlbumGridAdapter adapter;

	public AlbumGridDataService(AlbumGridAdapter adapter,
			List<Map<String, Object>> data) {
		this.adapter = adapter;
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<Map<String, Object>> doInBackground(Integer... params) {
		int page = params[0];
		MPageResult result = MAlbum.getMAlbum(Const.mSysId, Const.mToken)
				.findNewAlbums(page);
		return (List<Map<String, Object>>) result.getPage().getQueryList();
	}

	@Override
	protected void onPostExecute(List<Map<String, Object>> list) {
		data.addAll(list);
		adapter.notifyDataSetChanged();
	}

}
