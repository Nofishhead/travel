package com.fish.travel.controller;

import com.fish.travel.Result.Result;
import com.fish.travel.service.impl.AdminsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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

    //测试
    @GetMapping("test")
    public Result test() {
        return Result.success(adminsService.list());
    }


}
