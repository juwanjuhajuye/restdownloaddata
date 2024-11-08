package com.example.jjbfather.jjjqsrt2go2gorestdownload;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class CheckNewTablePayInCloudService extends Service implements Runnable {
    Context context;
    // DB 객체 선언
    static DatabaseInit dbInitForUploadCloud;
    Intent mIntent;


    public CheckNewTablePayInCloudService() {
        this.context = this;
        // DatabaseInit 객체 생성
        dbInitForUploadCloud = MainActivity.mDbInit;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
        // DatabaseInit 객체 생성
        dbInitForUploadCloud = MainActivity.mDbInit;

        String CHANNEL_ID = "CheckNewTablePayInCloudService";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_ID, NotificationManager.IMPORTANCE_LOW);
            ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("").setContentText("").build();
            startForeground(2,notification); // 추가
        }
        //Thread mThread = new Thread(this);
        //mThread.start();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        Thread mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void run() {
        try {
            //sendSalesDataToCloud(receivedSalesCode);
            GlobalMemberValues.logWrite("newtablepaystr", "신규 웹오더 체크서비스 시작" + "\n");
            checkNewTablePay();
        } catch (Exception e) {
            GlobalMemberValues.logWrite("newtablepaystr", "Thread Error1 : " + e.getMessage().toString() + "\n");
        }
    }


    public void checkNewTablePay() {
        String newTablePayString = "";
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isStrEmpty(GlobalMemberValues.STORE_INDEX)) {
                API_weborder_newtablepay apiIns = new API_weborder_newtablepay(GlobalMemberValues.STORE_INDEX);
                apiIns.execute(null, null, null);
                try {
                    Thread.sleep(GlobalMemberValues.API_THREAD_TIME); //1초마다 실행
                    if (apiIns.mFlag) {
                        newTablePayString = apiIns.mReturnValue;
                    }
                } catch (InterruptedException e) {
                    GlobalMemberValues.logWrite("newtablepaystr", "Thread Error : " + e.getMessage() + "\n");
                    //GlobalMemberValues.logWrite("APIDownLoadClass", "Thread Error : " + e.getMessage());
                }
            }
        }
        GlobalMemberValues.logWrite("newtablepaylogforexe", newTablePayString + "\n");

        if (!GlobalMemberValues.isStrEmpty(newTablePayString)) {
            GlobalMemberValues.logWrite("newtablepaylogforexe", newTablePayString + "\n");
            // 테이블페이가 있을 경우 이곳에서 처리
            GlobalMemberValues.openNewTablePayStr(newTablePayString);
        }


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

        } else {
            if (GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_NEWTABLEPAY != null && GlobalMemberValues.CURRENTSERVICEINTENT_NEWTABLEPAY != null) {
                GlobalMemberValues.CURRENTACTIVITYOPENEDSERVICE_NEWTABLEPAY.stopService(GlobalMemberValues.CURRENTSERVICEINTENT_NEWTABLEPAY);
            }
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
