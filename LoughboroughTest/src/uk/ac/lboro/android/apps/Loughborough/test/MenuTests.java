package uk.ac.lboro.android.apps.Loughborough.test;

import java.util.List;

import uk.ac.lboro.android.apps.Loughborough.R;
import uk.ac.lboro.android.apps.Loughborough.Buildings.BuildingSearch;
import uk.ac.lboro.android.apps.Loughborough.Buildings.Buildings;
import uk.ac.lboro.android.apps.Loughborough.Navigation.Navigation;
import uk.ac.lboro.android.apps.Loughborough.Other.Features;
import uk.ac.lboro.android.apps.Loughborough.Other.NormalWebview;
import uk.ac.lboro.android.apps.Loughborough.Other.SafetyToolbox;
import uk.ac.lboro.android.apps.Loughborough.Staff.Staff;
import uk.ac.lboro.android.apps.Loughborough.Staff.StaffSearch;
import uk.ac.lboro.android.apps.Loughborough.Ui.Menu;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

// The following code is based on code found here:
// http://www.vogella.com/tutorials/AndroidTesting/article.html
public class MenuTests extends ActivityInstrumentationTestCase2<Menu> {
	
	private Menu myMenu;
	private GridView myGridView;
	private ImageView myImageView;
	private static List<Buildings> myBuildingsArray;
	private static List<Staff> myStaffArray;
	private static List<Features> myFeaturesArray;

	public MenuTests() {
		super(Menu.class);
	}

	@Override
	protected void setUp() throws Exception {
		
		super.setUp();
		setActivityInitialTouchMode(false);
		myMenu = getActivity();
		myGridView = (GridView) myMenu.findViewById(R.id.gridview);
		myImageView = (ImageView) myMenu.findViewById(R.id.imageView1);
		myBuildingsArray = Menu.myBuildings;
		myStaffArray = Menu.myStaff;
		myFeaturesArray = Menu.myFeatures;
	}
	
	public void testIconsExist() {
		
		assertNotNull(myGridView);
		assertNotNull(myImageView);
	}
	
	public void testXMLParsing() {
		
		assertNotNull(myBuildingsArray);
		assertNotNull(myStaffArray);
		assertNotNull(myFeaturesArray);
	}
	
	
	public void testNavigationIntentTriggerViaOnClick() throws Exception {
		
		// Launching Navigation Activity
		
		// add monitor to check for the second activity
	    ActivityMonitor monitor = getInstrumentation().addMonitor(Navigation.class.getName(), null, false);
	    
	    // TouchUtils handles the sync with the main thread internally
	    TouchUtils.clickView(this, myGridView.getChildAt(0));

	    // wait 2 seconds for the start of the activity
	    Navigation startedActivity = (Navigation) monitor.waitForActivityWithTimeout(2000);
	    assertNotNull(startedActivity);

	    // search for the textView
	    TextView textView = (TextView) startedActivity.findViewById(R.id.textViewTitle);
	    
	    // check that the TextView is on the screen and is correct
	    ViewAsserts.assertOnScreen(startedActivity.getWindow().getDecorView(), textView);
	    assertEquals("Incorrect text of the button", "Navigation", textView.getText().toString());
	    
	    startedActivity.finish();
	}
	
	public void testBuildingSearchIntentTriggerViaOnClick() throws Exception {
		
		// Launching Building Search Activity
		
		// add monitor to check for the second activity
	    ActivityMonitor monitor = getInstrumentation().addMonitor(BuildingSearch.class.getName(), null, false);
	    
	    // TouchUtils handles the sync with the main thread internally
	    TouchUtils.clickView(this, myGridView.getChildAt(1));

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
	
	public void testStaffSearchIntentTriggerViaOnClick() throws Exception {
		
		// Launching Staff Search Activity
		
		// add monitor to check for the second activity
	    ActivityMonitor monitor = getInstrumentation().addMonitor(StaffSearch.class.getName(), null, false);
	    
	    // TouchUtils handles the sync with the main thread internally
	    TouchUtils.clickView(this, myGridView.getChildAt(12));

	    // wait 2 seconds for the start of the activity
	    StaffSearch startedActivity = (StaffSearch) monitor.waitForActivityWithTimeout(2000);
	    assertNotNull(startedActivity); // Phone has to be unlocked for this to work.

	    // search for the textView
	    TextView textView = (TextView) startedActivity.findViewById(R.id.textViewTitle);
	    
	    // check that the TextView is on the screen and is correct
	    ViewAsserts.assertOnScreen(startedActivity.getWindow().getDecorView(), textView);
	    assertEquals("Incorrect text of the button", "Staff Search", textView.getText().toString());
	    
	    startedActivity.finish();
	}
	
	public void testSafetyToolboxIntentTriggerViaOnClick() throws Exception {
		
		// Launching Safety Toolbox Activity
		
		// add monitor to check for the second activity
	    ActivityMonitor monitor = getInstrumentation().addMonitor(SafetyToolbox.class.getName(), null, false);
	    
	    // TouchUtils handles the sync with the main thread internally
	    TouchUtils.clickView(this, myGridView.getChildAt(14));

	    // wait 2 seconds for the start of the activity
	    SafetyToolbox startedActivity = (SafetyToolbox) monitor.waitForActivityWithTimeout(2000);
	    assertNotNull(startedActivity); // Phone has to be unlocked for this to work.

	    // search for the textView
	    TextView textView = (TextView) startedActivity.findViewById(R.id.textViewTitle);
	    
	    // check that the TextView is on the screen and is correct
	    ViewAsserts.assertOnScreen(startedActivity.getWindow().getDecorView(), textView);
	    assertEquals("Incorrect text of the button", "Safety Toolbox", textView.getText().toString());
	    
	    startedActivity.finish();
	}
	
	public void testCaspaIntentTriggerViaOnClick() throws Exception {
		
		// Launching Normal Webview Activity
		
		// add monitor to check for the second activity
	    ActivityMonitor monitor = getInstrumentation().addMonitor(NormalWebview.class.getName(), null, false);
	    
	    // TouchUtils handles the sync with the main thread internally
	    TouchUtils.clickView(this, myGridView.getChildAt(3));

	    // wait 2 seconds for the start of the activity
	    NormalWebview startedActivity = (NormalWebview) monitor.waitForActivityWithTimeout(2000);
	    assertNotNull(startedActivity); // Phone has to be unlocked for this to work.

	    // search for the textView
	    TextView textView = (TextView) startedActivity.findViewById(R.id.textViewTitle);
	    
	    // check that the TextView is on the screen and is correct
	    ViewAsserts.assertOnScreen(startedActivity.getWindow().getDecorView(), textView);
	    assertEquals("Incorrect text of the button", "Caspa", textView.getText().toString());
	    
	    startedActivity.finish();
	}
		
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}