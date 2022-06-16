package com.mm.web.advice;

import com.mm.web.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * 复用@ResponseBody等注解。
 *
 * ResponseBodyAdvice方法是在执行完HandlerMethodReturnValueHandler之后执行，全局捕获的异常返回，也会经过该方法。
 * 借助@RestControllerAdvice和ResponseBodyAdvice<T>来对项目的每一个@RestController标记的控制类的响应体进行后置切面通知处理。
 */
@RestControllerAdvice
public class ResponseBodyHandler implements ResponseBodyAdvice<Object> {

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Result) {
            return body;
        } else {
            String apiUrl;
            if (contextPath != null && contextPath != "") {
                apiUrl = contextPath + "/api";
            } else {
                apiUrl = "/api";
            }

            HttpServletRequest httpServletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            String url = httpServletRequest.getRequestURI();
            // api开头的url才进行包装
            if (url.startsWith(apiUrl)) {
                // String类型特别处理，防止发生类型转换异常
                if (body instanceof String) {
                    return JSON.toJSONString(R.ok(body));
                } else {
                    return R.ok(body);
                }
            } else {
                return body;
            }
        }
    }
}
