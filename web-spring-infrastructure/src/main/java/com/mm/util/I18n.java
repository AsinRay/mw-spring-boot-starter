package com.mm.util;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Arrays;

public class I18n {

    public static String msg(int code, Object... args) {
        String key = String.valueOf(code);
        return msg(key,args);
    }

    /**
     * 通过code 和 message 取i18n message.
     * 如果code配置的有值，则使用args来格式化占位符，如果没有占位符，则忽略args
     * 如果code没有配置，则直接返回args参数集合的toString.
     *
     * @param code  properties file code
     * @param args  args for template
     * @return
     */
    public static String msg(String code, Object... args) {
        // 支持不重启应用加载新的语言文件
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        String msg = "";
        try {
            msg = messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException nsme) {
            // 不做处理，直接返回所有args拼接后字符串
            msg = Arrays.asList(args).stream().map(Object::toString).reduce(String::concat).get();
            //log.warn("code: {} conf missing. please check your messages file.", code);
        }
        return msg;
    }

    /**
     * 如果没有配置会出异常
     * @param code
     * @param args
     * @return
     */
    public static String message(String code, Object... args) {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
