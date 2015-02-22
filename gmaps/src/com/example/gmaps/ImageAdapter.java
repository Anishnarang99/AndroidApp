package com.example.gmaps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
	
	View gridView;
	ImageView imageView;
    TextView textView;
	
	private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {  // if it's not recycled, initialise some attributes
        	
        	gridView = new View(mContext);
        	gridView = inflater.inflate(R.layout.grid_single, null);
        	imageView = (ImageView)gridView.findViewById(R.id.grid_image);
        	textView = (TextView) gridView.findViewById(R.id.grid_text);
        }
        
        else {
        	
        	gridView = (View) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        textView.setText(mNames[position]);
        return gridView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.map, R.drawable.buildingsearch, R.drawable.learn,
            R.drawable.caspa, R.drawable.timetable, R.drawable.mainwebsite,
            R.drawable.lsu, R.drawable.bustravel,R.drawable.email,
            R.drawable.news, R.drawable.events, R.drawable.library,
            R.drawable.staffsearch2, R.drawable.pclab, R.drawable.safetytoolbox};
    
    private String[] mNames = {
    		"Maps", "Building Search", "Learn", "Caspa", "Timetable",
			"Main Website", "LSU Website", "Bus Travel", "Email",
			"News", "Events", "Library", "Staff Search", "PC Labs", "Safety Toolbox"};
}