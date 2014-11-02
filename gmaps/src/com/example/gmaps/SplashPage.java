package com.example.gmaps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashPage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashpage);
		
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(2000);
				}
				catch (InterruptedException e){
					e.printStackTrace();
				}
				finally{
					Intent openMainActivity = new Intent("com.example.gmaps.MAIN"); // The name needs to match the action name in the Android Manifest xml
					startActivity(openMainActivity);
				}
			}
		};
		
		timer.start();
	}
}
