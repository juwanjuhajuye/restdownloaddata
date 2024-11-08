package com.example.jjbfather.jjjqsrt2go2gorestdownload;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.Vector;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-04.
 */
public class APIDownLoad_salon_storeservice_option_item extends AsyncTask {

    /** 데이터베이스 테이블 필드값 선언 및 초기화 ********************/
    /** 테이블에 따라 여기를 수정해야 함 ********************/
    String mDbTableName = "salon_storeservice_option_item";

    String idx = "";
    String scode = "";
    String sidx = "";
    String midx = "";
    String svcidx = "";
    String optionidx = "";
    String itemname = "";
    String itemprice = "";
    String itemuseyn = "";
    String sortnum = "";
    String wdate = "";

    String totaloptionitem = "";
    /**********************************************************/

    String mInsertSqlQuery = "";
    String mUpdateSqlQuery = "";
    String mQueryCollection = "";

    String mStrUrl = GlobalMemberValues.API_WEB_URL + "?" +
            "RequestSqlType=" + mDbTableName + "&sidx=" + GlobalMemberValues.STORE_INDEX + "&RType=" + GlobalMemberValues.API_WEB_DB_XML_TYPE +
            "&midx=" + CloudBackOffice.mCategoryIdx + "&appversioncode=" + GlobalMemberValues.getAppVersionCode();

    String tagName = "";
    // API ReturnCode
    String mApiReturnCode = "";
    URL mUrl;
    boolean mFlag;
    public Vector<String> sqlQueryVec = new Vector<String>();
    public Vector<String> sqlQueryVecIns = new Vector<String>();

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            GlobalMemberValues gmvs = new GlobalMemberValues();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            GlobalMemberValues.logWrite("menudownloadlog", "url : " + mStrUrl + "\n");

            mUrl = new URL(mStrUrl);
            InputStream in = mUrl.openStream();
            xpp.setInput(in, "UTF-8");
            int eventType = xpp.getEventType();
            boolean isCheckTag = false;
            boolean isDataTag = false;
            boolean isPossible = false;
            boolean isReturnCode = false;

            if (!GlobalMemberValues.isStrEmpty(CloudBackOffice.mCategoryIdx)) {
                String tempSpt[] = CloudBackOffice.mCategoryIdx.split("JJJ");
                String tempSql = "";
                for (int i = 0; i < tempSpt.length; i++) {
                    tempSql = "delete from " + mDbTableName;
                    tempSql += " where midx = '" + tempSpt[i] + "' ";
                    sqlQueryVecIns.add(tempSql);
                }
            } else {
                String tempSql = "";
                tempSql = "delete from " + mDbTableName;
                sqlQueryVecIns.add(tempSql);
            }

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
                            GlobalMemberValues.logWrite("salon_storeservice_option_item", "성공" + "\n");
                        } else {
                            isPossible = false;
                            GlobalMemberValues.logWrite("salon_storeservice_option_item", "에러" + "\n");
                        }
                        GlobalMemberValues.logWrite("salon_storeservice_option_item", "ReturnCode : " + mApiReturnCode + "\n");
                    }

                    if (isPossible && isDataTag) {
                        //GlobalMemberValues.logWrite("salon_storeservice_option_item", "성공일때만.." + "\n");
                        if (tagName.equals("idx")) {
                            idx = xpp.getText();
                        }
                        if (tagName.equals("scode")) {
                            scode = xpp.getText();
                        }
                        if (tagName.equals("sidx")) {
                            sidx = xpp.getText();
                        }
                        if (tagName.equals("midx")) {
                            midx = xpp.getText();
                        }
                        if (tagName.equals("svcidx")) {
                            svcidx = xpp.getText();
                        }
                        if (tagName.equals("optionidx")) {
                            optionidx = xpp.getText();
                        }
                        if (tagName.equals("itemname")) {
                            itemname = xpp.getText();
                        }
                        if (tagName.equals("itemprice")) {
                            itemprice = xpp.getText();
                        }
                        if (tagName.equals("itemuseyn")) {
                            itemuseyn = xpp.getText();
                        }
                        if (tagName.equals("sortnum")) {
                            sortnum = xpp.getText();
                        }
                        if (tagName.equals("wdate")) {
                            wdate = xpp.getText();
                        }

                        if (tagName.equals("totaloptionitem")) {
                            totaloptionitem = xpp.getText();
                        }

                    }



                } else if (eventType == XmlPullParser.END_TAG) {
                    tagName = xpp.getName();

                    if (tagName.equals("ReturnCode")) {
                        isReturnCode = false;
                    }
                    if (tagName.equals("Data")) {
                        // 등록쿼리 조합하기
                        mInsertSqlQuery = "insert into " + mDbTableName +
                                " ( " +
                                " idx, scode, sidx, midx, svcidx, optionidx, itemname, itemprice, " +
                                " itemuseyn, sortnum, wdate " +
                                " ) " +
                                " values (" +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(idx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(scode, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(sidx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(midx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(svcidx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(optionidx, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(itemname, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(itemprice, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(itemuseyn, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(sortnum, 0) + "', " +
                                "'" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "' " +
                                ")";


                        // 수정쿼리 조합하기
                        mUpdateSqlQuery = "update " + mDbTableName + " set " +
                                " scode = '" + GlobalMemberValues.getDBTextAfterChecked(scode, 0) + "', " +
                                " sidx = '" + GlobalMemberValues.getDBTextAfterChecked(sidx, 0) + "', " +
                                " midx = '" + GlobalMemberValues.getDBTextAfterChecked(midx, 0) + "', " +
                                " svcidx = '" + GlobalMemberValues.getDBTextAfterChecked(svcidx, 0) + "', " +
                                " optionidx = '" + GlobalMemberValues.getDBTextAfterChecked(optionidx, 0) + "', " +
                                " itemname = '" + GlobalMemberValues.getDBTextAfterChecked(itemname, 0) + "', " +
                                " itemprice = '" + GlobalMemberValues.getDBTextAfterChecked(itemprice, 0) + "', " +
                                " itemuseyn = '" + GlobalMemberValues.getDBTextAfterChecked(itemuseyn, 0) + "', " +
                                " sortnum = '" + GlobalMemberValues.getDBTextAfterChecked(sortnum, 0) + "', " +
                                " wdate = '" + GlobalMemberValues.getDBTextAfterChecked(wdate, 0) + "' " +
                                " where idx = " + idx;

                        sqlQueryVecIns.add(mInsertSqlQuery);

                        // 저장된 데이터가 있는지 검색해서 데이터기 있으면 update 없으면 insert 한다.
//                        if (gmvs.isDataInDBTable(mDbTableName, idx)) {
//                            sqlQueryVecIns.add(mUpdateSqlQuery);
//                        } else {
//                            sqlQueryVecIns.add(mInsertSqlQuery);
//                        }

                        // Vector 에 담을 값 조합하기 (인덱스 | 등록쿼리 | 수정쿼리)
                        mQueryCollection = idx + "||" + mInsertSqlQuery + "||" + mUpdateSqlQuery;

                        // 멤버필드에 선언한 Vector 변수에 담는다.
                        sqlQueryVec.addElement(mQueryCollection);

                        GlobalMemberValues.logWrite("salon_storeservice_option_item", "mQueryCollection : " + mQueryCollection);

                        // 화면재개 지연시간 지정
                        if (!GlobalMemberValues.isStrEmpty(totaloptionitem)) {
                            GlobalMemberValues.RESTARTSCREEN_DELYTIME = totaloptionitem;
                        }

                        /** 멤버변수 초기화 ******************************/
                        isDataTag = false;
                        mInsertSqlQuery = "";
                        mUpdateSqlQuery = "";
                        mQueryCollection = "";

                        // 테이블 멤버관련변수 초기화
                        idx = "";
                        scode = "";
                        sidx = "";
                        midx = "";
                        svcidx = "";
                        optionidx = "";
                        itemname = "";
                        itemprice = "";
                        itemuseyn = "";
                        sortnum = "";
                        wdate = "";
                        totaloptionitem = "";
                        /***********************************************/
                    }
                }
                eventType = xpp.next();
            }

            mFlag = true;       // true : 지정된 XML 파일을 읽고 필요한 데이터를 추출해서 저장완료된 상태

        } catch (Exception e) {
            GlobalMemberValues.logWrite("salon_storeservice_option_item", "예외발생 : " + e.getMessage());
        }

        if (GlobalMemberValues.downloadDataInsUpdDelType > 0) {
            String returnResult = "";
            if (sqlQueryVecIns.size() > 0) {
                returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResult(sqlQueryVecIns);
            }
            GlobalMemberValues.logWrite(mDbTableName, "DB 처리결과 : " + returnResult + "\n");
        }

        for (String strVec : sqlQueryVec) {
            GlobalMemberValues.logWrite("jjjapiquerylog", "----------------------------\n");
            GlobalMemberValues.logWrite("jjjapiquerylog", "VECTOR DATA : " + strVec + "\n");
            GlobalMemberValues.logWrite("jjjapiquerylog", "----------------------------\n");
        }
        return null;
    }
}
