package com.fish.travel.controller;

import com.fish.travel.Result.Result;
import com.fish.travel.entity.Liuyanban;
import com.fish.travel.service.ILiuyanbanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/travel/liuyanban")
public class LiuyanController {

    @Autowired
    private ILiuyanbanService liuyanService;

    // 获取留言列表
    @GetMapping("/list")
    public Result list() {
        return Result.success(liuyanService.list());
    }

    // 添加留言
    @PostMapping
    public Result add(@RequestBody Liuyanban liuyan) {
        liuyan.setAddtime(LocalDateTime.now());
        boolean success = liuyanService.save(liuyan);
        return success ? Result.success() : Result.fail("添加失败");
    }

    // 回复留言
    @PutMapping("/reply/{id}")
    public Result reply(@PathVariable Integer id, @RequestBody Liuyanban liuyan) {
        Liuyanban existLiuyan = liuyanService.getById(id);
        if (existLiuyan == null) {
            return Result.fail("留言不存在");
        }

        // 更新回复内容
        existLiuyan.setHuifuneirong(liuyan.getHuifuneirong());

        boolean success = liuyanService.updateById(existLiuyan);
        return success ? Result.success() : Result.fail("回复失败");
    }

    // 删除留言
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean success = liuyanService.removeById(id);
        return success ? Result.success() : Result.fail("删除失败");
    }

    // 获取留言详情
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Liuyanban liuyan = liuyanService.getById(id);
        return liuyan != null ? Result.success(liuyan) : Result.fail("留言不存在");
    }
}
