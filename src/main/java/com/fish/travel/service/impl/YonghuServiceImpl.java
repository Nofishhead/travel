package com.fish.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fish.travel.Result.Result;
import com.fish.travel.dto.UserLoginDTO;
import com.fish.travel.dto.UserRegisterDTO;
import com.fish.travel.entity.Yonghu;
import com.fish.travel.mapper.YonghuMapper;
import com.fish.travel.service.IYonghuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
@Service
public class YonghuServiceImpl extends ServiceImpl<YonghuMapper, Yonghu> implements IYonghuService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 用户登录
    public Result login(UserLoginDTO loginDTO) {
        // 1. 根据用户名查询
        LambdaQueryWrapper<Yonghu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Yonghu::getYonghuming, loginDTO.getUsername());
        Yonghu user = this.getOne(queryWrapper);

        // 2. 判断用户是否存在
        if (user == null) {
            return Result.fail("用户名不存在");
        }

        // 3. 密码比对
        if (!user.getMima().equals(loginDTO.getPassword())) {
            return Result.fail("密码错误");
        }

        // 4. 生成token
        String token = UUID.randomUUID().toString();

        // 5. 将用户信息存入redis
        String tokenKey = "user:token:" + token;
        redisTemplate.opsForValue().set(tokenKey, user, 30, TimeUnit.MINUTES);

        // 6. 返回结果
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        user.setMima(null); // 清除密码
        data.put("userInfo", user);

        return Result.success(data);
    }

    // 用户注册
    public Result register(UserRegisterDTO registerDTO) {
        // 1. 检查用户名是否已存在
        LambdaQueryWrapper<Yonghu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Yonghu::getYonghuming, registerDTO.getUsername());
        if (this.count(queryWrapper) > 0) {
            return Result.fail("用户名已存在");
        }

        // 2. 创建新用户
        Yonghu user = new Yonghu();
        user.setYonghuming(registerDTO.getUsername());
        user.setMima(registerDTO.getPassword());
        user.setYouxiang(registerDTO.getEmail());
        user.setShouji(registerDTO.getPhone());
        user.setAddtime(LocalDateTime.now());

        // 3. 保存用户
        this.save(user);

        return Result.success("注册成功");
    }

    // 用户退出登录
    public Result logout(String token) {
        // 1. 从redis中删除token
        String tokenKey = "user:token:" + token;
        redisTemplate.delete(tokenKey);

        return Result.success("退出成功");
    }
}
