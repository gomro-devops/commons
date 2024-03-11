package com.example.demo;

import cn.gomro.commons.restful.api.response.error.BaseException;
import cn.gomro.commons.restful.api.response.error.ErrorMessageCode;

/**
 *
 * @since 2024/3/11 16:37
 * @author Adam 2024/3/11 16:37 说明: 可声明特定的异常
 */
public class MyXXXBizExeception extends BaseException implements ErrorMessageCode {
    public MyXXXBizExeception(String message, ErrorMessageCode error) {
        super(message, error);
    }

    @Override
    public int getCode() {
        return 102;
    }

    @Override
    public String getReason() {
        return "特定错误";
    }

    @Override
    public String getSolution() {
        return "特定解决方案";
    }

    @Override
    public String getReference() {
        return "入口指引https://";
    }
}
