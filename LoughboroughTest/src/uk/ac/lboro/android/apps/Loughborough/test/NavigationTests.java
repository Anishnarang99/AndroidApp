package uk.ac.lboro.android.apps.Loughborough.test;

import uk.ac.lboro.android.apps.Loughborough.R;
import uk.ac.lboro.android.apps.Loughborough.Navigation.Navigation;
import uk.ac.lboro.android.apps.Loughborough.Buildings.BuildingSearch;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

// The following code is based on code found here:
// http://www.vogella.com/tutorials/AndroidTesting/article.html
public class NavigationTests extends ActivityInstrumentationTestCase2<Navigation> {
	
	private Navigation myNavigation;
	private TextView myTextHeading;
	private Button myButtonUni, myButtonFindMe, myButtonBuildingSearch;
	private GoogleMap map;

	public NavigationTests() {
		super(Navigation.class);
	}

	@Override
	protected void setUp() throws Exception {
		
		super.setUp();
		setActivityInitialTouchMode(false);
		myNavigation = getActivity();
		myTextHeading = (TextView) myNavigation.findViewById(R.id.textViewTitle);
		myButtonUni = (Button) myNavigation.findViewById(R.id.buttonUni);
		myButtonFindMe = (Button) myNavigation.findViewById(R.id.buttonFindMe);
		myButtonBuildingSearch = (Button) myNavigation.findViewById(R.id.buttonBuildingSearch);
		map = ((MapFragment) myNavigation.getFragmentManager().findFragmentById(R.id.map)).getMap();
	}
	

	public void testTextViewHeading() {
		
	    final String actual = myTextHeading.getText().toString();
	    // Log.d("Devon", "String actual: " + actual);
	    final String expected = "Navigation";
	    // Log.d("Devon", "String expected: " + expected);
	    assertEquals("Incorrect heading of the activity", expected, actual);
	}
	
	public void testButtonUni() {
		
	    final String actual = myButtonUni.getText().toString();
	    // Log.d("Devon", "String actual: " + actual);
	    final String expected = "Uni";
	    // Log.d("Devon", "String expected: " + expected);
	    assertEquals("Incorrect text of the button", expected, actual);
	    assertNotNull("Button not allowed to be null", myButtonUni);
	}
	
	public void testButtonFindMe() {
		
	    final String actual = myButtonFindMe.getText().toString();
	    // Log.d("Devon", "String actual: " + actual);
	    final String expected = "Find Me";
	    // Log.d("Devon", "String expected: " + expected);
	    assertEquals("Incorrect text of the button", expected, actual);
	    assertNotNull("Button not allowed to be null", myButtonFindMe);
	}
	
	public void testButtonBuildingSearch() {
		
	    final String actual = myButtonBuildingSearch.getText().toString();
	 	// Log.d("Devon", "String actual: " + actual);
	    final String expected = "Building Search";
		// Log.d("Devon", "String expected: " + expected);
	    assertEquals("Incorrect text of the button", expected, actual);
	    assertNotNull("Button not allowed to be null", myButtonBuildingSearch);
	}
	
	public void testGoogleMap() {
		
		Log.d("Devon", "Googlemap expected: " + map);
		assertNotNull(map);
	}
	
	public void testBuildSearchIntentTriggerViaOnClick() throws Exception {

		// add monitor to check for the second activity
	    ActivityMonitor monitor = getInstrumentation().addMonitor(BuildingSearch.class.getName(), null, false);
	    
	    // TouchUtils handles the sync with the main thread internally
	    TouchUtils.clickView(this, myButtonBuildingSearch);

	    // wait 2 seconds for the start of the activity
	    BuildingSearch startedActivity = (BuildingSearch) monitor.waitForActivityWithTimeout(2000);
	    assertNotNull(startedActivity); // Phone has to be unlocked for this to work.

	    // search for the textView
	    TextView textView = (TextView) startedActivity.findViewById(R.id.textViewTitle);
	    
	    // check that the TextView is on the screen and is correct
	    ViewAsserts.assertOnScreen(startedActivity.getWindow().getDecorView(), textView);
	    assertEquals("Incorrect text of the button", "Building Search", textView.getText().toString());
	    
	    startedActivity.finish();
	}
		
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}