package com.fish.travel.controller;

import com.fish.travel.Result.Result;
import com.fish.travel.entity.Difangmeishi;
import com.fish.travel.service.IDifangmeishiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/travel/difangmeishi")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class DifangmeishiController {

    @Autowired
    private IDifangmeishiService difangmeishiService;

    // 获取美食列表
    @GetMapping("/list")
    public Result list() {
        return Result.success(difangmeishiService.list());
    }

    // 添加美食
    @PostMapping
    public Result add(@RequestBody Difangmeishi difangmeishi) {
        // 生成美食编号
        difangmeishi.setMeishibianhao("MS" + System.currentTimeMillis());
        boolean success = difangmeishiService.save(difangmeishi);
        return success ? Result.success() : Result.fail("添加失败");
    }

    // 修改美食
    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody Difangmeishi difangmeishi) {
        difangmeishi.setId(id);
        boolean success = difangmeishiService.updateById(difangmeishi);
        return success ? Result.success() : Result.fail("修改失败");
    }

    // 删除美食
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean success = difangmeishiService.removeById(id);
        return success ? Result.success() : Result.fail("删除失败");
    }

    // 获取美食详情
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Difangmeishi difangmeishi = difangmeishiService.getById(id);
        return difangmeishi != null ? Result.success(difangmeishi) : Result.fail("美食不存在");
    }
}
