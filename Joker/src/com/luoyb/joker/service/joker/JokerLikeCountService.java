package com.luoyb.joker.service.joker;

import android.os.AsyncTask;
import cc.cnfc.message.service.joker.MJoker;

import com.luoyb.joker.util.Const;

public class JokerLikeCountService extends AsyncTask<String, Void, Void> {

	public JokerLikeCountService() {
	}

	@Override
	protected Void doInBackground(String... params) {
		String jokerId = params[0];
		MJoker.getMJoker(Const.mSysId, Const.mToken).addLikeCount(jokerId);
		return null;
	}

	@Override
	protected void onPostExecute(Void v) {
	}

}
