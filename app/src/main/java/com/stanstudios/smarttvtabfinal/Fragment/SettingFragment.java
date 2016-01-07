package com.stanstudios.smarttvtabfinal.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.stanstudios.smarttvtabfinal.Activity.MainActivity;
import com.stanstudios.smarttvtabfinal.LoadAds.MyMethod;
import com.stanstudios.smarttvtabfinal.R;

/**
 * Created by ${LTG} on ${10/12/1994}.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {
    private static final int OPENWIFI = 1;

    public SettingFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.setting_view, container, false);
        getId(v);
        return v;
    }

    private void getId(View v) {
        MainActivity.btnSetting = (Button) v.findViewById(R.id.btnSetting);
        MainActivity.btnSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSetting:
                switch (MainActivity.btnSetting.getText().toString()) {
                    case "Bật internet":

                        startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), OPENWIFI);
                        break;
                    case "Tải lại":
                        MainActivity.loadData();

                            break;
                    case "Đăng ký":
                        MyMethod.showToast(getContext(),"Đăng kí");
                        break;

                }

                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case OPENWIFI:
                MainActivity.btnSetting.setText(getString(R.string.reload_wifi));
                MainActivity.btnSetting.performClick();
                break;
            default:
                break;
        }
    }
}
