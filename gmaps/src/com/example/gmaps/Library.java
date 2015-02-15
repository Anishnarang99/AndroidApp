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

public class Library extends Activity implements OnClickListener {

	WebView ourBrowser;
	Button back, forward, refresh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Make the activity fullscreen - Make sure you set content view after making full screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.library);

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
		ourBrowser.loadUrl("http://lb-primo.hosted.exlibrisgroup.com/primo_library/libweb/action/search.do");
		/*ourBrowser.loadUrl("https://lb-primo.hosted.exlibrisgroup.com/pds?func=load-login&institute=LOUGHBOROUGH&calling_system=primo&lang=eng" +
				"&url=http://lb-primo.hosted.exlibrisgroup.com:80/primo_library/libweb/action/login.do?" +
				"targetURL=http%3a%2f%2flb-primo.hosted.exlibrisgroup.com%2fprimo_library%2flibweb%2faction%2fsearch.do" +
				"%3fdscnt%3d0%26amp%3bdstmp%3d1422297020966%26amp%3bvid%3dLB_VU1%26amp%3binitializeIndex%3dtrue&isMobile=true"); */ // Can't get secure login to load in the browser yet.
		// Do a HTTPS handshake before loading the website --> Look into it.
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
