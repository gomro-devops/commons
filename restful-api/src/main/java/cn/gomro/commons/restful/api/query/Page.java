package cn.gomro.commons.restful.api.query;

import cn.gomro.commons.utils.StringUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Adam
 * 分页
 */
@Data
@Schema(description = "分页属性")
public class Page implements Serializable {

    @Serial
    private static final long serialVersionUID = -5377791020439101004L;

    private static final String DESC = "DESC";
    private static final String ASC = "ASC";

    @Schema(description = "当前第几页", example = "1")
    private Integer page;
    @Schema(description = "每页限制", example = "20")
    private Integer limit;
    @Schema(description = "排序字段", example = "id")
    private String sort;
    @Schema(description = "排序方向", example = DESC)
    private String direction;

    public Page() {
        this(null, null, null, null);
    }

    public Page(Integer page, Integer limit) {
        this(page, limit, null, null);
    }

    /**
     * @param page      当前第几页
     * @param limit     每页限制
     * @param sort      排序字段
     * @param direction 排序方向
     */
    public Page(Integer page, Integer limit, String sort, String direction) {
        this.limit = limit == null ? 20 : limit;
        this.page = page == null ? 1 : page;
        this.sort = sort == null ? "id" : sort;
        this.direction = direction == null ? "DESC" : direction;
    }

    @Schema(hidden = true)
    public Integer getStartRow() {

        if (this.getPage() == null || this.getLimit() == null || this.getPage() <= 0 || this.getLimit() <= 0) {
            return 0;
        }

        return (this.getPage() - 1) * this.getLimit();
    }

    public static Page of(Integer page, Integer limit) {
        return new Page(page, limit);
    }

    public static Page of(Integer page, Integer limit, String sort, String direction) {
        return new Page(page, limit, sort, direction);
    }

    public String getDirection() {
        if (DESC.equals(direction) || ASC.equals(direction)) {
            return direction;
        }
        return DESC;
    }

    public String getSort() {
        if (StringUtils.isBlank(this.sort)) {
            return "id";
        }
        return sort;
    }
}
