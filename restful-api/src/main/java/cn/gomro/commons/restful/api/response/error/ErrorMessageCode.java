package cn.gomro.commons.restful.api.response.error;

import java.util.Arrays;

public enum ErrorMessageCode {

    GeneralError(202, "错误，请根据提示操作"),
    BadRequestError(400, "请求格式错误"),
    DeprecatedInterface(404, "接口不存在或已过期"),
    InternalError(500, "内部错误，需联系管理员"),
    ;

    public final int id;
    public final String name;

    ErrorMessageCode(int id, String name) {

        this.id = id;
        this.name = name;
    }

    public static ErrorMessageCode get(int id) {

        return Arrays.stream(ErrorMessageCode.values()).filter(v -> v.id == id).findFirst().orElse(null);
    }

    @Override
    public String toString() {

        return String.valueOf(id);
    }
}
