package com.example.gmaps;

import com.example.gmaps.R;
import com.example.gmaps.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class AboutUs extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// Make the activity fullscreen - Make sure you set content view after making full screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent("com.example.gmaps.MENU");
		startActivity(i);
	}
}
