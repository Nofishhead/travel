package com.fish.travel.controller;

import com.fish.travel.Result.Result;
import com.fish.travel.entity.Xinwenxinxi;
import com.fish.travel.service.IXinwenxinxiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/travel/xinwenxinxi")
public class XinwenxinxiController {

    @Autowired
    private IXinwenxinxiService xinwenxinxiService;

    // 获取新闻列表
    @GetMapping("/list")
    public Result list() {
        return Result.success(xinwenxinxiService.list());
    }

    // 添加新闻
    @PostMapping
    public Result add(@RequestBody Xinwenxinxi xinwenxinxi) {
        xinwenxinxi.setAddtime(LocalDateTime.now());
        xinwenxinxi.setDianjilv(0);
        boolean success = xinwenxinxiService.save(xinwenxinxi);
        return success ? Result.success() : Result.fail("添加失败");
    }

    // 修改新闻
    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody Xinwenxinxi xinwenxinxi) {
        xinwenxinxi.setId(id);
        boolean success = xinwenxinxiService.updateById(xinwenxinxi);
        return success ? Result.success() : Result.fail("修改失败");
    }

    // 删除新闻
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean success = xinwenxinxiService.removeById(id);
        return success ? Result.success() : Result.fail("删除失败");
    }

    // 获取新闻详情
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Xinwenxinxi xinwenxinxi = xinwenxinxiService.getById(id);
        if (xinwenxinxi != null) {
            // 增加点击率
            xinwenxinxi.setDianjilv(xinwenxinxi.getDianjilv() + 1);
            xinwenxinxiService.updateById(xinwenxinxi);
            return Result.success(xinwenxinxi);
        }
        return Result.fail("新闻不存在");
    }
}
