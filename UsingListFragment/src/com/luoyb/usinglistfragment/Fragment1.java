package com.luoyb.usinglistfragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Fragment1 extends ListFragment {
	
	String[] presidents = {
			"Dwinght D. Eisenhower",
			"John F. Kennedy",
			"Lyndon B. Johnson",
			"Richard Nixon",
			"Gerald Ford",
			"Jimmy Carter",
			"Ronald Reagan",
			"George H. W. Bush",
			"Bill CLinton",
			"George W. Bush",
			"Barack Obama"
	};
	
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		return inflater.inflate(R.layout.fragment1, container, false);
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setListAdapter(new ArrayAdapter<String>(this.getActivity(),
				android.R.layout.simple_list_item_1,this.presidents));
	}
	
	public void onListItemClick(ListView parent,View v,int position,long id){
		Toast.makeText(this.getActivity(), 
				"You have selected "+presidents[position], Toast.LENGTH_SHORT).show();
		Log.d(this.getTag(), this.presidents[position]);
	}

}
