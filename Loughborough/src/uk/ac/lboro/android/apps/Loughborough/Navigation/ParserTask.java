package uk.ac.lboro.android.apps.Loughborough.Navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

// The following code is based on code found here:
// http://wptrafficanalyzer.in/blog/drawing-driving-route-directions-between-two-locations-using-google-directions-in-google-map-android-api-v2/
/** A class to parse the Google Places in JSON format */

class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
	
	GoogleMap gMap;
	
    public ParserTask(GoogleMap map) {
    	gMap = map;
	}

	// Parsing the data in non-UI thread
    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

    JSONObject jObject;
    List<List<HashMap<String, String>>> routes = null;

        try {
        	
            jObject = new JSONObject(jsonData[0]);
            DirectionsJSONParser parser = new DirectionsJSONParser();

            // Starts parsing data
            routes = parser.parse(jObject);
        } catch(Exception e) {
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

        // Drawing polyline route on the map.
        gMap.addPolyline(lineOptions);
     }
}
