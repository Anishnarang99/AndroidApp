package uk.ac.lboro.android.apps.Loughborough.test;

import uk.ac.lboro.android.apps.Loughborough.R;
import uk.ac.lboro.android.apps.Loughborough.Other.SafetyToolbox;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.TextView;

// The following code is based on code found here:
// http://www.vogella.com/tutorials/AndroidTesting/article.html
public class SafetyToolboxTests extends ActivityInstrumentationTestCase2<SafetyToolbox> {
	
	private SafetyToolbox mySafetyToolbox;
	private TextView myTextHeading;
	private Button myButtonFlashlight, myButtonCallSecurity, myButtonCallNHS;

	public SafetyToolboxTests() {
		super(SafetyToolbox.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		mySafetyToolbox = getActivity();
		myTextHeading = (TextView) mySafetyToolbox.findViewById(R.id.textViewTitle);
		myButtonFlashlight = (Button) mySafetyToolbox.findViewById(R.id.buttonFlashlight);
		myButtonCallSecurity = (Button) mySafetyToolbox.findViewById(R.id.buttonCallSecurity);
		myButtonCallNHS = (Button) mySafetyToolbox.findViewById(R.id.buttonCallNHSDirect);
	}
	

	public void testTextViewHeading() {
		
	    final String actual = myTextHeading.getText().toString();
	    // Log.d("Devon", "String actual: " + actual);
	    final String expected = "Safety Toolbox";
	    // Log.d("Devon", "String expected: " + expected);
	    assertEquals("Incorrect heading of the activity", expected, actual);
	}
	
	public void testButtonFlashlight() {
		
	    final String actual = myButtonFlashlight.getText().toString();
	    // Log.d("Devon", "String actual: " + actual);
	    final String expected = "Flashlight";
	    // Log.d("Devon", "String expected: " + expected);
	    assertEquals("Incorrect text of the button", expected, actual);
	    assertNotNull("Button not allowed to be null", myButtonFlashlight);
	}
	
	public void testButtonCallSecurity() {
		
	    final String actual = myButtonCallSecurity.getText().toString();
	    // Log.d("Devon", "String actual: " + actual);
	    final String expected = "Call Security";
	    // Log.d("Devon", "String expected: " + expected);
	    assertEquals("Incorrect text of the button", expected, actual);
	    assertNotNull("Button not allowed to be null", myButtonCallSecurity);
	}
	
	public void testButtonCallNHS() {
		
	    final String actual = myButtonCallNHS.getText().toString();
	 	// Log.d("Devon", "String actual: " + actual);
	    final String expected = "Call NHS Direct";
		// Log.d("Devon", "String expected: " + expected);
	    assertEquals("Incorrect text of the button", expected, actual);
	    assertNotNull("Button not allowed to be null", myButtonCallNHS);
	}
	
	public void testFlashLightOn() {
		
		TouchUtils.clickView(this, myButtonFlashlight);
		boolean flashOn = mySafetyToolbox.isFlashOn;
		assertTrue(flashOn);
	}
		
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}