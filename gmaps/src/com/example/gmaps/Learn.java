package com.example.gmaps;

import java.io.File;

import com.example.gmaps.R;
import com.example.gmaps.R.id;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.DownloadManager.Request;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class Learn extends Activity implements OnClickListener {

	WebView ourBrowser;
	Button back, forward, refresh;
	
	ProgressDialog progressBar;
	WebViewClient mWebViewClient;
	DownloadManager dm;
	long enqueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		launchInChrome();
		
		// Make the activity fullscreen - Make sure you set content view after making full screen.
/*		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.learn);*/

		
		
		//initialiseVariables();
		//loadBrowser();
	}

	private void launchInChrome() {

		String urlString ="http://learn.lboro.ac.uk/my";
		Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(urlString));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setPackage("com.android.chrome");
		
		try {
		    startActivity(intent);
		} catch (ActivityNotFoundException ex) {
			
		    // Chrome browser presumably not installed so allow user to choose instead
		    intent.setPackage(null);
		    startActivity(intent);
		}
		
	}

	private void initialiseVariables() {

		back = (Button) findViewById(R.id.buttonBack);
		forward = (Button) findViewById(R.id.buttonForward);
		refresh = (Button) findViewById(R.id.buttonRefresh);
		back.setOnClickListener(this);
		forward.setOnClickListener(this);
		refresh.setOnClickListener(this);
		
		progressBar = new ProgressDialog(this);
		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressBar.setMessage("Please wait...");
		progressBar.setCancelable(true);
		progressBar.setMax(100);
		progressBar.show();
	}

	private void loadBrowser() {
		
/*		// This will handle downloading. It requires Gingerbread, though
	    final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

	    // This is where downloaded files will be written, using the package name isn't required
	    // but it's a good way to communicate who owns the directory
	    final File destinationDir = new File (Environment.getExternalStorageDirectory(), getPackageName());
	    
	    if (!destinationDir.exists()) {
	    	
	        destinationDir.mkdir(); // Don't forget to make the directory if it's not there
	    }*/

		ourBrowser = (WebView) findViewById(R.id.WebViewBrowser);
		ourBrowser.getSettings().setJavaScriptEnabled(true);
		ourBrowser.getSettings().getSaveFormData();
		ourBrowser.getSettings().setLoadWithOverviewMode(true);
		ourBrowser.getSettings().setUseWideViewPort(true);
		ourBrowser.getSettings().setBuiltInZoomControls(true);
		ourBrowser.getSettings().setDisplayZoomControls(false);
		ourBrowser.setWebChromeClient(new WebChromeClient(){
			
		    public void onProgressChanged(WebView view, int progress){

		    	progressBar.setProgress(progress * 100);

		    	if(progress == 100 && progressBar.isShowing())
		    		progressBar.dismiss();
		      }
		    }
		  );

		ourBrowser.setWebViewClient(new WebViewClient() {
		    
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				boolean shouldOverride = false;

				if (url.endsWith(".pdf")) {

					shouldOverride = true;
					Log.d("Devon", "PDF link: " + url);
					view.getContext().startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					
					// Downloads PDFs but not correctly. Need help.
/*					dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
			        Request request = new Request(Uri.parse(url));
			        //request.setMimeType("application/pdf");
			        Log.d("Devon", "request: " + request);
			        enqueue = dm.enqueue(request);

			        Intent i = new Intent();
			        i.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
			        startActivity(i);*/
		        	
			        /*				
					Log.d("Devon", "Downloading here.");
					startActivity(new Intent (Intent.ACTION_VIEW, Uri.parse(url)));
					view.loadUrl(url);
				
		        	Request request = new Request(Uri.parse(url));
	                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
	                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "download"); 
	                
	                // You can change the name of the downloads, by changing "download" to everything you want, such as the mWebview title...
	                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
	                dm.enqueue(request);*/
		        	
		        	/*Uri source = Uri.parse(pdfurl);
		        	// Make a new request pointing to the pdf url
	                DownloadManager.Request request = new DownloadManager.Request(source);
	                // Use the same file name for the destination
	                File destinationFile = new File (destinationDir, source.getLastPathSegment());
	                request.setDestinationUri(Uri.fromFile(destinationFile));
	                // Add it to the manager
	                manager.enqueue(request);*/
	                
		        	/*startActivity(new Intent (Intent.ACTION_VIEW, Uri.parse(url)));
		        	//String url = "http://docs.google.com/gview?embedded=true&url=" + pdfurl;
		        	String url = "https://docs.google.com/viewer?url=" + pdfurl;
		        	Log.d("Devon", "url to load: " + url);
		        	view.loadUrl(url);*/
		            //return true;
		        }
		        return shouldOverride;
		    }
		});
		
		ourBrowser.setInitialScale(100);
		ourBrowser.loadUrl("http://learn.lboro.ac.uk/my");
	}
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.buttonBack:

			if (ourBrowser.canGoBack())
				ourBrowser.goBack();
			break;

		case R.id.buttonForward:
			if (ourBrowser.canGoForward())
				ourBrowser.goForward();
			break;

		case R.id.buttonRefresh:

			ourBrowser.reload();
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent("com.example.gmaps.MENU");
		startActivity(i);
	}
}
