package com.luoyb.usingintent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SecondActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.secondactivity);
		EditText txt_username = (EditText) this.findViewById(R.id.txt_username);
		String str = this.getIntent().getStringExtra("str");
		txt_username.setText(str);
	}
	
	public void onClick(View view){
		Intent data = new Intent();
		EditText txt_username = (EditText) this.findViewById(R.id.txt_username);
		data.setData(Uri.parse(txt_username.getText().toString()));
		this.setResult(RESULT_OK, data);
		this.finish();
	}
}
