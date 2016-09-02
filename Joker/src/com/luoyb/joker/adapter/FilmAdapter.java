package com.luoyb.joker.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cc.cnfc.message.pub.Const;

import com.luoyb.joker.FilmCommentActivity;
import com.luoyb.joker.R;
import com.luoyb.joker.core.ImageRender;

public class FilmAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private LayoutInflater mInflater;

	public FilmAdapter(Context ctx, List<Map<String, Object>> data) {
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
		// Log.i("position", position + "");
		FilmViewHolder holder = null;
		if (convertView == null) {
			holder = new FilmViewHolder();
			convertView = mInflater.inflate(R.layout.film_list_item, null);

			holder.filmName = (TextView) convertView
					.findViewById(R.id.filmName);
			holder.filmCover = (ImageView) convertView
					.findViewById(R.id.filmCover);
			convertView.setTag(holder);
		} else {
			holder = (FilmViewHolder) convertView.getTag();
		}

		// 赋值
		holder.filmName.setText(String.valueOf(data.get(position).get("name")));

		ImageRender.renderImage(
				Const.DOMAIN
						+ String.valueOf(data.get(position).get("coverUrl")),
				holder.filmCover);

		// 存放jokder的记录id，使得后面可以传递给单击事件监听器
		holder.filmName.setTag(String.valueOf(data.get(position).get("id")));

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				TextView tv = (TextView) view.findViewById(R.id.filmName);
				String filmId = (String) tv.getTag();
				String filmName = (String) tv.getText();

				Intent i = new Intent(context, FilmCommentActivity.class);
				i.putExtra("filmId", filmId);
				i.putExtra("filmName", filmName);
				context.startActivity(i);
			}
		});
		return convertView;
	}
}

final class FilmViewHolder {
	public TextView filmName;
	public ImageView filmCover;
}
