package cn.gomro.commons.bus;

import lombok.*;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonRemoteEvent extends RemoteApplicationEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = -8329401433070132781L;

    public final static String SAVE = "save";
    public final static String UPDATE = "update";
    public final static String REMOVE = "remove";

    private String type; // save,update,remove
    private String oldJson;
    private String newJson;
    private String classFullName; // 区分事件类

    /**
     * @param originService 原服务 如果原服务不存在仅能发到 本地事件！ busProperties.getId()
     * @param type          操作类型
     * @param oldJson       原对象（修改之前的旧对象）
     * @param newJson       目标对象（新对象）
     * @param classFullName 业务类完整名
     */
    public CommonRemoteEvent(String originService, String type, String oldJson, String newJson, String classFullName) {
        super(oldJson, originService);
        this.type = type;
        this.oldJson = oldJson;
        this.newJson = newJson;
        this.classFullName = classFullName;
    }
}
