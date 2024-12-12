package com.fish.travel.controller;

import com.fish.travel.Result.Result;
import com.fish.travel.service.impl.YonghuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

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

}
