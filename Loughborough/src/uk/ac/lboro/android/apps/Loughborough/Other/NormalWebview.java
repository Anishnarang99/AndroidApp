package uk.ac.lboro.android.apps.Loughborough.Other;

import uk.ac.lboro.android.apps.Loughborough.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
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
import android.widget.TextView;

public class NormalWebview extends Activity implements OnClickListener {

	WebView ourBrowser;
	TextView textView;
	Button back, forward, refresh;
	ProgressDialog progressBar;
	String textViewName, webLink;
	
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
		
		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.webview);

		Intent i = getIntent();
		textViewName = i.getStringExtra("FeatName");
		webLink = i.getStringExtra("WebLink");
		
		Log.d("Devon", "Webview textviewname: " + textViewName);
		// Log.d("Devon", "Webview weblink: " + webLink);
		
		initialiseVariables();
		loadBrowser();
	}

	private void initialiseVariables() {
		
		textView = (TextView) findViewById(R.id.textViewTitle);
		back = (Button) findViewById(R.id.buttonBack);
		forward = (Button) findViewById(R.id.buttonForward);
		refresh = (Button) findViewById(R.id.buttonRefresh);
		back.setOnClickListener(this);
		forward.setOnClickListener(this);
		refresh.setOnClickListener(this);
		
		textView.setText(textViewName);
		textView.setTextSize(16);
		textView.setTextColor(Color.WHITE);
		textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
		
		progressBar = new ProgressDialog(this);
		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressBar.setMessage("Please wait...");
		progressBar.setCancelable(true);
		progressBar.setMax(100);
		progressBar.show();
	}

	private void loadBrowser() {

		ourBrowser = (WebView) findViewById(R.id.WebViewBrowser);
		ourBrowser.getSettings().setJavaScriptEnabled(true);
		ourBrowser.getSettings().setLoadWithOverviewMode(true);
		ourBrowser.getSettings().setUseWideViewPort(true);
		ourBrowser.getSettings().setBuiltInZoomControls(true);
		ourBrowser.getSettings().setDisplayZoomControls(false);
		if (textViewName.contains("Bus Travel Info"))
			ourBrowser.getSettings().setUserAgentString("Mozilla/5.0");
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
		ourBrowser.loadUrl(webLink);
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
            customViewContainer = new FrameLayout(NormalWebview.this);
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

            setContentView(R.layout.webview);
            loadBrowser();
		}
		
		@Override
		public void onProgressChanged(WebView view, int progress){
	    	
	    	progressBar.setProgress(progress * 100);

	    	if(progress == 100 && progressBar.isShowing())
	    		progressBar.dismiss();
	      }
	}

	@Override
	public void onClick(View v) {

		int id = v.getId();
		
		if (id == R.id.buttonBack) {
			
			if (ourBrowser.canGoBack())
				ourBrowser.goBack();
		}
		
		else if (id == R.id.buttonForward) {
			
			if (ourBrowser.canGoForward())
				ourBrowser.goForward();
		}
		
		else if (id == R.id.buttonRefresh) {
			
			ourBrowser.reload();
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent("uk.ac.lboro.android.apps.Loughborough.Ui.MENU");
		startActivity(i);
	}
}
