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
import com.luoyb.joker.service.joker.JokerLikeCountService;
import com.luoyb.joker.util.Utils;

public class JokerAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private LayoutInflater mInflater;

	public JokerAdapter(Context ctx, List<Map<String, Object>> data) {
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
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.joker_list_item, null);
			holder.content = (TextView) convertView
					.findViewById(R.id.jokerText);
			holder.createTime = (TextView) convertView
					.findViewById(R.id.jokerCreateTime);
			holder.likeCount = (TextView) convertView
					.findViewById(R.id.jokerLikeCount);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 赋值
		holder.content.setText(String
				.valueOf(data.get(position).get("content")));
		holder.createTime.setText(Utils.timestamp2Datetime(String.valueOf(data
				.get(position).get("createTime"))));
		holder.likeCount.setText(String.valueOf(data.get(position).get(
				"likeCount")));
		// 存放jokder的记录id，使得后面可以传递给单击事件监听器
		holder.likeCount.setTag(String.valueOf(data.get(position).get("id")));

		holder.likeCount.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				TextView tv = (TextView) view;
				int likeCount = Integer.valueOf((String) tv.getText());
				String jokerId = (String) tv.getTag();
				tv.setText(String.valueOf(likeCount + 1));
				new JokerLikeCountService().execute(jokerId);
			}
		});
		return convertView;
	}
}

final class ViewHolder {
	public TextView content;
	public TextView createTime;
	public TextView likeCount;
}
