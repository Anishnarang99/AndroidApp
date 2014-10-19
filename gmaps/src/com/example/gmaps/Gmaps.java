package com.example.gmaps;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Gmaps extends Activity implements OnMapClickListener {

	private final LatLng LOCATION_UNI = new LatLng(52.764755, -1.235223); // University
	private final LatLng LOCATION_LSU = new LatLng(52.768995, -1.227829); // Loughborough Students Union
	private final LatLng LOCATION_WOLFSON = new LatLng(52.762859, -1.240977); // Wolfson Building
	private final LatLng LOCATION_HOME = new LatLng(52.775477, -1.223194); // Home

	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Make the activity fullscreen - Make sure you set content view after making full screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_gmaps);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNI, (float) 14);
		
		map.animateCamera(update);
		
		map.setOnMapClickListener(this);

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

		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(
				LOCATION_WOLFSON, 16);
		map.clear(); // Clears all overlays from the map.
		map.addMarker(new MarkerOptions().position(LOCATION_WOLFSON).title("Wolfson"));
		map.animateCamera(update);
	}

	public void onClick_FindMe(View v) {

		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		map.clear(); // Clears all overlays from the map.
		map.setMyLocationEnabled(true);
		
		
	}

	@Override
	public void onMapClick(LatLng point) {
		
        map.addMarker(new MarkerOptions().position(LOCATION_HOME));
    }

}
