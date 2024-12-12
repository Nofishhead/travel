package com.fish.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("diqu")
public class Diqu {
    
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String diqumingcheng;
    private String miaoshu;
}
