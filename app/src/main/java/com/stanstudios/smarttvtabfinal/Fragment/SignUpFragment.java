package com.stanstudios.smarttvtabfinal.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.stanstudios.smarttvtabfinal.Activity.MainActivity;
import com.stanstudios.smarttvtabfinal.LoadAds.MyMethod;
import com.stanstudios.smarttvtabfinal.LoadAds.SignUp;
import com.stanstudios.smarttvtabfinal.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${LTG} on ${10/12/1994}.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener {
    private TextView txtTitle;
    private EditText editNameDevice, editCodeCompany, editTimeUsing;
    private String checkSignUp;
    private String dataSignUp = "";

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
        txtTitle.setTypeface(MainActivity.font);
        v.findViewById(R.id.btnSignUp).setOnClickListener(this);
    }

    private void getId(View v) {
        txtTitle = (TextView) v.findViewById(R.id.txtSignUpTitle);
        editCodeCompany = (EditText) v.findViewById(R.id.editCodeCompany);
        editNameDevice = (EditText) v.findViewById(R.id.editNameDevice);
        editTimeUsing = (EditText) v.findViewById(R.id.editTimeUsing);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                dataSignUp = ConverttoJson();
                if (inputOK())
                    signUp();
                break;
            default:
                break;
        }
    }

    private boolean inputOK() {
        if (editNameDevice.getText().toString().isEmpty()) {
            editNameDevice.requestFocus();
            return false;
        } else if (editTimeUsing.getText().toString().isEmpty()) {
            editTimeUsing.requestFocus();
            return false;
        } else if (editCodeCompany.getText().toString().isEmpty()) {
            editCodeCompany.requestFocus();
            return false;

        } else
            return true;
    }

    private void signUp() {

        myAsyncTasksend myRequest = new myAsyncTasksend();

        myRequest.execute();
    }

    private void sendSignUp(String dataSignUp) {
        SoapObject request = new SoapObject(MyMethod.NAMESPACE, MyMethod.METHOD_NAME_SIGNUPDEVICE);
        PropertyInfo pi = new PropertyInfo();
        pi.setName("data");
        pi.setValue(dataSignUp);
        pi.setType(String.class);
        request.addProperty(pi);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transportSE = new HttpTransportSE(MyMethod.URL);
        try {
            transportSE.call(MyMethod.SOAP_ACTION_SIGNUPDEVICE, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            checkSignUp = response.toString();
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public String ConverttoJson() {
        String kq = "";
        List<SignUp> ListLine = loadline();
        if (ListLine != null) {
            Gson gson = new Gson();
            kq = gson.toJson(ListLine);
        }
        return kq;
    }

    private List<SignUp> loadline() {
        List<SignUp> result = new ArrayList<>();
        SignUp ci = new SignUp();
        ci.setDeviceID(MyMethod.DEVICEID);
        ci.setDescription(editNameDevice.getText().toString());
        ci.setTimeUsing(editTimeUsing.getText().toString());
        ci.setCompany(editCodeCompany.getText().toString());
        result.add(ci);
        return result;
    }

    private class myAsyncTasksend extends AsyncTask<Void, Void, Void> {

        @Override

        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            if (checkSignUp.equals(getContext().getString(R.string.success))) {
                MyMethod.showToast(getContext(), getContext().getString(R.string.signup_success));
                MainActivity.btnSetting.setText(getContext().getString(R.string.reload_wifi));
                MainActivity.viewPager.setCurrentItem(MainActivity.TYPE_SETTING);

            } else if (checkSignUp.contains(getContext().getString(R.string.signup_fail_duplicate))) {
                MyMethod.showToast(getContext(), getContext().getString(R.string.signup_fail_duplicate_vn));
                MainActivity.btnSetting.setText(getContext().getString(R.string.reload_wifi));
                MainActivity.viewPager.setCurrentItem(MainActivity.TYPE_SETTING);
            } else if (checkSignUp.contains(getContext().getString(R.string.signup_fail_companycode))) {
                MyMethod.showToast(getContext(), getContext().getString(R.string.signup_fail_companycode_vn));
                editCodeCompany.setText("");
                editCodeCompany.requestFocus();
            } else
                MyMethod.showToast(getContext(), checkSignUp);
        }


        @Override

        protected void onPreExecute() {

            super.onPreExecute();

            //dang xu li

        }


        @Override

        protected Void doInBackground(Void... params) {

            sendSignUp(dataSignUp);


            return null;

        }

    }
}
