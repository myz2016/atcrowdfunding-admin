package com.mfh.crowd.funding.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mfh
 * @date 2020/1/31 15:31
 */
public class CrowdFundingConstant {
    public static final String ATTR_NAME_MESSAGE = "MESSAGE";
    public static final String ATTR_NAME_LOGIN_ADMIN = "LOGIN-ADMIN";
    public static final String ATTR_NAME_PAGE_INFO = "PAGE-INFO";

    public static final String MESSAGE_LOGIN_FAILED = "用户名或密码错误，请核对后重新登录！";
    public static final String MESSAGE_CODE_INVALID = "明文不是有效字符串，请核对后再操作！";

    public static final String MESSAGE_ACCESS_DENIED = "请登录后在操作!";

    public static final String MESSAGE_LOGIN_ACCT_ALREADY_IN_USE = "登录账号被占用，请重新设定！";

    public static final Map<String, String> EXCEPTION_MESSAGE_MAP = new HashMap<String, String>(){{
        put("java.lang.ArithmeticException", "系统在进行数学运算时发生异常");
        put("java.lang.RuntimeException", "系统在运行时发生错误");
    }};
}
