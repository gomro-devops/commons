package cn.gomro.commons.restful.api.response.error;

public interface DefaultErrorMessageCode {

    // 500
    ErrorMessageCode InternalError = new ErrorMessageCode() {

        @Override
        public int getCode() {
            return 500;
        }

        @Override
        public String getMessage() {
            return "内部错误，需联系管理员";
        }

        @Override
        public String getReason() {
            return "通过是出现了未定义的Exceptions";
        }

        @Override
        public String getSolution() {
            return "联系管理员";
        }

        @Override
        public String getReference() {
            return "";
        }

    };
    // 404
    ErrorMessageCode DeprecatedInterface = new ErrorMessageCode() {

        @Override
        public int getCode() {
            return 404;
        }

        @Override
        public String getMessage() {
            return "接口不存在或已过期";
        }

        @Override
        public String getReason() {
            return "调用了已过期的接口";
        }

        @Override
        public String getSolution() {
            return "请查阅最新文档";
        }

        @Override
        public String getReference() {
            return "";
        }

    };
    // 400
    ErrorMessageCode BadRequestError = new ErrorMessageCode() {

        @Override
        public int getCode() {
            return 400;
        }

        @Override
        public String getMessage() {
            return "请求格式错误";
        }

        @Override
        public String getReason() {
            return "格式不符合预期";
        }

        @Override
        public String getSolution() {
            return "修改请求参数";
        }

        @Override
        public String getReference() {
            return "";
        }

    };
    // 202
    ErrorMessageCode GeneralError = new ErrorMessageCode() {
        @Override
        public int getCode() {
            return 202;
        }

        @Override
        public String getMessage() {
            return "错误，请根据提示操作";
        }

        @Override
        public String getReason() {
            return "请检查是否重复请求";
        }

        @Override
        public String getSolution() {
            return "系统收到了请求，但无法正确处理";
        }

        @Override
        public String getReference() {
            return "";
        }

    };
}
