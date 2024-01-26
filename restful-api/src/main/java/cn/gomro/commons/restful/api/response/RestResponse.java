package cn.gomro.commons.restful.api.response;

import cn.gomro.commons.restful.api.response.error.BaseException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Schema(description = "数据对象 状态码不正确时需参考 ErrorData 输出")
    private T data = null;

    @Schema(description = "产生时间")
    private String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


    public RestResponse(long code, String msg, long count, T data) {

        this.code = code;
        this.message = msg;
        this.count = count;
        this.data = data;
    }

    public static <V> RestResponse<V> success(String msg, long count, V data) {

        return new RestResponse<>(200, msg, count, data);
    }

    public static <V> RestResponse<V> success(String msg, V data) {

        return success(msg, 0, data);
    }

    public static <V> RestResponse<V> success(long count, V data) {

        return success("", count, data);
    }

    public static <V> RestResponse<V> success(long count) {

        return success("", count, null);
    }

    public static <V> RestResponse<V> success(String msg) {

        return success(msg, 0, null);
    }

    public static <V> RestResponse<V> success() {

        return success("成功", 0, null);
    }

    public static <V> RestResponse<V> success(long code, String msg, long count, V data) {

        return new RestResponse<>(code, msg, count, data);
    }

    public static <V> RestResponse<V> failed(long code, String msg, V errorData) {

        return new RestResponse<>(code, msg, 0, errorData);
    }

    /**
     * 如果有错误抛出
     */
    @JsonIgnore
    public T getDataErrorThrow() {
        if (code != 200) {
            throw new BaseException(code, message, data);
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
