package com.example.gmaps;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class Gmaps extends Activity implements OnMapClickListener, OnMarkerClickListener {

	private final LatLng LOCATION_UNI = new LatLng(52.765997, -1.234043); // University
	private final LatLng LOCATION_LSU = new LatLng(52.768995, -1.227829); // Loughborough Students Union
	private final LatLng LOCATION_WOLFSON = new LatLng(52.762358, -1.240732); // Wolfson Building
	private final LatLng LOCATION_HOME = new LatLng(52.775477, -1.223194); // Home
	private final LatLng LOCATION_DESIGN = new LatLng(52.765247, -1.222557); // Design School
	private final LatLng LOCATION_PO = new LatLng(52.766058, -1.233866); // Purple Onion
	private final LatLng LOCATION_LIB = new LatLng(52.762851, -1.236784); // Library

	private GoogleMap map;
	private Marker myMarker;	

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
		
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNI, (float) 17);
		map.animateCamera(update);
		
		map.setOnMapClickListener(this);
		// map.setOnMapLongClickListener(this); Enable this if you want to use onlongclick on map.
		map.setOnMarkerClickListener(this);
		
	}

	public void onClick_Uni(View v) {

		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNI, (float) 17);
		map.clear(); // Clears all overlays from the map.
		map.animateCamera(update);	
	}

	public void onClick_Lsu(View v) {

		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_LSU, (float) 17.3);
		map.clear(); // Clears all overlays from the map.
		myMarker = map.addMarker(new MarkerOptions().position(LOCATION_LSU).title("Lsu"));
		map.animateCamera(update);
	}

	public void onClick_Wolfson(View v) {

		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_WOLFSON, (float) 17.3);
		map.clear(); // Clears all overlays from the map.
		myMarker = map.addMarker(new MarkerOptions().position(LOCATION_WOLFSON).title("Wolfson"));
		map.animateCamera(update);
	}

	public void onClick_FindMe(View v) {

		Location myLocation = map.getMyLocation();
		
		if( myLocation == null ) {
			
			Toast.makeText(this, "Your location is not available", Toast.LENGTH_LONG).show();
		} else {
			
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 16);
			map.animateCamera(update);
		}
	}
	
	public void onClick_Route(View v) {

		//Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
		//Uri.parse("http://maps.google.com/maps?saddr=52.762859, -1.240977&daddr=52.775477, -1.223194")); // Route from Wolfson to Home
		//startActivity(intent);
		Toast.makeText(this, "This button does nothing. Yet...", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onMapClick(LatLng point) {
		
		map.clear();
		myMarker = map.addMarker(new MarkerOptions().position(point));
		Log.d("Gmaps", " Short Map Click: Lat: " + point.latitude + " Lng: " + point.longitude);
		
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
		
		if (marker.equals(myMarker)) {
			Toast.makeText(this, "Launching Building Search", Toast.LENGTH_SHORT).show();
			Intent i = new Intent("com.example.gmaps.BUILDINGSEARCH");
			startActivity(i);
		}
		
		return true;
	}

	
// Below is currently a floating text menu. Use this as a basis when wanting to choose options when clicking on a marker.
	
/*	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.route_directions, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    
	    switch (item.getItemId()) {
	    
	    case R.id.directionsToHere:
	    	Toast.makeText(this, "You have clicked the 'directions to here' button!", Toast.LENGTH_LONG).show();
	        return true;
	        
	    case R.id.directionsFromHere:
	    	Toast.makeText(this, "You have clicked the 'directions from here' button!", Toast.LENGTH_LONG).show();
	        return true;
	    default:
	        return super.onContextItemSelected(item);
	    }
	}*/

}
