package com.stanstudios.smarttvtabfinal.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.stanstudios.smarttvtabfinal.Activity.MainActivity;
import com.stanstudios.smarttvtabfinal.R;

/**
 * Created by ${LTG} on ${10/12/1994}.
 */
public class VideoViewFragment extends Fragment {

    public VideoViewFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.video_view, container, false);
        getId(v);
        return v;

    }

    private void getId(View v) {
        MainActivity.videoView = (VideoView) v.findViewById(R.id.videoview);
    }


    @Override
    public void onPause() {
        MainActivity.videoView.stopPlayback();
        super.onPause();
    }

}
