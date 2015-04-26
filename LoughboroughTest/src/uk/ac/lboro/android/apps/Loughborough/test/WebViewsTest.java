package uk.ac.lboro.android.apps.Loughborough.test;

import uk.ac.lboro.android.apps.Loughborough.R;
import uk.ac.lboro.android.apps.Loughborough.Other.NormalWebview;
import uk.ac.lboro.android.apps.Loughborough.Ui.Menu;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.widget.GridView;
import android.widget.TextView;

// The following code is based on code found here:
// http://www.vogella.com/tutorials/AndroidTesting/article.html
public class WebViewsTest extends ActivityInstrumentationTestCase2<Menu> {
	
	private Menu myMenu;
	private GridView myGridView;

	public WebViewsTest() {
		super(Menu.class);
	}

	@Override
	protected void setUp() throws Exception {
		
		super.setUp();
		setActivityInitialTouchMode(false);
		myMenu = getActivity();
		myGridView = (GridView) myMenu.findViewById(R.id.gridview);
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