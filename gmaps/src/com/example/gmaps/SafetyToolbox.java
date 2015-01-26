package com.example.gmaps;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class SafetyToolbox extends Activity implements OnClickListener {
	
	Button Flashlight, CallSecurity, CallNHS;
	Camera camera;
	boolean hasFlash, isFlashOn;
	Parameters params;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		// Makes it fullscreen - Make sure you set content view after making it full screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.safetytoolbox);
		
		initialiseVariables();
		
		checkFlashAvailibility();
		getCamera();
	}

	private void initialiseVariables() {
		
		Flashlight = (Button) findViewById(R.id.buttonFlashlight);
		CallSecurity = (Button) findViewById(R.id.buttonCallSecurity);
		CallNHS = (Button) findViewById(R.id.buttonCallNHSDirect);
		Flashlight.setOnClickListener(this);
		CallSecurity.setOnClickListener(this);
		CallNHS.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {

		case R.id.buttonFlashlight:
			
			if (!isFlashOn) {
				
				turnOnFlash();
				break;
			}
			
			else {
				
				turnOffFlash();
				break;
			}

		case R.id.buttonCallSecurity:
			
			
			break;

		case R.id.buttonCallNHSDirect:

			
			break;
		}
	}

	private void checkFlashAvailibility() {
		
		// Check Device supports Flash
		hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
		
		if (!hasFlash) {
		    // device doesn't support flash
		    // Show alert message and close the application
			Toast.makeText(this, "Your device does not contain flash!", Toast.LENGTH_LONG).show();
		}
	}

	private void getCamera() {
		
	    if (camera == null) {
	    	
	        try {
	            camera = Camera.open();
	            params = camera.getParameters();
	        }
	        
	        catch (RuntimeException e) {
	            Log.e("Gmaps", "Camera Error. Failed to Open. Error: " + e.getMessage());
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
	        camera.setParameters(params);
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
	        camera.startPreview();
	        isFlashOn = false;
		}
	}
	
	@Override
	protected void onPause() {
	    super.onPause();
	     
	    // on pause turn off the flash
	    turnOffFlash();
	}
	
	
	
	
	
	
	
	
	
}