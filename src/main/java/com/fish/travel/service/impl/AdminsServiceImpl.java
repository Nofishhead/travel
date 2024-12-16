package com.fish.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fish.travel.Result.Result;
import com.fish.travel.dto.AdminLoginDTO;
import com.fish.travel.entity.Admins;
import com.fish.travel.mapper.AdminsMapper;
import com.fish.travel.service.IAdminsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
@Service
public class AdminsServiceImpl extends ServiceImpl<AdminsMapper, Admins> implements IAdminsService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private HttpServletRequest request;

    // 存储token和用户ID的映射关系
    private static final Map<String, Integer> tokenMap = new ConcurrentHashMap<>();

    public Result login(AdminLoginDTO adminLoginDTO) {
        //System.out.println("开始处理登录：" + adminLoginDTO); // 调试日志

        // 1. 根据用户名查询
        LambdaQueryWrapper<Admins> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admins::getUsername, adminLoginDTO.getUsername());
        Admins admin = this.getOne(queryWrapper);

        System.out.println("查询到的用户：" + admin); // 调试日志

        // 2. 判断用户是否存在
        if (admin == null) {
            return Result.fail("用户名不存在");
        }

        // 3. 密码比对
        if (!admin.getPwd().equals(adminLoginDTO.getPwd())) {
            return Result.fail("密码错误");
        }

        // 4. 生成token
        String token = UUID.randomUUID().toString();
        System.out.println("生成的token：" + token); // 调试日志

        // 5. 将用户信息存入redis
        String tokenKey = "admin:token:" + token;
        redisTemplate.opsForValue().set(tokenKey, admin, 30, TimeUnit.MINUTES);

        // 6. 保存token和用户ID的映射关系
        tokenMap.put(token, admin.getId());

        // 返回数据时确保格式正确
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("userInfo", admin);

        return Result.success(data);  // 确保返回code为200
    }

    public Result logout() {
        // 1. 获取token
        String token = request.getHeader("token");
        if (token == null) {
            return Result.fail("未登录");
        }

        // 2. 从redis中删除token
        String tokenKey = "admin:token:" + token;
        redisTemplate.delete(tokenKey);

        // 3. 清除token和用户ID的映射关系
        tokenMap.remove(token);

        return Result.success("退出成功");
    }

    // 根据token获取用户信息
    public Admins getAdminByToken(String token) {
        Integer userId = tokenMap.get(token);
        if (userId != null) {
            return this.getById(userId);
        }
        return null;
    }
}
