package cn.gomro.commons.restful.api.response.error;

import cn.gomro.commons.log.FriendlyErrorLog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class BizError {

    @Schema(description = "错误码", type = "long")
    private Integer code = -1;

    @Schema(description = "错误原因", type = "string")
    private String reason = "";

    @Schema(description = "错误解决方案", type = "string")
    private String solution = "";

    @Schema(description = "参考跳转引用地址", type = "string")
    private String reference = "";

    @Schema(description = "异常链的文本描述")
    private String[] errors = {};


    public BizError(ErrorMessageCode error, Exception e) {
        this.code = error.getCode();
        this.reason = error.getReason();
        this.solution = error.getSolution();
        this.reference = error.getReference();
        String[] errors = {};
        if (e != null) {
            String warn = FriendlyErrorLog.warn(e);
            String replace = warn.replace("\n", ",");
            errors = StringUtils.commaDelimitedListToStringArray(replace);
        }
        this.errors = errors;
    }
}
