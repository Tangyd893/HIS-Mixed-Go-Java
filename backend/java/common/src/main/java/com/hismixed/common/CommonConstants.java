package com.hismixed.common;

/**
 * 公共常量定义
 */
public final class CommonConstants {

    private CommonConstants() {
    }

    /** 认证请求头 Token 字段名 */
    public static final String HEADER_TOKEN = "Authorization";

    /** Token 前缀 */
    public static final String TOKEN_PREFIX = "Bearer ";

    /** 系统管理员角色 */
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    /** 医生角色 */
    public static final String ROLE_DOCTOR = "ROLE_DOCTOR";

    /** 护士角色 */
    public static final String ROLE_NURSE = "ROLE_NURSE";
}
