package com.fish.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 美食分类
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
@Data
public class Meishifenlei implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分类名称
     */
    private String fenleimingcheng;

    /**
     * 添加时间
     */
    private LocalDateTime addtime;
}
