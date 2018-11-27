package com.books.notebasecore.constants;

public class BaseConstants {
    // 默认分页常量,每页10条
    public static final int PAGE_SIZE_DEFAULT = 20;

    // 默认分页常量,每页10条
    public static final int PAGE_SIZE_DEFAULT_10 = 10;

    // 默认索引页常量
    public static final int PAGE_INDEX_DEFAULT = 1;

    // 默认状态常量
    public static final int STATUS_DEFAULT = 2;

    public static final String IMAGE_KEY = "lkjhgfdsa"; // image路径加密key

    public static final String IMAGE_KEY_IV = "1899b9ec4fde"; // image路径加密IV

    /**
     * 显示平台，01-ios，11-android，21-h5，22-微信公众号，31-pc,41-后台下单
     */
    public final static String PLATFORM_01 = "01";

    public final static String PLATFORM_11 = "11";

    public final static String PLATFORM_21 = "21";

    public final static String PLATFORM_22 = "22";

    public final static String PLATFORM_31 = "31";

    public final static String PLATFORM_41 = "41";

    /**
     * 是否可用，0-不可用，1-可用
     */
    public final static Integer IS_ENABLE_0 = 0;

    public final static Integer IS_ENABLE_1 = 1;

    /**
     * 短信验证码类型，0-注册短信，1-重置密码短信，2-绑定手机号码短信,3-提现金额短信
     */
    public static final int SMS_CODE_STATUS_0 = 0;

    public static final int SMS_CODE_STATUS_1 = 1;

    public static final int SMS_CODE_STATUS_2 = 2;

    public static final int SMS_CODE_STATUS_3 = 3;

    public final static String IS_ACTIVE_Y = "Y";

    public final static String IS_ACTIVE_A = "A";

}
