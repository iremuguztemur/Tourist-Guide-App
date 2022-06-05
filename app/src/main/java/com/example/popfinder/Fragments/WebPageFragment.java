package com.example.popfinder.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.popfinder.R;


public class WebPageFragment extends Fragment {
    WebView mWebView;

    public WebPageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_web_page, container, false );


        mWebView = view.findViewById(R.id.my_webview);
        WebSettings settings = mWebView.getSettings();
        String url = "https://www.bing.com/maps/embed?h=400&w=500&cp=39.934519363678305~32.838134765625&lvl=15";
        // probably a good idea to check it's not null, to avoid these situations:
        if(mWebView != null){
            mWebView.loadUrl(url);
            mWebView.clearView();
            mWebView.measure(100, 100);
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            settings.setDomStorageEnabled(true);
            mWebView.setWebViewClient(new WebViewClient () {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
        }



        return view;
    }
}