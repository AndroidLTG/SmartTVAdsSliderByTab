package com.stanstudios.smarttvtabfinal.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stanstudios.smarttvtabfinal.LoadAds.Ads;
import com.stanstudios.smarttvtabfinal.R;

/**
 * Created by ${LTG} on ${10/12/1994}.
 */
public class AdsFragment extends Fragment {
    public AdsFragment()
    {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ads_view,container,false);
        getId(v);
        event(v);
        return v;
    }

    private void event(View v) {

    }


    private void getId(View v) {
    }
}
