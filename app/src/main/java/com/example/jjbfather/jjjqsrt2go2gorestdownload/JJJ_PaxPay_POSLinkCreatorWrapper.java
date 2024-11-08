package com.example.jjbfather.jjjqsrt2go2gorestdownload;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.util.Log;
import android.widget.Toast;

import com.pax.poslink.CommSetting;
import com.pax.poslink.PosLink;
import com.pax.poslink.poslink.POSLinkCreator;
import com.pax.poslink.usb.UsbUtil;

public class JJJ_PaxPay_POSLinkCreatorWrapper {

    private static PosLink create(Context context) {
        String iniFile = context.getFilesDir().getAbsolutePath() + "/" + SettingINI.FILENAME;
        CommSetting commset = SettingINI.getCommSettingFromFile(iniFile);
        if (commset.getType().equals(CommSetting.USB) && !UsbUtil.hasPermission(context)) {
            UsbDevice usbDevice = UsbUtil.getDevice(context);
            if (usbDevice == null) {
                Toast.makeText(context, "Please plug in the POS machine with USB.", Toast.LENGTH_SHORT).show();
            }
        }
        return POSLinkCreator.createPoslink(context);
    }

    public static void createSync(final Context context, final JJJ_PaxPay_AppThreadPool.FinishInMainThreadCallback<PosLink> callback) {
        Log.i("DEBUG", "Start Create POSLink");
        callback.onFinish(JJJ_PaxPay_POSLinkCreatorWrapper.create(context));
        Log.i("DEBUG", "Finish Create POSLink");
}}
