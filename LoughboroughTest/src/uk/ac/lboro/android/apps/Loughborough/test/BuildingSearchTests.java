package uk.ac.lboro.android.apps.Loughborough.test;

import uk.ac.lboro.android.apps.Loughborough.R;
import uk.ac.lboro.android.apps.Loughborough.Buildings.BuildingSearch;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

// The following code is based on code found here:
// http://www.vogella.com/tutorials/AndroidTesting/article.html
public class BuildingSearchTests extends ActivityInstrumentationTestCase2<BuildingSearch> {
	
	private BuildingSearch myBuildSearch;
	private TextView myTextHeading;
	private AutoCompleteTextView myAutoCompTextView;


	public BuildingSearchTests() {
		super(BuildingSearch.class);
	}

	@Override
	protected void setUp() throws Exception {
		
		super.setUp();
		myBuildSearch = getActivity();
		myTextHeading = (TextView) myBuildSearch.findViewById(R.id.textViewTitle);
		myAutoCompTextView = (AutoCompleteTextView) myBuildSearch.findViewById(R.id.autoCompleteTextViewBuildingName);
	}

	public void testTextViewHeading() {
		
	    final String actual = myTextHeading.getText().toString();
	    // Log.d("Devon", "String actual: " + actual);
	    final String expected = "Building Search";
	    // Log.d("Devon", "String expected: " + expected);
	    assertEquals("Incorrect heading of the activity", expected, actual);
	}
	
	public void testAutoCompBuildingName() {
		
		// Need to set the textview text in another thread and then sync with the app.
		myBuildSearch.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				myAutoCompTextView.setText("James France Building");
			}
		});
		
		getInstrumentation().waitForIdleSync();

		final String actual = myAutoCompTextView.getText().toString();
		Log.d("Devon", "String auto comp text view actual: " + actual);
		assertEquals("Incorrect text in field", "James France Building", actual);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}