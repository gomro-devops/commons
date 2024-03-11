package cn.gomro.commons.restful.api.response.error;

/**
 * @author Adam 2024/3/11 8:31 说明: 统一业务异常码
 * @since 2024/3/11 8:31
 */

public interface ErrorMessageCode {

    /**
     * 给每个  项目/service/method  编号
     * 仅需要定义最后一层method内的业务异常；
     * <p>
     * 统一业务异常码不具有复用性；
     * 统一系统异常码可复用；
     */

    public int getCode();
    public String getMessage();
    public String getReason();
    public String getSolution();
    public String getReference();
    public String[] getErrors();


}
