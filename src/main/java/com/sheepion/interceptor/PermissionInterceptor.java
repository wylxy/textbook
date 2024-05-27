package com.sheepion.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sheepion.common.HasPermission;
import com.sheepion.common.Result;
import com.sheepion.common.ResultCode;
import com.sheepion.common.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
@Component
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("权限拦截器执行 {} {} {}",request.getMethod(),request.getRequestURI(),handler);
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod= (HandlerMethod) handler;
            HasPermission hasPermission=handlerMethod.getMethod().getAnnotation(HasPermission.class);
            if (hasPermission==null) {
                log.debug("不需要权限");
                return true;
            }
            //判断用户拥有的权限是否包含接口上的权限，有任意一个就返回true
            List<String> userPermissions = UserHolder.getCurrentPermissions();
            String[] permissions=hasPermission.value();
            for(String permission:permissions){
                if (userPermissions.contains(permission)){
                    log.debug("权限通过 {}",permission);
                    return true;
                }
            }
            log.debug("权限不足");
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(Result.failed(ResultCode.FORBIDDEN,"权限不足").toString());
            return false;
        }
        log.debug("不需要权限");
        return true;
    }
}
