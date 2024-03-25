package cn.gomro.commons.restful.api.exception;

import cn.gomro.commons.restful.api.response.RestResponse;
import cn.gomro.commons.restful.api.response.error.BizError;
import cn.gomro.commons.restful.api.response.error.DefaultErrorMessageCode;
import cn.gomro.commons.utils.RequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ResponseBody
    @ExceptionHandler
    public RestResponse<?> exception(Exception ex, WebRequest request) {
        if (log.isDebugEnabled()) {
            RequestUtil.debug(request);
        }

        BizError errorData = new BizError(DefaultErrorMessageCode.InternalError, ex);
        return new RestResponse<>(DefaultErrorMessageCode.InternalError.getCode(),
                ex.getMessage(), 0, null, errorData);
    }

}
