package uk.ac.lboro.android.apps.Loughborough.Ui;

import uk.ac.lboro.android.apps.Loughborough.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashPage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// Make the activity fullscreen - Make sure you set content view after making full screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashpage);
		
		// In a new thread, pause on the splash page for two seconds then open the menu screen.
		Thread timer = new Thread(){
			@Override
			public void run(){
				try{
					sleep(2000); // Stays on the splash page for 2 seconds.
				}
				catch (InterruptedException e){
					e.printStackTrace();
				}
				finally{
					// Load the menu activity.
					Intent openMainActivity = new Intent("uk.ac.lboro.android.apps.Loughborough.Ui.MENU"); // The name needs to match the action name in the Android Manifest xml
					startActivity(openMainActivity);
				}
			}
		};
		
		timer.start();
	}
	
	// Exits the app when the back button is pressed.
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}
