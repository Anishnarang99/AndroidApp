package uk.ac.lboro.android.apps.Loughborough.Staff;

import java.util.ArrayList;
import java.util.List;

import uk.ac.lboro.android.apps.Loughborough.R;
import uk.ac.lboro.android.apps.Loughborough.Ui.Menu;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

public class StaffSearch extends Activity {
	
	AutoCompleteTextView autoCompTextStaff;
	TextView textView, staffName, staffDept, staffEmail, staffExt;
	String textViewName, sStaffName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Makes it fullscreen - Make sure you set content view after making it full screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.staffsearch);

		initialiseVariables();
	}
	
	// Initialising variables
	private void initialiseVariables() {
		
		textView = (TextView) findViewById(R.id.textViewTitle);
		staffName = (TextView) findViewById(R.id.textViewStaffName);
		staffDept = (TextView) findViewById(R.id.textViewStaffDept);
		staffEmail = (TextView) findViewById(R.id.textViewStaffEmail);
		staffExt = (TextView) findViewById(R.id.textViewStaffExtension);
		autoCompTextStaff = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewStaffName);
		
		// Set up the heading of the activity
		textViewName = "Staff Search";
		textView.setText(textViewName);
		textView.setTextSize(16);
		textView.setTextColor(Color.parseColor("#C70066"));
		textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
		
		// Build string array of buildings from mybuildings list
		List<String> staffNames = new ArrayList<String>();
		for (Staff stf : Menu.myStaff) {
			String stname = stf.getStaffName();
			staffNames.add(stname);
		}

		// Create the adapter and set it to the AutoCompleteTextView
		ArrayAdapter<String> adapterStaffName = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, staffNames);
		autoCompTextStaff.setAdapter(adapterStaffName);
		autoCompTextStaff.setThreshold(1);
	}
	
	// Pressing the 'Find Staff' button.
	public void onClick_FindStaff(View v) {

		// Displays the staff's information below.
		sStaffName = autoCompTextStaff.getText().toString();

		Staff stf = getStaffFromName(sStaffName);

		if (stf == null) {

			Toast.makeText(this, "Cannot find the staff member.", Toast.LENGTH_LONG).show();
			Log.d("Devon", "Cannot find the staff member in list.");
			return;
		}
		
		// Updates the text views on the screen.
		staffName.setText("Name: " + stf.name);
		staffDept.setText("Department: " + stf.dept);
		staffEmail.setText("Email: " + stf.email);
		staffExt.setText("Extension: " + stf.extension);
	}
	
	private Staff getStaffFromName(String sname) {
		
		Staff stf = null;

		if (!sname.isEmpty()) {

			stf = findStaffFromName(sname);
			return stf;
		}
		
		return null;
	}
	
	// Determines whether the staff name entered exist, if it is, return the Staff object.
	private Staff findStaffFromName (String name)
	{	
		//Log.d("Devon", "Staff Name is: " + name);
		
		for(Staff staff : Menu.myStaff)
		{
			String sname = staff.getStaffName();
			
			//Log.d("Devon", "sname in findStaffFromName is: " + sname);
			
			if(sname.equalsIgnoreCase(name))
				return staff;
		}
		
		return null;
	}
	
	// Goes back to the menu screen when the back button is pressed.
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
