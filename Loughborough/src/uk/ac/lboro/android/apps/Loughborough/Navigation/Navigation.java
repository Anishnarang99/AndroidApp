package uk.ac.lboro.android.apps.Loughborough.Navigation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import uk.ac.lboro.android.apps.Loughborough.R;
import uk.ac.lboro.android.apps.Loughborough.R.id;
import uk.ac.lboro.android.apps.Loughborough.R.layout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class Navigation extends Activity {

	private final LatLng LOCATION_UNI = new LatLng(52.765997, -1.234043); // University

	GoogleMap map;
	CameraUpdate update;
	Location myLocation;
	
	int zoomLevel;
	String lat, lng, currentLat, currentLng, showDirections;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		// Make the activity fullscreen - Make sure you set content view after making full screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_gmaps);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		map.getUiSettings().setZoomControlsEnabled(false);
		map.setMyLocationEnabled(true);
		update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNI, 17);
		map.animateCamera(update);
		// Retrieving current location co-ords and building co-ords to show route direction.
 		Intent i = getIntent();
		lat = i.getStringExtra("LAT");
		lng = i.getStringExtra("LNG");
		currentLat = i.getStringExtra("CURRENTLAT");
		currentLng = i.getStringExtra("CURRENTLNG");
		showDirections = i.getStringExtra("showDirections");   
		
		if (showDirections != null) {
			
			Log.d("Devon", "Showing route directions");
			showDirections(lat, lng, currentLat, currentLng);
			update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNI, 14);
			map.animateCamera(update);
		}
	}
	

	public void onClick_Uni(View v) {

		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNI, 17);
		map.clear(); // Clears all overlays from the map.
		map.animateCamera(update);	
	}
	

	public void onClick_FindMe(View v) {
		
		zoomLevel = 16;
		getMyLocation(zoomLevel);
	}
	
	
	private void getMyLocation(int zoomLevel) {

		myLocation = map.getMyLocation();
		
		if (myLocation == null ) {
			
			Toast.makeText(this, "Your location is not available", Toast.LENGTH_LONG).show();
		} else {
			
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), zoomLevel);
			map.animateCamera(update);
		}
	}


	public void onClick_BuildingSearch(View v) {

		Toast.makeText(this, "Launching Building Search", Toast.LENGTH_SHORT).show();
		Intent i = new Intent("uk.ac.lboro.android.apps.Loughborough.Buildings.BUILDINGSEARCH");
		startActivity(i);
	}

	public void showDirections(String lat, String lng, String currentLat, String currentLng) {
			
		// May need to convert the lat, lng etc to doubles and then put it into the array.
		LatLng origin = new LatLng(Double.parseDouble(currentLat), Double.parseDouble(currentLng));
		LatLng dest = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));

		// Getting URL to the Google Directions API
	    String url = getDirectionsUrl(origin, dest);
	
	    DownloadTask downloadTask = new DownloadTask(map);
	
	    // Start downloading json data from Google Directions API
	    Log.d("Devon", "URL: " + url);
	    if (!url.isEmpty())
	    	downloadTask.execute(url);
	    else
	    	Toast.makeText(this, "Your location is not available", Toast.LENGTH_LONG).show();
	    	Log.d("Devon", "URL: " + url);
	}
	
    private String getDirectionsUrl(LatLng origin, LatLng dest){
     	 
        // Output format
        String output = "json";
        
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
 
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
 
        // Sensor enabled
        String walkingmode = "&mode=walking&units=metric";
 
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + walkingmode;
 
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        
        // Log.d("Devon", "URL: " + url);
        return url;
    }
    
    @Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent("uk.ac.lboro.android.apps.Loughborough.Ui.MENU");
		startActivity(i);
	}
}
