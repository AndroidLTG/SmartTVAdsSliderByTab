package com.stanstudios.smarttvtabfinal.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stanstudios.smarttvtabfinal.R;

/**
 * Created by ${LTG} on ${10/12/1994}.
 */
public class SignUpFragment extends Fragment {
    private TextView txtTitle;

    public SignUpFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sign_up, container, false);
        getId(v);
        event(v);
        return v;
    }

    private void event(View v) {
        Typeface face = Typeface.createFromAsset(v.getContext().getAssets(), "fonts.ttf");
        txtTitle.setTypeface(face);
    }

    private void getId(View v) {
        txtTitle = (TextView) v.findViewById(R.id.txtSignUpTitle);

    }
}
