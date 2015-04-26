package uk.ac.lboro.android.apps.Loughborough.Other;

import java.io.IOException;

import uk.ac.lboro.android.apps.Loughborough.R;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SafetyToolbox extends Activity implements OnClickListener {
	
	Button Flashlight, CallSecurity, CallNHS;
	TextView textView;
	String textViewName;
	Camera camera;
	public boolean hasFlash, isFlashOn;
	Parameters params;
	SurfaceTexture surfaceTexture; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		// Makes it fullscreen - Make sure you set content view after making it full screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.safetytoolbox);
		
		// Initialise variables
		initialiseVariables();
	}

	private void initialiseVariables() {
		
		textView = (TextView) findViewById(R.id.textViewTitle);
		Flashlight = (Button) findViewById(R.id.buttonFlashlight);
		CallSecurity = (Button) findViewById(R.id.buttonCallSecurity);
		CallNHS = (Button) findViewById(R.id.buttonCallNHSDirect);
		Flashlight.setOnClickListener(this);
		CallSecurity.setOnClickListener(this);
		CallNHS.setOnClickListener(this);
		
		// Sets up the heading in the activity
		textViewName = "Safety Toolbox";
		textView.setText(textViewName);
		textView.setTextSize(16);
		textView.setTextColor(Color.parseColor("#C70066"));
		textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
	}

	@Override
	public void onClick(View v) {
		
		String SecurityNumber;
		String NHSDirectNumber;
		
		int id = v.getId();

		// If the Flashlight button is pressed
		// The following code is based on code found here: http://www.mkyong.com/android/how-to-turn-onoff-camera-ledflashlight-in-android/
		if (id == R.id.buttonFlashlight) {
			
			
			if (!isFlashOn) {
				
				checkFlashAvailibility();
				getCamera();
				turnOnFlash();
			}
			
			else {
				
				turnOffFlash();
			}
		}
		
		// If the Call Security button is pressed
		if (id == R.id.buttonCallSecurity) {
			
			// SecurityNumber = "456852"; // Fake security number as a place holder.
			SecurityNumber = "0800 526966"; // Real security number to change to when app goes live.
			
			try {
				
			    Intent callIntent = new Intent(Intent.ACTION_CALL);
			    
			    // Need "tel:" there to make a call
			    callIntent.setData(Uri.parse("tel:"+ SecurityNumber));
			    startActivity(callIntent);
			} catch (ActivityNotFoundException e) {
				
			    Toast.makeText(getApplicationContext(), "Error in your phone call" + e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
		
		// If the Call NHS Direct button is pressed
		if (id == R.id.buttonCallNHSDirect) {

			// NHSDirectNumber = "0123456"; // Fake NHS Direct number as a place holder.
			NHSDirectNumber = "111"; // Real NHS Direct number to change to when app goes live.
			
			try {
				
			    Intent callIntent = new Intent(Intent.ACTION_CALL);
			    
			    // Need "tel:" there to make a call
			    callIntent.setData(Uri.parse("tel:"+ NHSDirectNumber));
			    startActivity(callIntent);
			} catch (ActivityNotFoundException e) {
				
			    Toast.makeText(getApplicationContext(), "Error in your phone call" + e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
	}

	private void checkFlashAvailibility() {
		
		// Checks device supports flash
		hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
		
		if (!hasFlash)
			Toast.makeText(this, "Your device does not contain flash!", Toast.LENGTH_LONG).show();
	}

	private void getCamera() {
		
	    if (camera == null) {
	    	
	        try {
	            camera = Camera.open();
	            params = camera.getParameters();
	        }
	        
	        catch (RuntimeException e) {
	            Log.e("Devon", "Camera Error. Failed to Open. Error: " + e.getMessage());
	        }
	    }
	}
	
	private void turnOnFlash() {

		if (!isFlashOn) {
			
	        if (camera == null || params == null) {
	            return;
	        }
	         
	        params = camera.getParameters();
	        params.setFlashMode(Parameters.FLASH_MODE_TORCH);
	        
	        try {
	        	camera.setParameters(params);
	        	surfaceTexture = new SurfaceTexture(0); // To get the flashlight to work on Android 5.0+
	        	camera.setPreviewTexture(surfaceTexture);
	        }
	        
	        catch (RuntimeException | IOException e) {
	            Log.e("Devon", "Camera Error. Failed to Open. Error: " + e.getMessage());
	            
	        }
	        camera.startPreview();
	        isFlashOn = true;
		}
	}
	
	private void turnOffFlash() {

		if (isFlashOn) {
			
	        if (camera == null || params == null) {
	            return;
	        }
	        
	        params = camera.getParameters();
	        params.setFlashMode(Parameters.FLASH_MODE_OFF);
	        camera.setParameters(params);
	        camera.stopPreview();
	        camera.release();
	        camera = null;
	        isFlashOn = false;
		}
	}
	
	// Goes back to the menu screen when the back button is pressed.
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		turnOffFlash();
	}
	
	// Turns off the flashlight if the application is exited.
	@Override
	protected void onPause() {
	    super.onPause();
	    turnOffFlash();
	}	
}