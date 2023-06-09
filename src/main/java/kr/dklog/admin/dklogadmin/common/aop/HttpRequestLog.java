package kr.dklog.admin.dklogadmin.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static kr.dklog.admin.dklogadmin.common.aop.LogColor.*;

@Slf4j
@Aspect
@Component
public class HttpRequestLog {

    @Pointcut("execution(* kr.dklog.admin.dklogadmin.controller..*.*(..))")
    private void cut() {
    }

    @Before("cut()")
    public void request(JoinPoint joinPoint) throws IllegalAccessException {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String controllerName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        boolean colorFlag = !controllerName.contains("Exception");
        String logColor = colorFlag ? ANSI_YELLOW : ANSI_RED;

        String logContent = "\n" +
                ANSI_BLUE + "===================================================================================================\n" +
                logColor + "Request         : " + ANSI_RESET + controllerName + "." + methodName + ANSI_CYAN + " [" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:SS")) + "]\n" +
                logColor + "HTTP Method     : " + ANSI_RESET + request.getMethod() + "\n" +
                logColor + "Request URI     : " + ANSI_RESET + request.getRequestURI() + "\n" +
                logColor + "Request Data    : " + ANSI_RESET + getRequestData(joinPoint) + "\n" +
                logColor + "IP              : " + ANSI_RESET + getRemoteIP(request) + "\n" +
                logColor + "Username        : " + ANSI_RESET + request.getHeader("username") + "\n" +
                ANSI_BLUE + "===================================================================================================" + ANSI_RESET;

        log.info(logContent);
    }

    @AfterThrowing(value = "cut()", throwing = "exception")
    public void throwing(JoinPoint joinPoint, Exception exception) {
        String controllerName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        String logContent = "\n" +
                ANSI_BLUE + "===================================================================================================\n" +
                ANSI_RED + "Exception       : " + ANSI_RESET + controllerName + "." + methodName + ANSI_CYAN + " [" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:SS")) + "]\n" +
                ANSI_RED + "Exception Name  : " + ANSI_RESET + exception.getClass().getSimpleName() + "\n" +
                ANSI_BLUE + "===================================================================================================" + ANSI_RESET;

        log.info(logContent);
    }

    private String getRequestData(JoinPoint joinPoint) throws IllegalAccessException {
        Object[] args = joinPoint.getArgs();

        String result = "";
        for (Object arg : args) {
            if (arg.getClass().getSimpleName().equals("AdminData")) {
                continue;
            }
            result += arg.getClass().getSimpleName() + "[";
            Field[] declaredFields = arg.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                result += declaredField.getName() + " = " + declaredField.get(arg) + ", ";
            }
            result += "], ";
        }

        return result;
    }

    private String getRemoteIP(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
