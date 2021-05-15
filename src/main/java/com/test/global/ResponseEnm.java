package com.test.global;

/**
 * Created by 27463 on 2017/9/18.
 */
public enum ResponseEnm {

    OK_E(200, "OK"),
    OK(200, "成功"),
    //不支持的参数
    InvalidArgument_E(400, "InvalidArgument"),
    InvalidArgument(400, "不合法的参数"),
    //未授权
    Unauthorized_E(401, "Unauthorized"),
    Unauthorized(401, "未授权"),
    //授权失败
    AuthorizeFail_E(402, "AuthorizeFail"),
    AuthorizeFail(402, "授权失败"),
    //禁止访问
    Foridden_E(403, "Foridden"),
    Foridden(403, "禁止访问"),
    //未找到
    NotFound_E(404, "NotFound"),
    NotFound(404, "未找到访问资源"),

    //系统异常
    InternalServerError_E(500, "InternalServerError"),
    InternalServerError(500, "系统异常"),

    Failed_E(520, "Failed"),
    Failed(520, "失败");

    private int result;
    private String i18nMessage;

    ResponseEnm(int code, String msg) {
        this.result = code;
        this.i18nMessage = msg;
    }

    public int getResult() {
        return result;
    }

    public String getI18nMessage() {
        return i18nMessage;
    }
}
