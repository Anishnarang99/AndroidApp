package com.example.gmaps;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;


public class Menu extends Activity {

	// Has to have the EXACT same name as the corresponding classes.
	String classes[] = {"Gmaps", "BuildingSearch", "Learn", "Caspa", "Timetable",
						"MainWebsite", "LsuWebsite", "BusTravelInfo", "Email",
						"News", "Events", "Library", "StaffSearch", "PcLabs", "SafetyToolbox"};
	
	public static List<Buildings> myBuildings = new ArrayList<Buildings>();
	
	// Variables to get current location
	static Location mylocation;
	LocationManager lm;
	LocationListener myListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		// Makes it fullscreen - Make sure you set content view after making it full screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.grid_menu);
		
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(this));	
		
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

	            String appActivity = classes[position];
	    		
	    		try {
	    			
	    		Class ourClass = Class.forName("com.example.gmaps." + appActivity);
	    		Log.d("Devon", "Class is: com.example.gmaps." + appActivity);
	    		
	    		Intent ourIntent = new Intent(Menu.this, ourClass);
	    		startActivity(ourIntent);
	    		}
	    		
	    		catch (ClassNotFoundException e) {
	    			
	    			e.printStackTrace();
	    		}
	        }
	    });
	    
		BuildingsXML(); // Reading XML file containing Loughborough building names.
		
		getCurrentLocation(); // Gets current location of phone to determine route directions.
	}
	
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
			Intent i = new Intent("com.example.gmaps.ABOUTUS");
			startActivity(i);
			break;
			
		case R.id.exit:
			finish();
			break;
		}
		
		return false;
	}
	
	// Loads the data about all the buildings from an XML file.
	public void BuildingsXML() {

		try {
			
			// Take xml file via input stream from the assets folder.
			InputStream in_s = getApplicationContext().getAssets().open("lborofull.xml");
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(in_s);


			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work

			doc.getDocumentElement().normalize();
			  
			Log.d("Devon", "Root element: " + doc.getDocumentElement().getNodeName());
			  
			NodeList nList = doc.getElementsByTagName("building");
			  
			for (int i = 0; i < nList.getLength(); i++) {
			  
				Node nNode = nList.item(i);
			  
				Log.d("Devon", "Current Element: " + nNode.getNodeName());
			  
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			  
					Element eElement = (Element) nNode;
			  
					// Log.d("Devon","Id : " + eElement.getAttribute("id"));
					String BuildingName = eElement.getElementsByTagName("name").item(0).getTextContent();
					Log.d("Devon", "Building Name: " + BuildingName);
					String BuildingLat = eElement.getElementsByTagName("latitude").item(0).getTextContent();
					Log.d("Devon", "Latitude: " + BuildingLat);
					String BuildingLng = eElement.getElementsByTagName("longitude").item(0).getTextContent();
					Log.d("Devon", "Longitude: " + BuildingLng);
					String RoomCodes = eElement.getElementsByTagName("roomcodes").item(0).getTextContent();
					Log.d("Devon", "Room codes: " + RoomCodes);
					
					// Converts the co-ords from strings into longs.
					//long BuildingLat = Long.parseLong(BuildingLatitude);//.longValue();
					//long BuildingLng = Long.parseLong(BuildingLongitude);//.longValue();
					
					// Creates an object
					Buildings building = new Buildings(BuildingName, RoomCodes, BuildingLat, BuildingLng);
					myBuildings.add(building);
					Log.d("Devon", "Added building object called: " + building.getBuildingName());
				}
			}
		}
		
		catch (Exception e) {
				Log.d("Devon", "In the log");
				e.printStackTrace();
			    }
	}	
	
	public void getCurrentLocation() {

		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		//Log.d("Devon", "Passive provider enabled: " + lm.isProviderEnabled(LocationManager.PASSIVE_PROVIDER));
		//Log.d("Devon", "GPS provider enabled: " + lm.isProviderEnabled(LocationManager.GPS_PROVIDER));
		//Log.d("Devon", "Network provider enabled: " + lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER));

		myListener = new LocationListener() {
			
			public void onLocationChanged(Location location) {

				mylocation = location;
				Log.d("Devon", "Location Listener retrieved: " + location);
				
				// Write something here to stop listening for the current location.
				lm.removeUpdates(myListener);
				Log.d ("Devon", "Location listener has been de-activated.");
				
			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
			}

			@Override
			public void onProviderEnabled(String provider) {			
			}

			@Override
			public void onProviderDisabled(String provider) {		
			}
		};
		
		// Update this to use the best provider to find location.
		if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true) {
			
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myListener);
			Log.d ("Devon", "Location listener has been activated via Network.");
			Log.d ("Devon", "Location (via Network) is: " + mylocation);
		}
		else if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {
			
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myListener);
			Log.d ("Devon", "Location listener has been activated via GPS.");
			Log.d ("Devon", "Location (via GPS) is: " + mylocation);
		}
		else {
			
			Log.d ("Devon", "Cannot find location. Please enable Wifi or GPS.");
			Toast.makeText(this, "Cannot find location. Please enable Wifi or GPS and then try again.", Toast.LENGTH_LONG).show();
		}
        
	}
	
	public static Location getLocation() {
		
		return mylocation;
	}
	
}