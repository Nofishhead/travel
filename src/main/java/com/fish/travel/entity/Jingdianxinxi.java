package com.fish.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("jingdianxinxi")
public class Jingdianxinxi {
    
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String jingdianbianhao;
    private String jingdianmingcheng;
    private Integer suoshudiqu;
    private String tupian;
    private String kaifangshijian;
    private String dizhi;
    private Double piaojia;
    private Integer liulanliang;
    private String miaoshu;
}