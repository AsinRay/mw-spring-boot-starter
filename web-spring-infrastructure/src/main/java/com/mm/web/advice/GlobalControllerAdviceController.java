package com.mm.web.advice;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;


/**
 * StringTrimmerEditor
 * （https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-beans）
 * 可以实现「Get」方法时参数去除空格,如果构造函数传为true，可以完成empty string 转换为null
 * 默认没有注册，需要用户手工注册
 */

@ControllerAdvice
public class GlobalControllerAdviceController {

    //WebDataBinder是用来绑定请求参数到指定的属性编辑器，可以继承WebBindingInitializer
    //来实现一个全部controller共享的dataBinder Java代码
    @InitBinder
    public void dataBind(WebDataBinder binder) {
        ///注册
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
