package com.fish.travel.controller;

import com.fish.travel.entity.Jingdianxinxi;
import com.fish.travel.mapper.JingdianxinxiMapper;
import com.fish.travel.Result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/travel/jingdianxinxi")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class JingdianxinxiController {

    @Autowired
    private JingdianxinxiMapper jingdianxinxiMapper;

    // 获取景点列表
    @GetMapping("/list")
    public Result list() {
        // 获取景点列表（包含地区名称）
        List<Map<String, Object>> list = jingdianxinxiMapper.selectListWithDiqu();
        return Result.success(list);
    }

    // 添加景点
    @PostMapping
    public Result add(@RequestBody Jingdianxinxi jingdianxinxi) {
        // 生成景点编号
        String bianhao = "JD" + System.currentTimeMillis();
        jingdianxinxi.setJingdianbianhao(bianhao);

        // 设置默认浏览量
        jingdianxinxi.setLiulanliang(0);

        int rows = jingdianxinxiMapper.insert(jingdianxinxi);
        return rows > 0 ? Result.success() : Result.fail("添加失败");
    }

    // 修改景点
    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody Jingdianxinxi jingdianxinxi) {
        jingdianxinxi.setId(id);
        int rows = jingdianxinxiMapper.updateById(jingdianxinxi);
        return rows > 0 ? Result.success() : Result.fail("修改失败");
    }

    // 删除景点
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        int rows = jingdianxinxiMapper.deleteById(id);
        return rows > 0 ? Result.success() : Result.fail("删除失败");
    }

    // 获取景点详情
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Jingdianxinxi jingdianxinxi = jingdianxinxiMapper.selectById(id);
        return jingdianxinxi != null ? Result.success(jingdianxinxi) : Result.fail("景点不存在");
    }
}
