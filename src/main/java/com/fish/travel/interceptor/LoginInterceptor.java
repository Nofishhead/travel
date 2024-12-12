package com.fish.travel.interceptor;

import com.fish.travel.entity.Admins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 获取请求的url
        String url = request.getRequestURI();
        // 2. 判断url是否是登录接口
        if (url.contains("/login")) {
            return true;
        }

        // 3. 获取token
        String token = request.getHeader("token");
        if (token == null) {
            response.setStatus(401);
            return false;
        }

        // 4. 从redis中获取用户信息
        String tokenKey = "admin:token:" + token;
        Admins admin = (Admins) redisTemplate.opsForValue().get(tokenKey);
        if (admin == null) {
            response.setStatus(401);
            return false;
        }

        // 5. 刷新token有效期
        redisTemplate.expire(tokenKey, 30, TimeUnit.MINUTES);
        
        return true;
    }
} 