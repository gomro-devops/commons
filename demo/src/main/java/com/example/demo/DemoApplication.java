package com.example.demo;

import cn.gomro.commons.actuator.version.VersionController;
import cn.gomro.commons.restful.api.response.error.DefaultErrorMessageCode;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.TypeToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationConfigurationException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@ComponentScan(basePackageClasses = VersionController.class,
        basePackages = "com.example.demo")
@SpringBootApplication
public class DemoApplication {

    @SneakyThrows
    public static void main(String[] args) {
        System.setProperty("applicationName", "演示项目");
        System.setProperty("applicationCode", "101");
        ConfigurableApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);

        // 使用 ClassScanner 扫描所有类
        ClassPath classPath = ClassPath.from(ClassLoader.getSystemClassLoader());
        ImmutableSet<ClassPath.ClassInfo> allClasses = classPath.getAllClasses();

        // 过滤所有子接口
        for (ClassPath.ClassInfo clazzInfo : allClasses) {
            if (clazzInfo.getName().startsWith("com.example") || clazzInfo.getName().startsWith("cn.gomro")) {
                Class<?> clazz = Class.forName(clazzInfo.getName());

                boolean isSupper = clazz == DefaultErrorMessageCode.class;
                boolean isSub = TypeToken.of(clazz).isSubtypeOf(TypeToken.of(DefaultErrorMessageCode.class));
                if (isSub || isSupper) {
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        Object o = field.get(null);
                        log.info("Error定义: {}, {}", field.getName(), o);
                    }
                }

                //获取类编码
                Schema clazzAnnotation = clazz.getAnnotation(Schema.class);
                if (clazzAnnotation == null) {
                    continue;
                }
                String clazzDescription = clazzAnnotation.description();
                String clazzId = clazzAnnotation.$id();

                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    //获取方法编码
                    Operation methodAnnotation = method.getAnnotation(Operation.class);
                    if (methodAnnotation == null) {
                        continue;
                    }
                    String methodSummary = methodAnnotation.summary();
                    String operationId = methodAnnotation.operationId();

                    String bizErrorCode = System.getProperty("applicationCode") + clazzId + operationId;
                    String msg = System.getProperty("applicationName") + "/" + clazzDescription + "/" + methodSummary;
                    log.info("编码: {},描述: {}, 位置: {}", bizErrorCode, msg, clazzInfo.getName());
                }
            }
        }

    }
}
