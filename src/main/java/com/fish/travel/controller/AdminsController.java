package com.fish.travel.controller;

import com.fish.travel.Result.Result;
import com.fish.travel.dto.AdminLoginDTO;
import com.fish.travel.entity.Admins;
import com.fish.travel.service.impl.AdminsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

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
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminsController {

    @Autowired
    private AdminsServiceImpl adminsService;

    //测试
//    @GetMapping("/test")
//    public Result test() {
//        return Result.success(adminsService.list());
//    }
//
//    //测试
//    @GetMapping("/test2")
//    public String test2() {
//        return "adminsService.list()";
//    }

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

    //


}
