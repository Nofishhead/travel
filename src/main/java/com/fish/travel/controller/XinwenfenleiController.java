package com.fish.travel.controller;

import com.fish.travel.Result.Result;
import com.fish.travel.entity.Xinwenfenlei;
import com.fish.travel.service.IXinwenfenleiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/travel/xinwenfenlei")
public class XinwenfenleiController {

    @Autowired
    private IXinwenfenleiService xinwenfenleiService;

    // 获取分类列表
    @GetMapping("/list")
    public Result list() {
        return Result.success(xinwenfenleiService.list());
    }

    // 添加分类
    @PostMapping
    public Result add(@RequestBody Xinwenfenlei xinwenfenlei) {
        xinwenfenlei.setAddtime(LocalDateTime.now());
        boolean success = xinwenfenleiService.save(xinwenfenlei);
        return success ? Result.success() : Result.fail("添加失败");
    }

    // 修改分类
    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody Xinwenfenlei xinwenfenlei) {
        xinwenfenlei.setId(id);
        boolean success = xinwenfenleiService.updateById(xinwenfenlei);
        return success ? Result.success() : Result.fail("修改失败");
    }

    // 删除分类
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean success = xinwenfenleiService.removeById(id);
        return success ? Result.success() : Result.fail("删除失败");
    }

    // 获取分类详情
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.success(xinwenfenleiService.getById(id));
    }
}
