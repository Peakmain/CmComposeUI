/*
 * Copyright (c) 2014,KJFrameForAndroid Open Source Project,张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peakmain.androidapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;


import java.util.regex.Pattern;

/**
 * @author ：Peakmain
 * createTime：2026/4/13
 * mail:2726449200@qq.com
 * describe：系统信息工具包<
 */
//@SuppressLint("SimpleDateFormat")
public final class SystemTool {
//    public static final String NETWORK_TYPE_WIFI = "wifi";
//    public static final String NETWORK_TYPE_3G = "eg";
//    public static final String NETWORK_TYPE_2G = "2g";
//    public static final String NETWORK_TYPE_WAP = "wap";
//    public static final String NETWORK_TYPE_UNKNOWN = "unknown";
//    public static final String NETWORK_TYPE_DISCONNECT = "disconnect";
//    private static String mDeviceImei;
//
//    /**
//     * 指定格式返回当前系统时间
//     */
//    public static String getDataTime(String format) {
//        SimpleDateFormat df = new SimpleDateFormat(format);
//        return df.format(new Date());
//    }
//
//    /**
//     * 返回当前系统时间(格式以HH:mm形式)
//     */
//    public static String getDataTime() {
//        return getDataTime("HH:mm");
//    }
//
//    /**
//     * 获取手机IMEI码
//     */
//    public static String getPhoneIMEI(Context cxt) {
//        TelephonyManager tm = (TelephonyManager) cxt
//                .getSystemService(Context.TELEPHONY_SERVICE);
//        return tm.getDeviceId();
//    }
//

    /**
     * 获取手机系统SDK版本
     *
     * @return 如API 17 则返回 17
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机系统版本号
     *
     * @return 形如2.3.3
     */
    public static String getDeviceSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }
    /**
     * 获取完整设备名，如：OPPO Find X8
     */
    public static String getDeviceFullName() {
        String brand = Build.BRAND == null ? "" : Build.BRAND.trim();
        String model = Build.MODEL == null ? "" : Build.MODEL.trim();
        if (brand.isEmpty()) {
            return model;
        }
        if (model.isEmpty()) {
            return brand;
        }
        return brand + " " + model;
    }
//
//    /**
//     * 调用系统发送短信
//     */
//    public static void sendSMS(Context cxt, String smsBody) {
//        Uri smsToUri = Uri.parse("smsto:");
//        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
//        intent.putExtra("sms_body", smsBody);
//        cxt.startActivity(intent);
//    }

    /**
     * 判断网络是否连接
     */
    public static boolean checkNet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null;// 网络是否连接
    }

//
//    /**
//     * 判断是否为wifi联网
//     */
//    public static boolean isWiFi(Context cxt) {
//        ConnectivityManager cm = (ConnectivityManager) cxt
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        // wifi的状态：ConnectivityManager.TYPE_WIFI
//        // 3G的状态：ConnectivityManager.TYPE_MOBILE
//        State state = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
//        return State.CONNECTED == state;
//    }

    /**
     * 检测当的网络（WLAN、3G/2G）状态是否可用
     *
     * @param context Context
     * @return true 表示网络可用,false 表示网络不可用
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    // 当前网络是连接的
                    // 当前所连接的网络可用
                    return info.getState() == State.CONNECTED;
                }
            }
        }
        return false;
    }
//
//    /**
//     * 隐藏系统键盘
//     * <p/>
//     * <br>
//     * <b>警告</b> 必须是确定键盘显示时才能调用
//     */
//    public static void hideKeyBoard(Activity aty) {
//        ((InputMethodManager) aty
//                .getSystemService(Context.INPUT_METHOD_SERVICE))
//                .hideSoftInputFromWindow(
//                        aty.getCurrentFocus().getWindowToken(),
//                        InputMethodManager.HIDE_NOT_ALWAYS);
//    }
//
//    //此方法，如果显示则隐藏，如果隐藏则显示
//    public static void hintKbOne(Activity aty) {
//        InputMethodManager imm = (InputMethodManager) aty.getSystemService(Context.INPUT_METHOD_SERVICE);
//        // 得到InputMethodManager的实例
//        if (imm.isActive()) {
//            // 如果开启
//            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
//                    InputMethodManager.HIDE_NOT_ALWAYS);
//
//        }
//    }

    //此方法只是关闭软键盘
    public static void hintKbTwo(Activity aty) {
        InputMethodManager imm = (InputMethodManager) aty.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && aty.getCurrentFocus() != null) {
            if (aty.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(aty.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 隐藏输入法
     *
     * @param activity activity
     */
    public static void hideSoftInput(Activity activity) {
        View currentFocusView = activity.getCurrentFocus();
        hideSoftInput(currentFocusView);
    }

    /**
     * 隐藏输入法
     *
     * @param currentFocusView 当前焦点view
     */
    public static void hideSoftInput(View currentFocusView) {
        if (currentFocusView != null) {
            IBinder token = currentFocusView.getWindowToken();
            if (token != null) {
                InputMethodManager im = (InputMethodManager) currentFocusView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(token, 0);
            }
        }
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception ignored) {
        }
        return versionName;
    }



    public static boolean checkMobile(String mobile) {
        String regex = "(\\+\\d+)?1\\d{10}$";
        return Pattern.matches(regex, mobile);
    }

    /**
     * 检测是否安装支付宝
     *
     * @return * @param context
     */
    public static boolean checkAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;

    }


}