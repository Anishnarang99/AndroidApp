package com.example.gmaps;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Gmaps extends Activity implements OnMapClickListener, OnMapLongClickListener, OnMarkerClickListener {

	private final LatLng LOCATION_UNI = new LatLng(52.764755, -1.235223); // University
	private final LatLng LOCATION_LSU = new LatLng(52.768995, -1.227829); // Loughborough Students Union
	private final LatLng LOCATION_WOLFSON = new LatLng(52.762859, -1.240977); // Wolfson Building
	private final LatLng LOCATION_HOME = new LatLng(52.775477, -1.223194); // Home

	private GoogleMap map;
	
	TabHost tH;
	TabSpec tS;

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
		
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNI, (float) 14);
		map.animateCamera(update);
		
		map.setOnMapClickListener(this);
		map.setOnMapLongClickListener(this);
		
		// Setup Tabs
		tabSetup();
	}

	private void tabSetup() {
		
		tH = (TabHost) findViewById(R.id.tabhost);
		tH.setup();
		tS = tH.newTabSpec("TagSpec1");
		tS.setContent(R.id.tab1);
		tS.setIndicator("Navigation");
		tH.addTab(tS);
		tS = tH.newTabSpec("TagSpec2");
		tS.setContent(R.id.tab2);
		tS.setIndicator("Building Search");
		tH.addTab(tS);
		tS = tH.newTabSpec("TagSpec3");
		tS.setContent(R.id.tab3);
		tS.setIndicator("Other");
		tH.addTab(tS);
	}

	public void onClick_Uni(View v) {

		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNI, (float) 14);
		map.clear(); // Clears all overlays from the map.
		map.animateCamera(update);	
	}

	public void onClick_Lsu(View v) {

		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_LSU, 16);
		map.clear(); // Clears all overlays from the map.
		map.addMarker(new MarkerOptions().position(LOCATION_LSU).title("Lsu"));
		map.animateCamera(update);
	}

	public void onClick_Wolfson(View v) {

		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_WOLFSON, 16);
		map.clear(); // Clears all overlays from the map.
		map.addMarker(new MarkerOptions().position(LOCATION_WOLFSON).title("Wolfson"));
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

	@Override
	public void onMapClick(LatLng point) {
		
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(point, 17);
		map.animateCamera(update);
	}

	@Override
	public void onMapLongClick(LatLng point) {
		
		// Re-factor this to check if a marker has already been created by this method. If it has, remove it.
		map.clear();
		map.addMarker(new MarkerOptions().position(point));
		// Show some buttons whether they want "Directions to here" or "Directions from here" in the onMarkerClick method.
		// And then do more stuff from there.
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		
		Toast.makeText(this, "Add 'Directions to here' and 'Directions From here' buttons.", Toast.LENGTH_LONG).show();
		return true;
	}
	
	// Tab 2
	
	public void onClick_SearchbyName(View v) {

		Toast.makeText(this, "Search by building name here", Toast.LENGTH_LONG).show();
	}
	
	public void onClick_SearchbyCode(View v) {

		Toast.makeText(this, "Search by room code here", Toast.LENGTH_LONG).show();
	}
	
	
	// Additional Info - Menu when you click the settings button on phone:
	
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		
		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.additional_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
		
		case R.id.aboutUs:
			Intent i = new Intent("com.example.gmaps.ABOUT");
			startActivity(i);
			break;
			
		case R.id.exit:
			finish();
			break;
		}
		
		return false;
	}
}
