package com.fish.travel.controller;

import com.fish.travel.Result.Result;
import com.fish.travel.entity.Liuyanban;
import com.fish.travel.service.ILiuyanbanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/travel/liuyanban")
public class LiuyanController {

    private static final Logger log = LoggerFactory.getLogger(LiuyanController.class);

    @Autowired
    private ILiuyanbanService liuyanbanService;

    // 获取留言列表
    @GetMapping("/list")
    public Result list() {
        List<Liuyanban> list = liuyanbanService.list();
        return Result.success(list);
    }

    // 添加留言
    @PostMapping
    public Result add(@RequestBody Liuyanban liuyanban) {
        try {
            log.info("接收到的留言数据: {}", liuyanban);
            
            // 数据校验
            if (liuyanban.getXingming() == null || liuyanban.getXingming().trim().isEmpty()) {
                return Result.fail("姓名不能为空");
            }
            if (liuyanban.getLianxidianhua() == null || liuyanban.getLianxidianhua().trim().isEmpty()) {
                return Result.fail("联系电话不能为空");
            }
            if (liuyanban.getLiuyanneirong() == null || liuyanban.getLiuyanneirong().trim().isEmpty()) {
                return Result.fail("留言内容不能为空");
            }

            // 设置创建时间（如果前端没有传递，后端也设置一下保险）
            if (liuyanban.getAddtime() == null) {
                liuyanban.setAddtime(LocalDateTime.now());
            }
            
            // 设置留言人（如果前端没有传递，使用姓名）
            if (liuyanban.getLiuyanren() == null || liuyanban.getLiuyanren().trim().isEmpty()) {
                liuyanban.setLiuyanren(liuyanban.getXingming());
            }
            
            // 保存留言
            boolean success = liuyanbanService.save(liuyanban);
            return success ? Result.success("留言成功") : Result.fail("留言失败");
            
        } catch (Exception e) {
            log.error("添加留言失败", e);
            return Result.fail("系统错误：" + e.getMessage());
        }
    }

    // 回复留言
    @PutMapping("/{id}")
    public Result reply(@PathVariable Integer id, @RequestBody Liuyanban liuyanban) {
        Liuyanban existingMessage = liuyanbanService.getById(id);
        if (existingMessage == null) {
            return Result.fail("留言不存在");
        }
        existingMessage.setLiuyanneirong(liuyanban.getLiuyanneirong());
        boolean success = liuyanbanService.updateById(existingMessage);
        return success ? Result.success("回复成功") : Result.fail("回复失败");
    }

    // 删除留言
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean success = liuyanbanService.removeById(id);
        return success ? Result.success("删除成功") : Result.fail("删除失败");
    }

    // 获取留言详情
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Liuyanban liuyan = liuyanbanService.getById(id);
        return liuyan != null ? Result.success(liuyan) : Result.fail("留言不存在");
    }
}
