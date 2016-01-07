package com.stanstudios.smarttvtabfinal.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import com.stanstudios.smarttvtabfinal.Adapter.ViewPagerAdapter;
import com.stanstudios.smarttvtabfinal.AlarmManager.HelloTime;
import com.stanstudios.smarttvtabfinal.Fragment.AdsFragment;
import com.stanstudios.smarttvtabfinal.Fragment.ImageViewFragment;
import com.stanstudios.smarttvtabfinal.Fragment.SettingFragment;
import com.stanstudios.smarttvtabfinal.Fragment.SignUpFragment;
import com.stanstudios.smarttvtabfinal.Fragment.VideoViewFragment;
import com.stanstudios.smarttvtabfinal.Fragment.WebViewFragment;
import com.stanstudios.smarttvtabfinal.LoadAds.Ads;
import com.stanstudios.smarttvtabfinal.LoadAds.MyMethod;
import com.stanstudios.smarttvtabfinal.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int TYPE_SETTING = 0;
    private static final int TYPE_SIGNUP = 1;
    private static final int TYPE_VIDEO = 2;
    private static final int TYPE_IMAGEVIEW = 3;
    private static final int TYPE_WEBVIEW = 4;
    private static final int TYPE_ADS = 5;
    public static VideoView videoView;
    public static ImageView imageView;
    public static WebView webView;
    public static ViewPager viewPager;
    public static String FOLDER = "SmartTVAds";
    public static Button btnSetting;
    public static ArrayList<Ads> arrAds = new ArrayList<>();
    private static Context context;
    private static ProgressDialog mProgressDialog;
    private static Handler handler;
    private static int count = 0;
    private static java.lang.Runnable runnable;
    protected PowerManager.WakeLock mWakeLock;
    private TabLayout tabLayout;
    private HelloTime helloTime;

    public static void loadData() {
        MyMethod.createFolder(FOLDER);
        if (MyMethod.isOnline(context)) {
            MyMethod.requestDevice(arrAds, context, mProgressDialog, MyMethod.DEVICEID, FOLDER);
        } else viewPager.setCurrentItem(TYPE_SETTING);
        mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.w("Số Quảng Cáo : ", arrAds.size() + " cái");
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        switch (arrAds.get(count).getType()) {
                            case MyMethod.TYPE_VIDEO:
                                viewPager.setCurrentItem(TYPE_VIDEO);
                                MyMethod.loadVideo(videoView, MainActivity.FOLDER, "File1.mp4", MyMethod.VOLUME.get(1));
                                break;
                            case MyMethod.TYPE_IMAGE:
                                viewPager.setCurrentItem(TYPE_IMAGEVIEW);
                                MyMethod.loadImage(imageView, MainActivity.FOLDER, "File2.jpg");
                                break;
                            case MyMethod.TYPE_WEB:
                                viewPager.setCurrentItem(TYPE_WEBVIEW);
                                MyMethod.loadWebview(webView, MyMethod.POSITIONWEB.get(3), MyMethod.LINKWEB.get(3));
                                break;
                            case MyMethod.TYPE_ADS:
                                viewPager.setCurrentItem(TYPE_ADS);
                                break;
                            default:
                                break;
                        }
                        count++;
                        MyMethod.QC++;
                        if (count == arrAds.size()) {
                            count = 0;
                            handler.postDelayed(this, 1000 * (long) arrAds.get(count).getDuration());
                        } else
                            handler.postDelayed(this, 1000 * (long) arrAds.get(count - 1).getDuration()); // 1000 means 1 second duration
                    }
                };
                handler.postDelayed(runnable, 180); // 180 is the delay after new runnable is going to called

            }

        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getId();
        setupViewPager(viewPager);
        loadData();
        //init();
    }


    private void setupViewPager(final ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SettingFragment(), "SETTING");
        adapter.addFragment(new SignUpFragment(), "SIGNUP");
        adapter.addFragment(new VideoViewFragment(), "VIDEO");
        adapter.addFragment(new ImageViewFragment(), "IMAGE");
        adapter.addFragment(new WebViewFragment(), "WEB");
        adapter.addFragment(new AdsFragment(), "ADS");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case TYPE_VIDEO:
                        break;
                    case TYPE_ADS:
                        if (videoView.isPlaying()) videoView.stopPlayback();
                        break;
                    case TYPE_IMAGEVIEW:
                        if (videoView.isPlaying()) videoView.stopPlayback();
                        break;
                    case TYPE_WEBVIEW:
                        if (videoView.isPlaying()) videoView.stopPlayback();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void getId() {
        context = getApplicationContext();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        mProgressDialog = new ProgressDialog(MainActivity.this);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, MyMethod.MYTAG);
        this.mWakeLock.acquire();
        handler = new Handler();
        helloTime = new HelloTime();
        if (helloTime != null) {
            helloTime.SetAlarm(getApplicationContext());
        } else {
            MyMethod.showToast(context, "Lỗi: alarm bị null");
        }
    }

    @Override
    protected void onDestroy() {
        helloTime.CancelAlarm(context);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
