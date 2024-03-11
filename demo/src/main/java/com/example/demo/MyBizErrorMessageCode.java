package com.example.demo;

import cn.gomro.commons.restful.api.response.error.ErrorMessageCode;

public interface MyBizErrorMessageCode {
    // 500
    ErrorMessageCode BizAAAError = new ErrorMessageCode() {

        @Override
        public int getCode() {
            return 105;
        }

        @Override
        public String getMessage() {
            return "请先操作A再操作此功能";
        }

        @Override
        public String getReason() {
            return "未操作A的单据不能操作B";
        }

        @Override
        public String getSolution() {
            return "先去操作A";
        }

        @Override
        public String getReference() {
            return "http://a的链接";
        }

    };
}
