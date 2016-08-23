package com.luoyb.joker.service;

import android.os.AsyncTask;
import cc.cnfc.message.service.MJoker;

public class JokerLikeCountService extends AsyncTask<String, Void, Void> {

	public JokerLikeCountService() {
	}

	@Override
	protected Void doInBackground(String... params) {
		String jokerId = params[0];
		MJoker.getMJoker("ltuoyb", "Vtll34vHseD^ss").addLikeCount(jokerId);
		return null;
	}

	@Override
	protected void onPostExecute(Void v) {
	}

}
