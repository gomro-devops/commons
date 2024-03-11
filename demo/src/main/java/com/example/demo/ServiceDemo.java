package com.example.demo;

import cn.gomro.commons.restful.api.response.error.BaseException;
import org.springframework.stereotype.Service;

@Service
public class ServiceDemo {

    public Foo failDemo4() {
        throw new RuntimeException("ServiceDemo.failDemo4()");
    }

    public Foo failDemo41() {
        throw new BaseException("ServiceDemo.failDemo41()", MyBizErrorMessageCode.BizAAAError);
    }
}
