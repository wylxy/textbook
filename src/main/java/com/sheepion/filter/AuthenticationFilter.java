package com.sheepion.filter;

import com.sheepion.common.UserHolder;
import com.sheepion.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
@Component
@WebFilter(urlPatterns = "/api/*", filterName = "AuthenticationFilter")
public class AuthenticationFilter extends OncePerRequestFilter {
    //不需要登录也能访问的地址
    private static String[] WHITE_LIST;

    public String[] getWhiteList() {
        return WHITE_LIST;
    }

    @Value("${white-list}")
    public void setWhiteList(String[] whiteList) {
        WHITE_LIST = whiteList;
    }

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("进入AuthenticationFilter -> doFilterInternal 函数");
        log.debug("请求路径：{}", request.getRequestURI());
        log.debug("请求方法：{}",request.getMethod());
        //如果是OPTIONS直接放行
        if ("OPTIONS".equals(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }
        //从请求头中获取token
        //请求头key：Authorization
        //token格式：Bearer <token>
        String header = request.getHeader("Authorization");
        String token = getToken(header);
        //放行白名单请求
        for (String pattern : WHITE_LIST) {
            if (PATH_MATCHER.match(pattern, request.getRequestURI())) {
                log.debug("白名单放行 {}", pattern);
                //如果token中有用户id，设置当前用户
                Integer userId = getUserIdFromToken(token);
                Byte roleId = getRoleIdFromToken(token);
                if (userId != null) {
                    log.debug("token中获取的用户id为：{}", userId);
                    UserHolder.setCurrentUser(userId);
                }
                if (roleId != null) {
                    log.debug("token中获取的用户角色为：{}", roleId);
                    UserHolder.setCurrentRoleId(roleId);
                }
                List<String> permissions=getPermissionsFromToken(token);
                if (permissions != null) {
                    log.debug("token中获取的用户权限为：{}", permissions);
                    UserHolder.setCurrentPermissions(permissions);
                }
                filterChain.doFilter(request, response);
                return;
            }
        }
        if (token == null) {
            log.debug("无token信息");
            responseError(response, "无token信息");
            return;
        }
        Integer userId = getUserIdFromToken(token);
        Byte roleId = getRoleIdFromToken(token);
        List<String> permissions=getPermissionsFromToken(token);
        if (userId == null || roleId == null) {
            responseError(response, "token无效");
            return;
        }
        log.debug("token中获取的用户id为：{}", userId);
        log.debug("token中获取的用户角色为：{}", roleId);
        log.debug("token中获取的用户权限为：{}", permissions);
        UserHolder.setCurrentUser(userId);
        UserHolder.setCurrentRoleId(roleId);
        UserHolder.setCurrentPermissions(permissions);
        filterChain.doFilter(request, response);
    }

    /**
     * 从请求头中获取token
     *
     * @param header 请求头
     * @return token，如果token格式错误，返回null
     */
    private String getToken(String header) {
        if (header == null) {
            log.debug("无token信息");
            return null;
        }
        if (!header.startsWith("Bearer ")) {
            log.debug("token格式错误");
            return null;
        }
        String token = header.substring(7);
        log.debug("token：{}", token);
        return token;
    }

    /**
     * 验证token是否有效
     *
     * @param token token
     * @return 有效返回true，无效返回false
     */
    private boolean isTokenValid(String token) {
        //获取token失败的情况
        if (token == null) {
            log.debug("无token信息");
            return false;
        }
        //token无法解析的情况
        try {
            if (JwtUtil.parseToken(token) == null) {
                log.debug("token无效");
                return false;
            }
        } catch (Exception e) {
            log.debug("token无效");
            return false;
        }
        //token过期的情况
        if (JwtUtil.isTokenExpired(token)) {
            log.debug("token已过期");
            return false;
        }
        return true;
    }
    private List<String> getPermissionsFromToken(String token){
        log.debug("从token中获取用户权限 {}", token);
        if (!isTokenValid(token)) {
            log.debug("token无效");
            return null;
        }
        log.debug("token内容为：{}", JwtUtil.parseToken(token).toString());
        //从token中获取用户权限
        List<String> permissions = JwtUtil.getPermissions(token);
        if (permissions == null) {
            log.debug("token无效");
            return null;
        }
        return permissions;
    }
    private Integer getUserIdFromToken(String token) {
        log.debug("从token中获取用户id {}", token);
        if (!isTokenValid(token)) {
            log.debug("token无效");
            return null;
        }
        log.debug("token内容为：{}", JwtUtil.parseToken(token).toString());
        //从token中获取用户id
        Integer userId = JwtUtil.getUserId(token);
        if (userId == null) {
            log.debug("token无效");
            return null;
        }
        return userId;
    }

    private Byte getRoleIdFromToken(String token) {
        log.debug("从token中获取用户角色id {}", token);
        if (!isTokenValid(token)) {
            log.debug("token无效");
            return null;
        }
        log.debug("token内容为：{}", JwtUtil.parseToken(token).toString());
        //从token中获取用户角色id
        Byte roleId = JwtUtil.getRoleId(token);
        if (roleId == null) {
            log.debug("token无效");
            return null;
        }
        return roleId;
    }

    private void responseError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print("{\"error\": \"" + message + "\"}");
        out.flush();
        out.close();
    }
}
