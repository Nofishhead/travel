package com.fish.travel.controller;

import com.fish.travel.Result.Result;
import com.fish.travel.entity.Youqinglianjie;
import com.fish.travel.service.IYouqinglianjieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 友情链接 前端控制器
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
@RestController
@RequestMapping("/travel/youqinglianjie")
public class YouqinglianjieController {

    @Autowired
    private IYouqinglianjieService youqinglianjieService;

    // 获取友情链接列表
    @GetMapping("/list")
    public Result list() {
        return Result.success(youqinglianjieService.list());
    }

    // 添加友情链接
    @PostMapping
    public Result add(@RequestBody Youqinglianjie youqinglianjie) {
        youqinglianjie.setAddtime(LocalDateTime.now());
        boolean success = youqinglianjieService.save(youqinglianjie);
        return success ? Result.success() : Result.fail("添加失败");
    }

    // 修改友情链接
    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody Youqinglianjie youqinglianjie) {
        youqinglianjie.setId(id);
        boolean success = youqinglianjieService.updateById(youqinglianjie);
        return success ? Result.success() : Result.fail("修改失败");
    }

    // 删除友情链接
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean success = youqinglianjieService.removeById(id);
        return success ? Result.success() : Result.fail("删除失败");
    }
}
