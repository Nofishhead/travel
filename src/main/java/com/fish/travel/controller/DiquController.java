package com.fish.travel.controller;

import com.fish.travel.entity.Diqu;
import com.fish.travel.mapper.DiquMapper;
import com.fish.travel.Result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 地区 前端控制器
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
@RestController
@RequestMapping("/travel/diqu")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class DiquController {

    @Autowired
    private DiquMapper diquMapper;

    // 获取地区列表
    @GetMapping("/list")
    public Result list() {
        List<Diqu> list = diquMapper.selectList(null);
        return Result.success(list);
    }

    // 添加地区
    @PostMapping
    public Result add(@RequestBody Diqu diqu) {
        int rows = diquMapper.insert(diqu);
        return rows > 0 ? Result.success() : Result.fail("添加失败");
    }

    // 修改地区
    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody Diqu diqu) {
        diqu.setId(id);
        int rows = diquMapper.updateById(diqu);
        return rows > 0 ? Result.success() : Result.fail("修改失败");
    }

    // 删除地区
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        int rows = diquMapper.deleteById(id);
        return rows > 0 ? Result.success() : Result.fail("删除失败");
    }

    // 获取地区详情
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Diqu diqu = diquMapper.selectById(id);
        return diqu != null ? Result.success(diqu) : Result.fail("地区不存在");
    }
}
