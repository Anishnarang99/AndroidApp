package com.example.gmaps;

import com.example.gmaps.R;
import com.example.gmaps.R.id;
import com.example.gmaps.R.layout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class Email extends Activity implements OnClickListener {

	WebView ourBrowser;
	Button back, forward, refresh;
	ProgressDialog progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Make the activity fullscreen - Make sure you set content view after making full screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.email);
		
		initialiseVariables();
		loadBrowser();
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

		ourBrowser = (WebView) findViewById(R.id.WebViewBrowser);
		ourBrowser.getSettings().setJavaScriptEnabled(true);
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
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
		ourBrowser.setInitialScale(100);
		ourBrowser.loadUrl("https://mail.google.com/a/student.lboro.ac.uk/?AuthEventSource=SSO");
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
