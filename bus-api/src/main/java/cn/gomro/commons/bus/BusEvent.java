package cn.gomro.commons.bus;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Objects;

@Slf4j
@Component
public class BusEvent {

    private final ObjectMapper objectMapper;
    private final BusProperties busProperties;
    private final ApplicationEventPublisher applicationEventPublisher;

    public BusEvent(ObjectMapper objectMapper, BusProperties busProperties, ApplicationEventPublisher applicationEventPublisher) {
        this.objectMapper = objectMapper;
        this.busProperties = busProperties;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @SneakyThrows
    public void send(String type, Object old, Object model, String classFullName) {
        Assert.notNull(old, "old must not be null");
        Assert.notNull(model, "model must not be null");

        String oldJson = objectMapper.writeValueAsString(old);
        String newJson = objectMapper.writeValueAsString(model);
        if (StringUtils.equalsIgnoreCase(oldJson, newJson)) {
            throw new IllegalArgumentException("Object old can not equals new model");
        }
        CommonRemoteEvent event = new CommonRemoteEvent(busProperties.getId(), type, oldJson, newJson, classFullName);
        applicationEventPublisher.publishEvent(event);
    }

    /**
     *
     * @param model
     * @author Adam 2024/3/25 12:00 说明: 新增事件 不进审计表
     */
    @SneakyThrows
    public void saveEvent(Object model) {
        Assert.notNull(model, "model must not be null");

        String oldJson = "";
        String newJson = objectMapper.writeValueAsString(model);
        if (StringUtils.equalsIgnoreCase(oldJson, newJson)) {
            throw new IllegalArgumentException("Object old can not equals new model");
        }
        CommonRemoteEvent event = new CommonRemoteEvent(busProperties.getId(),
                CommonRemoteEvent.SAVE, oldJson, newJson, model.getClass().getName());
        applicationEventPublisher.publishEvent(event);
    }

    /**
     *
     * @param old
     * @param model
     * @author Adam 2024/3/25 12:00 说明: 更新事件 记录到审计表
     */
    @SneakyThrows
    public void updateEvent(Object old, Object model) {
        if(!Objects.equals(model.getClass().getName(),old.getClass().getName())) {
            throw new IllegalArgumentException("old object class not equals new model");
        }

        Assert.notNull(old, "old must not be null");
        Assert.notNull(model, "model must not be null");

        String oldJson = objectMapper.writeValueAsString(old);
        String newJson = objectMapper.writeValueAsString(model);
        if (StringUtils.equalsIgnoreCase(oldJson, newJson)) {
            throw new IllegalArgumentException("Object old can not equals new model");
        }
        CommonRemoteEvent event = new CommonRemoteEvent(busProperties.getId(),
                CommonRemoteEvent.UPDATE, oldJson, newJson, model.getClass().getName());
        applicationEventPublisher.publishEvent(event);
    }

    /**
     *
     * @param old
     * @param model
     * @author Adam 2024/3/25 11:59 说明: 逻辑删除事件 记录到审计表
     */
    @SneakyThrows
    public void removeEvent(Object old, Object model) {
        if(!Objects.equals(model.getClass().getName(),old.getClass().getName())) {
            throw new IllegalArgumentException("old object class not equals new model");
        }

        Assert.notNull(old, "old must not be null");
        Assert.notNull(model, "model must not be null");

        String oldJson = objectMapper.writeValueAsString(old);
        String newJson = objectMapper.writeValueAsString(model);
        if (StringUtils.equalsIgnoreCase(oldJson, newJson)) {
            throw new IllegalArgumentException("Object old can not equals new model");
        }
        CommonRemoteEvent event = new CommonRemoteEvent(busProperties.getId(),
                CommonRemoteEvent.REMOVE, oldJson, newJson, model.getClass().getName());
        applicationEventPublisher.publishEvent(event);
    }
}
