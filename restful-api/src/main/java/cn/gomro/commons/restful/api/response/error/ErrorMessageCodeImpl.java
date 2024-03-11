package cn.gomro.commons.restful.api.response.error;

import cn.gomro.commons.restful.api.response.error.ErrorMessageCode;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Adam 2024/3/11 16:37 说明: 可声明特定的异常
 * @since 2024/3/11 16:37
 */
@Data
@AllArgsConstructor
public class ErrorMessageCodeImpl implements ErrorMessageCode {

    private int code;
    private String message;
    private String reason;
    private String solution;
    private String reference;

}
