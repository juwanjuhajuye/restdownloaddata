package com.example.jjbfather.jjjqsrt2go2gorestdownload;

import android.content.Context;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.Vector;

public class API_appinstanceidupload_tocloud extends AsyncTask {
    String mReturnValue = "";

    String mStrUrl = "";

    String tagName = "";
    // API ReturnCode
    String mApiReturnCode = "";
    URL mUrl;
    boolean mFlag;
    public Vector<String> sqlQueryVec = new Vector<String>();

    public API_appinstanceidupload_tocloud(Context paramContext, String paramStr) {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            if (!GlobalMemberValues.isStrEmpty(paramStr)) {
                mStrUrl = GlobalMemberValues.API_WEB + "API_UP_AppInstanceId_ForAndroid.asp?" + paramStr;
                /**
                 mStrUrl = "http://nzmsaloncloud.mvppos.com/api/API_UP_AppInstanceId_ForAndroid.asp?sidx=132
                 &stcode=ST02GPEFF26487409&appinstanceid=";
                 **/

                GlobalMemberValues.logWrite("ApiMStrUrl", "url : " + mStrUrl + "\n");
            }
        } else {
            GlobalMemberValues.displayDialog(paramContext, "Waraning", "Internet is not connected", "Close");
        }
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            mUrl = new URL(mStrUrl);
            InputStream in = mUrl.openStream();
            xpp.setInput(in, "UTF-8");
            int eventType = xpp.getEventType();
            boolean isDataTag = false;
            boolean isPossible = false;
            boolean isReturnCode = false;

            while (eventType != XmlPullParser.END_DOCUMENT){
                if (eventType == XmlPullParser.START_TAG) {
                    tagName = xpp.getName();
                    if (tagName.equals("ReturnCode")) {
                        isReturnCode = true;
                    }
                    if (tagName.equals("Data")) {
                        isReturnCode = false;
                        isDataTag = true;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    if (isReturnCode && tagName.equals("ReturnCode")) {
                        mApiReturnCode = xpp.getText();
                        if (mApiReturnCode.equals("00")) {
                            isPossible = true;
                            GlobalMemberValues.logWrite("API_inoutdataupload_tocloudClass", "00000 .." + "\n");
                        } else {
                            isPossible = false;
                        }
                    }

                    if (isPossible && isDataTag) {
                        GlobalMemberValues.logWrite("API_inoutdataupload_tocloudClass", "성공일때만.." + "\n");
                        GlobalMemberValues.logWrite("API_inoutdataupload_tocloudClass", "ReturnValue : " + xpp.getText() + "\n");
                        if (tagName.equals("ReturnValue")) {
                            mReturnValue = xpp.getText();
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    tagName = xpp.getName();

                    if (tagName.equals("ReturnCode")) {
                        isReturnCode = false;
                    }
                }
                eventType = xpp.next();
            }

            mFlag = true;       // true : 지정된 XML 파일을 읽고 필요한 데이터를 추출해서 저장완료된 상태

        } catch (Exception e) {
            // 예외발생
            GlobalMemberValues.logWrite("API_inoutdataupload_tocloudClass", "error : " + e.getMessage().toString() + "\n");
        }
        return null;
    }
}
