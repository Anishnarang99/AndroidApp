package uk.ac.lboro.android.apps.Loughborough.test;

import uk.ac.lboro.android.apps.Loughborough.R;
import uk.ac.lboro.android.apps.Loughborough.Staff.StaffSearch;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

// The following code is based on code found here:
// http://www.vogella.com/tutorials/AndroidTesting/article.html
public class StaffSearchTests extends ActivityInstrumentationTestCase2<StaffSearch> {
	
	private StaffSearch myStaffSearch;
	private TextView myTextHeading;
	private AutoCompleteTextView myAutoCompTextView;


	public StaffSearchTests() {
		super(StaffSearch.class);
	}

	@Override
	protected void setUp() throws Exception {
		
		super.setUp();
		myStaffSearch = getActivity();
		myTextHeading = (TextView) myStaffSearch.findViewById(R.id.textViewTitle);
		myAutoCompTextView = (AutoCompleteTextView) myStaffSearch.findViewById(R.id.autoCompleteTextViewStaffName);
	}

	public void testTextViewHeading() {
		
	    final String actual = myTextHeading.getText().toString();
	    // Log.d("Devon", "String actual: " + actual);
	    final String expected = "Staff Search";
	    // Log.d("Devon", "String expected: " + expected);
	    assertEquals("Incorrect heading of the activity", expected, actual);
	}
	
	public void testAutoCompStaffName() {
		
		// Need to set the textview text in another thread and then sync with the app.
		myStaffSearch.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				myAutoCompTextView.setText("Simon Pomeroy");
			}
		});
		
		getInstrumentation().waitForIdleSync();

		final String actual = myAutoCompTextView.getText().toString();
		// Log.d("Devon", "String auto comp text view actual: " + actual);
		assertEquals("Incorrect text in field", "Simon Pomeroy", actual);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}