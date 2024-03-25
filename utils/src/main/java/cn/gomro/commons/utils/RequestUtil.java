package cn.gomro.commons.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.WebRequest;

import java.security.Principal;
import java.util.Enumeration;
import java.util.Iterator;

@Slf4j
public class RequestUtil {

    /**
     * 输出请求头及请求体 注意不要读流
     *
     * @param request 请求
     */
    public static void debug(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();

        sb.append("\n======== request ========\n");
        sb.append(request.getClass().getSimpleName())
                .append("; uri=").append(request.getRequestURI())
                .append("; client=").append(request.getRemoteAddr());

        sb.append("\n======== parameters ========\n");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String parameter = request.getParameter(name);
            sb.append(name).append(":").append(parameter).append("; ");
        }

        sb.append("\n======== headers ========\n");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            sb.append(name).append("=").append(request.getHeader(name)).append("; ");
        }

        sb.append("\n======== session ========\n");
        HttpSession session = request.getSession();
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            sb.append(name).append("=").append(session.getAttribute(name)).append("; \n");
        }

        sb.append("\n======== principal ========\n");
        Principal principal = request.getUserPrincipal();
        sb.append(principal);

        log.debug(sb.toString());
    }

    @SneakyThrows
    public static void debug(WebRequest request) {
        StringBuilder sb = new StringBuilder();

        sb.append("\n======== request ========\n");
        sb.append(request.toString());

        sb.append("\n======== parameters ========\n");
        Iterator<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasNext()) {
            String name = parameterNames.next();
            String parameter = request.getParameter(name);
            sb.append(name).append(":").append(parameter).append("; ");
        }

        sb.append("\n======== headers ========\n");
        Iterator<String> headerNames = request.getHeaderNames();
        while (headerNames.hasNext()) {
            String name = headerNames.next();
            sb.append(name).append("=").append(request.getHeader(name)).append("; ");
        }

        sb.append("\n======== principal ========\n");
        Principal principal = request.getUserPrincipal();
        sb.append(principal);

        log.debug(sb.toString());
    }
}
