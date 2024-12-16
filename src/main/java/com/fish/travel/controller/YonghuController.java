package com.fish.travel.controller;

import com.fish.travel.Result.Result;
import com.fish.travel.dto.UserLoginDTO;
import com.fish.travel.dto.UserRegisterDTO;
import com.fish.travel.service.impl.YonghuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
@RestController
@RequestMapping("/travel/yonghu")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class YonghuController {

    @Autowired
    private YonghuServiceImpl yonghuService;

    //查询用户
    @GetMapping("/list")
    public Result list(){

        return Result.success(yonghuService.list());

    }

    // 用户登录
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO loginDTO) {
        return yonghuService.login(loginDTO);
    }

    // 用户注册
    @PostMapping("/register") 
    public Result register(@RequestBody UserRegisterDTO registerDTO) {
        return yonghuService.register(registerDTO);
    }

    // 用户退出登录
    @PostMapping("/logout")
    public Result logout(@RequestHeader("token") String token) {
        return yonghuService.logout(token);
    }

}
