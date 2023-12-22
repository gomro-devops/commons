package cn.gomro.commons.restful.api.response.error;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6875838312173763613L;

    // 202 是http状态码 服务器已接受请求，但尚未处理。
    private long httpCode = 202;
    private ErrorData errorData;

    public BaseException(String message) {
        super(message);
    }

    public <T> BaseException(long code, String message, T data) {
        super(message);
        this.httpCode = code;
        if (data instanceof ErrorData) {
            this.errorData = (ErrorData) data;
        }
    }

}
