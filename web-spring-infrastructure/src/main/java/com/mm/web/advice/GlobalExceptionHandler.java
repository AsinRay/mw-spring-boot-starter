package com.mm.web.advice;


import com.mm.web.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String logExceptionFormat = "Err: Code: %s Detail: %s";


    /**
     * 处理form格式的请求
     * 请求格式为：/action?a=1&b=hello&name=ming
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    R handleControllerException(HttpServletRequest request, Throwable ex) {
        if (ex instanceof BindException) {
            BindException bindException = BindException.class.cast(ex);
            List<ObjectError> errors = bindException.getBindingResult().getAllErrors();
            return R.fail(4000,errors.get(0).getDefaultMessage());
        }
        return R.fail(4000);
    }

    /**
     * 处理RequestBody中的数据验证异常
     * @param e
     * @return 返回错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R validationBodyException(MethodArgumentNotValidException e){
        BindingResult result  = e.getBindingResult();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            StringBuffer buffer = new StringBuffer();
            errors.forEach(p ->{
                FieldError fe = (FieldError) p;
                log.error("Data check failure : object{},field{},errorMessage{}",
                        fe.getObjectName(),fe.getField(),fe.getDefaultMessage());
                buffer.append(fe.getDefaultMessage()).append(",");
            });
            return R.fail(4000,buffer.toString());
        }
        return R.fail(4000);
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public R uploadException(MaxUploadSizeExceededException e) throws IOException {
        return R.fail(4900,"最大上传文件为20M，上传文件大小超出限制!");
    }


    //500错误
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    public R server500(RuntimeException ex) {
        log.error(ex.getMessage(),ex);
        return process(500,"服务器出现异常，请与联系管理员");
    }

    @ExceptionHandler(java.lang.Exception.class)
    public R unknownException(java.lang.Exception e) {
        log.error(e.getMessage(),e);
        return  process(9999,e.getMessage());
    }

    private <T extends Throwable> R process(Integer code, T ex) {
        return process(code,ex.getMessage());
    }

    private <T extends CharSequence> R process(Integer code, T msg) {
        // TODO 此处没有记录当前异常的详细信息
        log.error(String.format(logExceptionFormat, code, msg));
        return R.fail(code, msg);
    }
}