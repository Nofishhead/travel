package com.fish.travel;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.util.Collections;
import java.util.List;

@SpringBootTest
public class ObwikiApplicationTests {


    @Test
    public void contextLoads() {
        String url = "jdbc:mysql://localhost:3306/travel?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai";

        FastAutoGenerator.create(url, "root", "q123456")
                .globalConfig(builder -> {
                    builder.author("fish") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("C://Users//余治桦//Desktop//毕业设计//选题审批表//项目源码//代码生成器"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.fish") // 设置父包名
                            .moduleName("travel") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "C://Users//余治桦//Desktop//毕业设计//选题审批表//项目源码//代码生成器")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("admins,difangmeishi,diqu,jingdianxinxi,liuyanban,lunbotu,lvyouxianlu,meishifenlei,shoucangjilu,xinwenfenlei,xinwenxinxi,yonghu,youqinglianjie,yuding"); // 设置需要生成的表名
                    //.addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                //.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }




}
