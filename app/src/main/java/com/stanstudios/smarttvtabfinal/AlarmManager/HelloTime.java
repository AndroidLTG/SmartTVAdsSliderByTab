//package com.stanstudios.smarttvtabfinal.AlarmManager;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.os.PowerManager;
//import com.stanstudios.smarttvtabfinal.Activity.MainActivity;
//import com.stanstudios.smarttvtabfinal.LoadAds.MyMethod;
//
///**
// * Created by ${LTG} on ${10/12/1994}.
// */
//public class HelloTime extends BroadcastReceiver {
//    private static final String MYTAG = "LTG";
//    private float duration = 0;
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, MYTAG);
//        //Acquire the lock
//        wl.acquire();
//        //Dosomething
//        for (int i = 0; i < MainActivity.arrAds.size(); i++) {
//            MyMethod.showToast(context, "Quảng cáo " + i);
//            duration = MainActivity.arrAds.get(MyMethod.QC).getDuration();
//        }
//        //Release the lock
//        wl.release();
//    }
//
//    public void SetAlarm(Context context) {
//        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(context, HelloTime.class);
//        intent.putExtra("Alarm", Boolean.FALSE);
//        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
//        //After after 5 seconds
//        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (long) (1000 * duration), pi);
//    }
//
//    public void CancelAlarm(Context context) {
//        Intent intent = new Intent(context, HelloTime.class);
//        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.cancel(sender);
//    }
//}
