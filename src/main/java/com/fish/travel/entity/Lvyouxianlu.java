package com.fish.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("lvyouxianlu")
public class Lvyouxianlu {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 线路编号
     */
    private String xianlubianhao;

    /**
     * 线路名称
     */
    private String xianlumingcheng;

    /**
     * 图片
     */
    private String tupian;

    /**
     * 出发地
     */
    private String chufadi;

    /**
     * 途经地
     */
    private String tujingdi;

    /**
     * 终点
     */
    private String zhongdian;

    /**
     * 价格
     */
    private BigDecimal jiage;

    /**
     * 浏览量
     */
    private Integer liulanliang;

    /**
     * 线路特色
     */
    private String xianlutese;

    /**
     * 线路简介
     */
    private String xianlujianjie;

    /**
     * 添加时间
     */
    private LocalDateTime addtime;
}
