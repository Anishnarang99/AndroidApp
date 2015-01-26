package com.example.gmaps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

// NEED TO RENAME THIS CLASS AND EVERYTHING RELATED TO THIS TO --> ROUTE DIRECTIONS??
public class Other extends Activity {

	String lat, lng, currentLat, currentLng;
	WebView ourBrowser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Make the activity fullscreen - Make sure you set content view after making full screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.other);

		Intent i = getIntent();
		lat = i.getStringExtra("LAT");
		lng = i.getStringExtra("LNG");
		currentLat = i.getStringExtra("CURRENTLAT");
		currentLng = i.getStringExtra("CURRENTLNG");

		Log.d("Devon", "Dest lat is: " + lat + " Dest lng is: " + lng);
		Log.d("Devon", "Current Lat: " + currentLat + " Current Lng: " + currentLng);

		embedApi();
	}

	public void embedApi() {

		int iframeWidth, iframeHeight;
		String originLat, originLng, destLat, destLng;

		ourBrowser = (WebView) findViewById(R.id.WebViewBrowser);
		ourBrowser.getSettings().setJavaScriptEnabled(true);
		// webView.loadUrl("http://www.google.co.uk");
		// webView.loadUrl("file:///android_asset/embedapi.html");

		iframeWidth = 283;
		iframeHeight = 480;
		// originLat = 52.762859; // For testing while not at Lboro
		// originLng = -1.240977; // For testing while not at Lboro
		originLat = currentLat;
		originLng = currentLng;
		destLat = lat;
		destLng = lng;
		Log.d("Devon", "Show the route directions!");

		String embedAPI = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"><html><body><html><body>"
				+ "<iframe width=\""
				+ iframeWidth
				+ "\" height=\""
				+ iframeHeight
				+ "\" frameborder=\"0\" style=\"border:0\""
				+ "src=\"https://www.google.com/maps/embed/v1/directions?key=AIzaSyC-n41tpUZ1RmXCVrp2CKwr9YQmYdjZQW0&origin="
				+ originLat
				+ ","
				+ originLng
				+ "&destination="
				+ destLat
				+ ","
				+ destLng
				+ "&mode=walking&units=metric\">"
				+ "</iframe></body></html>";

		ourBrowser.loadData(embedAPI, "text/html", "UTF-8");
	}
}