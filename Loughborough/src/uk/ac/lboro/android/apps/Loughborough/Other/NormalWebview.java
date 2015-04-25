package uk.ac.lboro.android.apps.Loughborough.Other;

import java.util.List;

import uk.ac.lboro.android.apps.Loughborough.R;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class NormalWebview extends Activity {

	WebView ourBrowser;
	TextView textView, textViewBrowserButtons;
	Button back, forward, refresh;
	ProgressDialog progressBar;
	String textViewName, StringBrowserButtons, webLink;
	ActivityManager actMan;
	
	private FrameLayout customViewContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View customView;
    private WebChromeClient mWebChromeClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Make the activity fullscreen - Make sure you set content view after making full screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		// Shows a progress bar on the screen.
		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
		
		setContentView(R.layout.webview);
		
		// Retrieves the passed variables when this intent is called.
		Intent i = getIntent();
		textViewName = i.getStringExtra("FeatName");
		webLink = i.getStringExtra("WebLink");
		
		// Log.d("Devon", "Webview textviewname: " + textViewName);
		// Log.d("Devon", "Webview weblink: " + webLink);
		
		// Initialises variables
		initialiseVariables();
		
		// Loads the browser in the webview.
		loadBrowser();
	}

	private void initialiseVariables() {
		
		textView = (TextView) findViewById(R.id.textViewTitle);
		textViewBrowserButtons = (TextView) findViewById(R.id.textViewBrowserButtons);
		back = (Button) findViewById(R.id.buttonBack);
		forward = (Button) findViewById(R.id.buttonForward);
		refresh = (Button) findViewById(R.id.buttonRefresh);
		
		// Sets up the heading of the activity
		textView.setText(textViewName);
		textView.setTextSize(16);
		textView.setTextColor(Color.parseColor("#C70066"));
		textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
		
		// Sets up the "Browser Controls" text.
		StringBrowserButtons = "Browser Controls:";
		textViewBrowserButtons.setText(StringBrowserButtons);
		textViewBrowserButtons.setTextSize(12);
		textViewBrowserButtons.setTextColor(Color.parseColor("#C70066"));
		
		// Sets up the progress bar
		progressBar = new ProgressDialog(this);
		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressBar.setMessage("Please wait...");
		progressBar.setCancelable(true);
		progressBar.setMax(100);
		progressBar.show();
	}

	private void loadBrowser() {
		
		// Setting up the browser.
		ourBrowser = (WebView) findViewById(R.id.WebViewBrowser);
		ourBrowser.getSettings().setJavaScriptEnabled(true);
		ourBrowser.getSettings().setLoadWithOverviewMode(true);
		ourBrowser.getSettings().setUseWideViewPort(true);
		ourBrowser.getSettings().setBuiltInZoomControls(true);
		ourBrowser.getSettings().setDisplayZoomControls(false);
		// Change the useragent to Mozilla if viewing the bus travel information so the correct page loads.
		if (textViewName.contains("Bus Travel Info"))
			ourBrowser.getSettings().setUserAgentString("Mozilla/5.0");
		mWebChromeClient = new MyWebChromeClient();
		ourBrowser.setWebChromeClient(mWebChromeClient);
		ourBrowser.setWebViewClient(new WebViewClient() {
			
			// Loads a new URL within the same webview.
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
		ourBrowser.setInitialScale(100);
		ourBrowser.loadUrl(webLink);
		
		// Kills the media process activity after playing a youtube video because it doesn't turn off automatically.
		actMan = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> pids = actMan.getRunningAppProcesses();
        for(int i = 0; i < pids.size(); i++)
               {
                   ActivityManager.RunningAppProcessInfo info = pids.get(i);
                   //Log.d("Devon", "Process ID info:" + info.processName + " " + info.pid);
                   if(info.processName.equalsIgnoreCase("android.process.media")){
                      Log.d("Devon", "Killing media player process via pid: " + info.pid);
                      android.os.Process.killProcess(info.pid);
                   } 
               }
	}
	
	// When you click the fullscreen icon on a youtube video, it shows in a full screen.
	// The following code is based on code found here: http://stackoverflow.com/questions/14763483/android-webview-with-an-embedded-youtube-video-full-screen-button-freezes-video
	class MyWebChromeClient extends WebChromeClient {
		
		@Override
        public void onShowCustomView(View view,CustomViewCallback callback) {

            // If a view already exists then immediately terminate the new one
            if (customView != null) {
                callback.onCustomViewHidden();
                return;
            }

            ourBrowser.setVisibility(View.GONE);
            customViewContainer = new FrameLayout(NormalWebview.this);
            customViewContainer.setBackgroundResource(android.R.color.black);
            customViewContainer.addView(view);
            customView = view;
            customViewCallback = callback;
            customViewContainer.setVisibility(View.VISIBLE);
            setContentView(customViewContainer);
        }

		@Override
        public void onHideCustomView() {
            //super.onHideCustomView();
            
            if (customView == null) {
                return;
            } else {

	
	            // Hide the custom view.
	            customView.setVisibility(View.GONE);
	
	            // Remove the custom view from its container.
	            customViewContainer.removeView(customView);
	            customView = null;
	            customViewContainer.setVisibility(View.GONE);
	            customViewCallback.onCustomViewHidden();

	            ourBrowser.setVisibility(View.VISIBLE);
	            setContentView(R.layout.webview);
	            finish();
	            startActivity(getIntent());
            }
		}
		
		// Updates the progress bar
		@Override
		public void onProgressChanged(WebView view, int progress){
	    	
	    	progressBar.setProgress(progress * 100);

	    	if(progress == 100 && progressBar.isShowing())
	    		progressBar.dismiss();
	      }
	}

	// Pressing the 'Refresh' button.
	public void onClick_Refresh(View v) {

		ourBrowser.reload();	
	}
	
	// Pressing the 'Back' button.
	public void onClick_Back(View v) {

		if (ourBrowser.canGoBack())
			ourBrowser.goBack();	
	}
	
	// Pressing the 'Forward' button.
	public void onClick_Forward(View v) {

		if (ourBrowser.canGoForward())
			ourBrowser.goForward();	
	}
	
	// Goes back to the menu screen when the back button is pressed.
	@Override
	public void onBackPressed() {
		if (customViewContainer != null)
	        mWebChromeClient.onHideCustomView();
	    else {
	    	super.onBackPressed();
	    }
	}
}
