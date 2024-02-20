package cn.gomro.commons.actuator.version;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  暴露 /actuator/info 请先配置以下内容
 *
 * management:
 *   endpoints:
 *     web:
 *       exposure:
 *         include: health,info
 *   info:
 *     defaults:
 *       enabled: true
 *
 * @since 2024/2/20 10:08
 * @author Adam 2024/2/20 10:08 说明:
 */
@Slf4j
@RestController
@RequestMapping("/version")
public class VersionController implements InfoContributor {

    @Value("${spring.profiles.active:not set}")
    private String profile;

    @Value("${spring.application.name:not set}")
    private String applicationName;

    @Value("${spring.cloud.client.hostname:not set}")
    private String cloudClientHostname;

    @GetMapping("")
    public String version() {
        String appVersion = System.getenv("APP_VERSION");
        if (appVersion == null || appVersion.isEmpty()) {
            appVersion = "APP_VERSION environment is not set!";
        }
        log.debug("appVersion is {}", appVersion);
        return appVersion;
    }

    @Override
    public void contribute(Info.Builder builder) {

        builder.withDetail("profile", profile)
                .withDetail("application", applicationName)
                .withDetail("hostname", cloudClientHostname);
    }
}
