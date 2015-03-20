package uk.ac.lboro.android.apps.Loughborough.Ui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.android.gms.maps.CameraUpdateFactory;

import uk.ac.lboro.android.apps.Loughborough.R;
import uk.ac.lboro.android.apps.Loughborough.Buildings.Buildings;
import uk.ac.lboro.android.apps.Loughborough.Other.Features;
import uk.ac.lboro.android.apps.Loughborough.Staff.Staff;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
	
	String classes[] = {"Navigation.Navigation", "Buildings.BuildingSearch", "Learn", "WebView Caspa", "WebView Timetable",
						"WebView Main Website", "WebView Lsu Website", "WebView Bus Travel Info", "WebView Email",
						"WebView News", "WebView Events", "Library", "Staff.StaffSearch", "WebView PC Lab Availability", "Other.SafetyToolbox"};
	
	public static List<Buildings> myBuildings = new ArrayList<Buildings>();
	public static List<Staff> myStaff = new ArrayList<Staff>();
	public static List<Features> myFeatures = new ArrayList<Features>();
	
	Map<String, String> myDictionary = new HashMap<String, String>();
	
	// Variables to get current location
	static Location mylocation;
	LocationManager lm;
	LocationListener myListener;
	GridView gridview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		// Makes it fullscreen - Make sure you set content view after making it full screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.grid_menu);
		
	    getCurrentLocation(); // Gets current location of phone to determine route directions. 
	    
	    // Only parses the XML files if the Lists are not already populated.
	    if (myFeatures.isEmpty())
	    	FeaturesXML(); // Reading XML file containing Loughborough features (Learn, Caspa, Timetable, etc).
	    
	    if (myBuildings.isEmpty())
	    	BuildingsXML(); // Reading XML file containing Loughborough building names.
	    
	    if (myStaff.isEmpty())
	    	StaffSearchXML(); // Reading XML file containing Loughborough EESE staff names.
	    
	    extractFeaturesInfo();
		
	    gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(this));	
		
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	    	
	        @Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

	            String appActivity = classes[position];
	    		
	    		try {
	    			
	    			if (appActivity == "Learn") {
	    				Log.d("Devon", "Loading LEARN in Chrome.");
	    				String urlString ="http://learn.lboro.ac.uk/my";
	    				loadinChrome(urlString);
	    			}
	    			
	    			else if (appActivity == "Library") {
	    				Log.d("Devon", "Loading Library in Chrome.");
	    				String urlString ="http://lb-primo.hosted.exlibrisgroup.com/primo_library/libweb/action/search.do";
	    				loadinChrome(urlString);
	    			}
	    			
	    			else if (appActivity.startsWith("WebView")) {
	    				Log.d("Devon", "Loading activity in a normal webview.");
	    				
	    				Log.d("Devon", "Activity: " + appActivity);
	    				
	    				String featname = appActivity.substring(8);
	    				Log.d("Devon", "featname: " + featname);
	    				
	    				String weblink = myDictionary.get(featname);
	    				Log.d("Devon", "Activity weblink: " + weblink);
	    				
	    				loadNormalWebview(appActivity, featname, weblink);
	    			}
	    			
	    			else {
	    			
			    		Class ourClass = Class.forName("uk.ac.lboro.android.apps.Loughborough." + appActivity);
			    		Log.d("Devon", "Class is: uk.ac.lboro.android.apps.Loughborough." + appActivity);
			    		
			    		Intent ourIntent = new Intent(Menu.this, ourClass);
			    		startActivity(ourIntent);
	    			}
	    		}
	    		
	    		catch (ClassNotFoundException e) {
	    			
	    			e.printStackTrace();
	    		}
	        }
	    });
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
		
		int id = item.getItemId();
		
		if (id == R.id.aboutUs) {

			aboutUsInfo();
		}
		
		return false;
	}
	
	private void extractFeaturesInfo() {
		
		// Putting features in a dictionary.
		for (Features feat: myFeatures) {
			String fname = feat.getFeatureName();
			String flink = feat.getWebsiteLink();
			myDictionary.put(fname,flink);
		}
		
		// Log.d("Devon", "Feature myDictionary: " + myDictionary);
	}
	
	private void loadinChrome(String urlString) {

		Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(urlString));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setPackage("com.android.chrome");
		
		try {
		    startActivity(intent);
		} catch (ActivityNotFoundException ex) {
			
		    // Chrome browser presumably not installed so allow user to choose instead
		    intent.setPackage(null);
		    startActivity(intent);
		}
	}
	
	private void loadNormalWebview(String appActivity, String featname, String weblink) {
		
		Intent i = new Intent("uk.ac.lboro.android.apps.Loughborough.Other.NORMALWEBVIEW");
		i.putExtra("FeatName", featname);
		i.putExtra("WebLink", weblink);
		startActivity(i);
	}
	
	// Loads the features (Learn, Caspa) via an XML file.
	private void FeaturesXML() {
		
		try {

			// Take xml file via input stream from the assets folder.
			InputStream inputStream = getApplicationContext().getAssets().open("features.xml");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputStream);

			doc.getDocumentElement().normalize();

			//Log.d("Devon", "Root element: " + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("feature");

			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i);

				//Log.d("Devon", "Current Element: " + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					// Log.d("Devon","Id : " + eElement.getAttribute("id"));
					String FeatureName = eElement.getElementsByTagName("name").item(0).getTextContent();
					// Log.d("Devon", "Feature Name: " + FeatureName);
					
					String WebLink = eElement.getElementsByTagName("weblink").item(0).getTextContent();
					// Log.d("Devon", "Weblink: " + WebLink);
					
					// Creates an object
					Features features = new Features(FeatureName, WebLink);
					myFeatures.add(features);
					
					//Log.d("Devon", "Added feature: " + features.getFeatureName());
				}
			}
		}

		catch (Exception e) {
			Log.d("Devon", "Parsing Features XML file failed. Check the log.");
			e.printStackTrace();
		}
	}
	
	// Loads the data about all the buildings via an XML file.
	private void BuildingsXML() {

		try {
			
			// Take xml file via input stream from the assets folder.
			InputStream inputStream = getApplicationContext().getAssets().open("buildings.xml");
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputStream);

			doc.getDocumentElement().normalize();
			  
			//Log.d("Devon", "Root element: " + doc.getDocumentElement().getNodeName());
			  
			NodeList nList = doc.getElementsByTagName("building");
			  
			for (int i = 0; i < nList.getLength(); i++) {
			  
				Node nNode = nList.item(i);
			  
				//Log.d("Devon", "Current Element: " + nNode.getNodeName());
			  
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			  
					Element eElement = (Element) nNode;
			  
					// Log.d("Devon","Id : " + eElement.getAttribute("id"));
					String BuildingName = eElement.getElementsByTagName("name").item(0).getTextContent();
					//Log.d("Devon", "Building Name: " + BuildingName);
					String BuildingLat = eElement.getElementsByTagName("latitude").item(0).getTextContent();
					//Log.d("Devon", "Latitude: " + BuildingLat);
					String BuildingLng = eElement.getElementsByTagName("longitude").item(0).getTextContent();
					//Log.d("Devon", "Longitude: " + BuildingLng);
					String RoomCodes = eElement.getElementsByTagName("roomcodes").item(0).getTextContent();
					//Log.d("Devon", "Room codes: " + RoomCodes);
					
					// Creates an object
					Buildings building = new Buildings(BuildingName, RoomCodes, BuildingLat, BuildingLng);
					myBuildings.add(building);
					//Log.d("Devon", "Added building object called: " + building.getBuildingName());
				}
			}
		}
		
		catch (Exception e) {
				Log.d("Devon", "Parsing Building XML file failed. Check the log.");
				e.printStackTrace();
			    }
	}	
	
	// Loads the staff data via an XML file.
	private void StaffSearchXML() {

		try {

			// Take xml file via input stream from the assets folder.
			InputStream inputStream = getApplicationContext().getAssets().open("staff.xml");
			

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputStream);

			doc.getDocumentElement().normalize();

			//Log.d("Devon", "Root element: " + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("person");

			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i);

				//Log.d("Devon", "Current Element: " + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					// Log.d("Devon","Id : " + eElement.getAttribute("id"));
					String PersonName = eElement.getElementsByTagName("name").item(0).getTextContent();
					//Log.d("Devon", "Person Name: " + PersonName);
					
					String Dept = eElement.getElementsByTagName("department").item(0).getTextContent();
					//Log.d("Devon", "Department: " + Dept);
					
					String Email = eElement.getElementsByTagName("email").item(0).getTextContent();
					//Log.d("Devon", "Email: " + Email);
					
					String Extension = eElement.getElementsByTagName("extension").item(0).getTextContent();
					//Log.d("Devon", "Extension: " + Extension);
					
					// Creates an object
					Staff staff = new Staff(PersonName, Dept, Email, Extension);
					myStaff.add(staff);
					
					//Log.d("Devon", "Added staff: " + staff.getStaffName());
				}
			}
		}

		catch (Exception e) {
			Log.d("Devon", "Parsing Staff XML file failed. Check the log.");
			e.printStackTrace();
		}
	}

	public void getCurrentLocation() {

		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		myListener = new LocationListener() {
			
			@Override
			public void onLocationChanged(Location location) {

				mylocation = location;
				Log.d("Devon", "Location Listener retrieved: " + location);
				
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
		
		// Finds location using Wifi or GPS
		if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true) {
			
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myListener);
			Log.d ("Devon", "Location listener has been activated via Network (Wifi).");
		}
		else if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {
			
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myListener);
			Log.d ("Devon", "Location listener has been activated via GPS.");
		}
		else {
			
			Log.d ("Devon", "Cannot retrieve current location, please enable location and re-start the app.");
			Toast.makeText(this, "Cannot retrieve current location, please enable location and restart the app.", Toast.LENGTH_LONG).show();
		}
	}
	
	public static Location getLocation() {
		
		return mylocation;
	}
	
	public void onClick_AboutUs(View v) {

		aboutUsInfo();
	}
	
	private void aboutUsInfo() {
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		 
		// set title
		alertDialogBuilder.setTitle("About Us");
		
		String message = "This is an app to help students with their day to day life at Loughborough University." +
				" This was created as a final year project by Devon Kerai." +
				" If you have any queries, please email me at: d.kerai-11@student.lboro.ac.uk";
		
		// set dialog message
		alertDialogBuilder.setMessage(message).setCancelable(false)
			
		.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// Close the dialog box
				dialog.cancel();
				}
			});
 
			AlertDialog alertDialog = alertDialogBuilder.create();
 
			alertDialog.show();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        moveTaskToBack(true);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}