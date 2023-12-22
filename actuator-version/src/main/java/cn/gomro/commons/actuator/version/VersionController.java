package cn.gomro.commons.actuator.version;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/version")
public class VersionController {

    @GetMapping("")
    public String version() {
        String appVersion = System.getenv("APP_VERSION");
        if (appVersion == null || appVersion.isEmpty()) {
            appVersion = "APP_VERSION environment is not set!";
        }
        log.debug("appVersion is {}", appVersion);
        return appVersion;
    }
}
