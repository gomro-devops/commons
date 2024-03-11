package com.example.demo;

import cn.gomro.commons.restful.api.response.error.DefaultErrorMessageCode;
import cn.gomro.commons.restful.api.response.error.ErrorMessageCode;
import cn.gomro.commons.restful.api.response.error.ErrorMessageCodeImpl;

public interface MyBizErrorMessageCode extends DefaultErrorMessageCode {
    // 105
    ErrorMessageCode BizAAAError = new ErrorMessageCodeImpl(105, "请先操作A再操作此功能",
            "未操作A的单据不能操作B", "先去操作A", "/a/link");
}
