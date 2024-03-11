package cn.gomro.commons.restful.api.response.error;

/**
 * @author Adam 2024/3/11 8:31 说明: 统一业务异常码
 * @since 2024/3/11 8:31
 */

public interface ErrorMessageCode {

    int getCode();
    String getMessage();
    String getReason();
    String getSolution();
    String getReference();

}
