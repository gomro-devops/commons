## 项目说明：

该项目会自动暴露一个/version 地址，
默认取 APP_VERSION 的环境变量，
可以在Dockerfile或在其它部署时增加环境变量以监测到软件版本；

Dockerfile样例 ：

    FROM registry.cn-hangzhou.aliyuncs.com/gomro/openjdk17
    MAINTAINER gomro <devops@gomro.cn>

    ARG JAR_FILE
    ARG APP_VERSION

    ENV APP_VERSION=${APP_VERSION}
    COPY ${JAR_FILE} app.jar
    ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Duser.timezone=GMT+08","-jar","/app.jar"]

    EXPOSE 80

