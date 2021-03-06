package com.stanstudios.smarttvtabfinal.LoadAds;

import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;

import com.stanstudios.smarttvtabfinal.Activity.MainActivity;
import com.stanstudios.smarttvtabfinal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ${LTG} on ${10/12/1994}.
 */
public class RequestTask extends AsyncTask<Void, Void, Boolean> {
    Exception error;
    private Context context;
    private PowerManager.WakeLock mWakeLock;
    private String deviceID;
    private String data;
    private DownloadTask downloadTask;
    private ArrayList<Ads> arrAds;

    public RequestTask(ArrayList<Ads> arrAds, DownloadTask downloadTask, Context context, String deviceID) {
        this.context = context;
        this.deviceID = deviceID;
        this.downloadTask = downloadTask;
        this.arrAds = arrAds;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            requestDevice(deviceID);
            return true;
        } catch (Exception e) {
            error = e;
            return false;
        }

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        mWakeLock.release();
        if (result) {
            if (data.contains(context.getString(R.string.request_fail_not_sign))) {
                MyMethod.showToast(context, context.getString(R.string.device_not_sign));
                MainActivity.viewPager.setCurrentItem(MainActivity.TYPE_SIGNUP);
            } else if (data.contains(context.getString(R.string.request_fail_not_permision))) {
                MyMethod.showToast(context, context.getString(R.string.device_not_permision));
                MainActivity.txtSettingTitle.setText(context.getString(R.string.device_not_permision));
                MainActivity.btnSetting.setText(context.getString(R.string.reload_wifi));
                try {
                    parseDataInfo();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    parseData();// get companycode
                    String[] link = new String[arrAds.size()];
                    for (int i = 0; i < arrAds.size(); i++) {
                        switch (arrAds.get(i).getType()) {
                            case MyMethod.TYPE_ADS:

                                break;
                            case MyMethod.TYPE_IMAGE:
                                MyMethod.NAME.put(arrAds.get(i).getSerial(),arrAds.get(i).getName());
                                link[i] = arrAds.get(i).getUrl();
                                break;
                            case MyMethod.TYPE_VIDEO:
                                MyMethod.NAME.put(arrAds.get(i).getSerial(),arrAds.get(i).getName());
                                MyMethod.VOLUME.put(arrAds.get(i).getSerial(), arrAds.get(i).getVolume());
                                link[i] = arrAds.get(i).getUrl();
                                break;
                            case MyMethod.TYPE_WEB:
                                MyMethod.LINKWEB.put(arrAds.get(i).getSerial(), arrAds.get(i).getUrl());
                                MyMethod.POSITIONWEB.put(arrAds.get(i).getSerial(), 1000);
                                break;
                        }

                    }
                    downloadTask.execute(link);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }
        //  mProgressDialog.dismiss();
    }

    private void parseData() throws JSONException {
        arrAds.clear();
        JSONObject jsonObject = new JSONObject(data);
        JSONArray arrJsonAds = jsonObject.getJSONArray("Table");
        int nAds = arrJsonAds.length();
        for (int i = 0; i < nAds; i++) {
            Ads cv = new Ads();
            cv.setSerial(Integer.parseInt(arrJsonAds.getJSONObject(i).getString("LineID")));
            cv.setType(Integer.parseInt(arrJsonAds.getJSONObject(i).getString("Type")));
            cv.setExtension(arrJsonAds.getJSONObject(i).getString("extension"));
            cv.setUrl(arrJsonAds.getJSONObject(i).getString("Url"));
            cv.setName(arrJsonAds.getJSONObject(i).getString("Add_No_"));
            cv.setBackupUrl(arrJsonAds.getJSONObject(i).getString("BackupUrl"));
            cv.setStartDate(arrJsonAds.getJSONObject(i).getString("StartTime"));
            cv.setDuration(Float.parseFloat(arrJsonAds.getJSONObject(i).getString("DurationTime")));
            cv.setVolume(Float.parseFloat(arrJsonAds.getJSONObject(i).getString("Volume")));
            MainActivity.txtNameDevice.setText(arrJsonAds.getJSONObject(i).getString("Decription"));
            MainActivity.txtTimeUsing.setText(arrJsonAds.getJSONObject(i).getString("TimeUsing"));
            MainActivity.txtCompany.setText(arrJsonAds.getJSONObject(i).getString("Company"));
            arrAds.add(cv);
        }
    }

    private void parseDataInfo() throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        JSONArray arrJsonAds = jsonObject.getJSONArray("Table1");
        int nAds = arrJsonAds.length();
        for (int i = 0; i < nAds; i++) {
            MainActivity.txtNameDevice.setText(arrJsonAds.getJSONObject(i).getString("Decription"));
            MainActivity.txtTimeUsing.setText(arrJsonAds.getJSONObject(i).getString("TimeUsing"));
            MainActivity.txtCompany.setText(arrJsonAds.getJSONObject(i).getString("Company"));
        }
    }

    private void requestDevice(String deviceID) {
        SoapObject request = new SoapObject(MyMethod.NAMESPACE, MyMethod.METHOD_NAME_REQUESTDEVICE);
        //Property which holds input parameters
        PropertyInfo pi = new PropertyInfo();
        pi.setName("deviceid");
        pi.setValue(deviceID);
        pi.setType(String.class);
        request.addProperty(pi);
        //Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        //Set output SOAP object
        envelope.setOutputSoapObject(request);
        //Create HTTP call object
        HttpTransportSE transportSE = new HttpTransportSE(MyMethod.URL);
        try {
            transportSE.call(MyMethod.SOAP_ACTION_REQUESTDEVICE, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            data = response.toString();
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
    }
}