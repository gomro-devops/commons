package cn.gomro.commons.restful.api.response.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ErrorData {


    @Schema(description = "错误文本描述")
    private String[] errors = {};

    @Schema(description = "错误原因", type = "string")
    private String reason = "";

    @Schema(description = "错误解决方案", type = "string")
    private String solution = "";

    @Schema(description = "参考跳转引用地址", type = "string")
    private String reference = "";
}
