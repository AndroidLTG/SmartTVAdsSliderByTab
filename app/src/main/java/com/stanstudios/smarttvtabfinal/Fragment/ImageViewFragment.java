package com.stanstudios.smarttvtabfinal.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.stanstudios.smarttvtabfinal.Activity.MainActivity;
import com.stanstudios.smarttvtabfinal.LoadAds.MyMethod;
import com.stanstudios.smarttvtabfinal.R;

/**
 * Created by ${LTG} on ${10/12/1994}.
 */
public class ImageViewFragment extends Fragment {

    public ImageViewFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.image_view, container, false);
        getId(v);
        return v;
    }


    private void getId(View v) {
        MainActivity.imageView = (ImageView) v.findViewById(R.id.imageview);
    }

}
