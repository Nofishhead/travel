package com.fish.travel.controller;

import com.fish.travel.Result.Result;
import com.fish.travel.entity.Yuding;
import com.fish.travel.service.IYudingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 预定 前端控制器
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
@RestController
@RequestMapping("/travel/yuding")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class YudingController {

    @Autowired
    private IYudingService yudingService;

    // 获取预定列表
    @GetMapping("/list")
    public Result list() {
        return Result.success(yudingService.list());
    }

    // 添加预定
    @PostMapping
    public Result add(@RequestBody Yuding yuding) {
        // 生成订单号
        yuding.setDingdanhao("DD" + System.currentTimeMillis());
        // 设置默认状态
        yuding.setZhuangtai("待确认");
        // 设置是否支付
        yuding.setIszf("否");
        // 设置添加时间
        yuding.setAddtime(LocalDateTime.now());

        boolean success = yudingService.save(yuding);
        return success ? Result.success() : Result.fail("预定失败");
    }

    // 修改预定
    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody Yuding yuding) {
        yuding.setId(id);
        boolean success = yudingService.updateById(yuding);
        return success ? Result.success() : Result.fail("修改失败");
    }

    // 删除预定
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean success = yudingService.removeById(id);
        return success ? Result.success() : Result.fail("删除失败");
    }

    // 获取预定详情
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Yuding yuding = yudingService.getById(id);
        return yuding != null ? Result.success(yuding) : Result.fail("预定不存在");
    }

    // 更新预定状态
    @PutMapping("/status/{id}")
    public Result updateStatus(@PathVariable Integer id, @RequestParam String status) {
        Yuding yuding = new Yuding();
        yuding.setId(id);
        yuding.setZhuangtai(status);
        boolean success = yudingService.updateById(yuding);
        return success ? Result.success() : Result.fail("状态更新失败");
    }

    // 更新支付状态
    @PutMapping("/pay/{id}")
    public Result updatePayStatus(@PathVariable Integer id) {
        Yuding yuding = new Yuding();
        yuding.setId(id);
        yuding.setIszf("是");
        boolean success = yudingService.updateById(yuding);
        return success ? Result.success() : Result.fail("支付状态更新失败");
    }
}
