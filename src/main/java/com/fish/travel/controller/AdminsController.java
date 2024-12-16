package com.fish.travel.controller;

import com.fish.travel.Result.Result;
import com.fish.travel.dto.AdminLoginDTO;
import com.fish.travel.entity.Admins;
import com.fish.travel.service.impl.AdminsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 管理员 前端控制器
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
@RestController
@RequestMapping("/travel/admins")
public class AdminsController {

    @Autowired
    private AdminsServiceImpl adminsService;

    //管理员登录
    @PostMapping("/login")
    public Result login(@RequestBody AdminLoginDTO admin) {
        return adminsService.login(admin);
    }

    //管理员退出登录
    @PostMapping("/logout")
    public Result logout(@RequestHeader(value = "token", required = true) String token) {
        return adminsService.logout();
    }

    // 获取当前登录用户信息
    @GetMapping("/info")
    public Result info(@RequestHeader("token") String token) {
        try {
            // 根据token获取用户信息
            Admins admin = adminsService.getAdminByToken(token);
            if (admin != null) {
                // 清除敏感信息
                admin.setPwd(null);
                Map<String, Object> data = new HashMap<>();
                data.put("username", admin.getUsername());
                return Result.success(data);
            }
            return Result.fail("获取用户信息失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("获取用户信息失败");
        }
    }

}
