package com.stanstudios.smarttvtabfinal.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.stanstudios.smarttvtabfinal.Activity.MainActivity;
import com.stanstudios.smarttvtabfinal.R;

/**
 * Created by ${LTG} on ${10/12/1994}.
 */
public class WebViewFragment extends Fragment {

    public WebViewFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.web_view, container, false);
        getId(v);
        return v;
    }

    private void getId(View v) {
        MainActivity.webView = (WebView) v.findViewById(R.id.webview);
    }

}
