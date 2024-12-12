package com.fish.travel.controller;

import com.fish.travel.Result.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/travel/upload")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class UploadController {

    private final String UPLOAD_PATH = "uploads/";

    @PostMapping
    public Result uploadFile(@RequestParam("file") MultipartFile file, 
                           @RequestParam("type") String type) {
        try {
            // 获取文件名
            String originalFilename = file.getOriginalFilename();
            // 获取文件后缀
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 生成新文件名
            String fileName = UUID.randomUUID().toString() + suffix;
            // 确定存储目录
            String directory = UPLOAD_PATH + type + "/";
            // 创建目录
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 存储文件
            File dest = new File(directory + fileName);
            file.transferTo(dest);
            // 返回文件访问路径
            String filePath = "/uploads/" + type + "/" + fileName;
            return Result.success(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail("文件上传失败");
        }
    }
} 