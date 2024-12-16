package com.fish.travel.controller;

import com.fish.travel.Result.Result;
import com.fish.travel.entity.Lvyouxianlu;
import com.fish.travel.service.ILvyouxianluService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 旅游线路 前端控制器
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
@RestController
@RequestMapping("/travel/lvyouxianlu")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class LvyouxianluController {

    @Autowired
    private ILvyouxianluService lvyouxianluService;

    // 获取线路列表
    @GetMapping("/list")
    public Result list() {
        return Result.success(lvyouxianluService.list());
    }

    // 添加线路
    @PostMapping
    public Result add(@RequestBody Lvyouxianlu lvyouxianlu) {
        // 生成线路编号
        lvyouxianlu.setXianlubianhao("XL" + System.currentTimeMillis());
        // 设置默认浏览量
        lvyouxianlu.setLiulanliang(0);
        
        boolean success = lvyouxianluService.save(lvyouxianlu);
        return success ? Result.success() : Result.fail("添加失败");
    }

    // 修改线路
    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody Lvyouxianlu lvyouxianlu) {
        lvyouxianlu.setId(id);
        boolean success = lvyouxianluService.updateById(lvyouxianlu);
        return success ? Result.success() : Result.fail("修改失败");
    }

    // 删除线路
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean success = lvyouxianluService.removeById(id);
        return success ? Result.success() : Result.fail("删除失败");
    }

    // 获取线路详情
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Lvyouxianlu lvyouxianlu = lvyouxianluService.getById(id);
        return lvyouxianlu != null ? Result.success(lvyouxianlu) : Result.fail("线路不存在");
    }
}
