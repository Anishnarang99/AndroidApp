package uk.ac.lboro.android.apps.Loughborough.Other;

import uk.ac.lboro.android.apps.Loughborough.R;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
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
	}

	private void initialiseVariables() {
		
		textView = (TextView) findViewById(R.id.textViewTitle);
		Flashlight = (Button) findViewById(R.id.buttonFlashlight);
		CallSecurity = (Button) findViewById(R.id.buttonCallSecurity);
		CallNHS = (Button) findViewById(R.id.buttonCallNHSDirect);
		Flashlight.setOnClickListener(this);
		CallSecurity.setOnClickListener(this);
		CallNHS.setOnClickListener(this);
		
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

		if (id == R.id.buttonCallSecurity) {
			
			// SecurityNumber = "456852"; // Fake security number as a place holder.
			SecurityNumber = "0800 526966"; // Real security number to change to when app goes live.
			
			try {
				
			    Intent my_callIntent = new Intent(Intent.ACTION_CALL);
			    
			    // Need "tel:" there to make a call
			    my_callIntent.setData(Uri.parse("tel:"+ SecurityNumber));
			    startActivity(my_callIntent);
			} catch (ActivityNotFoundException e) {
				
			    Toast.makeText(getApplicationContext(), "Error in your phone call" + e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
		
		if (id == R.id.buttonCallNHSDirect) {

			// NHSDirectNumber = "0123456"; // Fake NHS Direct number as a place holder.
			NHSDirectNumber = "111"; // Real NHS Direct number to change to when app goes live.
			
			try {
				
			    Intent my_callIntent = new Intent(Intent.ACTION_CALL);
			    
			    // Need "tel:" there to make a call
			    my_callIntent.setData(Uri.parse("tel:"+ NHSDirectNumber));
			    startActivity(my_callIntent);
			} catch (ActivityNotFoundException e) {
				
			    Toast.makeText(getApplicationContext(), "Error in your phone call" + e.getMessage(), Toast.LENGTH_LONG).show();
			}
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
	        }
	        
	        catch (RuntimeException e) {
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
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		turnOffFlash();
		Intent i = new Intent("uk.ac.lboro.android.apps.Loughborough.Ui.MENU");
		startActivity(i);
	}
	
	@Override
	protected void onPause() {
	    super.onPause();
	    turnOffFlash();
	}	
}