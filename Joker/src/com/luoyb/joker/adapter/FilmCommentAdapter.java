package com.luoyb.joker.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luoyb.joker.R;
import com.luoyb.joker.util.Utils;

public class FilmCommentAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private LayoutInflater mInflater;

	public FilmCommentAdapter(Context ctx, List<Map<String, Object>> data) {
		this.context = ctx;
		this.data = data;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		FilmCommentViewHolder holder = null;
		if (convertView == null) {
			holder = new FilmCommentViewHolder();
			convertView = mInflater.inflate(R.layout.film_comment_item, null);
			holder.comment = (TextView) convertView
					.findViewById(R.id.filmComment);
			holder.createTime = (TextView) convertView
					.findViewById(R.id.filmCommentCreateTime);
			convertView.setTag(holder);
		} else {
			holder = (FilmCommentViewHolder) convertView.getTag();
		}

		// 赋值
		holder.comment.setText(String
				.valueOf(data.get(position).get("comment")));
		holder.createTime.setText(Utils.timestamp2Datetime(String.valueOf(data
				.get(position).get("createTime"))));

		return convertView;
	}
}

final class FilmCommentViewHolder {
	public TextView comment;
	public TextView createTime;
}
