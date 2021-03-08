package com.netease.contentsalesystem.interceptor;

import com.netease.contentsalesystem.constant.UserType;
import com.netease.contentsalesystem.entity.User;
import com.netease.contentsalesystem.exception.LoginException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.netease.contentsalesystem.constant.Const.CURRENT_USER;

/**
 * 拦截对相关url的请求
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) return true;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CURRENT_USER);
        String uri = request.getRequestURI();
        if (user == null && (uri.indexOf("/cart") != -1 || uri.indexOf("account") != -1)) {
            response.sendRedirect("/login");
            return false;
        }
        if (uri.indexOf("/product") != -1 && request.getMethod().equalsIgnoreCase("POST")
        && user.getUserType() != UserType.SELLER) {
            throw new LoginException("请以卖家账户登录");
        }
        if (uri.indexOf("/upload") != -1 && user.getUserType() != UserType.SELLER) {
            throw new LoginException("请以卖家账户登录");
        }
        return true;
    }

}
