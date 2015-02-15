package com.example.gmaps;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class BusTravelInfo extends Activity implements OnClickListener {

	WebView ourBrowser;
	Button back, forward, refresh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Make the activity fullscreen - Make sure you set content view after making full screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.bustravelinfo);

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
	}

	private void loadBrowser() {

		ourBrowser = (WebView) findViewById(R.id.WebViewBrowser);
		ourBrowser.getSettings().setJavaScriptEnabled(true);
		ourBrowser.getSettings().setLoadWithOverviewMode(true);
		ourBrowser.getSettings().setUseWideViewPort(true);
		ourBrowser.getSettings().setBuiltInZoomControls(true);
		ourBrowser.getSettings().setDisplayZoomControls(false);
		ourBrowser.getSettings().setUserAgentString("Mozilla/5.0");
		ourBrowser.setWebChromeClient(new WebChromeClient());
		ourBrowser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
		ourBrowser.setInitialScale(130);
		ourBrowser.loadUrl("https://www.kinchbus.co.uk/services/sprint/timetable");
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

}
