package com.luoyb.helloworld;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class TestService extends Service {

	private TestBinder testBinder = new TestBinder();
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public class TestBinder extends Binder{
		TestService getService(){
			return TestService.this;
		}
		
	}

}
