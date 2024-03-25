package cn.gomro.commons.restful.api.response.error;

import java.sql.Time;

/**
 *
 * @since 2024/3/14 11:19
 * @author Adam 2024/3/14 11:19 说明: 通用性异常请参考：<a href="https://www.runoob.com/http/http-status-codes.html">HTTP 状态码</a> 尽可能保持一致性理解。
 */
public interface DefaultErrorMessageCode {

    // 500
    ErrorMessageCode InternalError = new ErrorMessageCodeImpl(500, "内部错误，需联系管理员",
            "通常是出现了未定义的Exceptions", "联系管理员", "");
    // 408
    ErrorMessageCode RequestTimeOut = new ErrorMessageCodeImpl(408, "用户超时",
            "服务器等待客户端发送的请求时间过长，超时", "请查阅最新文档", "");

    // 404
    ErrorMessageCode NotFoundError = new ErrorMessageCodeImpl(404, "资源不存在",
            "接口不存在或已过期，调用了已过期的接口", "请查阅最新文档", "");

    // 403
    ErrorMessageCode ForbiddenError = new ErrorMessageCodeImpl(403, "权限拒绝",
            "服务器理解请求客户端的请求，但是拒绝执行此请求。", "请检查是否有权限操作", "");

    // 400
    ErrorMessageCode BadRequestError = new ErrorMessageCodeImpl(400, "请求格式错误",
            "格式不符合预期", "修改请求参数", "");

    // 204
    ErrorMessageCode NoContentError = new ErrorMessageCodeImpl(204, "无内容",
            "无内容。服务器成功处理，但未返回内容。", "系统收到了请求，但无法正确处理", "");

    // 202
    ErrorMessageCode GeneralError = new ErrorMessageCodeImpl(202, "错误，请根据提示操作",
            "请检查是否重复请求", "系统收到了请求，但无法正确处理", "");

}
