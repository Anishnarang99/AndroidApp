package com.example.gmaps;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.gmaps.Buildings;
import com.example.gmaps.Menu;

public class BuildingSearch extends Activity {

	AutoCompleteTextView autoCompTextBuildings, autoCompTextRoomCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Make the activity fullscreen - Make sure you set content view after making it full screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.building_search);

		Log.d("Devon", "Log message in buildingsearch.java");
				
		setUpAutoCompleteTextViews();
	}

	private void setUpAutoCompleteTextViews() {

		autoCompTextBuildings = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewBuildingName);

		// Build string array of buildings from mybuildings list
		List<String> buildingNames = new ArrayList<String>();
		for (Buildings bldg : Menu.myBuildings) {
			String bname = bldg.getBuildingName();
			buildingNames.add(bname);
		}

		// Create the adapter and set it to the AutoCompleteTextView
		ArrayAdapter<String> adapterBuildingName = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1, buildingNames);
		autoCompTextBuildings.setAdapter(adapterBuildingName);
		autoCompTextBuildings.setThreshold(1);

		// Need to do the same for the room code auto complete field.

		autoCompTextRoomCode = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewRoomCode);

		// Build string array of buildings from mybuildings list
		List<String> roomCodes = new ArrayList<String>();
		for (Buildings bldg : Menu.myBuildings) {
			String broomcode = bldg.getRoomCodes();

			// Need to correctly split the room codes from the XML file and take
			// them in as separate entries in the ArrayList.
			String[] splitRoomCodes = broomcode.split(",");
			for (int i = 0; i < splitRoomCodes.length; i++)
				roomCodes.add(splitRoomCodes[i].trim()); // Adds trimmed
		}

		// Create the adapter and set it to the AutoCompleteTextView
		ArrayAdapter<String> adapterRoomCodes = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, roomCodes);
		autoCompTextRoomCode.setAdapter(adapterRoomCodes);
		autoCompTextRoomCode.setThreshold(1);

	}

	public void onClick_GetDirections(View v) {

		// Launches the 'Other' activity and displays the directions.

		String buildingName, RoomCode, currentLat, currentLng;

		buildingName = autoCompTextBuildings.getText().toString();
		RoomCode = autoCompTextRoomCode.getText().toString();

		Buildings bn = getBuildingFromNameOrRoomCode(buildingName, RoomCode);

		if (bn == null) {

			Toast.makeText(this, "Cannot find location via building name or room code.", Toast.LENGTH_LONG).show();
			Log.d("Devon", "Cannot find building name in list.");
			return;
		}
		
		Location mylocation = Menu.getLocation();
		
		if (mylocation == null) {
			Toast.makeText(this, "Cannot retrieve current location, please wait and then try again.", Toast.LENGTH_LONG).show();
			return;
		}
		
		currentLat = String.valueOf(mylocation.getLatitude());
		currentLng = String.valueOf(mylocation.getLongitude());
		
		Log.d("Devon", "Current Lat: " + currentLat + " Current Lng: " + currentLng);
		
		if (currentLat == "0.0" && currentLng == "0.0") {
			
			Toast.makeText(this, "Cannot find current location.", Toast.LENGTH_LONG).show();
			Log.d("Devon", "Cannot find current location.");
			return;
		}
		
		Log.d("Devon", "Building name retrieved from textview: " + buildingName);
		Log.d("Devon", "Room code retrieved from textview: " + RoomCode);
		Log.d("Devon", "Current Lat: " + currentLat + " Current Lng: " + currentLng);

		Intent i = new Intent("com.example.gmaps.OTHER");

		i.putExtra("LAT", bn.getLatitude()); // Building name will be blank (not null) if field is not in.
		i.putExtra("LNG", bn.getLongitude()); // Room code will be blank (not null) if field is not filled in.
		i.putExtra("CURRENTLAT", currentLat);
		i.putExtra("CURRENTLNG", currentLng);

		startActivity(i);
	}

	private Buildings getBuildingFromNameOrRoomCode(String bname, String rcode) {
		
		Buildings bn = null;

		if (!bname.isEmpty()) {

			bn = findBuildingFromName(bname);
			return bn;
		}

		else if (!rcode.isEmpty()) {

			bn = findBuildingFromRoomCode(rcode);
			return bn;
		}
		return null;
	}
	
	private Buildings findBuildingFromName (String name)
	{	
		//Log.d("Devon", "Building Name is: " + name);
		
		for(Buildings bldg : Menu.myBuildings)
		{
			String bname = bldg.getBuildingName();
			
			//Log.d("Devon", "bname in findLocationFromName is: " + bname);
			
			if(bname.equalsIgnoreCase(name))
				return bldg;
		}
		
		return null;
	}
	
	private Buildings findBuildingFromRoomCode (String name) {
		
		//Log.d("Devon", "Room code is: " + name);
		
		for(Buildings bldg : Menu.myBuildings)
		{
			String rcode = bldg.getRoomCodes();
			
			//Log.d("Devon", "rcode in findLocationFromRoomCode is: " + rcode);
			
			if(rcode.contains(name))
				return bldg;
		}
		
		return null;
	}


}
