package com.example.jjbfather.jjjqsrt2go2gorestdownload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        GlobalMemberValues.openTimeMenuSelectPopupAuto();

        String tempTime = DateMethodClass.nowHourGet() + ":" + DateMethodClass.nowMinuteGet();

        GlobalMemberValues.logWrite("autotimemenulogjjj2", "여기실행-1... 현재시간 : " + tempTime + "\n");
        GlobalMemberValues.logWrite("autotimemenulogjjj2", "여기실행-1... nowTimeCodeValue : " + GlobalMemberValues.NOW_TIME_CODEVALUE + "\n");


    }


}

