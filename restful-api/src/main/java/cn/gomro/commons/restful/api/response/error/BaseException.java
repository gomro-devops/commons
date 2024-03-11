package cn.gomro.commons.restful.api.response.error;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6875838312173763613L;

    private ErrorMessageCode error;

    public <T> BaseException(String message, ErrorMessageCode error) {
        super(message);
        this.error = error;
    }

}
