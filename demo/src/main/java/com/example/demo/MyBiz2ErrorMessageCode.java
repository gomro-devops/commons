package com.example.demo;

import cn.gomro.commons.restful.api.response.error.DefaultErrorMessageCode;
import cn.gomro.commons.restful.api.response.error.ErrorMessageCode;
import cn.gomro.commons.restful.api.response.error.ErrorMessageCodeImpl;

public interface MyBiz2ErrorMessageCode extends DefaultErrorMessageCode {
    // 105
    ErrorMessageCode BizAAAError2 = new ErrorMessageCodeImpl(106, "请先操作A再操作此功能2",
            "未操作A的单据不能操作B2", "先去操作A2", "/a/link2");
}
