package com.stanstudios.smarttvtabfinal.LoadAds;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ${LTG} on ${10/12/1994}.
 */
public class MyMethod {
    public static final String NAMESPACE = "http://tempuri.org/";
    public static final String METHOD_NAME_REQUESTDEVICE = "AddTV_RequestAds";
    public static final String URL = "http://indico.vn:8101/mywebservice.asmx?WSDL";
    public static final String SOAP_ACTION_REQUESTDEVICE = "http://tempuri.org/AddTV_RequestAds";
    public static final String TYPE_VIDEO = "VIDEO";
    public static final String TYPE_IMAGE = "PC";
    public static final String TYPE_WEB = "WEB";
    public static final String TYPE_ADS = "QC";
    public static final String MYTAG = "LTG";
    public static HashMap<Integer, String> LINKWEB = new HashMap<>();
    public static HashMap<Integer, Float> VOLUME = new HashMap<>();
    public static HashMap<Integer, Float> DURATION = new HashMap<>();
    public static HashMap<Integer, Integer> POSITIONWEB = new HashMap<>();
    public static int QC = 0;
    public static String DEVICEID = "tientest";

    public static void createFolder(String foldername) {
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + foldername);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        if (success) {
            Log.w(foldername, "Tạo folder thành công");
        } else {
            // Do something else on failure
            Log.w(foldername, "Tạo folder thất bại");
        }

        //Xoa rong folder
        File dir = new File(Environment.getExternalStorageDirectory(), foldername);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                new File(dir, aChildren).delete();
            }
        }

    }


    public static void requestDevice(ArrayList<Ads> arrAds, Context context, final ProgressDialog mProgressDialog, final String deviceId, String folder) {
        mProgressDialog.setMessage("Đang quét thiết bị");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);
        final DownloadTask downloadTask = new DownloadTask(context, folder, mProgressDialog);
        final RequestTask requestTask = new RequestTask(arrAds, downloadTask, context, deviceId);
        requestTask.execute();
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    public static void loadImage(ImageView imageView, String folderName, String fileName) {
        try {
            String uriPath = Environment.getExternalStorageDirectory() + "/" + folderName + "/" + fileName;
            Bitmap bmp = BitmapFactory.decodeFile(uriPath);
            imageView.setImageBitmap(bmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadVideo(final VideoView video, String folderName, String fileName, final float VOLUME) {
        // Create a progressbar
        try {
            String uriPath = Environment.getExternalStorageDirectory() + "/" + folderName + "/" + fileName;
            video.setVideoPath(uriPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(VOLUME / 100, VOLUME / 100);
                video.start();
            }
        });


    }


    public static void loadWebview(final WebView web, final int positionWeb, String LINK_WEB) {
        web.clearCache(true);
        web.removeAllViews();
        web.getSettings().setJavaScriptEnabled(false);
        web.invalidate();
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                web.scrollTo(0, positionWeb);
            }
        });
        web.loadUrl(LINK_WEB);
        web.requestFocus();
    }

    public static void showToast(Context context, String t) {
        Toast.makeText(context, t, Toast.LENGTH_SHORT).show();
    }
}
