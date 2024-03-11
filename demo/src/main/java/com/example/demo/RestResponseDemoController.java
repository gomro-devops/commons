package com.example.demo;

import cn.gomro.commons.restful.api.response.RestResponse;
import cn.gomro.commons.restful.api.response.error.BaseException;
import cn.gomro.commons.restful.api.response.error.DefaultErrorMessageCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@Slf4j
@Schema(description = "模块描述", $id = "201")
@RestController
@RequestMapping("rest-demo")
public class RestResponseDemoController {

    @Autowired
    private ServiceDemo serviceDemo;

    @Operation(summary = "业务描述1", operationId = "301")
    @GetMapping("sucessDemo1")
    public RestResponse<Foo> sucessDemo1() {
        return RestResponse.success("成功");
        // 等价
//        return RestResponse.success();
    }

    @Operation(summary = "业务描述2", operationId = "302")
    @GetMapping("sucessDemo2")
    public RestResponse<Foo> sucessDemo2() {
        return RestResponse.success("成功", new Foo("bar"));
        // 等价
//        return RestResponse.success(1,new Foo("bar"));
    }

    @Operation(summary = "业务描述3", operationId = "303")
    @GetMapping("failDemo3")
    public RestResponse<Foo> failDemo3() {
        return RestResponse.failed(DefaultErrorMessageCode.InternalError);
    }

    @Operation(summary = "业务描述4", operationId = "304")
    @GetMapping("failDemo4")
    public RestResponse<Foo> failDemo4() {
        try {
            int nextInt = new Random().nextInt(10);
            log.info("nextInt {}", nextInt);
            if (nextInt / 2 == 0) {
                serviceDemo.failDemo4();
            }else {
                serviceDemo.failDemo41();
            }
        } catch (BaseException e) {
            log.info("return from BaseException e");
            return RestResponse.failed(e);
        } catch (Exception e){
            log.info("return from Exception e");
            return RestResponse.failed(e);
        }
        return RestResponse.success("never get in");
    }


    @Operation(summary = "业务描述5", operationId = "305")
    @GetMapping("failDemo5")
    public RestResponse<Foo> failDemo5() {

        return RestResponse.failed(MyBizErrorMessageCode.BizAAAError);
    }

    @Operation(summary = "业务描述6", operationId = "306")
    @GetMapping("failDemo6")
    public RestResponse<Foo> failDemo6() {
        try {
            int nextInt = new Random().nextInt(10);
            log.info("nextInt {}", nextInt);
            if (nextInt / 2 == 0) {
                throw new BaseException("failDemo6", DefaultErrorMessageCode.GeneralError);
            }
        } catch (Exception e) {
            return RestResponse.failed(e);
        }
        return RestResponse.failed(DefaultErrorMessageCode.GeneralError);
    }
}
