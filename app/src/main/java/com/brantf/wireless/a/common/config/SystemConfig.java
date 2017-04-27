package com.brantf.wireless.a.common.config;

import com.brantf.wireless.a.BuildConfig;

public class SystemConfig {
    /**
     * xia 使用
     */
    public static final String DATABASE_NAME = BuildConfig.DB_NAME;
    public static final String APP_KEY = BuildConfig.APP_KEY;
    public static final String APP_SECRET = BuildConfig.APP_SECRET;
    /**
     * xia 使用
     */
    //  public static String SERVER_URL = "http://facilitymanagement.cn:9090";
//    public static String SERVER_URL = "http://facilityone.dlinkddns.com:8082";//default
//    public static String SERVER_URL = "http://192.168.1.144:8082";//
    public static String SERVER_URL = BuildConfig.SERVER_URL;//ida
//    public static String SERVER_URL = "https://fmone.cn";
//    public static String SERVER_URL = "https://wechat.fmone.cn";
//    public static String SERVER_URL = "http://203.156.255.212";//东湖
//    public static String SERVER_URL = "http://182.61.17.69";//豪曼空调
//    public static String SERVER_URL = "http://180.168.142.194:8888"; // 一建地铁
//    public static String SERVER_URL = "https://saas.fmone.cn";// SAAS
//    public static String SERVER_URL = "http://drfz.neusoft.edu.cn";// Neusoft 东软
}
