package com.example.gmaps;

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
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class Gmaps extends Activity implements OnMapClickListener, OnMarkerClickListener {

	private final LatLng LOCATION_UNI = new LatLng(52.765997, -1.234043); // University

	private GoogleMap map;
	private Marker myMarker;
	
	String lat, lng, currentLat, currentLng;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		// Make the activity fullscreen - Make sure you set content view after making full screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_gmaps);
		
		String showDirections;

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		map.getUiSettings().setZoomControlsEnabled(false);
		map.setMyLocationEnabled(true);
		
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNI, (float) 17);
		map.animateCamera(update);
		
		map.setOnMapClickListener(this);
		// map.setOnMapLongClickListener(this); Enable this if you want to use onlongclick on map.
		map.setOnMarkerClickListener(this);
        
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
		}
	}
	

	public void onClick_Uni(View v) {

		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNI, (float) 17);
		map.clear(); // Clears all overlays from the map.
		map.animateCamera(update);	
	}
	

	public void onClick_FindMe(View v) {

		Location myLocation = map.getMyLocation();
		
		if (myLocation == null ) {
			
			Toast.makeText(this, "Your location is not available", Toast.LENGTH_LONG).show();
		} else {
			
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 16);
			map.animateCamera(update);
		}
	}
	
	
	public void onClick_BuildingSearch(View v) {

		Toast.makeText(this, "Launching Building Search", Toast.LENGTH_SHORT).show();
		Intent i = new Intent("com.example.gmaps.BUILDINGSEARCH");
		startActivity(i);
	}
	
	
	@Override
	public void onMapClick(LatLng point) {
		
		map.clear();
		myMarker = map.addMarker(new MarkerOptions().position(point));
		Log.d("Gmaps", "Short Map Click: Lat: " + point.latitude + " Lng: " + point.longitude);
	}

	
	/*@Override
	public void onMapLongClick(LatLng point) {
		
		// Re-factor this to check if a marker has already been created by this method. If it has, remove it.
		map.clear();
		map.addMarker(new MarkerOptions().position(point));
		Log.d("Devon", "Lat: " + point.latitude + " Lng: " + point.longitude);
		Toast.makeText(this, "Inflate menu to say 'get here' on a button?.", Toast.LENGTH_LONG).show();
		// Show some buttons whether they want "Directions to here" or "Directions from here" in the onMarkerClick method.
		// And then do more stuff from there.
	} Be sure to implement OnMapLongClickListener onto the activity if you want to use onlongclick on map*/
	
	
	@Override
	public boolean onMarkerClick(Marker marker) {
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
 
		// set title
		alertDialogBuilder.setTitle("Directions");
 
		// set dialog message
		alertDialogBuilder.setMessage("Do you want directions to or from here?").setCancelable(false)
			
		.setNegativeButton("To Here", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// Launching building search activity - should pass co-ordinates
				Log.d("Gmaps", "Launching building search.");
				Intent i = new Intent("com.example.gmaps.BUILDINGSEARCH");
				startActivity(i);
				}
			})
				
		.setNeutralButton("From Here", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// Launching building activity and pass starting co-ordinates
				Log.d("Gmaps", "You want directions from here.");
				}
			})
			
		.setPositiveButton("Cancel",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// if this button is clicked, just close the dialog box and do nothing
				dialog.cancel();
				}
			});
 
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
 
			// show it
			alertDialog.show();
			return false;
	}

	public void showDirections(String lat, String lng, String currentLat, String currentLng) {
			
		// May need to convert the lat, lng etc to doubles and then put it into the array.
		LatLng origin = new LatLng(Double.parseDouble(currentLat), Double.parseDouble(currentLng));
		LatLng dest = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));

		// Getting URL to the Google Directions API
	    String url = getDirectionsUrl(origin, dest);
	
	    DownloadTask downloadTask = new DownloadTask();
	
	    // Start downloading json data from Google Directions API
	    downloadTask.execute(url);
	}
	
	// String lat, String lng, String currentLat, String currentLng
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
    
 
    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
    	
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        
        try{
            URL url = new URL(strUrl);
 
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
 
            // Connecting to url
            urlConnection.connect();
 
            // Reading data from url
            iStream = urlConnection.getInputStream();
 
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
 
            StringBuffer sb  = new StringBuffer();
 
            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }
 
            data = sb.toString();
 
            br.close();
 
        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
 
    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String>{
 
        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {
 
            // For storing data from web service
 
            String data = "";
 
            try{
                // Fetching the data from web service
                 data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }
 
        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
 
            ParserTask parserTask = new ParserTask();
 
            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }
 
    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
 
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
 
        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;
 
            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
 
                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }
 
        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
 
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
 
            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();
 
                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);
 
                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);
 
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
 
                    points.add(position);
                }
 
                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(6);
                lineOptions.color(Color.RED);
            }
 
             // Drawing polyline in the Google Map for the i-th route
             map.addPolyline(lineOptions);
         }
    }
}
