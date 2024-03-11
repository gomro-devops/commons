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
            return null;
        }

        @Override
        public String getSolution() {
            return null;
        }

        @Override
        public String getReference() {
            return null;
        }

        @Override
        public String[] getErrors() {
            return new String[0];
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
            return null;
        }

        @Override
        public String getSolution() {
            return null;
        }

        @Override
        public String getReference() {
            return null;
        }

        @Override
        public String[] getErrors() {
            return new String[0];
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
            return null;
        }

        @Override
        public String getSolution() {
            return null;
        }

        @Override
        public String getReference() {
            return null;
        }

        @Override
        public String[] getErrors() {
            return new String[0];
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
            return null;
        }

        @Override
        public String getSolution() {
            return null;
        }

        @Override
        public String getReference() {
            return null;
        }

        @Override
        public String[] getErrors() {
            return null;
        }
    };
}
