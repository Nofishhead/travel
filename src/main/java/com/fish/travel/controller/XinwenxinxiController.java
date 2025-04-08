package com.fish.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    // 获取新闻列表（带分页和搜索）
    @GetMapping("/list")
    public Result list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category
    ) {
        try {
            Page<Xinwenxinxi> pageInfo = new Page<>(page, pageSize);
            LambdaQueryWrapper<Xinwenxinxi> wrapper = new LambdaQueryWrapper<>();

            // 添加搜索条件
            if (keyword != null && !keyword.trim().isEmpty()) {
                wrapper.like(Xinwenxinxi::getBiaoti, keyword)
                        .or()
                        .like(Xinwenxinxi::getNeirong, keyword);
            }

            // 修改分类条件
            if (category != null && !category.trim().isEmpty()) {
                wrapper.eq(Xinwenxinxi::getFenlei, Integer.valueOf(category));
            }

            // 按添加时间倒序排序
            wrapper.orderByDesc(Xinwenxinxi::getAddtime);

            Page<Xinwenxinxi> result = xinwenxinxiService.page(pageInfo, wrapper);
            return Result.success(result);
        } catch (Exception e) {
            return Result.fail("获取新闻列表失败：" + e.getMessage());
        }
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
        try {
            Xinwenxinxi news = xinwenxinxiService.getById(id);
            if (news != null) {
                // 更新点击率
                news.setDianjilv(news.getDianjilv() + 1);
                xinwenxinxiService.updateById(news);
                return Result.success(news);
            }
            return Result.fail("新闻不存在");
        } catch (Exception e) {
            return Result.fail("获取新闻详情失败：" + e.getMessage());
        }
    }
}
