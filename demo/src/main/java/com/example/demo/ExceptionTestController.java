package com.example.demo;

import cn.gomro.commons.restful.api.response.error.BaseException;
import cn.gomro.commons.restful.api.response.error.DefaultErrorMessageCode;
import cn.gomro.commons.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ExceptionTestController {

    @SneakyThrows
    @GetMapping("/sec/ex/{n}")
    public String ex1(@PathVariable("n") Integer n, HttpServletRequest request) {
        if (log.isDebugEnabled()) {
            RequestUtil.debug(request);
        }

        return switch (n) {
            case 1 -> throw new BaseException("BaseException", DefaultErrorMessageCode.GeneralError);
            case 2 -> throw new RuntimeException("RuntimeException111");
            case 3 -> throw new IllegalArgumentException("IllegalArgumentException222");
            case 4 -> throw new Exception("Exception333");
            default -> "? n";
        };
    }
}
