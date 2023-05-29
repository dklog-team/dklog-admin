package kr.dklog.admin.dklogadmin.common.resolver;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import kr.dklog.admin.dklogadmin.common.data.AdminData;
import kr.dklog.admin.dklogadmin.config.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final AppConfig appConfig;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(AdminData.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String jws = webRequest.getHeader("Authorization");

        if (jws == null || jws.equals("")) {
            throw new RuntimeException("인증이 필요합니다");
        }

        log.info("Authorization: {}", jws);
        log.info("jwt key: {}", appConfig.getJwtKey());

        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(appConfig.getJwtKey())
                    .build()
                    .parseClaimsJws(jws);

            log.info("claims: {}", claims);
            String userId = claims.getBody().getSubject();
            return new AdminData(Long.parseLong(userId));
        } catch (JwtException e) {
            throw new RuntimeException("인증이 필요합니다");
        }
    }
}
