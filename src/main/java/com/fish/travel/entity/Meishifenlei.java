package com.fish.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 美食分类
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFenleimingcheng() {
        return fenleimingcheng;
    }

    public void setFenleimingcheng(String fenleimingcheng) {
        this.fenleimingcheng = fenleimingcheng;
    }

    public LocalDateTime getAddtime() {
        return addtime;
    }

    public void setAddtime(LocalDateTime addtime) {
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "Meishifenlei{" +
        "id=" + id +
        ", fenleimingcheng=" + fenleimingcheng +
        ", addtime=" + addtime +
        "}";
    }
}
