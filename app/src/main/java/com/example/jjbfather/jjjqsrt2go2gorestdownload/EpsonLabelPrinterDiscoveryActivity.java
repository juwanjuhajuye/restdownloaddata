package com.example.jjbfather.jjjqsrt2go2gorestdownload;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.epson.epos2.discovery.Discovery;
import com.epson.epos2.discovery.DiscoveryListener;
import com.epson.epos2.discovery.FilterOption;
import com.epson.epos2.discovery.DeviceInfo;
import com.epson.epos2.Epos2Exception;

public class EpsonLabelPrinterDiscoveryActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Context mContext = null;
    private ArrayList<HashMap<String, String>> mPrinterList = null;
    private SimpleAdapter mPrinterListAdapter = null;
    private FilterOption mFilterOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epson_label_discovery);

        mContext = this;

        Button button = (Button)findViewById(R.id.btnRestart);
        button.setOnClickListener(this);

        mPrinterList = new ArrayList<HashMap<String, String>>();
        mPrinterListAdapter = new SimpleAdapter(this, mPrinterList, R.layout.epson_discovery_row,
                new String[] { "PrinterName", "Target" },
                new int[] { R.id.PrinterName, R.id.Target });
        ListView list = (ListView)findViewById(R.id.lstReceiveData);
        list.setAdapter(mPrinterListAdapter);
        list.setOnItemClickListener(this);

        mFilterOption = new FilterOption();
        mFilterOption.setDeviceType(Discovery.TYPE_PRINTER);
        mFilterOption.setEpsonFilter(Discovery.FILTER_NAME);
        mFilterOption.setUsbDeviceName(Discovery.TRUE);

        try {
            Discovery.start(this, mFilterOption, mDiscoveryListener);
        }
        catch (Exception e) {
//            ShowMsg.showException(e, "start", mContext);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        while (true) {
            try {
                Discovery.stop();
                break;
            }
            catch (Epos2Exception e) {
                if (e.getErrorStatus() != Epos2Exception.ERR_PROCESSING) {
                    break;
                }
            }
        }

        mFilterOption = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRestart:
                restartDiscovery();
                break;

            default:
                // Do nothing
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();

        HashMap<String, String> item  = mPrinterList.get(position);
        // For USB, change the target to "USB:".
        // The USB port changes each time the printer restarts.
        // Please refer to the manual for details.
        String target = item.get("Target");
        if(target.startsWith("USB:")){
//            target = "USB:";
        }

        intent.putExtra("Target",target);

        setResult(RESULT_OK, intent);

        finish();
    }

    private void restartDiscovery() {
        while (true) {
            try {
                Discovery.stop();
                break;
            }
            catch (Epos2Exception e) {
                if (e.getErrorStatus() != Epos2Exception.ERR_PROCESSING) {
//                    ShowMsg.showException(e, "stop", mContext);
                    return;
                }
            }
        }

        mPrinterList.clear();
        mPrinterListAdapter.notifyDataSetChanged();

        try {
            Discovery.start(this, mFilterOption, mDiscoveryListener);
        }
        catch (Exception e) {
//            ShowMsg.showException(e, "stop", mContext);
        }
    }

    private DiscoveryListener mDiscoveryListener = new DiscoveryListener() {
        @Override
        public void onDiscovery(final DeviceInfo deviceInfo) {
            runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    HashMap<String, String> item = new HashMap<String, String>();
                    item.put("PrinterName", deviceInfo.getDeviceName());
                    item.put("Target", deviceInfo.getTarget());
                    mPrinterList.add(item);
                    mPrinterListAdapter.notifyDataSetChanged();
                }
            });
        }
    };
}
