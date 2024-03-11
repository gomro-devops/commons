package cn.gomro.commons.restful.api.response;

import cn.gomro.commons.log.FriendlyErrorLog;
import cn.gomro.commons.restful.api.response.error.BaseException;
import cn.gomro.commons.restful.api.response.error.BizError;
import cn.gomro.commons.restful.api.response.error.DefaultErrorMessageCode;
import cn.gomro.commons.restful.api.response.error.ErrorMessageCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationConfigurationException;
import org.springframework.util.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Data
@ToString(exclude = "data")
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "响应消息体")
public class RestResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -7257670320035598086L;

    @Schema(description = "响应号")
    private String id = UUID.randomUUID().toString();

    @Schema(description = "状态码")
    private long code = 200;

    @Schema(description = "描述")
    private String message = "";

    @Schema(description = "数据总数量")
    private long count = 0L;

    @Schema(description = "数据对象")
    private T data = null;

    @Schema(description = "报错时的错误信息 状态码不正确时需封装 ErrorData 输出")
    private BizError error;

    @Schema(description = "产生时间")
    private String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    public RestResponse(long code, String message, long count, T data, BizError error) {
        this.code = code;
        this.message = message;
        this.count = count;
        this.data = data;
        this.error = error;
    }

    public static <V> RestResponse<V> success(String msg, long count, V data) {

        return new RestResponse<V>(200, msg, count, data, null);
    }

    public static <V> RestResponse<V> success(long count, V data) {

        return new RestResponse<V>(200, "成功", count, data, null);
    }

    /**
     * @author Adam 2024/3/11 8:57 说明: 成功 返回消息和数据
     */
    public static <V> RestResponse<V> success(String msg, V data) {

        return success(msg, 0, data);
    }

    /**
     * @author Adam 2024/3/11 8:57 说明: 成功 返回数量
     */
    public static <V> RestResponse<V> success(long count) {

        return success("成功", count, null);
    }

    /**
     * @author Adam 2024/3/11 8:57 说明: 成功 返回消息
     */
    public static <V> RestResponse<V> success(String msg) {

        return success(msg, 0, null);
    }

    /**
     * @author Adam 2024/3/11 8:57 说明: 成功 不返回任何数据
     */
    public static <V> RestResponse<V> success() {

        return success("成功", 0, null);
    }

    /**
     * @author Adam 2024/3/11 9:58 说明: 仅保留一种失败手段
     */
    @SneakyThrows
    public static <V> RestResponse<V> failed(ErrorMessageCode error, Exception exception) {

        // 获取当前线程的堆栈跟踪信息
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        // 找到上一步调用的方法
        int index = 2;
        StackTraceElement caller = stackTraceElements[index];
        String name = RestResponse.class.getName();
        while (Objects.equals(name, caller.getClassName())) {
            index++;
            caller = stackTraceElements[index];
        }

        // 获取上一步调用方法的方法名
        String methodName = caller.getMethodName();
        String className = caller.getClassName();
        Class<?> clazz = Class.forName(className);
        Method method = clazz.getDeclaredMethod(methodName);

        //获取项目编码
        String applicationName = System.getProperty("applicationName");
        String applicationCode = System.getProperty("applicationCode");
        if (ObjectUtils.isEmpty(applicationCode)) {
            throw new RuntimeException("无法获取项目名, 请检查系统属性 applicationName");
        }
        if (ObjectUtils.isEmpty(applicationCode)) {
            throw new RuntimeException("无法获取项目编码, 请检查系统属性 applicationCode");
        }

        //获取类编码
        Schema clazzAnnotation = clazz.getAnnotation(Schema.class);
        if (clazzAnnotation == null) {
            throw new AnnotationConfigurationException("无法获取错误码, 请检查类的 Operation.class 注解");
        }
        String clazzDescription = clazzAnnotation.description();
        String clazzId = clazzAnnotation.$id();

        //获取方法编码
        Operation methodAnnotation = method.getAnnotation(Operation.class);
        if (methodAnnotation == null) {
            throw new AnnotationConfigurationException("无法获取错误码, 请检查方法的 Operation.class 注解");
        }
        String methodSummary = methodAnnotation.summary();
        String operationId = methodAnnotation.operationId();

        long code = -1L;
        try {
            String bizErrorCode = applicationCode + clazzId + operationId + error.getCode();
            code = Long.parseLong(bizErrorCode);
        } catch (NumberFormatException e) {
            FriendlyErrorLog.error(e);
            throw new RuntimeException("错误码转换失败, 请检查"
                    + applicationCode + "_" + clazzId + "_" + operationId + "_" + error.getCode());
        }
        //封装错误信息
        BizError errorData = new BizError(error, exception);

        String msg = applicationName + "/" + clazzDescription + "/" + methodSummary + "/" + error.getMessage();
        return new RestResponse<>(code, msg, 0, null, errorData);
    }

    @SneakyThrows
    public static <V> RestResponse<V> failed(ErrorMessageCode error) {
        return RestResponse.failed(error, null);
    }

    @SneakyThrows
    public static <V> RestResponse<V> failed(BaseException e) {
        return RestResponse.failed(e.getError(), e);
    }

    @SneakyThrows
    public static <V> RestResponse<V> failed(Exception e) {
        return RestResponse.failed(DefaultErrorMessageCode.InternalError, e);
    }


    /**
     * 如果有错误抛出
     */
    @JsonIgnore
    public T getDataErrorThrow() {
        if (code != 200) {
            throw new BaseException(message, DefaultErrorMessageCode.GeneralError);
        }
        return data;
    }

    /**
     * 如果有错误仅记录不抛出
     */
    @JsonIgnore
    public T getDataWarning() {

        if (code != 200) {
            log.warn("警告: code: {}, message: {}, data: {}", code, message, data);
        }
        return data;

    }
}
