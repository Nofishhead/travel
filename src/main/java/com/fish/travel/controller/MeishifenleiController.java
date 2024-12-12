package com.fish.travel.controller;

import com.fish.travel.Result.Result;
import com.fish.travel.entity.Meishifenlei;
import com.fish.travel.service.IMeishifenleiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 美食分类 前端控制器
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
@RestController
@RequestMapping("/travel/meishifenlei")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class MeishifenleiController {

    @Autowired
    private IMeishifenleiService meishifenleiService;

    // 获取分类列表
    @GetMapping("/list")
    public Result list() {
        return Result.success(meishifenleiService.list());
    }

    // 添加分类
    @PostMapping
    public Result add(@RequestBody Meishifenlei meishifenlei) {
        boolean success = meishifenleiService.save(meishifenlei);
        return success ? Result.success() : Result.fail("添加失败");
    }

    // 修改分类
    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody Meishifenlei meishifenlei) {
        meishifenlei.setId(id);
        boolean success = meishifenleiService.updateById(meishifenlei);
        return success ? Result.success() : Result.fail("修改失败");
    }

    // 删除分类
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean success = meishifenleiService.removeById(id);
        return success ? Result.success() : Result.fail("删除失败");
    }

    // 获取分类详情
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Meishifenlei meishifenlei = meishifenleiService.getById(id);
        return meishifenlei != null ? Result.success(meishifenlei) : Result.fail("分类不存在");
    }
}
