package fixel.cs.controller;

import fixel.cs.auth.TokenUser;
import fixel.cs.auth.vo.TokenUserInfo;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

// 들어온 파라미터를 검증하고
// 토큰 생성을 도와준다..??
@Component
public class TokenUserInfoResolver implements HandlerMethodArgumentResolver {

    // 들어온 parameter를 resolver가 지원할 수 있는지 아닌지를 true/false로 반환한다.
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        TokenUser tokenUserAnnotation = parameter.getParameterAnnotation(TokenUser.class);
        return tokenUserAnnotation != null && TokenUserInfo.class.isAssignableFrom(parameter.getParameterType());
    }

    // 실제 바인딩할 객체를 반환한다.
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        TokenUserInfo tokenUserInfo = new TokenUserInfo(
                webRequest.getAttribute("user.uid", WebRequest.SCOPE_REQUEST).toString(),
                webRequest.getAttribute("user.authId", WebRequest.SCOPE_REQUEST).toString()
        );

        return tokenUserInfo;
    }
}
