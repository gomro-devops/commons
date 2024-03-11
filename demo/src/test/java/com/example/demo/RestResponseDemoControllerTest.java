package com.example.demo;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestResponseDemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void setUp() {
        System.setProperty("applicationName", "演示项目");
        System.setProperty("applicationCode", "101");
    }

    @Test
    void sucessDemo1() throws Exception {

        // 模拟 HTTP 请求
        mockMvc.perform(get("/rest-demo/sucessDemo1"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    result.getResponse().setCharacterEncoding("UTF-8");
                })
                .andExpect(jsonPath("$.message").value("成功"))
                .andExpect(jsonPath("$.code").value(200))
                .andDo(MockMvcResultHandlers.print())
        ;
    }

    @SneakyThrows
    @Test
    void sucessDemo2() {
        // 模拟 HTTP 请求
        mockMvc.perform(get("/rest-demo/sucessDemo2"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    result.getResponse().setCharacterEncoding("UTF-8");
                })
                .andExpect(jsonPath("$.message").value("成功"))
                .andExpect(jsonPath("$.code").value(200))
                .andDo(MockMvcResultHandlers.print())
        ;
    }

    @SneakyThrows
    @Test
    void failDemo3() {
        // 模拟 HTTP 请求
        mockMvc.perform(get("/rest-demo/failDemo3"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    result.getResponse().setCharacterEncoding("UTF-8");
                })
                .andExpect(jsonPath("$.message").value("演示项目/模块描述/业务描述3/内部错误，需联系管理员"))
                .andExpect(jsonPath("$.code").value(101_201_303_500L))
                .andDo(MockMvcResultHandlers.print())
        ;
    }

    @SneakyThrows
    @Test
    void failDemo4() {
        // 模拟 HTTP 请求
        mockMvc.perform(get("/rest-demo/failDemo4"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    result.getResponse().setCharacterEncoding("UTF-8");
                })
                .andExpect(jsonPath("$.code").value(101_201_304_105L))
                .andDo(MockMvcResultHandlers.print())
        ;
    }

    @SneakyThrows
    @Test
    void failDemo5() {
        // 模拟 HTTP 请求
        mockMvc.perform(get("/rest-demo/failDemo5"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    result.getResponse().setCharacterEncoding("UTF-8");
                })
                .andExpect(jsonPath("$.message").value("演示项目/模块描述/业务描述5/请先操作A再操作此功能"))
                .andExpect(jsonPath("$.code").value(101_201_305_105L))
                .andDo(MockMvcResultHandlers.print())
        ;
    }

    @SneakyThrows
    @Test
    void failDemo6() {
        // 模拟 HTTP 请求
        mockMvc.perform(get("/rest-demo/failDemo6"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    result.getResponse().setCharacterEncoding("UTF-8");
                })
                .andExpect(jsonPath("$.message").value("演示项目/模块描述/业务描述6/错误，请根据提示操作"))
                .andExpect(jsonPath("$.code").value(101_201_306_202L))
                .andDo(MockMvcResultHandlers.print())
        ;
    }
}
