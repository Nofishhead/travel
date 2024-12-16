package com.fish.travel.controller;

import com.fish.travel.Result.Result;
import com.fish.travel.entity.Lunbotu;
import com.fish.travel.service.ILunbotuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 轮播图 前端控制器
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
@RestController
@RequestMapping("/travel/lunbotu")
public class LunbotuController {

    @Autowired
    private ILunbotuService lunbotuService;

    // 获取轮播图列表
    @GetMapping("/list")
    public Result list() {
        return Result.success(lunbotuService.list());
    }

    // 添加轮播图
    @PostMapping
    public Result add(@RequestBody Lunbotu lunbotu) {
        lunbotu.setAddtime(LocalDateTime.now());
        boolean success = lunbotuService.save(lunbotu);
        return success ? Result.success() : Result.fail("添加失败");
    }

    // 修改轮播图
    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody Lunbotu lunbotu) {
        lunbotu.setId(id);
        boolean success = lunbotuService.updateById(lunbotu);
        return success ? Result.success() : Result.fail("修改失败");
    }

    // 删除轮播图
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean success = lunbotuService.removeById(id);
        return success ? Result.success() : Result.fail("删除失败");
    }
}
