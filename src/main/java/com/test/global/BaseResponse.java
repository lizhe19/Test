package com.test.global;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class BaseResponse<T> {

    @JSONField(ordinal = 1)
    private Integer code;
    @JSONField(ordinal = 2)
    private Boolean success = false;
    @JSONField(ordinal = 3)
    private T result;

    public BaseResponse(int code) {
        this.code = code;
    }

    public BaseResponse() {
    }

    /**
     * 构造参数异常返回体
     *
     * @return
     */
    public static BaseResponse<String> genInvalidArgumentResult() {
        BaseResponse<String> baseResponse = new BaseResponse<String>(ResponseEnm.InvalidArgument_E.getResult());
        baseResponse
            .setResult(ResponseEnm.InvalidArgument_E.getI18nMessage());
        return baseResponse;
    }

    /**
     * 生成系统异常返回体
     *
     * @return
     */
    public static BaseResponse<String> genInternalServerErrorResult() {
        BaseResponse<String> baseResponse = new BaseResponse<String>(ResponseEnm.InternalServerError_E.getResult());
        baseResponse
            .setResult(ResponseEnm.InternalServerError_E.getI18nMessage());
        return baseResponse;
    }

    /**
     * 构造未授权返回体
     *
     * @return
     */
    public static BaseResponse<String> genUnauthorizedResult() {
        BaseResponse<String> baseResponse = new BaseResponse<>(ResponseEnm.Unauthorized_E.getResult());
        baseResponse.setResult(ResponseEnm.Unauthorized_E.getI18nMessage());
        return baseResponse;
    }

    /**
     * 构建禁止访问返回体
     *
     * @return
     */
    public static BaseResponse<String> genForiddenResult() {
        BaseResponse<String> baseResponse = new BaseResponse<>(ResponseEnm.Foridden_E.getResult());
        baseResponse.setResult(ResponseEnm.Foridden_E.getI18nMessage());
        return baseResponse;
    }
}
