package cn.gomro.commons.log;

import cn.gomro.commons.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Adam 2023/12/14 10:05 说明: 仅输出前三行异常及包含 环境变量 LOG_OUT_PACKAGE 的异常信息
 * @since 2023/12/14 10:05
 */
@Slf4j
public class FriendlyErrorLog {

    public FriendlyErrorLog() {
        String logOutPackage = System.getenv("LOG_OUT_PACKAGE");
        if(logOutPackage == null || logOutPackage.isEmpty()){
            throw new RuntimeException("请设置环境变量 LOG_OUT_PACKAGE");
        }
    }

    public static String error(Throwable e) {
        String stackTraceMessage = getStackTraceMessage(e);
        log.error("\n异常信息: {}\n发生在: {}", e.getMessage(), stackTraceMessage);
        return stackTraceMessage;
    }

    public static String error(Throwable e, String... param) {
        StringBuilder params = new StringBuilder();
        if (param != null) {
            for (String s : param) {
                params.append(",").append(s);
            }
        }
        String stackTraceMessage = getStackTraceMessage(e);
        log.error("\n异常信息: {} \n 参数：[{}] \n 发生在: {}", e.getMessage(),
                StringUtils.commaDelimitedTrimEmptyString(params.toString()), stackTraceMessage);
        return stackTraceMessage;
    }

    public static String warn(Throwable e) {
        String stackTraceMessage = getStackTraceMessage(e);
        log.warn("\n警告信息: {}\n发生在: {}", e.getMessage(), stackTraceMessage);
        return stackTraceMessage;
    }

    public static String warn(Throwable e, String... param) {
        StringBuilder params = new StringBuilder();
        if (param != null) {
            for (String s : param) {
                params.append(",").append(s);
            }
        }
        String stackTraceMessage = getStackTraceMessage(e);
        log.warn("\n警告信息: {} \n 参数: [{}] \n 发生在: {}", e.getMessage(), StringUtils.commaDelimitedTrimEmptyString(params.toString()), stackTraceMessage);
        return stackTraceMessage;
    }

    private static String getStackTraceMessage(Throwable throwable) {

        StringBuilder trace = new StringBuilder();
        Set<String> set = new LinkedHashSet<>();
        Collection<String> strings = printUsefulStackTrace(set, throwable);
        strings.stream().filter(StringUtils::isNotBlank).forEach(s -> trace.append(s).append("\n"));
        trace.deleteCharAt(trace.length() - 1);
        return trace.toString();
    }

    private static boolean isForOutPutError(String className) {
        if (StringUtils.isBlank(className)) {
            return false;
        }
        String logOutPackage = System.getenv("LOG_OUT_PACKAGE");
        if (StringUtils.isBlank(logOutPackage)) {
            return false;
        }
        if (StringUtils.contains(className, "gomro")) {
            return true;
        }
        return StringUtils.contains(className, logOutPackage);
    }

    private static Collection<String> printUsefulStackTrace(Set<String> set, Throwable throwable) {

        if (throwable == null) {
            return set;
        }
        set.add(throwable.getMessage() + "(" + throwable.getClass().getSimpleName() + ")");
        // 异常信息前三行必输出
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        if (stackTrace != null) {
            if (stackTrace.length > 0) {
                set.add("   " + stackTrace[0].getClassName() + ":" + stackTrace[0].getLineNumber());
            }
            if (stackTrace.length > 2) {
                String line1 = stackTrace[1].getClassName();
                String line2 = stackTrace[2].getClassName();
                // 非 LOG_OUT_PACKAGE 异常返回前三行
                if (!isForOutPutError(line1)) {
                    set.add("   " + line1 + ":" + stackTrace[1].getLineNumber());
                }
                if (!isForOutPutError(line1)) {
                    set.add("   " + line2 + ":" + stackTrace[2].getLineNumber());
                }
            }
            Arrays.stream(stackTrace).forEach(s -> {
                String className = s.getClassName();
                // 异常信息剩余行仅输出 LOG_OUT_PACKAGE 相关的 排除代理类
                boolean gomroError = isForOutPutError(className);
                boolean $$ = StringUtils.contains(className, "$$");
                if (gomroError && !$$) {
                    set.add("   " + className + ":" + s.getLineNumber());
                }
            });
        }

        // 输出 cause by 内容
        throwable = throwable.getCause();
        if (throwable != null) {
            set.add(" Cause by: ");
            return printUsefulStackTrace(set, throwable.getCause());
        }
        return set;
    }


}
