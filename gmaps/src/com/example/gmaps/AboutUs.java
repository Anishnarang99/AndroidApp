package com.example.gmaps;

import android.app.Activity;
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
}
