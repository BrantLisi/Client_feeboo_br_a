package com.brantf.wireless.a.common.base;

import android.content.Context;

import com.brantf.wireless.br_library.pref.BasePrefs;

public class UserPrefs extends BasePrefs {
    public static String UserPrefsName = "UserPrefs";

    public static String PRE_USER_REAL_NAME = "PreUserRealName";
    public static String PRE_USER_NAME_PRE = "PreUserNamePre";
    public static String PRE_USER_NAME = "PreUserName";
    public static String PRE_PUSH_USER_INFO = "PrePushUserInfo";
    public static String PRE_PUSH_USER_INFO_PRE = "PrePushUserInfoPre";
    public static String PRE_USER_PHONE = "PreUserPhone";
    public static String PRE_USER_ORG = "PreUserOrg";
    public static String PRE_USER_PHOTO = "PreUserPhoto";
    public static String PRE_USER_PASSWORD = "PreUserPassword";
    public static String PRE_USER_REMEMBER_PASSWORD = "PreUserRememberPassword";
    public static String AUTO_LOGIN = "AutoLogin";
    public static String USER_ID = "user_id";
    public static String USER_EMPLOYEE_ID = "user_employee_id";
    public static String USER_TYPE = "user_type";

    public static String UserFaceURL = "UserFaceURL";

    public static String CUR_USER_NAME = "CUR_USER_NAME";
    public static String UserPhone = "UserPhone";
    public static String UserEmail = "UserEmail";
    public static String NotFirstLogin = "NotFirstLogin";
    public static String FindNewVersion = "FindNewVersion";

    public static String Server_URL = "ServerUrl";

    public static String HAVE_OPEN_GUIDE = "HaveOpenGuide";
    public static String HAVE_DEL_TIMEOUT_PATROL = "have_del_timeout_patrol";//TODO 是否已经删除过期的巡检任务相关点位以及设备 下次更新删除(前面版本删除了任务其相关没删除导致扫描二维码点位显示过期任务)

    public static String IMAGES_SAVE_PATH = "image_save_path";
    public static String RECORD_AUDIO_SAVE_PATH = "record_audio_save_path";
    public static String RECORD_VIDEO_SAVE_PATH = "record_video_save_path";

    public static String DEVICEID = "device_id";

    public static String APP_OPEN = "app_open";
    public static String APP_UPDATE_TIP = "app_update_tip";

    public static String SYS_DATA_PRE_TIME = "sys_data_pre_time";
    public static String CUR_PROJECT_ID = "cur_project_id";
    public static String CUR_PROJECT_NAME = "cur_project_name";
    public static String CUR_PROJECT_TYPE = "cur_project_type";

    public static String SYS_DATA_DEVICE = "sys_data_device";
    public static String SYS_DATA_DEVICE_TYPE = "sys_data_device_type";
    public static String SYS_DATA_LOCATION = "sys_data_location";
    public static String SYS_DATA_ORG = "sys_data_org";
    public static String SYS_DATA_PRIORITY = "sys_data_priority";
    public static String SYS_DATA_FLOW = "sys_data_flow";
    public static String SYS_DATA_STYPE = "sys_data_service_type";
    public static String SYS_DATA_DTYPE = "sys_data_demand_type";
    public static String SYS_DATA_EVALUTE = "sys_data_service_evalute";

    public static String SETTING_PUSH = "system_push";

    public static String SETTING_CLR_TEMP = "clear_temp";
    public static String SETTING_CLR_OFFLINE = "clear_offline";
    public static String SETTING_CLR_PATROL_TASK = "clear_patrol_task";
    public static String SETTING_CLR_SETTING = "system_clear_setting";
    public static String SETTING_CLR_PUSH = "system_clear_push";

    public static String SETTING_NOTICE_SOUND = "set_notice_sound";
    public static String SETTING_NOTICE_VIBRATE = "set_notice_vibrate";
    public static String SETTING_NOTICE_LIGHT = "set_notice_light";

    public static String SHOW_DEFAULT_GRID = "show_default_grid";

    private static UserPrefs singleton = null;

    protected UserPrefs(Context context) {
        super(context);
        setNamespace(UserPrefs.UserPrefsName);
    }

    @Override
    protected String getModuleName() {
        return UserPrefsName;
    }

    public static UserPrefs getPrefs(Context context) {
        synchronized (LOCK_OBJ) {
            if (singleton == null) {
                singleton = new UserPrefs(context);
            }
        }
        return singleton;
    }
}
