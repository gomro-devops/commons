package cn.gomro.commons.restful.api.response.error;

public interface DefaultErrorMessageCode {

    // 500
    ErrorMessageCode InternalError = new ErrorMessageCodeImpl(500, "内部错误，需联系管理员",
            "通常是出现了未定义的Exceptions", "联系管理员", "");
    // 404
    ErrorMessageCode DeprecatedInterface = new ErrorMessageCodeImpl(404, "接口不存在或已过期",
            "调用了已过期的接口", "请查阅最新文档", "");

    // 400
    ErrorMessageCode BadRequestError = new ErrorMessageCodeImpl(400, "请求格式错误",
            "格式不符合预期", "修改请求参数", "");

    // 202
    ErrorMessageCode GeneralError = new ErrorMessageCodeImpl(202, "错误，请根据提示操作",
            "请检查是否重复请求", "系统收到了请求，但无法正确处理", "");
}
