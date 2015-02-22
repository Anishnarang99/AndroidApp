package com.example.gmaps;

import java.util.List;

import com.example.gmaps.R;
import com.example.gmaps.R.id;
import com.example.gmaps.R.layout;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;

public class LsuWebsite extends Activity implements OnClickListener {

	WebView ourBrowser;
	Button back, forward, refresh;
	
	private FrameLayout customViewContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View customView;
    private WebChromeClient mWebChromeClient;
    
    MediaPlayer mediaPlayer = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Make the activity fullscreen - Make sure you set content view after making full screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.lsuwebsite);
		
		ActivityManager manager =  (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> activities = ((ActivityManager) manager).getRunningAppProcesses();
		
		initialiseVariables();
		loadBrowser();
		
		for (int i = 0; i < activities.size(); i++)
			Log.d("Devon", "Processes: " + activities.get(i).processName);      
		}

	private void initialiseVariables() {

		back = (Button) findViewById(R.id.buttonBack);
		forward = (Button) findViewById(R.id.buttonForward);
		refresh = (Button) findViewById(R.id.buttonRefresh);
		back.setOnClickListener(this);
		forward.setOnClickListener(this);
		refresh.setOnClickListener(this);
	}

	private void loadBrowser() {

		ourBrowser = (WebView) findViewById(R.id.WebViewBrowser);
		ourBrowser.getSettings().setJavaScriptEnabled(true);
		ourBrowser.getSettings().setLoadWithOverviewMode(true);
		ourBrowser.getSettings().setUseWideViewPort(true);
		ourBrowser.getSettings().setBuiltInZoomControls(true);
		ourBrowser.getSettings().setDisplayZoomControls(false);
		mWebChromeClient = new MyWebChromeClient();
		ourBrowser.setWebChromeClient(mWebChromeClient);
		ourBrowser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
		ourBrowser.setInitialScale(100);
		ourBrowser.loadUrl("http://www.lsu.co.uk/");
	}
	
	class MyWebChromeClient extends WebChromeClient {
		
		@Override
        public void onShowCustomView(View view,CustomViewCallback callback) {

            // if a view already exists then immediately terminate the new one
            if (customView != null) {
                callback.onCustomViewHidden();
                return;
            }
            customView = view;
            ourBrowser.setVisibility(View.GONE);
            customViewContainer = new FrameLayout(LsuWebsite.this);
            customViewContainer.setVisibility(View.VISIBLE);
            customViewContainer.setBackgroundResource(android.R.color.black);
            customViewContainer.addView(view);
            customViewCallback = callback;
            setContentView(customViewContainer);
        }

		@Override
        public void onHideCustomView() {
            super.onHideCustomView();
            
            if (customView == null)
                return;
            
            ourBrowser.setVisibility(View.VISIBLE);
            customViewContainer.setVisibility(View.GONE);

            // Hide the custom view.
            customView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            customViewContainer.removeView(customView);
            customViewCallback.onCustomViewHidden();
            customView = null;
            
            // Close all parent views
            ViewGroup parent = (ViewGroup) ourBrowser.getParent();
            parent.removeView(ourBrowser);

            setContentView(R.layout.lsuwebsite);
            loadBrowser();


            // Need to stop mediaplayer form running - kill the process somehow?
            
/*            android.os.Process.getUidForName("android.process.media");
            Log.d("Devon", "MediaPlayer Uid: " + android.os.Process.getUidForName("android.process.media"));
            android.os.Process.killProcess(android.os.Process.getUidForName("android.process.media"));
            //Process.sendSignal(pid, Process.SIGNAL_KILL);
            String mediaPlayer = "android.process.media";
            //ActivityManager.killBackgroundProcesses(packageName);   
*/        }
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
	
	public void onBackPressed() {
	    if (customViewContainer != null)
	        mWebChromeClient.onHideCustomView();
	    else if (ourBrowser.canGoBack())
	    	ourBrowser.goBack();
	    else
	        super.onBackPressed();
	    	Intent i = new Intent("com.example.gmaps.MENU");
	    	startActivity(i);
	}
	
	@Override
	public void onPause()
	{
	    super.onPause();
	    ourBrowser.loadUrl("about:blank");
	    //ourBrowser.onPause();
	}
}
