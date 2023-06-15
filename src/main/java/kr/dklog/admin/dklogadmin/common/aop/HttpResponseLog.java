package kr.dklog.admin.dklogadmin.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static kr.dklog.admin.dklogadmin.common.aop.LogColor.*;

@Slf4j
@Aspect
@Component
public class HttpResponseLog {

    @Pointcut("execution(* kr.dklog.admin.dklogadmin.controller..*.*(..))")
    private void cut() {
    }

    @Around("cut()")
    public ResponseEntity<?> executionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String controllerName = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        final StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        ResponseEntity<?> response = (ResponseEntity<?>) joinPoint.proceed();
        stopWatch.stop();

        boolean colorFlag = String.valueOf(response.getStatusCodeValue()).startsWith("2");
        String logColor = colorFlag ? ANSI_GREEN : ANSI_RED;

        String logContent = "\n" +
                ANSI_BLUE + "===================================================================================================\n" +
                logColor + "Response        : " + ANSI_RESET + controllerName + "." + methodName + ANSI_CYAN + " [" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:SS")) + "]\n" +
                logColor + "HTTP Status     : " + ANSI_RESET + response.getStatusCode() + "\n" +
                logColor + "HTTP Body       : " + ANSI_RESET + getResponseData(response) + "\n" +
                logColor + "Execution Time  : " + ANSI_RESET + stopWatch.getTotalTimeMillis() + " ms" + "\n" +
                ANSI_BLUE + "===================================================================================================" + ANSI_RESET;

        log.info(logContent);

        return response;
    }

    private String getResponseData(ResponseEntity<?> response) throws IllegalAccessException {
        Object body = response.getBody();

        String result = "";
        if (body != null) {
            result += body.getClass().getSimpleName() + "[";
            Field[] declaredFields = body.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                result += declaredField.getName() + " = " + declaredField.get(body) + ", ";
            }
            result += "], ";
        }

        return result;
    }
}
