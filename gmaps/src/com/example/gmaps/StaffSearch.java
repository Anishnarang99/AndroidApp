package com.example.gmaps;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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
	
	
	
	AutoCompleteTextView autoCompTextLecturer;
	TextView LectName, LectDept, LectEmail, LectExt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Makes it fullscreen - Make sure you set content view after making it
		// full screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.staffsearch);

		setUpAutoCompleteTextViews();
		setUpTextViews();
	}

	private void setUpAutoCompleteTextViews() {

		autoCompTextLecturer = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewLecturerName);

		// Build string array of buildings from mybuildings list
		List<String> lecturerNames = new ArrayList<String>();
		for (Lecturers lec : Menu.myLecturer) {
			String lectname = lec.getLecturerName();
			lecturerNames.add(lectname);
		}

		// Create the adapter and set it to the AutoCompleteTextView
		ArrayAdapter<String> adapterLecturerName = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lecturerNames);
		autoCompTextLecturer.setAdapter(adapterLecturerName);
		autoCompTextLecturer.setThreshold(1);
	}

	private void setUpTextViews() {

		LectName = (TextView) findViewById(R.id.textViewLecturerName);
		LectDept = (TextView) findViewById(R.id.textViewLecturerDept);
		LectEmail = (TextView) findViewById(R.id.textViewLecturerEmail);
		LectExt = (TextView) findViewById(R.id.textViewLecturerExtension);

	}
	
	public void onClick_FindLecturer(View v) {

		// Displays the lecturer's information below.

		String lecturerName;

		lecturerName = autoCompTextLecturer.getText().toString();

		Lecturers ln = getLecturerFromName(lecturerName);

		if (ln == null) {

			Toast.makeText(this, "Cannot find the lecturer.", Toast.LENGTH_LONG).show();
			Log.d("Devon", "Cannot find the lecturer in list.");
			return;
		}
		
		LectName.setText("Name: " + ln.lecturerName);
		LectDept.setText("Department: " + ln.dept);
		LectEmail.setText("Email: " + ln.email);
		LectExt.setText("Extension: " + ln.extension);
	}
	
	private Lecturers getLecturerFromName(String lname) {
		
		Lecturers ln = null;

		if (!lname.isEmpty()) {

			ln = findLecturerFromName(lname);
			return ln;
		}
		
		return null;
	}
	
	private Lecturers findLecturerFromName (String name)
	{	
		//Log.d("Devon", "Lecturer Name is: " + name);
		
		for(Lecturers lect : Menu.myLecturer)
		{
			String lname = lect.getLecturerName();
			
			//Log.d("Devon", "lname in findLecturerFromName is: " + lname);
			
			if(lname.equalsIgnoreCase(name))
				return lect;
		}
		
		return null;
	}
}
